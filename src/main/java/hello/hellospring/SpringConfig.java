package hello.hellospring;

import hello.hellospring.aop.TimeTraceAop;
import hello.hellospring.repository.*;
import hello.hellospring.service.MemberService;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class SpringConfig {

//    private final DataSource dataSource;
//
//    @Autowired  // spring이 application.properties 보고 dataSource 만들어서 주입해 줌
//    public SpringConfig(DataSource dataSource) {
//        this.dataSource = dataSource;
//    }

    private final MemberRepository memberRepository;

    public SpringConfig(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
        System.out.println("memberRepository = " + memberRepository.getClass());
    }

    //    private EntityManager em;
//    public SpringConfig(EntityManager em) {
//        this.em = em;
//    }

    // 스프링 빈을 등록한다고 직접 지정
    @Bean
    public MemberService memberService() {
        return new MemberService(memberRepository);
    }

    // 정형화된 계층이 아니니까 따로 등록해주는 걸 추천, Component 로 등록해도 된다.
//    @Bean
//    public TimeTraceAop timeTraceAop() {
//        return new TimeTraceAop();
//    }

//    @Bean
//    public MemberRepository memberRepository() {
        // 구현체 바꿀 때, 여기만 수정하면 됨 (다형성 사용 가능)
//        return new MemoryMemberRepository();
//        return new JdbcMemberRepository(dataSource);
//        return new JdbcTemplateMemberRepository(dataSource);
//        return new JpaMemberRepository(em);
//    }

}
