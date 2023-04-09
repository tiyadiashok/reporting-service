package com.jpm.cfs.reportingserver.uitl;

import com.jpm.cfs.reportingserver.dto.MultiplicationTableDto;
import com.jpm.cfs.reportingserver.dto.MultiplicationTableResponse;
import com.jpm.cfs.reportingserver.entity.MultiplicationTable;
import org.springframework.beans.BeanUtils;

public class EntityDtoUtil {

    public static MultiplicationTableDto toDto(MultiplicationTable multiplicationTable) {
        MultiplicationTableDto dto = new MultiplicationTableDto();
        BeanUtils.copyProperties(multiplicationTable, dto);
        return dto;
    }

    public static MultiplicationTableResponse toResponse(MultiplicationTable multiplicationTable) {
        MultiplicationTableResponse response = new MultiplicationTableResponse();
        BeanUtils.copyProperties(multiplicationTable, response);
        return response;
    }

    public static MultiplicationTable toEntity(MultiplicationTableDto multiplicationTableDto) {
        MultiplicationTable entity = new MultiplicationTable();
        BeanUtils.copyProperties(multiplicationTableDto, entity);
        return entity;
    }

    public static MultiplicationTable toEntity(MultiplicationTableResponse multiplicationTableResponse) {
        MultiplicationTable entity = new MultiplicationTable();
        BeanUtils.copyProperties(multiplicationTableResponse, entity);
        return entity;
    }

    public static MultiplicationTableResponse toResponse(MultiplicationTableDto multiplicationTableDto) {
        MultiplicationTableResponse response = new MultiplicationTableResponse();
        BeanUtils.copyProperties(multiplicationTableDto, response);
        return response;
    }
}
