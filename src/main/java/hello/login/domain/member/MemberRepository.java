package hello.login.domain.member;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.*;

@Slf4j
@Repository
public class MemberRepository {

    private static Map<Long, Member> store = new HashMap<>();
    private static Long sequance = 0L;

    public Member save(Member member) {
        member.setId(sequance++);
        log.info("save: member={}", member);
        store.put(member.getId(), member);
        return member;
    }

    public Member findById(Long id) {
        return store.get(id);
    }

    public Optional<Member> findByLoginId(String loginId) {
        /*List<Member> members = findAll();
        for (Member member : members) {
            if(member.getLoginId().equals(loginId)) {
                return Optional.of(member);
            }
        }
        return Optional.empty();*/

        return findAll().stream()
                .filter(member -> member.getLoginId().equals(loginId))
                .findFirst();
    }

    public List<Member> findAll() {
        List<Member> members = new ArrayList<>(store.values());
        return members;
    }

    public void clearStore() {
        store.clear();
    }
}
