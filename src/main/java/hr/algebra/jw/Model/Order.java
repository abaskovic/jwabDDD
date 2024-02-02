package hr.algebra.jw.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "orders")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "order_id")
    private List<OrderItem> cartItems;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private LocalDate orderTime;

    private String paymentMethod;

    private String postalCode;

    private String address;
    private Double totalPrice;

}
