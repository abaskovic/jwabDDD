package hr.algebra.jw.Dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class UserDto {
    @NotEmpty(message = "Email cannot be empty")
    private String email;
    @NotEmpty(message = "Password cannot be empty")
    private String password;
    @NotEmpty(message = "Role cannot be empty")
    private String role;
    @NotEmpty(message = "Fullname cannot be empty")
    private String fullname;


}
