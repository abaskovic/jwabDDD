package hr.algebra.jw.Dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {
    private Long id;
    private List<OrderItemDto> orderItems;
    private Long userId;
    private LocalDate orderTime;
    @NotEmpty(message = "Payment method cannot be empty")
    private String paymentMethod;
    @NotEmpty(message = "Name cannot be empty")
    private String postalCode;
    @NotEmpty(message = "Name cannot be empty")
    private String address;
    private Double totalPrice;
}
