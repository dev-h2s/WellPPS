package com.wellnetworks.wellwebapi.controller.admin

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.databind.node.ObjectNode
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.treeToValue
import com.wellnetworks.wellcore.domain.dto.*
import com.wellnetworks.wellcore.service.utils.SearchCriteria
import com.wellnetworks.wellcore.service.WellMemberInfoService
import com.wellnetworks.wellwebapi.response.BaseItemRes
import com.wellnetworks.wellwebapi.response.BaseListRes
import com.wellnetworks.wellwebapi.response.BaseRes
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PostAuthorize
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import java.util.*

@RestController
//RestController는 @ResponseBody 어노테이션
//즉 주용도는 JSON/XML형태로 객체 데이터 반환을 목적
@RequestMapping("/admin/hr/")
class MemberController(private var memberInfoService: WellMemberInfoService) {
    // member.id로 매핑
    @GetMapping("member/{id}")
    /*@PreAuthorize("isAuthenticated() and" +
            " (hasRole(T(com.wellnetworks.wellcore.domain.enums.PermissionKey).SUPERADMIN) or" +
            " hasRole(T(com.wellnetworks.wellcore.domain.enums.PermissionKey).MEMBER))")*/

//    @pathVariable: url 경로에 포함된 변수 값을 추출하는데 사용됨
    fun getMember(@PathVariable id: String): ResponseEntity<BaseItemRes<WellMemberInfoDTO>> {
        // id를 idx로 바꾸고 대문자로 변경
        val uuidIdx: String
        try {
            uuidIdx = UUID.fromString(id).toString().uppercase()
        } catch (e: Exception) {
            // 응답 실패시 오류 문구
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(BaseItemRes(HttpStatus.BAD_REQUEST, "문서 번호가 잘못되었습니다."))
        }

        val member = memberInfoService.getMemberByIdx(uuidIdx)
        if (member.isEmpty)
            // 값이 비어있다면 오류문구 출력
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(BaseItemRes(HttpStatus.NOT_FOUND, "$id 데이터를 찾을 수 없습니다."))
// 외 성공 문구 출력
        return ResponseEntity.ok(BaseItemRes(HttpStatus.OK, "", member.get()))
    }

    @GetMapping("member")
    /*@PreAuthorize("isAuthenticated() and" +
            " (hasRole(T(com.wellnetworks.wellcore.domain.enums.PermissionKey).SUPER_ADMIN) or" +
            " hasRole(T(com.wellnetworks.wellcore.domain.enums.PermissionKey).MEMBER))")*/
    // 페이지 및 페이지 사이즈 표시
    fun getMemberList(
        @RequestParam("size", defaultValue = "10") size: Int,
        @RequestParam("page", defaultValue = "0") page: Int,
        //    HTTP 응답을 나타내는 객체
    ): ResponseEntity<BaseListRes<WellMemberInfoDTO>> {
        // 검색 키워드를 저장할 MutableList를 초기화. (검색 기능을 추가할 때 사용될 수 있다.)
        val searchKeywords: MutableList<SearchCriteria> = mutableListOf()
        // Pageable 객체 생성, size와 page를 사용하여 페이지네이션을 설정
        val pageable: Pageable = PageRequest.of(page, size)

        // Todo : 검색조건 추가
        // Service에서  목록을 조회하고 페이지 정보와 함께 클라이언트에게 응답
        val memberList = memberInfoService.searchMember(pageable, searchKeywords)

        //return ResponseEntity.ok(BaseListRes<WellMemberInfoDTO>(HttpStatus.OK, "",
        //    memberList.content, memberList.number, memberList.totalElements, memberList.totalPages))

        //BaseListRes 객체 생성, 성공시 비어있는 메시지
        return ResponseEntity.ok(BaseListRes(HttpStatus.OK, "",
            memberList.content, memberList.number, memberList.totalElements, memberList.totalPages))
    }



    // HTTP POST 메서드로 엔드포인트를 처리하는 메서드
    // 새로운 회원을 생성
    @PostMapping("member")
    /*@PreAuthorize("isAuthenticated() and"+
            " (hasRole(T(com.wellnetworks.wellcore.domain.enums.PermissionKey).SUPER_ADMIN) or" +
            " hasRole(T(com.wellnetworks.wellcore.domain.enums.PermissionKey).MEMBER))")*/
    fun createMember(@RequestPart("user") userJsonString: String,
                     @RequestPart("member") memberJsonString: String
                     //@RequestPart("file") files: List<MultipartFile>
    ): ResponseEntity<BaseRes> {
        val mapper = jacksonObjectMapper();
        mapper.registerModule(JavaTimeModule()); // 시간을 처리할때 json 직렬화 / 파싱시 문제 발생 x
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS); //옵션을 비활성화하여 날짜 시간을 문자열로 직렬화

