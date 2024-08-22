package siyeonson.spring_basic;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import siyeonson.spring_basic.repository.MemberRepository;
import siyeonson.spring_basic.service.MemberService;

@Configuration
public class SpringConfig {

    private MemberRepository memberRepository;

    public SpringConfig(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Bean
    public MemberService memberService() {
        return new MemberService(memberRepository);
    }

//    @Bean
//    public MemberRepository memberRepository() {
//        return new MemoryMemberRepository();
//        return new JdbcMemberRepository(dataSource);
//        return new JdbcTemplateMemberRepository(dataSource);
//        return new JpaMemberRepository(em);
//    }

}
