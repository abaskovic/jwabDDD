package hr.algebra.jw.Services;

import hr.algebra.jw.Dto.OrderDto;
import hr.algebra.jw.Dto.OrderItemDto;
import hr.algebra.jw.Model.*;
import hr.algebra.jw.Repositories.OrderRepository;
import hr.algebra.jw.Repositories.ProductRepository;
import hr.algebra.jw.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository repository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private OrderRepository orderRepository;

    private List<CartItem> convertOrderItemsToCartItems(List<OrderItemDto> dto) {
        List<CartItem> cartItems = dto.stream()
                .map(item -> {
                    CartItem cartItem = new CartItem();
                    Product product = productRepository.findById(item.getProductId()).get();
                    cartItem.setProduct(product);
                    cartItem.setQuantity(item.getQuantity());
                    return cartItem;
                }).collect(Collectors.toList());
        return cartItems;
    }

    public OrderItem fromDto(OrderItemDto orderItemDto) {
        OrderItem orderItem = new OrderItem();
        orderItem.setQuantity(orderItemDto.getQuantity());
        orderItem.setProduct(productRepository.findById(orderItemDto.getProductId()).get());

        return orderItem;
    }

    private User loggedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();
        return userRepository.findByEmail(currentUsername);
    }

    @Override
    public Order save(OrderDto dto) {


        List<OrderItem> orderItems = dto.getOrderItems().stream()
                .map(order -> {
                    return fromDto(order);
                })
                .collect(Collectors.toList());

        Order order = new Order();
        order.setCartItems(orderItems);
        order.setOrderTime(dto.getOrderTime());
        order.setAddress(dto.getAddress());
        order.setPostalCode(dto.getPostalCode());
        order.setPaymentMethod(dto.getPaymentMethod());
        order.setUser(loggedUser());
        order.setTotalPrice(dto.getTotalPrice());
        return repository.save(order);
    }

    @Override
    public List<Order> findByLoggedUser() {
        return repository.findByUserId((loggedUser().getId())).stream().toList();
    }



    public List<Order> searchOrdersByCustomerName(String customerName) {
        List<Order> orders = orderRepository.findAll();
        return orders.stream()
                .filter(order -> order.getUser() != null && order.getUser().getFullname() != null &&
                        order.getUser().getFullname().startsWith(customerName))
                .collect(Collectors.toList());
    }


    @Override
    public List<Order> searchOrdersByDate(LocalDate startDate, LocalDate endDate) {
        List<Order> orders = orderRepository.findAll();
        System.out.println(startDate);
        System.out.println(endDate);
        return orders.stream()
                .filter(order -> {
                    return order.getOrderTime() != null && !order.getOrderTime().isBefore(startDate) && !order.getOrderTime().isAfter(endDate);
                })
                .collect(Collectors.toList());
    }
}
