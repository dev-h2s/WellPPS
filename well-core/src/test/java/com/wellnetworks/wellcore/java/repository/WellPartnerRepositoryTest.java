package com.wellnetworks.wellcore.java.repository;

import com.wellnetworks.wellcore.java.domain.partner.WellPartnerEntity;
import jakarta.transaction.Transactional;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class WellPartnerRepositoryTest {

        @Test
            public void 거래처검색() throws Exception {
            //given
            WellPartnerEntity partner = new WellPartnerEntity();

            //when


            //then
         }
}