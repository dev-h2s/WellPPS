package com.wellnetworks.wellcore.java.service.code;

import com.wellnetworks.wellcore.java.dto.code.WellCodeListDTO;
import com.wellnetworks.wellcore.java.repository.code.WellCodeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WellCodeService {

    private final WellCodeRepository codeRepository;

    //대분류 전체 조회
    public List<WellCodeListDTO> getAllCType() {
        List<String> cTypes = codeRepository.findAllDistinctCType();
        return cTypes.stream()
                .map(WellCodeListDTO::new)
                .collect(Collectors.toList());
    }

    //타입별 조회
    //타입 생성
    //타입 수정
    //타입 정렬 변경
    //타입 삭제
}
