package gift;

import gift.Entity.Member;
import gift.Entity.Option;
import gift.Entity.Product;
import gift.Entity.Wish;
import gift.repository.WishRepository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@DataJpaTest
public class WishRepositoryTest {

    @Autowired
    private WishRepository wishRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    void testDeleteWish() {
        Member member = entityManager.persist(new Member("deleteUser", "d@k.com", "pw", "유저", "주소", "USER"));
        Product product = entityManager.persist(new Product(2L, "라떼", 3000, "https://latte.com"));
        Option option = entityManager.persist(new Option("HOT", 5, product));
        Wish wish = wishRepository.save(new Wish(member, product, option));

        wishRepository.delete(wish);

        List<Wish> result = wishRepository.findAll();
        assertThat(result).isEmpty();
    }

    @Test
    void testSaveWishWithOption() {
        // given
        Member member = entityManager.persist(new Member("testId", "test@kakao.com", "123456789", "테스트", "테스트 주소", "USER"));
        Product product = entityManager.persist(new Product(5L, "아메리카노", 2000, "https://test.com"));
        Option option = entityManager.persist(new Option("ICE", 10, product));
        Wish wish = new Wish(member, product, option);

        // when
        wishRepository.save(wish);

        // then
        List<Wish> result = wishRepository.findAll();
        assertThat(result).hasSize(1);
        Wish saved = result.get(0);
        assertThat(saved.getMember().getId()).isEqualTo("testId");
        assertThat(saved.getProduct().getName()).isEqualTo("아메리카노");
        assertThat(saved.getOption().getName()).isEqualTo("ICE");
        assertThat(saved.getOption().getQuantity()).isEqualTo(10);
    }

    @Test
    void testDuplicateWishNotAllowed() {
        Member member = entityManager.persist(new Member("dupe", "dupe@k.com", "pw", "듀플", "주소", "USER"));
        Product product = entityManager.persist(new Product(3L, "녹차라떼", 3500, "https://green.com"));
        Option option = entityManager.persist(new Option("Regular", 7, product));

        Wish wish1 = new Wish(member, product, option);
        Wish wish2 = new Wish(member, product, option);

        wishRepository.save(wish1);

        // 중복 저장 시도 시 예외 발생 가능성 테스트
        assertThatThrownBy(() -> wishRepository.save(wish2))
                .isInstanceOf(Exception.class); // or use DataIntegrityViolationException
    }
}
