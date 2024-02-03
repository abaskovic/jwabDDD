package hr.algebra.jw.Dto;

import hr.algebra.jw.Model.Image;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {
    @NotEmpty(message = "Name cannot be empty")
    private String name;
    private Long categoryId;
    @Min(0)
    private double price;
    @Size(min = 10, message = "The description should be at least 10 characters")
    @Column(columnDefinition = "TEXT")
    private String description;
    private Date createdAt;
    private MultipartFile imageFile;
    private Image image;

}
