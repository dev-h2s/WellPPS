package com.wellnetworks.wellcore.repository
// crud문 실행
import com.wellnetworks.wellcore.domain.WellPartnerEntity
import com.wellnetworks.wellcore.domain.enums.CompanyStateType
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.domain.Specification
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.repository.CrudRepository
import java.util.*

interface WellPartnerRepository: CrudRepository<WellPartnerEntity, String>, JpaSpecificationExecutor<WellPartnerEntity> {
    // 파트너 idx로 검색
    fun findByIdx(idx: String): Optional<WellPartnerEntity>

    //  페이지네이션을 적용하여 모든 파트너 엔티티 검색
    fun findAll(pageable: Pageable): Page<WellPartnerEntity>

    //  회사 이름을 사용하여 파트너 검색
    fun findByCompanyName(partnerName: String): Optional<WellPartnerEntity>

    //  회사 상태에 따라 파트너 엔티티 개수
    fun countByCompanyState(companyState: CompanyStateType): Long

    //  수수료 파일 idx & 계약 파일 idx 개수
    fun countByTaxRegistrationDocFileIdxAndContractDocFileIdx(taxRegistrationDocFileIdx: String, contractDocFileIdx: String): Long

    // 수수료 파일 idx 개수
    fun countByTaxRegistrationDocFileIdx(taxRegistrationDocFileIdx: String): Long

    //  수수료 파일이 null인 개수
    fun countByTaxRegistrationDocFileIdxIsNull(): Long

    //  계약 파일이 null인 개수
    fun countByContractDocFileIdxIsNull(): Long

    //  파트너 idx 삭제
    fun deleteByIdx(idx: String): Optional<Int>
}