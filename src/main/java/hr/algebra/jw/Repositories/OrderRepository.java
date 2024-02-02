package hr.algebra.jw.Repositories;

import hr.algebra.jw.Model.Order;
import hr.algebra.jw.Model.Product;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order,Long> {
    List<Order> findByUserId(Long userId);
}
