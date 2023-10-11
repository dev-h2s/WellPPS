package com.wellnetworks.wellcore.java.repository;

import com.wellnetworks.wellcore.java.domain.partner.WellPartnerEntity;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;


@SpringBootTest
@Transactional
class WellPartnerRepositoryTest {

    @Autowired WellPartnerRepository wellPartnerRepository;

        @Test
            public void 거래처생성() throws Exception {
                //given
            WellPartnerEntity partner = new WellPartnerEntity("1111111111111111");
            WellPartnerEntity savedPartner = wellPartnerRepository.save(partner);
            //when
            WellPartnerEntity findPartner = wellPartnerRepository.find(savedPartner.getPartnerIdx());

            //then
            assertThat(findPartner.getPartnerIdx()).isEqualTo(partner.getPartnerIdx());
            assertThat(findPartner.getPartnerName()).isEqualTo(partner.getPartnerName());
        }
}