package hr.algebra.jw.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LogDto {
    private Long userId;
    private Date createdAt;
    private String address;
}


