package hr.algebra.jw.Controller;

import hr.algebra.jw.Dto.CategoryDto;
import hr.algebra.jw.Model.Category;
import hr.algebra.jw.Model.Log;
import hr.algebra.jw.Repositories.CategoryRepository;
import hr.algebra.jw.Repositories.LogRepository;
import hr.algebra.jw.Services.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/admin/log")
@Controller
public class LogController {
    @Autowired
    private LogRepository repository;

    @GetMapping("")
    public String showCategoryList(Model model) {
        List<Log> logs = repository.findAll(Sort.by(Sort.Direction.DESC, "id"));
        model.addAttribute("list", logs);
        return "admin/log/index";
    }



}


