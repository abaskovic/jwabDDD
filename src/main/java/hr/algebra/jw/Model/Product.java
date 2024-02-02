package hr.algebra.jw.Model;

import com.fasterxml.jackson.databind.DatabindException;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "productsN")
@Data
@AllArgsConstructor
@NoArgsConstructor
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
    private String imageFileName;

    public Product(String name, Category category, double price, String description, String imageFileName, Date createdAt) {
        this.name = name;
        this.category = category;
        this.price = price;
        this.description = description;
        this.imageFileName = imageFileName;
        this.createdAt = createdAt;
    }
}
