package com.joe.nfortics;



public class OperationResult {

    private String error = "";
    private boolean successful = true;

    public OperationResult(String error, boolean successful) {
        this.error = error;
        this.successful = successful;
    }

    public String getError() {
        return error;
    }

    public boolean isSuccessful() {
        return successful;
    }
}
