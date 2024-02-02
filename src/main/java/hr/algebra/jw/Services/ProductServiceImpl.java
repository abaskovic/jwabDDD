package hr.algebra.jw.Services;

import hr.algebra.jw.Dto.ProductDto;
import hr.algebra.jw.Model.Category;
import hr.algebra.jw.Model.Product;
import hr.algebra.jw.Repositories.CategoryRepository;
import hr.algebra.jw.Repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository repository;

    private List<String> eventMessages = new ArrayList<>();
    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public Product save(ProductDto dto) {
        Category category = categoryRepository.findById(dto.getCategoryId()).get();

        Product product = new Product(dto.getName(), category, dto.getPrice(), dto.getDescription(), dto.getImageFileName(), dto.getCreatedAt());
        return repository.save(product);
    }

    @Override
    public Product update(ProductDto dto, long id) {
        Product existingProduct = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));
        Category category = categoryRepository.findById(dto.getCategoryId()).get();

        existingProduct.setName(dto.getName());
        existingProduct.setCategory(category);
        existingProduct.setPrice(dto.getPrice());
        existingProduct.setDescription(dto.getDescription());
        existingProduct.setImageFileName(dto.getImageFileName());
        existingProduct.setCreatedAt(dto.getCreatedAt());
        return repository.save(existingProduct);
    }

    public List<Category> findAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public void addEventMessage(String eventMessage) {
        if (eventMessages.size() >= 3) {
            eventMessages.remove(0);
        }
        eventMessages.add(eventMessage);
    }

    @Override
    public List<String> getEventMessages() {
        return new ArrayList<>(eventMessages);
    }


}
