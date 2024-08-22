package org.eple0329.springbasic.config;

import jakarta.persistence.EntityManager;
import javax.sql.DataSource;
import org.eple0329.springbasic.repository.JdbcMemberRepository;
import org.eple0329.springbasic.repository.JdbcTemplateMemberRepository;
import org.eple0329.springbasic.repository.JpaMemberRepository;
import org.eple0329.springbasic.repository.MemberRepository;
import org.eple0329.springbasic.repository.MemoryMemberRepository;
import org.eple0329.springbasic.service.MemberService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfig {

        private final MemberRepository memberRepository;
//        private final DataSource dataSource;
//        private final EntityManager em;

//        public SpringConfig(DataSource dataSource, EntityManager em) {
//                this.dataSource = dataSource;
//                this.em = em;
//        }

        public SpringConfig(MemberRepository memberRepository) {
                this.memberRepository = memberRepository;
        }

        // MemberService를 Bean으로 등록 (Spring Container에 등록)
        // MemberService를 생성자를 통해 MemberRepository를 주입
        @Bean
        public MemberService memberService() {
                return new MemberService(memberRepository);
        }

        // MemberRepository를 Bean으로 등록 (Spring Container에 등록)
        // MemberService에서 쓰기 위해 Bean으로 등록
//        @Bean
//        public MemberRepository memberRepository() {
//                //return new MemoryMemberRepository();
//                //return new JdbcMemberRepository(dataSource);
//                //return new JdbcTemplateMemberRepository(dataSource)
//                //return new JpaMemberRepository(em);
//        }
}
