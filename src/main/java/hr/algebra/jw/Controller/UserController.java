package hr.algebra.jw.Controller;

import hr.algebra.jw.Model.Log;
import hr.algebra.jw.Repositories.LogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping("/user")
@Controller
public class UserController {
    @Autowired
    private LogRepository repository;




}


