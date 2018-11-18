package com.convert.object2excel.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: wyy
 * @Date: 18-11-15 下午2:54
 */
public class ComponentProcessDTO {

    private String code;
    private String name;
    private String description;
    private List<ProcessDTO> processDTOList = new ArrayList<>();
    private String componentCode;

    public ComponentProcessDTO() {
    }

    public ComponentProcessDTO(String code, String name, String description, List<ProcessDTO> processDTOList, String componentCode) {
        this.code = code;
        this.name = name;
        this.description = description;
        this.processDTOList = processDTOList;
        this.componentCode = componentCode;
    }

    @Override
    public String toString() {
        return "ComponentProcessDTO{" +
                "code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", processDTOList=" + processDTOList +
                ", componentCode='" + componentCode + '\'' +
                '}';
    }

    public String getCode() {
        return code;
    }

    public ComponentProcessDTO setCode(String code) {
        this.code = code;
        return this;
    }

    public String getName() {
        return name;
    }

    public ComponentProcessDTO setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public ComponentProcessDTO setDescription(String description) {
        this.description = description;
        return this;
    }

    public List<ProcessDTO> getProcessDTOList() {
        return processDTOList;
    }

    public ComponentProcessDTO setProcessDTOList(List<ProcessDTO> processDTOList) {
        this.processDTOList = processDTOList;
        return this;
    }

    public String getComponentCode() {
        return componentCode;
    }

    public ComponentProcessDTO setComponentCode(String componentCode) {
        this.componentCode = componentCode;
        return this;
    }
}

