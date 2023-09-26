package com.wellnetworks.wellwebapi.controller
import com.wellnetworks.wellcore.service.WellFileStorageService
import com.wellnetworks.wellwebapi.response.BaseRes
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.io.Resource
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.UUID
@RestController
@RequestMapping("/file")
class FileStorageController(private var wellFileStorageService: WellFileStorageService) {

    // 파일 이미지 보여주기
    @GetMapping("image.do")
    fun attachPublicImage(@RequestParam(required = true) idx: String): ResponseEntity<out Any> {
        var uuidIdx = UUID.fromString(idx).toString().uppercase()

        if (!wellFileStorageService.existByIdx(uuidIdx, true)) {
            // 파일을 찾을 수 없는 경우, EXPECTATION_FAILED 상태와 메시지 반환
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
                .body(BaseRes(HttpStatus.EXPECTATION_FAILED, "File not found."))
        }

        // 파일이 public 상태이므로, getOneDownload으로 이미지 파일 다운로드
        var imageFile = wellFileStorageService.getOneDownload(uuidIdx, true)

        // 이미지 파일이 존재하지 않는 경우, EXPECTATION_FAILED 상태와 메시지 반환
        if (imageFile.isEmpty)
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
                .body(BaseRes(HttpStatus.EXPECTATION_FAILED, "File not found."))

        // 이미지 파일 찾기
        return when (val res = wellFileStorageService.getFile(imageFile.get()).getOrNull()) {
            // 파일 찾았을 경우 http응답 200
            is Resource -> ResponseEntity.status(HttpStatus.OK)
                // 응답의 콘텐츠 타입을 해당 이미지 파일의 확장자에 맞게 설정
                .header(HttpHeaders.CONTENT_TYPE, "image/${imageFile.get().FileExtension}")
                // 이미지 파일 리소스를 응답 본문에 포함
                .body<Resource>(res)
            // 이미지 파일을 찾을 수 없는 경우, EXPECTATION_FAILED 상태와 메시지 반환
            else -> ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
                .body(BaseRes(HttpStatus.EXPECTATION_FAILED, "File Not Found"))
        }
    }


    // 다운로드
    @GetMapping("download.do")
    fun downloadPublicFile(@RequestParam(required = true) idx: String): ResponseEntity<out Any> {
        var uuidIdx = UUID.fromString(idx).toString().uppercase()
        // 파일을 찾을 수 없는 경우, EXPECTATION_FAILED 상태와 메시지 반환
        if (!wellFileStorageService.existByIdx(uuidIdx, true)) {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(BaseRes(HttpStatus.EXPECTATION_FAILED, "File not found."))
        }
        // 파일이 public 상태이므로, getOneDownload으로 이미지 파일 다운로드
        var imageFile = wellFileStorageService.getOneDownload(uuidIdx, true)
        // 이미지 파일이 존재하지 않는 경우, EXPECTATION_FAILED 상태와 메시지 반환
        if (imageFile.isEmpty)
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(BaseRes(HttpStatus.EXPECTATION_FAILED, "File not found."))
        // 이미지 파일 찾기
        return when (val res = wellFileStorageService.getFile(imageFile.get()).getOrNull()) {
            // 파일 찾았을 경우 http응답 200
            is Resource -> ResponseEntity.status(HttpStatus.OK)
                // 응답의 콘텐츠 타입을 "application/octet-stream"으로 설정 (이진 파일 형식)
                .header(HttpHeaders.CONTENT_TYPE, "application/octet-stream")
                //.contentType(MediaType.parseMediaType("application/octet-stream"))
                // 다운로드할 때 사용할 파일 이름을 설정
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + imageFile.get().FileName)
                // 이미지 파일 리소스를 응답 본문에 포함
                .body<Resource>(res)
            // 이미지 파일을 찾을 수 없는 경우, EXPECTATION_FAILED 상태와 메시지 반환
            else -> ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
                .body(BaseRes(HttpStatus.EXPECTATION_FAILED, "File Not Found"))
        }
    }

    // 이미지 첨부
    @GetMapping("attachImage.do")
    fun attachImage(@RequestParam(required = true) idx: String, auth: Authentication): ResponseEntity<out Any> {
        var uuidIdx = UUID.fromString(idx).toString().uppercase()

        if (!wellFileStorageService.existByIdx(uuidIdx, false)) {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(BaseRes(HttpStatus.EXPECTATION_FAILED, "File not found."))
        }

        var userPermission = auth.authorities.map { it.authority }.toList()

        var imageFile = wellFileStorageService.getOneDownload(uuidIdx, false, userPermission)
        if (imageFile.isEmpty)
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(BaseRes(HttpStatus.EXPECTATION_FAILED, "File not found."))

        return when (val res = wellFileStorageService.getFile(imageFile.get()).getOrNull()) {
            is Resource -> ResponseEntity.status(HttpStatus.OK)
                .header(HttpHeaders.CONTENT_TYPE, "image/${imageFile.get().FileExtension}")
                .body<Resource>(res)

            else -> ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
                .body(BaseRes(HttpStatus.EXPECTATION_FAILED, "File Not Found"))
        }
    }

    // 파일 다운로드(권한이 추가됨)
    @GetMapping("fileDownload.do")
    fun downloadFile(@RequestParam(required = true) idx: String, auth: Authentication): ResponseEntity<out Any> {
        var uuidIdx = UUID.fromString(idx).toString().uppercase()

        if (!wellFileStorageService.existByIdx(uuidIdx, false)) {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(BaseRes(HttpStatus.EXPECTATION_FAILED, "File not found."))
        }

        var userPermission = auth.authorities.map { it.authority }.toList()

        var imageFile = wellFileStorageService.getOneDownload(uuidIdx, false, userPermission)
        if (imageFile.isEmpty)
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(BaseRes(HttpStatus.EXPECTATION_FAILED, "File not found."))

        return when (val res = wellFileStorageService.getFile(imageFile.get()).getOrNull()) {
            is Resource -> ResponseEntity.status(HttpStatus.OK)
                .header(HttpHeaders.CONTENT_TYPE, "application/octet-stream")
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + imageFile.get().FileName)
                .body<Resource>(res)

            else -> ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
                .body(BaseRes(HttpStatus.EXPECTATION_FAILED, "File Not Found"))
        }
    }
}