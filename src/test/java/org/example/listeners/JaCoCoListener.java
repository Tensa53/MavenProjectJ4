package org.example.listeners;

import org.junit.runner.Description;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunListener;

import static org.example.benchmarks.JaCoCoCoverageMatrix.updateCoverageMatrix;

public class JaCoCoListener extends RunListener {

    @Override
    public void testStarted(Description description) throws Exception {
        System.out.println("Started: " + getTestRun(description.getDisplayName()) + description.getMethodName());
    }

    @Override
    public void testFailure(Failure failure) throws Exception {
        Description description = failure.getDescription();
        System.out.println("Failed: " + getTestRun(description.getDisplayName()) + description.getMethodName());
    }

    @Override
    public void testFinished(Description description) throws Exception {
        System.out.println("Finished: " + getTestRun(description.getDisplayName()) + description.getMethodName());
        updateCoverageMatrix(description.getMethodName(), description.getClassName());
    }

    private String getTestRun(String displayName) {
        int i1 = displayName.indexOf("[");
        int i2 = displayName.lastIndexOf("]");

        if (i1 != -1 && i2 != -1) {
            return displayName.substring(i1, i2 + 1) + " ";
        } else {
            return "";
        }
    }
}
