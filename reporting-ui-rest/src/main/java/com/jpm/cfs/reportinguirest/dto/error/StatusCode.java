package com.jpm.cfs.reportinguirest.dto.error;

public enum StatusCode {

    EC001("Invalid request"),
    EC002("No response found"),
    EC003("Server Unavailable");

    private final String description;

    StatusCode(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
