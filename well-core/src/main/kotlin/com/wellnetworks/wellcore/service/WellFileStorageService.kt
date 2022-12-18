package com.wellnetworks.wellcore.service

import com.wellnetworks.wellcore.domain.WellFileStorageEntity
import com.wellnetworks.wellcore.domain.dto.WellFileStorageDTO
import com.wellnetworks.wellcore.domain.enums.PermissionList
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

    fun getOneDownload(idx: UUID, isPublic: Boolean, permissions: List<String>? = null): Optional<WellFileStorageDTO> {
        var fileItem = if (isPublic)
            wellFileStorageRepository.findFirstByIdxAndPublicTrue(idx)
        else wellFileStorageRepository.findFirstByIdxAndPublicFalse(idx)

        if (fileItem.isEmpty) {
            return Optional.empty()
        }

        if (isPublic) {
            return fileItem.map { it.getWellFileStorageDTO() }
        }

        if (permissions.isNullOrEmpty() || fileItem.get().permissionsKeysStringList.isNullOrEmpty()) {
            return Optional.empty()
        }

        for(item in fileItem.get().permissionsKeysStringList!!) {
            if (permissions.contains(item)) {
                return fileItem.map { it.getWellFileStorageDTO() }
            }
        }

        return Optional.empty()
    }

    fun existByIdx(idx: UUID, isPublic: Boolean): Boolean {
        return if (isPublic)
            wellFileStorageRepository.existsByIdxAndPublicTrue(idx)
        else wellFileStorageRepository.existsByIdxAndPublicFalse(idx)
    }

    fun getFile(fileData: WellFileStorageDTO): Result<Resource> =
        runCatching {
            val file: Path = Paths.get(uploadDirectory).resolve(
                Paths.get(fileData.TableID + "/" + fileData.Create_YYYYMM)
                    .resolve(fileData.Idx.toString() + "." + (fileData.FileExtension ?: ""))
            )

            val resource: Resource = UrlResource(file.toUri())
            if (resource.exists() || resource.isReadable) {
                resource
            } else throw FileNotFoundException("File Not Found ${file.fileName}")
        }.onFailure {
            logger.warn("File Open Error ${fileData.Idx.toString()} reason: ${it.javaClass}")
        }


    @Transactional(rollbackFor = [Exception::class])
    fun saveFile(tableID: String, writer: UUID, fileDescription: String?, isPublic: Boolean, permissions: List<String>?, file: MultipartFile?): UUID? {
        if (file == null) return null;
        if (file.originalFilename == null) return null;

        var fileIdx = UUID.randomUUID()
        var formaterYYYYMM = DateTimeFormatter.ofPattern("yyyyMM")
        var createYYYYMM = ZonedDateTime.now().format(formaterYYYYMM)
        var orgName = file.originalFilename!!
        var fileSize: Long = file.size

        var extPosIndex: Int? = orgName?.lastIndexOf(".")
        var ext = orgName?.substring(extPosIndex?.plus(1) as Int)

        var fileName = tableID + "/" + createYYYYMM + "/" + fileIdx.toString() + "." + (ext ?: "")

        // Todo: permissions 에 기본 권한 필터링 하도록 추가.

        var createFileEntity = WellFileStorageEntity(
            fileIdx, tableID,  writer, createYYYYMM, orgName, fileDescription, false, isPublic, permissions, fileSize, ext, 0
        )

        try {
            file.transferTo(Paths.get(uploadDirectory).resolve(fileName))
            wellFileStorageRepository.save(createFileEntity)
        } catch (e: Exception) {
            return null;
        }
        return fileIdx;
    }

    @Transactional(rollbackFor = [Exception::class])
    fun deleteFile(fileIdx: UUID): Boolean {
        try {
            val deleteFileEnitty = wellFileStorageRepository.findFirstByIdx(fileIdx).orElse(null) ?: return false

            var fileName =
                deleteFileEnitty.tableID + "/" + deleteFileEnitty.createYYYYMM + "/" + fileIdx.toString() + "." + (deleteFileEnitty.fileExtension
                    ?: "")
            val file = File(Paths.get(uploadDirectory).resolve(fileName).toString())
            val result = file.delete()
            if (result) {
                wellFileStorageRepository.deleteByIdx(fileIdx)
            }
        } catch (e: Exception) {
            return false
        }

        return true
    }
}