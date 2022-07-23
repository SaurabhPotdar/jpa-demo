package com.tce.dto;

import org.springframework.beans.factory.annotation.Value;

public interface EmployeeView {

    @Value("#{target.firstName + ' ' + target.lastName}")
    String getFullName();
    String getDesignation();

}
