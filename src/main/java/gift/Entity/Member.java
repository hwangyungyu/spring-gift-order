package gift.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "members")
public class Member {

    @Id
    @NotBlank(message = "아이디는 필수입니다.")
    @Size(min = 5, message = "아이디는 5자 이상이어야 합니다.")
    private String id;

    @NotBlank(message = "이메일은 필수입니다.")
    @Email(message = "유효한 이메일 형식이어야 합니다.")
    private String email;

    @NotBlank(message = "비밀번호는 필수입니다.")
    @Size(min = 9, message = "비밀번호는 9자 이상이어야 합니다.")
    private String password;

    @NotBlank(message = "이름은 필수입니다.")
    private String name;

    @NotBlank(message = "주소는 필수입니다.")
    private String address;

    private String role = "USER";  // 기본 권한

    // 생성자
    public Member() {}

    public Member(String id, String email, String password, String name, String address, String role) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.name = name;
        this.address = address;
        this.role = role;
    }

    // Getter
    public String getId() { return id; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public String getName() { return name; }
    public String getAddress() { return address; }
    public String getRole() { return role; }

    // Setter
    public void setId(String id) { this.id = id; }
    public void setEmail(String email) { this.email = email; }
    public void setPassword(String password) { this.password = password; }
    public void setName(String name) { this.name = name; }
    public void setAddress(String address) { this.address = address; }
    public void setRole(String role) { this.role = role; }

    public boolean isAdmin() {
        return "ADMIN".equalsIgnoreCase(this.role);
    }

}
