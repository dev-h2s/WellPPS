package com.wellnetworks.wellcore.java.repository;

import com.wellnetworks.wellcore.java.domain.partner.WellPartnerEntity;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.assertj.core.api.Assertions.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@DataJpaTest
@Transactional
@WebAppConfiguration
@ContextConfiguration
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Commit
class WellPartnerRepositoryTest {

    private static final Logger log = LoggerFactory.getLogger(WellPartnerRepositoryTest.class);
    @Autowired private WellPartnerRepository wellPartnerRepository;

        @Test
        public void 거래처생성() throws Exception {
                //given
            WellPartnerEntity partner = new WellPartnerEntity("테이블1","개통점","서효석", "96E6CADF-4FD3-41A5-8984-0C70E74D9EFE", "p123123");
            WellPartnerEntity savedPartner = wellPartnerRepository.save(partner);
            //when
            WellPartnerEntity findPartner = wellPartnerRepository.findByPartnerIdx(savedPartner.getPartnerIdx());

            //then
            assertThat(findPartner.getPartnerIdx()).isEqualTo(partner.getPartnerIdx());
            assertThat(findPartner.getPartnerName()).isEqualTo(partner.getPartnerName());

            log.info("거래처 확인 : " + savedPartner.getPartnerName());
        }

//        @Test
//        public void 페이지네이션_거래처검색() throws Exception {
//            //given
//            WellPartnerEntity wellPartnerEntity = new WellPartnerEntity();
//
//
//            //when
//
//            //then
//         }
}