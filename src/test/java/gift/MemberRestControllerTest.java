package gift;

import gift.Entity.Member;
import gift.repository.MemberRepository;
import gift.request.MemberRequest;
import gift.response.TokenResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClient;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql("/data.sql")
public class MemberRestControllerTest {

    @LocalServerPort
    private int port;

    private RestClient client = RestClient.builder().build();

    @Autowired
    private MemberRepository memberRepository;

    @BeforeEach
    void setupTestMember() {

        // 테스트용 계정 등록
        Member member = new Member("helloworld", "hello@kakao.com", "123456789", "테스트", "대한민국", "USER");
        memberRepository.save(member);
    }

    @Transactional
    @Test
    public void testRegisterMember() {
        var url = "http://localhost:" + port + "/api/register";
        var member = new Member("byeworld", "byeworld@kakao.com", "123456789", "안녕세상", "대한민국", "USER");

        var response = client.post()
                .uri(url)
                .body(member)
                .retrieve()
                .toEntity(Member.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getId()).isEqualTo("byeworld");
    }


    @Test
    public void testLogin() {
        var url = "http://localhost:" + port + "/api/login";
        var req = new MemberRequest("helloworld", "123456789", "null");

        var response = client.post()
                .uri(url)
                .body(req)
                .retrieve()
                .toEntity(String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void testRegisterduplicateId() {
        var url = "http://localhost:" + port + "/api/register";
        var duplicate = new Member("helloworld", "new@kakao.com", "password", "중복유저", "주소", "USER");

        var response = client.post()
                .uri(url)
                .body(duplicate)
                .retrieve()
                .toEntity(String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).contains("이미 사용 중인 아이디입니다.");
    }

    @Test
    public void logincheck() {
        var loginUrl = "http://localhost:" + port + "/api/login";
        var loginReq = new MemberRequest("helloworld", "123456789", null);

        var loginRes = client.post()
                .uri(loginUrl)
                .body(loginReq)
                .retrieve()
                .toEntity(TokenResponse.class);

        String token = loginRes.getBody().getToken();

        var productsPageUrl = "http://localhost:" + port + "/user/products";
        var html = client.get()
                .uri(productsPageUrl)
                .header("Authorization", "Bearer " + token)
                .retrieve()
                .body(String.class);

        assertThat(html).contains("helloworld님, 안녕하세요!");
    }

    @Test
    public void testLoginAsAdminAndUser() {
        // 관리자 계정 생성
        Member admin = new Member("admin01", "admin@kakao.com", "adminpw", "관리자", "서울", "ADMIN");
        memberRepository.save(admin);

        // 유저 계정은 @BeforeEach에서 저장됨

        // 관리자 로그인
        var adminLoginRes = client.post()
                .uri("http://localhost:" + port + "/api/login")
                .body(new MemberRequest("admin01", "adminpw", null))
                .retrieve()
                .toEntity(TokenResponse.class);

        assertThat(adminLoginRes.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(adminLoginRes.getBody().getRole()).isEqualTo("ADMIN");

        // 유저 로그인
        var userLoginRes = client.post()
                .uri("http://localhost:" + port + "/api/login")
                .body(new MemberRequest("helloworld", "123456789", null))
                .retrieve()
                .toEntity(TokenResponse.class);

        assertThat(userLoginRes.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(userLoginRes.getBody().getRole()).isEqualTo("USER");
    }


}
