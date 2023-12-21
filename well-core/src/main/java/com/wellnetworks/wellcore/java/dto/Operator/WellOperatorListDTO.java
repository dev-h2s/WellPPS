package com.wellnetworks.wellcore.java.dto.Operator;

import com.wellnetworks.wellcore.java.dto.Product.WellProductListDTO;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class WellOperatorListDTO {

    private String operatorIdx;

    private String operatorName; //통신사명

    private List<WellProductListDTO> products; //요금제 리스트
}
