package hr.algebra.jw.Services;

import hr.algebra.jw.Dto.CategoryDto;
import hr.algebra.jw.Dto.ProductDto;
import hr.algebra.jw.Model.Category;
import hr.algebra.jw.Model.Product;
import hr.algebra.jw.Repositories.CategoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository repository;

    @Override
    public Category save(CategoryDto dto) {
        Category category =new Category(dto.getName());
        return repository.save(category);
    }

    @Override
    public Category update(CategoryDto dto, long id) {
        Category existingCategory = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        existingCategory.setName(dto.getName());
        return repository.save(existingCategory);
    }
}
