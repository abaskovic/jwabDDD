package hr.algebra.jw.Services;

import hr.algebra.jw.Dto.OrderDto;
import hr.algebra.jw.Model.Order;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface OrderService {
    Order save(OrderDto dto);

    List<Order> findByLoggedUser ();

    List<Order> searchOrdersByCustomerName(String customerName);

    List<Order> searchOrdersByDate(LocalDate startDate, LocalDate endDate);
}
