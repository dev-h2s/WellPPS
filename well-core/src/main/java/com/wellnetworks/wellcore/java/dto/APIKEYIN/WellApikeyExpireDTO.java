package com.wellnetworks.wellcore.java.dto.APIKEYIN;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;


@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Validated
public class WellApikeyExpireDTO {
    private String apiKeyInIdx;
}
