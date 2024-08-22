package org.eple0329.springbasic.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import org.eple0329.springbasic.domain.Member;
import org.eple0329.springbasic.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;


@SpringBootTest
// 테스트 이후에 롤백해서 DB에 데이터가 남지 않도록 함
@Transactional
class MemberServiceIntegrationTest {

        @Autowired
        MemberService memberService;
        @Autowired
        MemberRepository memberRepository;

        @Test
        public void 회원가입() throws Exception {
                //Given
                Member member = new Member();
                member.setName("hello");

                //When
                Long saveId = memberService.join(member);

                //Then
                //Optional이기 때문에 get 사용
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
                // 예외가 발생해야 한다.
                assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
        }
}
