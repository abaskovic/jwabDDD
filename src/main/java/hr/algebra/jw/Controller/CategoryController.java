package hr.algebra.jw.Controller;

import hr.algebra.jw.Dto.CategoryDto;
import hr.algebra.jw.Model.Category;
import hr.algebra.jw.Repositories.CategoryRepository;
import hr.algebra.jw.Services.CategoryService;
import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/admin/category")
@Controller
public class CategoryController {
    @Autowired
    private CategoryRepository repository;
    @Autowired
    private CategoryService categoryService;

    @GetMapping("")
    public String showCategoryList(Model model) {
        List<Category> categories = repository.findAll(Sort.by(Sort.Direction.DESC, "id"));
        model.addAttribute("list", categories);
        return "admin/category/index";
    }

    @GetMapping("/create")
    public String addProduct(Model model) {
        CategoryDto categoryDto = new CategoryDto();
        model.addAttribute("categoryDto", categoryDto);
        return "admin/category/create";
    }
    @PostMapping("/create")
    public String addProduct(@Valid @ModelAttribute CategoryDto categoryDto,
                             BindingResult result) {
        if (result.hasErrors()) {
            return   "admin/category/create";
        }
        categoryService.save(categoryDto);
        return "redirect:/admin/category";
    }

    @GetMapping("/edit/{id}")
    public String editProduct(Model model,@PathVariable Long id) {
        try {
            Category category = repository.findById(id).get();
            CategoryDto categoryDto = new CategoryDto();
            categoryDto.setName(category.getName());
            model.addAttribute("category", category);
            model.addAttribute("categoryDto", categoryDto);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return "admin/category/edit";
    }

    @PostMapping("/edit/{id}")
    public String editProduct(Model model, @PathVariable Long id, @Valid @ModelAttribute CategoryDto categoryDto,
                              BindingResult result) {
        try {
            Category category = repository.findById(id).get();
            model.addAttribute("category", category);
            if (result.hasErrors()) {
                return "admin/category/edit";
            }
            categoryService.update(categoryDto,category.getId());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "redirect:/admin/category";
        }

        return "redirect:/admin/category";
    }

    @GetMapping("/details/{id}")
    public String showProduct(Model model, @PathVariable Long id) {
        try {
            System.out.println(id);
            Category category = repository.findById(id).get();
            model.addAttribute("category", category);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return "admin/category/details";
    }

    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable Long id) {
        try {
            Category category = repository.findById(id).get();
            repository.delete(category);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return "redirect:/admin/category";
    }

}


