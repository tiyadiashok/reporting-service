package com.jpm.cfs.reportingserver.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ClientConnectionRequest {

    private String clientName;
    private String secretKey;
}
