package org.eple0329.springbasic.service;

import java.util.List;
import java.util.Optional;
import org.eple0329.springbasic.domain.Member;
import org.eple0329.springbasic.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

// @Service
@Transactional
public class MemberService {

        private final MemberRepository memberRepository;

        // DI 가능하도록 변경 (의존성 주입), 생성자를 통해 외부에서 넣어줌 -> 코드의 유연성을 높임
        // @Autowired -> SpringConfig에서 Bean을 직접 주입으로 변경해봄
        public MemberService(MemberRepository memberRepository) {
                this.memberRepository = memberRepository;
        }

        // 회원 가입
        public Long join(Member member) {
                validateDuplicateMember(member); //중복 회원 검증
                memberRepository.save(member);
                return member.getId();
        }

        // 중복 멤버 유효성 검사
        private void validateDuplicateMember(Member member) {
                memberRepository.findByName(member.getName())
                        .ifPresent(m -> {
                                // Optional에 값이 있으면 실행
                                throw new IllegalStateException("이미 존재하는 회원입니다.");
                        });
        }

        // 전체 회원 조회
        public List<Member> findMembers() {
                return memberRepository.findAll();
        }

        // 회원 조회
        public Optional<Member> findOne(Long memberId) {
                return memberRepository.findById(memberId);
        }
}