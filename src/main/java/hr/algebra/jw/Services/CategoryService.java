package hr.algebra.jw.Services;

import hr.algebra.jw.Dto.CategoryDto;
import hr.algebra.jw.Dto.ProductDto;
import hr.algebra.jw.Model.Category;
import hr.algebra.jw.Model.Product;

public interface CategoryService {
    Category save(CategoryDto dto);
    Category update(CategoryDto dto, long id);
}
