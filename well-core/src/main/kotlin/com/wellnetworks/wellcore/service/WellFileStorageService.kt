package com.wellnetworks.wellcore.service
// 파일 서비스
import com.wellnetworks.wellcore.domain.WellFileStorageEntity
import com.wellnetworks.wellcore.domain.dto.WellFileStorageDTO
import com.wellnetworks.wellcore.domain.enums.PermissionKey
import com.wellnetworks.wellcore.repository.WellFileStorageRepository
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.io.Resource
import org.springframework.core.io.UrlResource
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.io.FileNotFoundException
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Optional
import java.util.UUID

@Component
class WellFileStorageService {
    @Autowired
    lateinit var wellFileStorageRepository: WellFileStorageRepository

    private val logger = KotlinLogging.logger {}

    @Value("\${well.filestorage.directory}")
    lateinit var uploadDirectory: String

    // 파일 다운로드
    fun getOneDownload(idx: String, isPublic: Boolean, permissions: List<String>? = null): Optional<WellFileStorageDTO> {
        // 파일 아이템이 PublicTrue 이면 findFirstByIdxAndPublicTrue에 맞는 엔티티값 가져오기
        var fileItem = if (isPublic)
            wellFileStorageRepository.findFirstByIdxAndPublicTrue(idx.uppercase())
        // 아니면 findFirstByIdx에 맞는 값 가져오기
        else wellFileStorageRepository.findFirstByIdx(idx.uppercase())
        // 파일 아이템이 비어있다면 빈값 리턴
        if (fileItem.isEmpty) {
            return Optional.empty()
        }
        // 파일 아이템이 true이면 getWellFileStorageDTO값 리턴
        if (fileItem.get().public) {
            return fileItem.map { it.getWellFileStorageDTO() }
        }
        // 권한이 null또는 비어있거나 파일 아이템 permissionsKeysStringList가  null또는 비어있으면 빈값 리턴
        if (permissions.isNullOrEmpty() || fileItem.get().permissionsKeysStringList.isNullOrEmpty()) {
            return Optional.empty()
        }
        // 권한이 SUPER_ADMIN이면 getWellFileStorageDTO값 리턴
        if (permissions.contains(PermissionKey.SUPER_ADMIN)) {
            return fileItem.map { it.getWellFileStorageDTO() }
        }

        // 파일 엔티티의 permissionsKeysStringList에 대한 반복문
        for (item in fileItem.get().permissionsKeysStringList!!) {
            // permissions 리스트에 해당 항목이 포함되어 있는지 확인
            if (permissions.contains(item)) {
                // 포함된 경우, 해당 파일 엔티티를 DTO로 매핑하고 반환
                return fileItem.map { it.getWellFileStorageDTO() }
            }
        }

        // 모든 조건을 만족하지 않으면 빈값 리턴
        return Optional.empty()
    }

    // 파일 idx찾기
    fun existByIdx(idx: String, isPublic: Boolean): Boolean {
        // public이 true이면 existsByIdxAndPublicTrue의 파일 idx값 리턴
        if (isPublic) {
            return wellFileStorageRepository.existsByIdxAndPublicTrue(idx)
        // 아니면 existsByIdx의 파일 idx값 리턴
        } else {
            return wellFileStorageRepository.existsByIdx(idx)
        }
    }

    // 파일 가져오기
    fun getFile(fileData: WellFileStorageDTO): Result<Resource> =
        runCatching {
            // 파일 경로 생성
            val file: Path = Paths.get(uploadDirectory).resolve(
                Paths.get(fileData.TableID + "/" + fileData.Create_YYYYMM)
                    .resolve(fileData.Idx.toString() + "." + (fileData.FileExtension ?: ""))
            )
            // 파일을 읽을 수 있는 형태의 리소스로 변환
            val resource: Resource = UrlResource(file.toUri())
            // 파일이 존재하고 읽을 수 있는 경우 리소스 반환
            if (resource.exists() || resource.isReadable) {
                resource
                // 파일이 존재하지 않는 경우 예외 처리
            } else throw FileNotFoundException("File Not Found ${file.fileName}")
        }.onFailure {
            // 파일을 열 때 발생한 오류 로그 기록
            logger.warn("File Open Error ${fileData.Idx.toString()} reason: ${it.javaClass}")
        }

    // 파일 저장
    @Transactional(rollbackFor = [Exception::class])
    fun saveFile(tableID: String, writer: String, fileDescription: String?, isPublic: Boolean, permissions: List<String>?, file: MultipartFile?): String? {
        // 파일이나 파일 원본 이름이 null인 경우 null리턴
        if (file == null) return null;
        if (file.originalFilename == null) return null;

        var fileIdx = UUID.randomUUID().toString()
        var formaterYYYYMM = DateTimeFormatter.ofPattern("yyyyMM")
        var createYYYYMM = ZonedDateTime.now().format(formaterYYYYMM)
        var orgName = file.originalFilename!!
        var fileSize: Long = file.size

        var extPosIndex: Int? = orgName?.lastIndexOf(".")
        var ext = orgName?.substring(extPosIndex?.plus(1) as Int)

        var fileName = tableID + "/" + createYYYYMM + "/" + fileIdx.toString() + "." + (ext ?: "")

        // Todo: permissions 에 기본 권한 필터링 하도록 추가.

        var createFileEntity = WellFileStorageEntity(
            fileIdx.uppercase(), tableID,  writer.uppercase(), createYYYYMM, orgName, fileDescription, false, isPublic, permissions, fileSize, ext, 0
        )

        try {
            Files.createDirectories(Paths.get(uploadDirectory).resolve("$tableID/$createYYYYMM/"))
            file.transferTo(Paths.get(uploadDirectory).resolve(fileName).toAbsolutePath().toFile())
            wellFileStorageRepository.save(createFileEntity)
        } catch (e: Exception) {
            return null;
        }
        return fileIdx;
    }

    // 파일 삭제
    @Transactional(rollbackFor = [Exception::class])
    fun deleteFile(fileIdx: String): Boolean {
        try {
            // idx
            val deleteFileEntity = wellFileStorageRepository.findFirstByIdx(fileIdx.uppercase()).orElse(null) ?: return false

            var fileName =
                deleteFileEntity.tableID + "/" + deleteFileEntity.createYYYYMM + "/" + fileIdx.toString() + "." + (deleteFileEntity.fileExtension
                    ?: "")
            val file = File(Paths.get(uploadDirectory).resolve(fileName).toString())
            val result = file.delete()
            if (result) {
                wellFileStorageRepository.deleteByIdx(fileIdx.uppercase())
            }
        } catch (e: Exception) {
            return false
        }

        return true
    }
}