package hr.algebra.jw.Controller;

import hr.algebra.jw.Dto.ProductDto;
import hr.algebra.jw.Model.Product;
import hr.algebra.jw.Services.ProductService;
import hr.algebra.jw.Repositories.ProductRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
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
import java.util.Date;
import java.util.List;

@RequestMapping("/admin/products")
@Controller
public class ProductController {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductService productService;

    @GetMapping("")
    public String showProductList(Model model) {
        List<Product> products = productRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));

        List<String> eventMessages = productService.getEventMessages();
        System.out.println(eventMessages);
        model.addAttribute("eventMessages", eventMessages);

        model.addAttribute("products", products);
        return "admin/products/index";
    }

    @GetMapping("/create")
    public String addProduct(Model model) {
        ProductDto productDto = new ProductDto();
        model.addAttribute("productDto", productDto);
        model.addAttribute("categories", productService.findAllCategories());
        return "admin/products/create";
    }

    @PostMapping("/create")
    public String addProduct(Model model,@Valid @ModelAttribute ProductDto productDto,
                             BindingResult result) {
        model.addAttribute("categories", productService.findAllCategories());

        if (productDto.getImageFile().isEmpty()) {
            result.addError(new FieldError("productDto", "imageFile", "The image file cannot be empty"));
        }

        if (result.hasErrors()) {
            return "admin/products/create";
        }

        MultipartFile image = productDto.getImageFile();
        Date createdAt = new Date();

        String storageFileName = createdAt.getTime() + "_" + image.getOriginalFilename();
        productDto.setImageFileName(storageFileName);
        productDto.setCreatedAt(createdAt);

        try {
            String uploadDir = "public/image/";
            Path uploadPath = Paths.get(uploadDir);

            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }
            try (InputStream inputStream = image.getInputStream()) {
                Files.copy(inputStream, Paths.get(uploadDir + storageFileName), StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        productService.save(productDto);
        return "redirect:/admin/products";
    }

    @GetMapping("/edit")
    public String editProduct(Model model, @RequestParam Long id) {

        try {
            model.addAttribute("categories", productService.findAllCategories());
            Product product = productRepository.findById(id).get();
            ProductDto productDto = new ProductDto();
            productDto.setName(product.getName());
            productDto.setDescription(product.getDescription());
            productDto.setPrice(product.getPrice());
            productDto.setCategoryId(product.getCategory().getId());
            model.addAttribute("product", product);
            model.addAttribute("productDto", productDto);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return "admin/products/edit";
    }


    @PostMapping("/edit")
    public String editProduct(Model model, @RequestParam Long id, @Valid @ModelAttribute ProductDto productDto, BindingResult result) {

        try {
            Product product = productRepository.findById(id).get();
            model.addAttribute("categories", productService.findAllCategories());
            model.addAttribute("product", product);

            if (result.hasErrors()) {
                return "admin/products/edit";
            }

            if (!productDto.getImageFile().isEmpty()) {
                try {
                    deleteImage(product);
                    String uploadDir = "public/image/";


                    MultipartFile image = productDto.getImageFile();
                    Date createdAt = new Date();

                    String storageFileName = createdAt.getTime() + "_" + image.getOriginalFilename();
                    productDto.setImageFileName(storageFileName);

                    try (InputStream inputStream = image.getInputStream()) {
                        Files.copy(inputStream, Paths.get(uploadDir + storageFileName), StandardCopyOption.REPLACE_EXISTING);
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }else {
                productDto.setImageFileName(product.getImageFileName());
            }
            productDto.setCreatedAt(product.getCreatedAt());
            productService.update(productDto, product.getId());

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "redirect:/admin/products";
        }

        return "redirect:/admin/products";
    }

    @GetMapping("/details")
    public String showProduct(Model model, @RequestParam Long id) {
        try {
            model.addAttribute("categories", productService.findAllCategories());
            Product product = productRepository.findById(id).get();
            model.addAttribute("product", product);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return "admin/products/details";
    }

    @GetMapping("/delete")
    public String deleteProduct(@RequestParam Long id) {
        try {
            Product product = productRepository.findById(id).get();

            deleteImage(product);

            productRepository.delete(product);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return "redirect:/admin/products";
    }

    private static void deleteImage(Product product) {
        String uploadDir = "public/image/";
        Path oldImagePath = Paths.get(uploadDir + product.getImageFileName());

        try {
            Files.delete(oldImagePath);
        } catch (Exception ex) {
            System.out.println("Ex" + ex.getMessage());
        }
    }


}


