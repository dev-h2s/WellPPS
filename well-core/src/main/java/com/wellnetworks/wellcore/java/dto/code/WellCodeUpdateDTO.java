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


    public WellCodeUpdateDTO(String name) {
        this.name = name;
    }
}
