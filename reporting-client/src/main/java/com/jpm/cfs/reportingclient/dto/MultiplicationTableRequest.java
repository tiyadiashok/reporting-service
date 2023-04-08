package com.jpm.cfs.reportingclient.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor(staticName = "create")
public class MultiplicationTableRequest {

    private int number;
    private int limit;
}
