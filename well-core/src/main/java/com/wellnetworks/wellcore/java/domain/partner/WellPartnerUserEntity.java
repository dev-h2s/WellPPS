package com.wellnetworks.wellcore.java.domain.partner;
// 거래처 유저
import com.wellnetworks.wellcore.java.domain.refreshtoken.PartnerRefreshTokenEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor
public class WellPartnerUserEntity {

    @Id //거래처_idx
    @Column(name = "p_idx")
    private String partnerIdx;

    @OneToOne(fetch = LAZY) //거래처 1대1
    @JoinColumn(name = "p_idx")
    private WellPartnerEntity partner;

    @ManyToOne(fetch = LAZY) //그룹별권한
    @JoinColumn(name = "pm_gkey")
    private WellPartnerPermissionGroupEntity partnerManagerGroupKey; //거래처유저그룹 엔티티 참조

    @Column(name = "p_identification") //로그인시 아이디
    private String partnerIdentification;

    @Column(name = "pwd")//거래처_패스워드
    private String partnerUserPwd;

    @Column(name = "permissions") //권한
    private String permissions;

    @Column(name = "tmp_pwd") //임시비밀번호
    private String tmpPwd;

    @Column(name = "tmp_pwd_expiration") //임시비밀번호 만료날짜
    private LocalDateTime tmpPwdExpiration;

    @Column(name = "tmp_pwd_count") //임시비밀번호 사용횟수
    private Integer tmpPwdCount;

    @Column(name = "tmp_pwd_dt") //임시비밀번호 생성일자
    private LocalDateTime tmpPwdDate;

    @Column(name = "tel_cert", columnDefinition = "bit") //전화번호 인증여부
    private Boolean isPhoneVerified;

    @Column(name = "phone_verification_code") // 휴대폰 인증 코드
    private String phoneVerificationCode;

    @Column(name = "phone_verification_attempts") // 휴대폰 인증 시도 횟수
    private Integer phoneVerificationAttempts;

    @Column(name = "phone_verification_expiration") // 휴대폰 인증 만료 시간
    private LocalDateTime phoneVerificationExpiration;

    @CreatedDate
    @Column(name = "phone_verification_sent_time") // 휴대폰 인증 코드 전송 시간
    private LocalDateTime phoneVerificationSentTime;

    @Column(name = "p_u_moddt") //유저정보 수정일자
    private LocalDateTime partnerUserModifyDate;

    @Column(name = "p_u_regdt") //유저정보 생성일자
    private LocalDateTime partnerUserRegisterDate;

    @Column(name = "Group_key") //그룹식별자
    private String groupKey;


    @Column(name = "password_reset_required") // 패스워드 변경해야하나 true면 임시패스워드로 로그인 가능
    private Boolean isPasswordResetRequired ;

    @Column
    private Boolean isFirstLogin ; // 첫로그인 여부

    @OneToOne(mappedBy = "partnerUser", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private PartnerRefreshTokenEntity refreshTokens;// 토큰 연결

    @Builder
    public WellPartnerUserEntity(String partnerIdx, WellPartnerEntity partner, WellPartnerPermissionGroupEntity partnerManagerGroupKey, String partnerIdentification, String partnerUserPwd, String permissions, String tmpPwd, LocalDateTime tmpPwdExpiration, Integer tmpPwdCount, LocalDateTime tmpPwdDate, LocalDateTime partnerUserModifyDate, LocalDateTime partnerUserRegisterDate
                                , Boolean isPhoneVerified, String phoneVerificationCode, Integer phoneVerificationAttempts, LocalDateTime phoneVerificationExpiration, LocalDateTime phoneVerificationSentTime, String groupKey
                                , String groupPermissionKey, Boolean isPasswordResetRequired,
                                 Boolean isFirstLogin, PartnerRefreshTokenEntity partnerRefreshToken, List<String> permissionsKeysStringList, PartnerRefreshTokenEntity refreshTokens) {
        this.partnerIdx = partnerIdx;
        this.partner = partner;
        this.partnerManagerGroupKey = partnerManagerGroupKey;
        this.partnerIdentification = partnerIdentification;
        this.partnerUserPwd = partnerUserPwd;
        this.permissions = permissions;
        this.tmpPwd = tmpPwd;
        this.tmpPwdExpiration = tmpPwdExpiration;
        this.tmpPwdCount = tmpPwdCount;
        this.tmpPwdDate = tmpPwdDate;
        this.isPhoneVerified = isPhoneVerified;
        this.phoneVerificationCode = phoneVerificationCode;
        this.phoneVerificationAttempts = phoneVerificationAttempts;
        this.phoneVerificationExpiration = phoneVerificationExpiration;
        this.phoneVerificationSentTime = phoneVerificationSentTime;
        this.partnerUserModifyDate = partnerUserModifyDate;
        this.partnerUserRegisterDate = partnerUserRegisterDate;
        this.groupKey = groupKey;
        this.groupPermissionKey = groupPermissionKey;
//        this.permissionsKeysStringList = permissionsKeysStringList;
        this.isPasswordResetRequired = isPasswordResetRequired ;
        this.isFirstLogin = isFirstLogin;
        this.refreshTokens = refreshTokens;
    }



    public void markFirstLoginComplete() {
        this.isFirstLogin = false;
    }

    //로그인시 패스워드 상태 매서드
    public void changePasswordAndInvalidateTempPassword(String newPassword, PasswordEncoder passwordEncoder) {
        this.partnerUserPwd = passwordEncoder.encode(newPassword);
        this.tmpPwd = null; // 임시 비밀번호 null로 설정
        this.isFirstLogin = false; // 첫 로그인 상태 업데이트
        this.isPasswordResetRequired = false;
    }


    // 추가한 권한 관련 필드
    private String groupPermissionKey;
    @ElementCollection //값 타입의 컬렉션임을 나타냅니다.
    @CollectionTable(name = "employee_permissions", joinColumns = @JoinColumn(name = "employee_idx")) //컬렉션 값을 저장할 테이블을 지정
    @Column(name = "permission")
//    private List<String> permissionsKeysStringList = Collections.emptyList();  // 예제 필드, 실제 필드와는 다를 수 있습니다.

    public Collection<GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();

        // 그룹 권한 추가
        if (groupPermissionKey != null && !groupPermissionKey.isEmpty()) {
            authorities.add(new SimpleGrantedAuthority(groupPermissionKey));
        }

        // 개별 권한 목록 추가
//        for (String permission : permissionsKeysStringList) {
//            authorities.add(new SimpleGrantedAuthority(permission));
//        }

        return authorities;
    }
}
