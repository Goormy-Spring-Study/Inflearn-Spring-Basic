package org.eple0329.springbasic.repository;

import java.util.Optional;
import org.eple0329.springbasic.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataJpaMemberRepository extends JpaRepository<Member, Long>,
        MemberRepository {

        Optional<Member> findByName(String name);

}
