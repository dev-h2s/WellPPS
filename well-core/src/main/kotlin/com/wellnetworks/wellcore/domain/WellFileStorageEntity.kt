package com.wellnetworks.wellcore.domain

import com.wellnetworks.wellcore.domain.converter.ListToStringConverter
import com.wellnetworks.wellcore.domain.dto.WellFileStorageDTO
import org.hibernate.Hibernate
import java.util.UUID
import javax.persistence.*

@Entity
@Table(name = "file_tb", indexes = [
    Index(name = "IX_tid", columnList = "tid ASC", unique = false),
])
data class WellFileStorageEntity (
    // 파일 이름은 UUID 를 사용.
    @Id
    @Column(name = "idx", unique = true, nullable = false)
    var idx: UUID,

    // 테이블 ID와 날자는 파일의 물리적 경로에 사용. (ex: /{tableID}/{cym}/{idx})
    @Column(name="tbl_id", length = 16, nullable = false)
    var tableID: String,

    @Column(name="wrt", nullable = false)
    var writer: UUID,

    // 데이터는 YYYYMM 으로 숫자만 등록
    @Column(name="cym", length = 6, nullable = false)
    var createYYYYMM: String,

    // 파일의 실제 이름
    @Column(name="fname", length = 255, nullable = false)
    var fileName: String,

    @Column(name="fdesc", length = 255, nullable = true)
    var fileDescription: String?,

    // 물리 파일이 삭제되어 없을 경우.
    @Column(name="isunlink", )
    var isLinkError: Boolean,

    @Column(name="ispub")
    var isPublic: Boolean,

    @Column(name = "permissions")
    @Convert(converter = ListToStringConverter::class)
    var permissionsKeysStringList: List<String>?,

    @Column(name="fsize", nullable = false)
    var fileSize: Long,

    // 파일의 확장자. (소문자로 캐스팅)
    @Column(name="fext", nullable = true)
    var fileExtension: String?,

    @Column(name="dcnt", nullable = false)
    var fileDownloadCount: Int,

): BaseEntity() {
    fun getWellFileStorageDTO(): WellFileStorageDTO {
        return WellFileStorageDTO(
            Idx = this.idx,
            TableID = this.tableID,
            Writer = this.writer,
            Create_YYYYMM = this.createYYYYMM,
            FileName = this.fileName,
            FileDescription = this.fileDescription,
            isLinkError = this.isLinkError,
            isPublic = this.isPublic,
            PermissionsKeysStringList = this.permissionsKeysStringList,
            FileSize = this.fileSize,
            FileExtension = this.fileExtension,
            FileDownloadCount = this.fileDownloadCount,
            Modify_Datetime = this.modifyDatetime,
            Register_Datetime = this.registerDatetime,
        )
    }

    override fun equals(other: Any?): Boolean {
        if (this == other) return true;
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false;
        other as WellFileStorageEntity

        return idx != null && idx == other.idx;
    }

    override fun hashCode(): Int {
        return idx.hashCode();
    }

    override fun toString(): String {
        return fileName;
    }
}