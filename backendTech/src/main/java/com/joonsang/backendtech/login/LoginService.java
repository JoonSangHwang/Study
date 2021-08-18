package com.joonsang.backendtech.login;

import com.joonsang.backendtech.domain.member.Member;
import com.joonsang.backendtech.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final MemberRepository memberRepository;

    /**
     * - ID 와 PW 를 검증하여 사용자 반환
     * - 비회원일 경우 NULL
     */
    public Member login(String loginId, String password) {
        /* 1. 기본 소스 */
//        Optional<Member> findMemberOptional = memberRepository.findByLoginId(loginId);
//        Member member = findMemberOptional.get();
//        if (member.getPassword().equals(password))
//            return member;
//
//        return null;

        /* 2. 리팩토링 소스 */
//        Optional<Member> findMemberOptional = memberRepository.findByLoginId(loginId);
//        return findMemberOptional.filter(m -> m.getPassword().equals(password))
//                .orElse(null);

        /* 3. 리팩토링 소스 */
        return memberRepository.findByLoginId(loginId)
                .filter(m -> m.getPassword().equals(password))
                .orElse(null);
    }
}
