package com.wellnetworks.wellcore.java.service.member;
import com.wellnetworks.wellcore.java.repository.member.employee.WellEmployeeUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
/*
사용자와 관련된 기능을 처리:
        사용자 정보 조회: getUserByUserID(userid: String)
        사용자 존재 여부 확인: existByUserID(userid: String)
        사용자 생성: createUser(user: WellUserDTOCreate)
        데이터 총 개수 가져오기: dataTotalCount()
        권한 목록 가져오기: getPermissionList()
        비밀번호 업데이트: updatePassword(user: WellUserDTOUpdate)
        임시 비밀번호 생성 횟수 확인: getTmpPassCountByIdx(idx: String)
*/
@Component
public class WellEmployeeService {
    // @Autowired 어노테이션은 스프링 프레임워크에서 자동으로 의존성 주입을 수행하는데 사용
    // 이것을 통해 해당 필드들은 스프링 컨테이너에서 관리되는 빈(Bean) 객체로 자동으로 주입
    // 주입된 빈은 클래스 내에서 사용할 수 있게 됨
    // lateinit은 Kotlin에서 사용되는 한정자로, 변수 초기화를 늦추는 기능을 제공
    // 코드에서 이 변수들을 사용하기 위해서는 스프링 프레임워크가 해당 변수들을 주입하고 초기화해야함

    @Autowired
    private WellEmployeeService wellEmployeeMemberService;

//    @Autowired
//    private WellEmployeeUserService wellEmployeeUserService;

//    @Autowired
//    private WellFileStorageService wellFileStorageService;

    @Autowired
    private WellEmployeeUserRepository wellEmployeeUserRepository;

}
