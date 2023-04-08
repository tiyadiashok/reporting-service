package com.jpm.cfs.reportinguirest.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ClientConnectionRequest {

    private String clientName;
    private String secretKey;
}
