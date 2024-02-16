package com.wellnetworks.wellcore.java.dto.code;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class WellCodeUpdateDTO {
    @Schema(description = "코드명", example = "신한은행, 국민은행...")
    private String name;

    @Schema(description = "정렬 순서", example = "1, 2...")
    private Long sort;

    public WellCodeUpdateDTO(String name, Long sort) {
        this.name = name;
        this.sort = sort;
    }
}
