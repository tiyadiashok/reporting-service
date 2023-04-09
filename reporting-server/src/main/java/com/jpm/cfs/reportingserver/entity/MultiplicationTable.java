package com.jpm.cfs.reportingserver.entity;

import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@ToString
@Table("multiplication_table")
public class MultiplicationTable {

    @Id
    private Integer id;
    private int number;
    private int multiplier;
    private int result;
}
