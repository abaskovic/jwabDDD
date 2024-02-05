package hr.algebra.jw.Controller;

import hr.algebra.jw.Model.Category;
import hr.algebra.jw.Model.Product;
import hr.algebra.jw.Repositories.ProductRepository;
import hr.algebra.jw.Services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class ShopController {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductService productService;

    @GetMapping("")
    public String showProducts(Model model, @RequestParam(required = false) Long categoryId) {
        List<Category> categories = productService.findAllCategories();
        Map<Long, Integer> productQuantities = new HashMap<>();

        List<Product> products;
        if (categoryId != null) {
            products = productRepository.findByCategoryId(categoryId, Sort.by(Sort.Direction.DESC, "id"));
        } else {
            products = productRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
        }
        for (Product product : products) {
            productQuantities.put(product.getId(), 1);
        }
        model.addAttribute("products", products);
        model.addAttribute("categories", categories);
        model.addAttribute("productQuantities", productQuantities);

        return "shop/index";
    }



    @GetMapping("/details")
    public String showProduct(Model model, @RequestParam Long id) {
        try {
            Product product = productRepository.findById(id).get();
            model.addAttribute("product", product);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return "shop/details";
    }


}



