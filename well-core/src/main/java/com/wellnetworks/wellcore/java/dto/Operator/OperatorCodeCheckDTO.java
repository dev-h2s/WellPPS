package com.wellnetworks.wellcore.java.dto.Operator;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
public class OperatorCodeCheckDTO {
    private String operatorCode;
}
