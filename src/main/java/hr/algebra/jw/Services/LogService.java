package hr.algebra.jw.Services;

import hr.algebra.jw.Dto.LogDto;
import hr.algebra.jw.Dto.UserDto;
import hr.algebra.jw.Model.Log;
import hr.algebra.jw.Model.User;

public interface LogService {
    Log save(LogDto logDto);
}