        try {
            // val user = mapper.treeToValue<WellUserDTOCreate>(objUserPartner.get("user"))
            // val member = mapper.treeToValue<WellMemberInfoDTOCreate>(objUserPartner.get("member"))
            // JSON 데이터를 객체로 변환하는 부분
            // userJsonString: 사용자 정보 JSON 데이터
            // memberJsonString: 회원 정보 JSON 데이터
            val user = mapper.readValue(userJsonString, WellUserDTOCreate::class.java)
            val member = mapper.readValue(memberJsonString, WellMemberInfoDTOCreate::class.java)
        // 회원 생성 서비스 메서드를 호출하여 회원을 생성하고, 그 결과를 확인
            if (!memberInfoService.createMember(user, member))
                // 회원 생성이 실패한 경우, 500 상태 코드와 실패 메시지를 응답
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(BaseRes(HttpStatus.INTERNAL_SERVER_ERROR, "맴버 추가 실패"))
        } catch (e: Exception) {
//            예외가 발생한 경우 오류 메세지
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(BaseRes(HttpStatus.BAD_REQUEST, "잘못된 요청입니다."))
        }
//  생성  성공시
        return ResponseEntity.ok(BaseRes(HttpStatus.OK, "맴버 추가 성공"))
    }
    // put 모든걸 = > patch로 수정 해야함
    // HTTP PUT 메서드로 "member" 엔드포인트를 처리하는 메서드
    // 이 엔드포인트는 회원 정보를 업데이트
    @PutMapping("member")
    /*@PreAuthorize("isAuthenticated() and" +
            " (hasRole(T(com.wellnetworks.wellcore.domain.enums.PermissionKey).SUPER_ADMIN) or" +
            " hasRole(T(com.wellnetworks.wellcore.domain.enums.PermissionKey).MEMBER))")*/

    //(@PathVariable id: String): ResponseEntity<BaseItemRes<WellPartnerDTO>> {
    //fun updatePartner(@RequestPart("partner") partner: String, @RequestPart("file") files: List<MultipartFile>): ResponseEntity<BaseRes> {

    fun updateMember(@RequestPart("member") member: String, @RequestPart("file", required = false) files: List<MultipartFile>?): ResponseEntity<BaseRes> {

        val mapper = jacksonObjectMapper()

        try {
            // 업데이트할 회원 정보를 JSON에서 읽어와 객체로 변환
            val member = mapper.readValue(member, WellMemberDTOUpdate::class.java)
            // 회원 정보 업데이트를 시도하고 실패하면 에러 응답을 클라이언트에게 반환
            if (!memberInfoService.updateMember(member, files))
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(BaseRes(HttpStatus.INTERNAL_SERVER_ERROR, "업데이트 실패"))

        } catch (e: Exception) {
            //예외 발생시 에러 메세지
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(BaseRes(HttpStatus.BAD_REQUEST, e.message ?: "잘못된 요청입니다."))
        }
        //성공메세지
        return ResponseEntity.ok(BaseRes(HttpStatus.OK, "업데이트 성공"))


    }

    @DeleteMapping("member/{id}")
    /*@PreAuthorize("isAuthenticated() and" +
            " (hasRole(T(com.wellnetworks.wellcore.domain.enums.PermissionKey).SUPER_ADMIN) or" +
            " hasRole(T(com.wellnetworks.wellcore.domain.enums.PermissionKey).MEMBER))")*/
    //삭제
    fun deleteMember(@PathVariable id: String) : ResponseEntity<BaseRes> {
       // uuid 사용
        val uuidIdx: String

        try{
            // id를 uuid로
            uuidIdx = UUID.fromString(id).toString()
        }catch (e: java.lang.Exception){
            // 에러 발생시 문구
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(BaseRes(HttpStatus.BAD_REQUEST, "문서 번호가 잘못되었습니다."))
        }

        try {
            // 데이터 조회 실패시 실패문구
            memberInfoService.deleteMemberById(uuidIdx)
        } catch (e: Exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(BaseRes(HttpStatus.NOT_FOUND, "$id 데이터를 찾을 수 없습니다."))
        }
          // 성공시 성공문구
        return ResponseEntity.ok(BaseRes(HttpStatus.OK, "delete ok"))
    }
}