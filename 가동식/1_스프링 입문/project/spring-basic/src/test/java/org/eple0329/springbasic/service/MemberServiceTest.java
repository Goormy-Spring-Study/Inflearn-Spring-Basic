package org.eple0329.springbasic.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import org.eple0329.springbasic.domain.Member;
import org.eple0329.springbasic.repository.MemoryMemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MemberServiceTest {

        MemberService memberService;
        MemoryMemberRepository memberRepository;

        // 테스트 실행 이전에 실행 -> Repository와 Service를 초기화
        @BeforeEach
        public void beforeEach() {
                memberRepository = new MemoryMemberRepository();
                memberService = new MemberService(memberRepository);
        }

        // 테스트 실행 이후에 실행 -> DB 초기화
        @AfterEach
        public void afterEach() {
                memberRepository.clearStore();
        }

        // 테스트 코드는 한글을 써도 됨. 실제 실행코드에는 포함되지 않기 때문에 문제없음!
        @Test
        public void 회원가입() throws Exception {
                //Given
                Member member = new Member();
                member.setName("hello");

                //When
                Long saveId = memberService.join(member);

                //Then
                Member findMember = memberRepository.findById(saveId).get();
                assertEquals(member.getName(), findMember.getName());
        }

        @Test
        public void 중복_회원_예외() throws Exception {
                //Given
                Member member1 = new Member();
                member1.setName("spring");
                Member member2 = new Member();
                member2.setName("spring");

                //When
                memberService.join(member1);
                IllegalStateException e = assertThrows(IllegalStateException.class,
                        () -> memberService.join(member2));
                //Then
                assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
        }
}