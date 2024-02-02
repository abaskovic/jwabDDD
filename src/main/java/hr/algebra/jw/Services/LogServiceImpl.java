package hr.algebra.jw.Services;

import hr.algebra.jw.Dto.LogDto;
import hr.algebra.jw.Dto.UserDto;
import hr.algebra.jw.Model.Category;
import hr.algebra.jw.Model.Log;
import hr.algebra.jw.Model.User;
import hr.algebra.jw.Repositories.LogRepository;
import hr.algebra.jw.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class LogServiceImpl implements LogService {

    @Autowired
    private LogRepository repository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public Log save(LogDto logDto) {

        User user = userRepository.findById(logDto.getUserId()).get();
        Date createdAt = new Date();
        Log log = new Log(user, createdAt, logDto.getAddress());
        return repository.save(log);
    }

}
