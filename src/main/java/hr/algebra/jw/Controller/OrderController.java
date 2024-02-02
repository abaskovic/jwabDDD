package hr.algebra.jw.Controller;

import hr.algebra.jw.Dto.ProductDto;
import hr.algebra.jw.Model.Order;
import hr.algebra.jw.Model.Product;
import hr.algebra.jw.Repositories.OrderRepository;
import hr.algebra.jw.Repositories.ProductRepository;
import hr.algebra.jw.Services.OrderService;
import hr.algebra.jw.Services.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Controller
public class OrderController {

    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderRepository orderRepository;

    @GetMapping("user/orders")
    public String showOrders(Model model) {
        List<Order> orders = orderService.findByLoggedUser();
        System.out.println(orders);
        model.addAttribute("orders", orders);
        return "user/orders";
    }

    @GetMapping("admin/orders")
    public String showAllOrders(Model model) {
        List<Order> orders = orderRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
        model.addAttribute("orders", orders);
        System.out.println(orders);
        return "admin/orders/index";
    }


    @GetMapping("admin/orders/search")
    public String searchOrdersByName(@RequestParam(name = "customerName", required = false) String customerName,
                                     @RequestParam(name = "startDate", required = false)  LocalDate startDate,
                                     @RequestParam(name = "endDate", required = false)  LocalDate endDate,
                                     Model model
    ) {

        if (customerName!=null){

        List<Order> searchedOrders = orderService.searchOrdersByCustomerName(customerName);
        model.addAttribute("orders", searchedOrders);
        }else if (startDate!=null && endDate!=null){
            List<Order> searchedOrders = orderService.searchOrdersByDate(startDate,endDate);
            model.addAttribute("orders", searchedOrders);
        }
        return "admin/orders/index";
    }
}


