package com.wellnetworks.wellcore.domain
// 파일 정보
import com.wellnetworks.wellcore.domain.converter.ListToStringConverter
import com.wellnetworks.wellcore.domain.dto.WellFileStorageDTO
import jakarta.persistence.*
import org.hibernate.Hibernate

@Entity
@Table(name = "file_tb", indexes = [
    Index(name = "IX_tid", columnList = "tbl_id ASC", unique = false),
])
data class WellFileStorageEntity (
    // 파일 이름은 UUID 를 사용.
    @Id
    @Column(name = "idx", columnDefinition = "uniqueidentifier", unique = true, nullable = false)
    var idx: String,

    // 테이블 ID와 날자는 파일의 물리적 경로에 사용. (ex: /{tableID}/{cym}/{idx})
    @Column(name="tbl_id", length = 16, nullable = false)
    var tableID: String,

    @Column(name="wrt", columnDefinition = "uniqueidentifier", nullable = false)
    var writer: String,

    // 데이터는 YYYYMM 으로 숫자만 등록
    @Column(name="cym", length = 6, nullable = false)
    var createYYYYMM: String,

    // 파일의 실제 이름
    @Column(name="fname", length = 255, nullable = false)
    var fileName: String,

    @Column(name="fdesc", length = 255, nullable = true)
    var fileDescription: String?,

    // 물리 파일이 삭제되어 없을 경우.
    @Column(name="isunlink", columnDefinition = "bit")
    var linkError: Boolean,

    @Column(name="ispub", columnDefinition = "bit")
    var public: Boolean,

    @Column(name = "permissions")
    @Convert(converter = ListToStringConverter::class)
    var permissionsKeysStringList: List<String>?,

    @Column(name="fsize", nullable = false)
    var fileSize: Long,

    // 파일의 확장자. (소문자로 캐스팅)
    @Column(name="fext", length = 16, nullable = true)
    var fileExtension: String?,

    @Column(name="dcnt", nullable = false)
    var fileDownloadCount: Long,

): BaseEntity() {
    fun getWellFileStorageDTO(): WellFileStorageDTO {
        return WellFileStorageDTO(
            Idx = this.idx.uppercase(),
            TableID = this.tableID,
            Writer = this.writer.uppercase(),
            Create_YYYYMM = this.createYYYYMM,
            FileName = this.fileName,
            FileDescription = this.fileDescription,
            isLinkError = this.linkError,
            isPublic = this.public,
            PermissionsKeysStringList = this.permissionsKeysStringList,
            FileSize = this.fileSize,
            FileExtension = this.fileExtension,
            FileDownloadCount = this.fileDownloadCount,
            Modify_Datetime = this.modifyDatetime,
            Register_Datetime = this.registerDatetime,
        )
    }

    // 값이 일치하는지 확인
    override fun equals(other: Any?): Boolean {
        if (this == other) return true;
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false;
        other as WellFileStorageEntity
        // idx 값이 모두 null이 아니고 일치하는 경우 true 반환
        return idx != null && idx == other.idx;
    }

    // 해시 코드를 사용하여 객체를 고유하게 식별
    override fun hashCode(): Int {
        return idx.hashCode();
    }

    // 객체를 문자열로 표시
    override fun toString(): String {
        return fileName;
    }
}