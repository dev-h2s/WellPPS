package com.wellnetworks.wellcore.java.dto.Charge;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class WellChargeInsertDTO {
    @NotNull
    private String apiKey;
    @NotNull
    private String phone;
}
