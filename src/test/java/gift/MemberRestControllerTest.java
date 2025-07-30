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
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;

import java.util.Map;

import static com.jayway.jsonpath.internal.path.PathCompiler.fail;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MemberRestControllerTest {

    @LocalServerPort
    private int port;

    private RestClient client = RestClient.builder().build();

    @Autowired
    private MemberRepository memberRepository;

    @BeforeEach
    void setupTestMember() {

        // 테스트용 계정 등록
        Member member = new Member("hello_world", "hello@kakao.com", "123456789", "테스트", "대한민국", "USER");
        memberRepository.save(member);
    }

    @Test
    @Transactional
    public void testRegisterMember() {
        var url = "http://localhost:" + port + "/api/register";
        var member = new Member("bye_world", "byeworld@kakao.com", "123456789", "안녕세상", "대한민국", "USER");

        var response = client.post()
                .uri(url)
                .body(member)
                .retrieve()
                .toEntity(Member.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getNickname()).isEqualTo("bye_world");
    }


    @Test
    public void testLogin() {
        var url = "http://localhost:" + port + "/api/login";
        var req = new MemberRequest("hello_world", "123456789");

        var response = client.post()
                .uri(url)
                .body(req)
                .retrieve()
                .toEntity(String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }


}
