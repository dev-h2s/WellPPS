package com.wellnetworks.wellcore.java.dto.FIle;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class WellFileDetailDTO {
    private Long fileId;
    private String originFileName;
    private String fileKind;


}
