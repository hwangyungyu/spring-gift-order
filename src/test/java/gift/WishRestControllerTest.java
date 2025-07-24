package gift;

import gift.Entity.Member;
import gift.Entity.Product;
import gift.repository.MemberRepository;
import gift.repository.ProductRepository;
import gift.request.MemberRequest;
import gift.response.TokenResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.RestClient;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class WishRestControllerTest {

    @LocalServerPort
    int port;

    RestClient client = RestClient.builder().build();

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    ProductRepository productRepository;

    String token;

    @BeforeEach
    void setup() {
        Member member = new Member("helloworld", "hello@kakao.com", "123456789", "테스트", "대한민국", "USER");
        memberRepository.save(member);

        Product product = new Product();
        product.setName("테스트 상품");
        product.setPrice(3000);
        product.setImageUrl("http://image.com");
        product.setMDapproved(true);
        productRepository.save(product);

        // 로그인
        var loginRes = client.post()
                .uri("http://localhost:" + port + "/api/login")
                .body(new MemberRequest("helloworld", "123456789", null))
                .retrieve()
                .toEntity(TokenResponse.class);

        token = loginRes.getBody().getToken();
    }

    @Test
    void testAddWish() {
        Long productId = productRepository.findAll().get(0).getId();
        var url = "http://localhost:" + port + "/user/wishes/" + productId + "/wish";

        var response = client.post()
                .uri(url)
                .header("Authorization", "Bearer " + token)
                .retrieve()
                .toBodilessEntity();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FOUND); // 리디렉션됨 (302)
    }

    @Test
    void testRemoveWish() {
        Long productId = productRepository.findAll().get(0).getId();

        // 먼저 위시 등록
        client.post()
                .uri("http://localhost:" + port + "/user/wishes/" + productId + "/wish")
                .header("Authorization", "Bearer " + token)
                .retrieve();

        // 위시 제거
        var response = client.post()
                .uri("http://localhost:" + port + "/user/wishes/" + productId + "/delete")
                .header("Authorization", "Bearer " + token)
                .retrieve()
                .toBodilessEntity();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FOUND);
    }
}

