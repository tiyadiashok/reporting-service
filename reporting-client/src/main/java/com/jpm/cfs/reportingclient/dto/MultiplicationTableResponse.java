package com.jpm.cfs.reportingclient.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor(staticName = "create")
public class MultiplicationTableResponse {

        private int number;
        private int multiplier;
        private int result;
}
