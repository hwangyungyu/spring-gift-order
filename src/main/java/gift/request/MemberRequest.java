package gift.request;

public class MemberRequest {
    private final String id;
    private final String password;
    private final String name;

    public MemberRequest(String id, String password, String name) {
        this.id = id;
        this.password = password;
        this.name = name;
    }

    public String getId() { return id; }
    public String getPassword() { return password; }
    public String getName() { return name; }
}
