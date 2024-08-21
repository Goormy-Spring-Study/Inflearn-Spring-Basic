package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
public class MemberService {
    private final MemberRepository memberRepository;

    // 직접 생성하지 않고 외부에서 넣어줌 -> Dependency Injection(DI)
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
        // 지금의 경우에는 구현체인 MemoryMemberRepository 를 넣어줌(빈으로 등록되어 있는!)
    }

    private void validateDuplicateMember(Member member) {
        memberRepository.findByName(member.getName())
                .ifPresent(m -> {
                    throw new IllegalArgumentException("이미 존재하는 회원입니다. ");
                });
    }

    /*
    회원 가입
     */
    public Long join(Member member) {
        // 같은 이름이 있는 중복 회원 X
        // Optional 사용하는 이유 -> Optional 으로 다양한 메소드 활용할 수 있다.
        validateDuplicateMember(member);    // 중복 회원 검증
        memberRepository.save(member);
        return member.getId();
    }

    /*
    전체 회원 조회
     */
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    public Optional<Member> findOne(Long memberId) {
        return memberRepository.findById(memberId);
    }

}
