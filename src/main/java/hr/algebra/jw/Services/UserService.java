package hr.algebra.jw.Services;

import hr.algebra.jw.Dto.UserDto;
import hr.algebra.jw.Model.User;

public interface UserService {
    User save(UserDto userDto);
}
