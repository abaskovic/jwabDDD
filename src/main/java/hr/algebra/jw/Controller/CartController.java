package hr.algebra.jw.Controller;

import hr.algebra.jw.Model.Cart;
import hr.algebra.jw.Model.CartItem;
import hr.algebra.jw.Model.Product;
import hr.algebra.jw.Repositories.ProductRepository;
import hr.algebra.jw.Services.ProductService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@Controller
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private ProductRepository productRepository;

    public Cart getCart(HttpSession session) {
        Cart cart = (Cart) session.getAttribute("cart");
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

    @GetMapping("")
    public String showCart(Model model, HttpSession session) {
        Cart cart = getCart(session);
        model.addAttribute("cart", cart);
        model.addAttribute("total", calculateTotal(cart));
        return "cart/index";
    }

    @PostMapping("/add")
    public ResponseEntity<String> addItemToCart(@RequestParam Long productId, @RequestParam int quantity, HttpSession session) {
        System.out.println("Product id: " + productId);
        System.out.println("Quantity: " + quantity);
        Product product = productRepository.findById(productId).get();


        System.out.println("Product: " + product);
        if (product == null) {
            return ResponseEntity.badRequest().body("Product not found");
        }

        Cart cart = getCart(session);
        System.out.println("Cart: " + cart);

        Optional<CartItem> existingItem = cart.getCartItems().stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst();

        if (existingItem.isPresent()) {
            updateCartItem(existingItem.get().getProduct().getId(), existingItem.get().getQuantity() + quantity, session);
        } else {
            CartItem cartItem = new CartItem(product, quantity);
            cart.addCartItem(cartItem);
        }

        session.setAttribute("cart", cart);


        return ResponseEntity.ok("Product added to cart");
    }


    @PostMapping("/update")
    public ResponseEntity<String> updateCartItem(@RequestParam Long productId, @RequestParam int quantity, HttpSession session) {
        Cart cart = getCart(session);

        cart.updateCartItem(productId, quantity);

        session.setAttribute("cart", cart);

        return ResponseEntity.ok("Quantity updated successfully");
    }

    @PostMapping("/remove")
    public String removeItemFromCart(@RequestParam Long productId, HttpSession session) {
        Cart cart = getCart(session);

        cart.removeCartItem(productId);

        session.setAttribute("cart", cart);

        return "redirect:/cart";
    }

    @PostMapping("/removeAll")
    public String removeAllItemsFromCart(HttpSession session) {
        session.removeAttribute("cart");

        return "redirect:/cart";
    }

}
