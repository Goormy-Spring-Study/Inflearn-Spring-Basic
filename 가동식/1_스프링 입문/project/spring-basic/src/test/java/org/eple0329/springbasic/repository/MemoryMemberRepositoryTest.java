package org.eple0329.springbasic.repository;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import org.eple0329.springbasic.domain.Member;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

class MemoryMemberRepositoryTest {

        MemoryMemberRepository repository = new MemoryMemberRepository();

        // 각 테스트가 종료될 때마다 실행
        @AfterEach
        public void afterEach() {
                // 데이터 초기화
                repository.clearStore();
        }

        @Test
        public void save() {
                // Given
                Member member = new Member();
                member.setName("spring");

                // When
                repository.save(member);

                // Then
                Member result = repository.findById(member.getId()).get();
                assertThat(result).isEqualTo(member);
        }

        @Test
        public void findById() {
                // Given
                Member member1 = new Member();
                member1.setName("spring1");
                repository.save(member1);
                Member member2 = new Member();
                member2.setName("spring2");
                repository.save(member2);

                // When
                Member result = repository.findById(member1.getId()).get();

                // Then
                assertThat(result).isEqualTo(member1);
        }

        @Test
        public void findAll() {
                // Given
                Member member1 = new Member();
                member1.setName("spring1");
                repository.save(member1);
                Member member2 = new Member();
                member2.setName("spring2");
                repository.save(member2);

                // When
                // Then
                assertThat(repository.findAll().size()).isEqualTo(2);
        }

        @Test
        public void findByName() {
                // Given
                Member member1 = new Member();
                member1.setName("spring1");
                repository.save(member1);
                Member member2 = new Member();
                member2.setName("spring2");
                repository.save(member2);

                // When
                Member result = repository.findByName("spring1").get();

                // Then
                assertThat(result).isEqualTo(member1);
        }

}