package com.wellnetworks.wellcore.java.dto.FIle;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
public class WellFileDetailDTO {
    private Long fileId;
    private String originFileName;
    private String fileKind;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WellFileDetailDTO that = (WellFileDetailDTO) o;
        return Objects.equals(fileKind, that.fileKind);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fileKind);
    }
}
