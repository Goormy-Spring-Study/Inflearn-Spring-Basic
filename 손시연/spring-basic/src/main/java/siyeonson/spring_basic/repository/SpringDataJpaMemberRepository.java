package siyeonson.spring_basic.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import siyeonson.spring_basic.domain.Member;

import java.util.Optional;

public interface SpringDataJpaMemberRepository extends JpaRepository<Member, Long>, MemberRepository {

    @Override
    Optional<Member> findByName(String name);

}
