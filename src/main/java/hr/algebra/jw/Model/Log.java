package hr.algebra.jw.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "logs")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Log {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    private Date createdAt;
    private String address;

    public Log(User user, Date createdAt, String address) {
        this.user = user;
        this.createdAt = createdAt;
        this.address = address;
    }
}
