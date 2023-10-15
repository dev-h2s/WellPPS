package com.wellnetworks.wellcore.java.repository;

import com.wellnetworks.wellcore.java.repository.Partner.WellPartnerRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@DataJpaTest
@Transactional
@WebAppConfiguration
@ContextConfiguration
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
class WellPartnerRepositoryTest {

    private static final Logger log = LoggerFactory.getLogger(WellPartnerRepositoryTest.class);
    @Autowired private WellPartnerRepository wellPartnerRepository;

//        @Test
//        public void 거래처생성() throws Exception {
//                //given
//            WellPartnerEntity partner = new WellPartnerEntity("테이블1","개통점","서효석", "96E6CADF-4FD3-41A5-8984-0C70E74D9EFE", "p123123", 1L, "개통점", "건방진효석");
//            WellPartnerEntity savedPartner = wellPartnerRepository.save(partner);
//            //when
//            WellPartnerEntity findPartner = wellPartnerRepository.findByPartnerIdx(savedPartner.getPartnerIdx());
//
//            //then
//            assertThat(findPartner.getPartnerIdx()).isEqualTo(partner.getPartnerIdx());
//            assertThat(findPartner.getPartnerName()).isEqualTo(partner.getPartnerName());
//
//            log.info("거래처 확인 : " + savedPartner.getPartnerName());
//        }

//    @Test
//    public void 페이지네이션_거래처검색() {
//        //given
//        List<WellPartnerEntity> partnerEntities = new ArrayList<>();
//
//        // 원하는 WellPartnerEntity를 생성하고 partnerEntities에 추가
//        for (int i = 0; i < 10; i++) {
//            String uniqueId = UUID.randomUUID().toString(); // 고유한 UUID 생성
//            partnerEntities.add(new WellPartnerEntity("테이블1","개통점","서효석", uniqueId, uniqueId, 1L, "개통점", "건방진효석"));
//        }
//
//        wellPartnerRepository.saveAll(partnerEntities);
//
//        Page<WellPartnerEntity> result = null; // result 변수를 null로 초기화
//
//        //when
//        try {
//            Pageable pageable = (Pageable) PageRequest.of(0, 10);
//            result = wellPartnerRepository.findAll((org.springframework.data.domain.Pageable) pageable);
//        } catch (Exception e) {
//            System.out.println("에러 발생: " + e.getMessage());
//        }
//
//        //then
//        assertThat(result).isNotNull(); // result가 null이 아닌지 확인
//        assertThat(result.getContent().size()).isEqualTo(10); // 한 페이지당 10개의 결과를 기대
//    }

//    @Test
//    public void 상부점_select박스() {
//        //given
//        // 부모 거래처와 자식 거래처를 생성하고 저장
//        WellPartnerEntity parentPartner = new WellPartnerEntity("테이블1", "개통점", "효석상부", "96E6CADF-4FD3-41A5-8984-0C20E74D9EFE", "p123123-parent", 1L, "개통점", "건방진효석");
//        wellPartnerRepository.save(parentPartner);
//
//        WellPartnerEntity childPartner1 = new WellPartnerEntity("테이블1", "개통점", "진", "16E6CADF-4FD3-41A5-8984-0C70E74D3EFE", "p123123-child1", 1L, "개통점", "건방진효석"); // 상부 점의 partnerIdx를 설정
//        wellPartnerRepository.save(childPartner1);
//
//        WellPartnerEntity childPartner2 = new WellPartnerEntity("테이블1", "개통점", "나상부아니다", "36E6CADF-4FD3-41A5-8984-0C70E74D2EFE", "p123123-child2", 1L, "개통점", "건방진효석"); // 상부 점 없음
//        wellPartnerRepository.save(childPartner2);
//
//        //when
//        // 특정 상부점 구분으로 거래처 검색
//        List<WellPartnerEntity> foundPartner = wellPartnerRepository.findByPartnerUpperId(1L);
//
//        //then
//        // 검색된 상부점 거래처가 null이 아니어야 함
//        assertThat(foundPartner).isNotEmpty();
//
//        // 상부 점으로 설정된 자식 거래처들의 개수 확인 (자식이 3개여야 함)
//        assertThat(foundPartner.size()).isEqualTo(3);
//
//        // 각 자식 거래처의 이름 확인
//        int parentCount = 0;
//        int childCount = 0;
//
//        for (WellPartnerEntity partner : foundPartner) {
//            if (partner.getPartnerUpperId() != null) {
//                // 자식 거래처
//                childCount++;
//                if (partner.getPartnerName().equals("진")) {
//                    assertThat(partner.getPartnerName()).isEqualTo("진");
//                } else if (partner.getPartnerName().equals("나상부아니다")) {
//                    assertThat(partner.getPartnerName()).isEqualTo("나상부아니다");
//                }
//            } else {
//                // 부모 상부점
//                parentCount++;
//                assertThat(partner.getPartnerName()).isEqualTo("효석상부");
//            }
//        }
//    }

//    @Test
//    public void 충전할인율구분_select박스() {
//        //given
//        // 충전할인율 구분으로 거래처 생성 및 저장
//        WellPartnerEntity partner1 = new WellPartnerEntity("테이블1", "개통점", "거래처1", "16E6CADF-4FD3-41A5-8984-0C70E74D9EFE", "p1231231", 1L, "개통점", "건방진효석");
//        wellPartnerRepository.save(partner1);
//
//        WellPartnerEntity partner2 = new WellPartnerEntity("테이블2", "개통점", "거래처2", "26E6CADF-4FD3-41A5-8984-0C70E74D3EFE", "p1231232", 1L, "상부점", "효석");
//        wellPartnerRepository.save(partner2);
//
//        WellPartnerEntity partner3 = new WellPartnerEntity("테이블3", "개통점", "거래처3", "36E6CADF-4FD3-41A5-8984-0C70E74D2EFE", "p1231233", 1L, "api", "효석");
//        wellPartnerRepository.save(partner3);
//
//        //when
//        // 특정 충전할인율 구분으로 거래처 검색
//        List<WellPartnerEntity> foundPartner = wellPartnerRepository.findByDiscountCategory("api");
//
//        //then
////        Optional<WellPartnerEntity> partner32 = Optional.ofNullable(wellPartnerRepository.findByPartnerIdx(partner3.getPartnerIdx()));
//        assertThat(foundPartner).isNotEmpty();
//        assertThat(foundPartner.size()).isEqualTo(1);
//    }

//    @Test
//    public void 거래처구분_select박스() {
//        //given
//        // 충전할인율 구분으로 거래처 생성 및 저장
//        WellPartnerEntity partner1 = new WellPartnerEntity("테이블1", "개통점", "거래처1", "16E6CADF-4FD3-41A5-8984-0C70E74D9EFE", "p1231231", 1L, "개통점", "효석" ,"거래중");
//        wellPartnerRepository.save(partner1);
//
//        WellPartnerEntity partner2 = new WellPartnerEntity("테이블2", "딜러", "거래처2", "26E6CADF-4FD3-41A5-8984-0C70E74D3EFE", "p1231232", 1L, "상부점", "건방진효석", "거래중");
//        wellPartnerRepository.save(partner2);
//
//        WellPartnerEntity partner3 = new WellPartnerEntity("테이블3", "충전점", "거래처3", "36E6CADF-4FD3-41A5-8984-0C70E74D2EFE", "p1231233", 1L, "api", "건방진효석");
//        wellPartnerRepository.save(partner3);
//
//        //when
//        // 특정 영업담당자 구분으로 거래처 검색
//        List<WellPartnerEntity> foundPartner = wellPartnerRepository.findByPartnerType("개통점");
//
//        //then
//        assertThat(foundPartner).isNotEmpty();
//        assertThat(foundPartner.size()).isEqualTo(1);
//    }

//    @Test
//    public void 영업담당자구분_select박스() {
//        //given
//        // 충전할인율 구분으로 거래처 생성 및 저장
//        WellPartnerEntity partner1 = new WellPartnerEntity("테이블1", "개통점", "거래처1", "16E6CADF-4FD3-41A5-8984-0C70E74D9EFE", "p1231231", 1L, "개통점", "효석", "거래중");
//        wellPartnerRepository.save(partner1);
//
//        WellPartnerEntity partner2 = new WellPartnerEntity("테이블2", "개통점", "거래처2", "26E6CADF-4FD3-41A5-8984-0C70E74D3EFE", "p1231232", 1L, "상부점", "건방진효석", "거래중");
//        wellPartnerRepository.save(partner2);
//
//        WellPartnerEntity partner3 = new WellPartnerEntity("테이블3", "개통점", "거래처3", "36E6CADF-4FD3-41A5-8984-0C70E74D2EFE", "p1231233", 1L, "api", "건방진효석", "거래중");
//        wellPartnerRepository.save(partner3);
//
//        //when
//        // 특정 영업담당자 구분으로 거래처 검색
//        List<WellPartnerEntity> foundPartner = wellPartnerRepository.findBySalesManager("건방진효석");
//
//        //then
//        assertThat(foundPartner).isNotEmpty();
//        assertThat(foundPartner.size()).isEqualTo(2);
//    }

