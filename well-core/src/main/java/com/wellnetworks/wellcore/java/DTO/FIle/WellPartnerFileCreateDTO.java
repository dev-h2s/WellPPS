package com.wellnetworks.wellcore.java.dto.FIle;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class WellPartnerFileCreateDTO {
    @JsonProperty("file_idx")
    private String fileIdx;
    @JsonProperty("p_idx")
    private String partnerIdx;
}
