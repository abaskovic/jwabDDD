package hr.algebra.jw.Services;

import hr.algebra.jw.Dto.ProductDto;
import hr.algebra.jw.Model.Category;
import hr.algebra.jw.Model.Product;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface ProductService {
    Product save(ProductDto productDto);
    Product update(ProductDto productDto, long id);
    public List<Category> findAllCategories();

}
