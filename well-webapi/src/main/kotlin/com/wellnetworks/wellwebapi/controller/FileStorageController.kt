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
import javax.servlet.http.HttpServletRequest

@RestController
@RequestMapping("/file")
class FileStorageController(private var wellFileStorageService: WellFileStorageService) {

    @GetMapping("image.do")
    fun attachPublicImage(@RequestParam(required = true) idx: String): ResponseEntity<out Any> {
        var uuidIdx = UUID.fromString(idx).toString().uppercase()

        if (!wellFileStorageService.existByIdx(uuidIdx, true)) {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(BaseRes(HttpStatus.EXPECTATION_FAILED, "File not found."))
        }

        var imageFile = wellFileStorageService.getOneDownload(uuidIdx, true)
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

    @GetMapping("download.do")
    fun downloadPublicFile(@RequestParam(required = true) idx: String): ResponseEntity<out Any> {
        var uuidIdx = UUID.fromString(idx).toString().uppercase()

        if (!wellFileStorageService.existByIdx(uuidIdx, true)) {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(BaseRes(HttpStatus.EXPECTATION_FAILED, "File not found."))
        }

        var imageFile = wellFileStorageService.getOneDownload(uuidIdx, true)
        if (imageFile.isEmpty)
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(BaseRes(HttpStatus.EXPECTATION_FAILED, "File not found."))

        return when (val res = wellFileStorageService.getFile(imageFile.get()).getOrNull()) {
            is Resource -> ResponseEntity.status(HttpStatus.OK)
                .header(HttpHeaders.CONTENT_TYPE, "application/octet-stream")
                //.contentType(MediaType.parseMediaType("application/octet-stream"))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + imageFile.get().FileName)
                .body<Resource>(res)

            else -> ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
                .body(BaseRes(HttpStatus.EXPECTATION_FAILED, "File Not Found"))
        }
    }

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