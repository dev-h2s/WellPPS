package com.wellnetworks.wellcore.java.dto.FIle;

import com.wellnetworks.wellcore.java.domain.partner.WellPartnerEntity;
import lombok.Data;

@Data
public class WellPartnerFileStorageDTO {
    private String fileIdx;
    private String partnerIdx;

    public WellPartnerFileStorageDTO(WellFIleStorageDTO file, WellPartnerEntity partner) {
        this.fileIdx = file.getFileIdx();
        this.partnerIdx = partner.getPartnerIdx();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        WellPartnerFileStorageDTO other = (WellPartnerFileStorageDTO) obj;

        return fileIdx != null && fileIdx.equals(other.fileIdx) &&
                partnerIdx != null && partnerIdx.equals(other.partnerIdx);
    }
}
