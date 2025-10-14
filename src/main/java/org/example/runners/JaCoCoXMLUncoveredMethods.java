package org.example.runners;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ConstructorDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class JaCoCoXMLUncoveredMethods {

    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException {

        String xmlPath = args[0];
        String outputPath = args[1];

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
        DocumentBuilder builder = dbf.newDocumentBuilder();
        Document doc = builder.parse(new File(xmlPath));
        NodeList classNodes = doc.getElementsByTagName("class");

        TreeMap<String, Set<String>> uncoveredMethods = new TreeMap<>();

        for (int i = 0; i < classNodes.getLength(); i++) {
            Node node = classNodes.item(i);
            String className = node.getAttributes().getNamedItem("name").getNodeValue();
            String classDottedName = className.replace("/",".");
            System.out.println("Class: " + classDottedName);
            NodeList classNodeChildNodes = node.getChildNodes();
            Set<String> methods = new HashSet<>();
            for (int j = 0; j < classNodeChildNodes.getLength(); j++) {
                Node childNode = classNodeChildNodes.item(j);
                if (childNode.getNodeName().equals("method")) {
                    String methodName = childNode.getAttributes().getNamedItem("name").getNodeValue();
                    String descriptor = childNode.getAttributes().getNamedItem("desc").getNodeValue();
                    int methodLine = 0;
                    if (methodName.equals("<init>")) {
                        methodLine = Integer.parseInt(childNode.getAttributes().getNamedItem("line").getNodeValue());
                    } else {
                        methodLine = Integer.parseInt(childNode.getAttributes().getNamedItem("line").getNodeValue()) - 1;
                    }
//                    String signature = getMethodSignature(descriptor);
                    String signature = getMethodSignatureParsed(methodLine, className);
//                    System.out.println("Signature for method " + methodName + ": " + signature);
                    String fullMethodName = "";
                    if (methodName.equals("<init>")) {
                        fullMethodName = classDottedName + classDottedName.substring(classDottedName.lastIndexOf(".")) + "(" + signature + ")";
                    } else {
                        fullMethodName = classDottedName + "." + methodName + "(" + signature + ")";
                    }
                    Node methodCounterNode = childNode.getLastChild();
                    Integer counter = Integer.valueOf(methodCounterNode.getAttributes().getNamedItem("missed").getNodeValue());
                    if (counter > 0) {
                        System.out.println("Uncovered: " + fullMethodName);
                        methods.add(fullMethodName);
                    }
                }

            }

            uncoveredMethods.put(classDottedName, methods);

        }

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writerWithDefaultPrettyPrinter().writeValue(new FileWriter(outputPath), uncoveredMethods);

    }

    private static String getMethodSignatureParsed(int methodLine, String className) {
        String classSourceDir = "src/main/java/";

        String classPath = classSourceDir + className +  ".java";

//        System.out.println("classPath: " + classPath);

        CompilationUnit cu = null;
        try {
            cu = StaticJavaParser.parse(Files.newInputStream(Paths.get(classPath)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        StringBuilder methodSignature = new StringBuilder();

        cu.findAll(MethodDeclaration.class).forEach((methodDeclaration) -> {
            methodDeclaration.getBegin().ifPresent(begin -> {
                if (begin.line == methodLine) {
                    methodDeclaration.getParameters().forEach(p -> {
                        methodSignature.append(p.getType()).append(",");
                    });
                }
            });
        });

        cu.findAll(ConstructorDeclaration.class).forEach(constructorDeclaration -> {
            constructorDeclaration.getBegin().ifPresent(begin -> {
                if (begin.line == methodLine) {
                    constructorDeclaration.getParameters().forEach(p -> {
                        methodSignature.append(p.getType()).append(",");
                    });
                }
            });
        });

        int lastColumn = methodSignature.lastIndexOf(",");

        if (lastColumn != -1) {
            String sig = methodSignature.substring(0,lastColumn);
//            System.out.println("Signature:" + sig);
            return sig;
        } else {
//            System.out.println("Signature:" + signature.toString());

            return methodSignature.toString();
        }
    }

    private static String getMethodSignature(String methodDescriptor) {

        //TODO Vedi i generics

        String parameters = methodDescriptor.substring(methodDescriptor.indexOf("(") + 1, methodDescriptor.indexOf(")"));

        String array = "[]";

        int i = 0;

        int len = parameters.length();

        StringBuilder signature = new StringBuilder();

        while (i < len) {
            String typeName;
            //squares to know the new position for substring: 1 in case of array, 2 in case of matrix
            int squares = 0;
            //skip to know the new position for substring: 2 in case of class instance ("type letter" and semicolon)
            int skip = 2;
            char character = parameters.charAt(i);

            if (character == '[') {
                if (parameters.charAt(i + 1) == '[') {
                    squares = 2;
                    typeName = getTypeNamebyDescriptor(parameters.substring(i + squares));
                    signature.append(typeName).append(array).append(array).append(",");
                    if (parameters.charAt(i + squares) == 'L') {
                        //in case of a class, other than the letter, the descriptor tells also the name of the class
                        i += squares + skip + typeName.length();
                    } else {
                        i += squares + 1;
                    }
                } else {
                    squares = 1;
                    typeName = getTypeNamebyDescriptor(parameters.substring(i + 1));
                    signature.append(typeName).append(array).append(",");
                    if (parameters.charAt(i + squares) == 'L') {
                        i += squares + skip + typeName.length();
                    } else {
                        i += squares + 1;
                    }
                }
            } else {
                typeName = getTypeNamebyDescriptor(parameters.substring(i));
                signature.append(typeName).append(",");
                if (parameters.charAt(i) == 'L') {
                    i += skip + typeName.length();
                } else {
                    i += 1;
                }
            }
        }

        int lastColumn = signature.lastIndexOf(",");

        if (lastColumn != -1) {
            String sig = signature.substring(0,lastColumn);
//            System.out.println("Signature:" + sig);
            return sig;
        } else {
//            System.out.println("Signature:" + signature.toString());

            return signature.toString();
        }

    }

    private static String getTypeNamebyDescriptor(String desc) {
        String name = "";

        switch (desc.charAt(0)) {
            case 'B':
                name = "byte";
                break;
            case 'C':
                name = "char";
                break;
            case 'D':
                name = "double";
                break;
            case 'F':
                name = "float";
                break;
            case 'I':
                name = "int";
                break;
            case 'J':
                name = "long";
                break;
            case 'S':
                name = "short";
                break;
            case 'Z':
                name = "boolean";
                break;
            case 'L':
                String s = desc.substring(1,desc.indexOf(";"));
                name = s.replace("/",".");
        }

        return name;
    }
}
