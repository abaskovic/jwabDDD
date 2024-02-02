package hr.algebra.jw.Controller;

import hr.algebra.jw.Dto.CategoryDto;
import hr.algebra.jw.Dto.OrderDto;
import hr.algebra.jw.Dto.OrderItemDto;
import hr.algebra.jw.Model.Cart;
import hr.algebra.jw.Model.CartItem;
import hr.algebra.jw.Model.Product;
import hr.algebra.jw.Repositories.ProductRepository;
import hr.algebra.jw.Services.OrderService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Controller
public class CheckoutController {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderService orderService;

    private Cart cart;

    public Cart getCart(HttpSession session) {
        cart = (Cart) session.getAttribute("cart");
        if (cart == null) {
            cart = new Cart();
            session.setAttribute("cart", cart);
        }
        return cart;
    }

    private double calculateTotal(Cart cart) {
        return cart.getCartItems().stream()
                .mapToDouble(item -> item.getProduct().getPrice() * item.getQuantity())
                .sum();
    }

    @GetMapping("user/checkout")
    public String showCart(Model model, HttpSession session) {
        getCart(session);
        OrderDto orderDto= new OrderDto();
        model.addAttribute("cart", cart);
        model.addAttribute("orderDto", orderDto);
        model.addAttribute("total", calculateTotal(cart));
        return "user/checkout";
    }

    private static List<OrderItemDto> convertCartItemsToOrderItemDtos(List<CartItem> cart) {

        List<OrderItemDto> orderItemDtos = cart.stream()
                .map(cartItem -> {
                    OrderItemDto orderItemDto = new OrderItemDto();
                    orderItemDto.setProductId(cartItem.getProduct().getId());
                    orderItemDto.setQuantity(cartItem.getQuantity());
                    return orderItemDto;
                })
                .collect(Collectors.toList());
        return orderItemDtos;
    }

    @PostMapping("user/checkout")
    public String saveOrder(Model model,@Valid @ModelAttribute OrderDto orderDto,
                            BindingResult result) {

        model.addAttribute("cart", cart);
        model.addAttribute("total", calculateTotal(cart));
        if (result.hasErrors()) {
            return "user/checkout";
        }

        orderDto.setOrderItems(convertCartItemsToOrderItemDtos(cart.getCartItems()));
        orderDto.setOrderTime(LocalDate.now());
        orderDto.setTotalPrice(calculateTotal(cart));
        orderService.save(orderDto);
        return "redirect:/user/orders";
    }


}
