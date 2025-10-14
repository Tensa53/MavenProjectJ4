package org.example.runners;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.*;

public class AverageSingleShotTime {
    public static void main(String[] args) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        File dir = new File(args[0]);
        String outputPath = args[1];

        File[] subDirs = dir.listFiles();

        for (File subDir : subDirs) {
            if (subDir.isDirectory()) {
                for (File file : subDir.listFiles()) {
                    JsonNode jsonNode =  objectMapper.readTree(file);

                    if (jsonNode.get(1) != null){
                        for(JsonNode arrayNode : jsonNode) {
                            writeJMHTimes(arrayNode);
                        }
                    } else {
                        writeJMHTimes(jsonNode.get(0));
                    }
                }

            }
        }

        System.out.println(map);
        System.out.println(map.size());

        ObjectMapper objectMapper2 = new ObjectMapper();
        objectMapper2.writerWithDefaultPrettyPrinter().writeValue(new FileWriter(outputPath), map);
    }

    private static void writeJMHTimes(JsonNode jsonNode) throws IOException {
        JsonNode benchmark = jsonNode.get("benchmark");

        JsonNode params = jsonNode.get("params");

        StringBuilder benchName = new StringBuilder();

        benchName.append(benchmark.asText());

        StringBuilder benchParams = new StringBuilder();
        if (params != null) {
            benchParams.append("#");
            params.fields().forEachRemaining(entry -> {
                String key = entry.getKey();
                String value = entry.getValue().asText();
                benchParams.append(key).append("=").append(value).append("_");
            });

            benchParams.deleteCharAt(benchParams.lastIndexOf("_"));
            benchName.append(benchParams.toString());
        }

        JsonNode metrics = jsonNode.get("primaryMetric");

        JsonNode raw = metrics.get("rawData");

        ArrayList<BigDecimal> rawData = new ArrayList<>();

        for (JsonNode node : raw) {
            for (JsonNode node2 : node) {
                rawData.add(new BigDecimal(node2.toString()));
            }
        }

        BigDecimal sum = BigDecimal.ZERO;

        for (BigDecimal data: rawData) {
            sum = sum.add(data);
        }

        JsonNode score = metrics.get("score");
        BigDecimal scoreB = new BigDecimal(score.toString(), new MathContext(5));
        BigDecimal average = sum.divide(BigDecimal.valueOf(rawData.size()), new MathContext(5));
        map.put(benchName.toString(), average);
    }

    private  static TreeMap<String, BigDecimal> map = new TreeMap<>();
}
