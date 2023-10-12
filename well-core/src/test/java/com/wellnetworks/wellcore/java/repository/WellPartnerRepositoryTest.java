package com.wellnetworks.wellcore.java.repository;

import com.wellnetworks.wellcore.java.domain.partner.WellPartnerEntity;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.*;


@DataJpaTest
@Transactional
@AutoConfigureTestDatabase
class WellPartnerRepositoryTest {

    @Autowired private WellPartnerRepository wellPartnerRepository;

    @Test
        public void 거래처생성() throws Exception {
                //given
            WellPartnerEntity partner = new WellPartnerEntity("96E6CADF-4FD3-41A5-8984-0C70E74D9EFE", "p123123");
            WellPartnerEntity savedPartner = wellPartnerRepository.save(partner);
            //when
            WellPartnerEntity findPartner = wellPartnerRepository.findByPartnerIdx(savedPartner.getPartnerIdx());

            //then
            assertThat(findPartner.getPartnerIdx()).isEqualTo(partner.getPartnerIdx());
            assertThat(findPartner.getPartnerName()).isEqualTo(partner.getPartnerName());
        }
}