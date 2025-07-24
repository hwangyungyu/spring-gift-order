package gift;

import gift.Entity.Member;

public class LoginResult {
    private final String token;
    private final Member member;

    public LoginResult(String token, Member member) {
        this.token = token;
        this.member = member;
    }

    public String getToken() { return token; }
    public Member getMember() { return member; }
}