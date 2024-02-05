package hr.algebra.jw.Model;

import com.fasterxml.jackson.databind.DatabindException;
import hr.algebra.jw.Listeners.ProductEntityListener;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import java.util.Date;

@Entity
@Table(name = "productsN")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(ProductEntityListener.class)
public class Product {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
    private double price;
    @Column(columnDefinition = "TEXT")
    private String description;
    private Date createdAt;
    @ManyToOne
    @JoinColumn(name = "image_id")
    private Image image;


    public Product(String name, Category category, double price, String description, Date createdAt) {
        this.name = name;
        this.category = category;
        this.price = price;
        this.description = description;
        this.createdAt = createdAt;
    }
}