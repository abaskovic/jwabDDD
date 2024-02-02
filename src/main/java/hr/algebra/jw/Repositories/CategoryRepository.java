package hr.algebra.jw.Repositories;

import hr.algebra.jw.Model.Category;
import hr.algebra.jw.Model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category,Long> {

}
