package hr.algebra.jw.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "Users", uniqueConstraints = @UniqueConstraint(columnNames = "email"))
public class User {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String password;
    private String role;
    private String fullname;

    @Override
    public String toString() {
        return
                 fullname  +
                " - "  +email ;
    }

    public User(String email, String password, String role, String fullname) {
        this.email = email;
        this.password = password;
        this.role = role;
        this.fullname = fullname;
    }
}
