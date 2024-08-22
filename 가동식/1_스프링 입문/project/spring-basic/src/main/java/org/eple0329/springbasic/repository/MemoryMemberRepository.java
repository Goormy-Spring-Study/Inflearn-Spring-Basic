package org.eple0329.springbasic.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.eple0329.springbasic.domain.Member;

public class MemoryMemberRepository implements MemberRepository {

        private static Map<Long, Member> store = new HashMap<>();

        private static long sequence = 0L;

        // Member 저장 -> HashMap에 저장
        @Override
        public Member save(Member member) {
                member.setId(++sequence);
                store.put(member.getId(), member);
                return member;
        }

        // Member ID로 Member 찾기
        @Override
        public Optional<Member> findById(Long id) {
                return Optional.ofNullable(store.get(id));
        }

        // 모든 Member 찾기
        @Override
        public List<Member> findAll() {
                return new ArrayList<>(store.values());
        }

        // Member 이름으로 Member 찾기
        @Override
        public Optional<Member> findByName(String name) {
                return store.values().stream()
                        .filter(member -> member.getName().equals(name))
                        .findAny();
        }

        // HashMap 초기화
        public void clearStore() {
                store.clear();
        }
}