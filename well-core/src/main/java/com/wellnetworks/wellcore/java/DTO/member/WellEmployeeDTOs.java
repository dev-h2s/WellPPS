package com.wellnetworks.wellcore.java.DTO.member;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.ZonedDateTime;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)

public class WellUserDTOs  {

    @JsonProperty("idx")
    public String idx;

    @JsonProperty("uid")
    public String userID;

    @JsonProperty("gidx")
    public String groupPermissionIdx;

    @JsonProperty("pkey")
    public List<String> permissionsKeysStringList;

    @JsonProperty("pwdh")
    public String passwordHash;

    @JsonProperty("tpwd")
    public String temporaryPassword;

    @JsonProperty("tpwx")
    public ZonedDateTime temporaryPasswordExpire;

    @JsonProperty("tpwc")
    public Byte temporaryPasswordCreateCount;

    @JsonProperty("tpwt")
    public ZonedDateTime temporaryPasswordCreateDatetime;

    // Constructor directly initializing fields without setters
    public WellUserDTO(String idx,
                       String userID,
                       String groupPermissionIdx,
                       List<String> permissionsKeysStringList,
                       String passwordHash,
                       String temporaryPassword,
                       ZonedDateTime temporaryPasswordExpire,
                       Byte temporaryPasswordCreateCount,
                       ZonedDateTime temporaryPasswordCreateDatetime,
                       ZonedDateTime modifyDatetime,
                       ZonedDateTime registerDatetime) {
        super(modifyDatetime, registerDatetime);
        this.idx = idx;
        this.userID = userID;
        this.groupPermissionIdx = groupPermissionIdx;
        this.permissionsKeysStringList = permissionsKeysStringList;
        this.passwordHash = passwordHash;
        this.temporaryPassword = temporaryPassword;
        this.temporaryPasswordExpire = temporaryPasswordExpire;
        this.temporaryPasswordCreateCount = temporaryPasswordCreateCount;
        this.temporaryPasswordCreateDatetime = temporaryPasswordCreateDatetime;
    }

    // Overriding equals and hashCode to ensure correct behavior when objects are used in collections
    @Override
    public int hashCode() {
        return Objects.hash(idx);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || Hibernate.getClass(this) != Hibernate.getClass(obj)) return false;
        WellUserDTO other = (WellUserDTO) obj;
        return idx != null && idx.equalsIgnoreCase(other.idx);
    }
}
