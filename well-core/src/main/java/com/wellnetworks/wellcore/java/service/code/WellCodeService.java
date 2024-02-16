package com.wellnetworks.wellcore.java.service.code;

import com.wellnetworks.wellcore.java.domain.code.WellCodeEntity;
import com.wellnetworks.wellcore.java.dto.code.WellCodeCreateDTO;
import com.wellnetworks.wellcore.java.dto.code.WellCodeDetailDTO;
import com.wellnetworks.wellcore.java.dto.code.WellCodeListDTO;
import com.wellnetworks.wellcore.java.dto.code.WellCodeUpdateDTO;
import com.wellnetworks.wellcore.java.repository.code.WellCodeRepository;
import jakarta.persistence.EntityNotFoundException;
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
    public List<WellCodeDetailDTO> getCodesByCType(String cType) {
        List<WellCodeEntity> codes = codeRepository.findByCType(cType);
        return codes.stream()
                .map(code -> new WellCodeDetailDTO(code.getCType(), code.getName(), code.getSort()))
                .collect(Collectors.toList());
    }

    //타입 생성
    public WellCodeCreateDTO createCode(WellCodeCreateDTO codeDetail) {
        WellCodeEntity newCode = codeRepository.save(new WellCodeEntity(null, codeDetail.getCType(), codeDetail.getName(), codeDetail.getSort()));
        return new WellCodeCreateDTO(newCode.getCType(), newCode.getName(), newCode.getSort());
    }

    //타입 수정
    public WellCodeUpdateDTO updateCode(Long id, WellCodeUpdateDTO codeUpdateDTO) {
        WellCodeEntity existingCode = codeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Code not found"));

        // Builder를 사용한 엔티티 업데이트
        WellCodeEntity updatedCode = existingCode.toBuilder()
                .name(codeUpdateDTO.getName())
                .sort(codeUpdateDTO.getSort())
                .build();

        codeRepository.save(updatedCode);

        return new WellCodeUpdateDTO(updatedCode.getName(), updatedCode.getSort());
    }

    //타입 정렬 변경
    //타입 삭제
}