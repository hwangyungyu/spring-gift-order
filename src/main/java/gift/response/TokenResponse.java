package gift.response;

import gift.Entity.Member;

public record TokenResponse(
        String token,
        Member member,
        String role
) {
    public TokenResponse(String token, Member member) {
        this(token, member, member.getRole());
    }
}