    //계약서랑 등록증은 일단 보류
//    @Test
//    public void 등록증_select박스검색() {
//        // Given
//        WellFileStorageEntity registrationFile = new WellFileStorageEntity("46E6CADF-4FD3-41A5-8984-0C70E74D9EFE", "등록증");
//        WellPartnerEntity partner1 = new WellPartnerEntity("테이블1", "개통점", "거래처1", "16E6CADF-4FD3-41A5-8984-0C70E74D9EFE", "p1231231", 1L, "개통점", "효석", "거래완료", "서울");
//        WellPartnerFIleStorageEntity partnerFileStorage = new WellPartnerFIleStorageEntity(registrationFile, partner1);
//
//        wellPartnerRepository.save(partner1);
//        wellPartnerRepository.save(registrationFile);
//        wellPartnerRepository.save(partnerFileStorage);
//
//        // When
//        List<WellFileStorageEntity> registrationFiles = wellPartnerRepository.findByRegistrationByPartnerIdx(partner1.getPartnerIdx());
//
//
//        log.info("registrationFiles = {}",registrationFiles.get(0).getFileKind());
//        log.info("registrationFiles = {}",registrationFiles.size());
//        log.info("registrationFiles = {}",registrationFiles.get(0).getFileIdx());
//        // Then
//        assertThat(registrationFiles).isNotEmpty();
//        for (WellFileStorageEntity file : registrationFiles) {
//            assertThat(file.getFileKind()).isEqualTo("등록증");
//            log.info("registrationFiles = {}",registrationFiles.get(0).getFileKind());
//        }
//        assertThat(registrationFiles.size()).isEqualTo(1);
//
//    }
//    @Test
//    public void 계약서_select박스검색() {
//        // Given
//        WellFileStorageEntity registrationFile = new WellFileStorageEntity("46E6CADF-4FD3-41A5-8984-0C70E74D9EFE", "등록증");
//        WellPartnerEntity partner1 = new WellPartnerEntity("테이블1", "개통점", "거래처1", "16E6CADF-4FD3-41A5-8984-0C70E74D9EFE", "p1231231", 1L, "개통점", "효석", "거래중");
//        WellPartnerFIleStorageEntity partnerFileStorage = new WellPartnerFIleStorageEntity(registrationFile, partner1);
//
//        wellPartnerRepository.save(partner1);
//        wellPartnerRepository.save(registrationFile);
//        wellPartnerRepository.save(partnerFileStorage);
//
//        // When
//        List<WellFileStorageEntity> registrationFiles = wellPartnerRepository.findByRegistrationByPartnerIdx(partner1.getPartnerIdx());
//
//
//        log.info("registrationFiles = {}",registrationFiles.get(0).getFileKind());
//        log.info("registrationFiles = {}",registrationFiles.size());
//        log.info("registrationFiles = {}",registrationFiles.get(0).getFileIdx());
//        // Then
//        assertThat(registrationFiles).isNotEmpty();
//        for (WellFileStorageEntity file : registrationFiles) {
//            assertThat(file.getFileKind()).isEqualTo("등록증");
//        }
//        assertThat(registrationFiles.size()).isEqualTo(1);
//
//    }

//    @Test
//    public void 거래유무_select박스() {
//        //given
//        // 충전할인율 구분으로 거래처 생성 및 저장
//        WellPartnerEntity partner1 = new WellPartnerEntity("테이블1", "개통점", "거래처1", "16E6CADF-4FD3-41A5-8984-0C70E74D9EFE", "p1231231", 1L, "개통점", "효석", "거래중");
//        wellPartnerRepository.save(partner1);
//
//        WellPartnerEntity partner2 = new WellPartnerEntity("테이블2", "개통점", "거래처2", "26E6CADF-4FD3-41A5-8984-0C70E74D3EFE", "p1231232", 1L, "상부점", "건방진효석", "거래중");
//        wellPartnerRepository.save(partner2);
//
//        WellPartnerEntity partner3 = new WellPartnerEntity("테이블3", "개통점", "거래처3", "36E6CADF-4FD3-41A5-8984-0C70E74D2EFE", "p1231233", 1L, "api", "건방진효석", "가등록");
//        wellPartnerRepository.save(partner3);
//
//        //when
//        // 특정 영업담당자 구분으로 거래처 검색
//        List<WellPartnerEntity> foundPartner = wellPartnerRepository.findByTransactionStatus("거래중");
//
//        //then
//        assertThat(foundPartner).isNotEmpty();
//        assertThat(foundPartner.size()).isEqualTo(2);
//    }

//    @Test
//    public void 지역_select박스() {
//        //given
//        // 충전할인율 구분으로 거래처 생성 및 저장
//        WellPartnerEntity partner1 = new WellPartnerEntity("테이블1", "개통점", "거래처1", "16E6CADF-4FD3-41A5-8984-0C70E74D9EFE", "p1231231", 1L, "개통점", "효석", "거래중", "경기");
//        wellPartnerRepository.save(partner1);
//
//        WellPartnerEntity partner2 = new WellPartnerEntity("테이블2", "개통점", "거래처2", "26E6CADF-4FD3-41A5-8984-0C70E74D3EFE", "p1231232", 1L, "상부점", "건방진효석", "거래중", "서울");
//        wellPartnerRepository.save(partner2);
//
//        WellPartnerEntity partner3 = new WellPartnerEntity("테이블3", "개통점", "거래처3", "36E6CADF-4FD3-41A5-8984-0C70E74D2EFE", "p1231233", 1L, "api", "건방진효석", "거래중", "인천");
//        wellPartnerRepository.save(partner3);
//
//        //when
//        // 특정 지역으로 거래처 검색
//        List<WellPartnerEntity> foundPartner = wellPartnerRepository.findByRegion("서울");
//
//        //then
//        assertThat(foundPartner).isNotEmpty();
//        assertThat(foundPartner.size()).isEqualTo(1);
//    }

}