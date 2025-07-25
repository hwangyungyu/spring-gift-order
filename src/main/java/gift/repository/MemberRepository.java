package gift.repository;

import gift.Entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, String> {

    // 이메일로 멤버 조회
    Optional<Member> findByEmail(String email);
}
