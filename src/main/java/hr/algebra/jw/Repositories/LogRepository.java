package hr.algebra.jw.Repositories;

import hr.algebra.jw.Model.Log;
import hr.algebra.jw.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LogRepository extends JpaRepository<Log, Long> {
}
