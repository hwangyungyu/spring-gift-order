package gift.response;

import gift.Entity.Member;

public class TokenResponse {
    private String token;
    private Member member;
    private String role;

    public TokenResponse(String token, Member member) {
        this.token = token;
        this.member = member;
        this.role = member.getRole();
    }

    public String getToken() {
        return token;
    }
    public Member getMember() {
        return member;
    }
    public String getRole() {
        return role;
    }
}

