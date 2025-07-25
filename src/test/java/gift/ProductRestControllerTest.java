package gift;

import gift.Entity.Product;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClient;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql("/data.sql")
public class ProductRestControllerTest {
    @LocalServerPort
    private int port;

    private RestClient client = RestClient.builder().build();


    @Transactional
    @Test
    public void testCreateProduct() {
        var url = "http://localhost:" + port + "/products";
        var newProduct = new Product(10L, "민트 초코 라떼", 5500, "https://test");
        var response = client.post()
                .uri(url)
                .body(newProduct)
                .retrieve()
                .toEntity(Product.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        var actual = response.getBody();
        assertThat(actual.getName()).isEqualTo("민트 초코 라떼");
    }


    @Test
    public void testReadProduct() {
        var url = "http://localhost:" + port + "/products/1";
        var response = client.get()
                .uri(url)
                .retrieve()
                .toEntity(Product.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        var actual = response.getBody();
        assertThat(actual.getName()).isEqualTo("아이스 카페 아메리카노 T");
    }

    // 처음에 첫번째 데이터를 넣고 시작하니 1번째 데이터를 업데이트하여 테스트
    @Test
    public void testUpdateProduct() {
        var url = "http://localhost:" + port + "/products/1";
        var updateProduct = new Product(1L, "에스프레소", 5500, "https://test.com/update");
        var response = client.post()
                .uri(url)
                .body(updateProduct)
                .retrieve()
                .toEntity(Product.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        var actual = response.getBody();
        assertThat(actual.getName()).isEqualTo("에스프레소");
    }


}
