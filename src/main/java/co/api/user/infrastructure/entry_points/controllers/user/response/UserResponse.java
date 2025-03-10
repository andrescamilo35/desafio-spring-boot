package co.api.user.infrastructure.entry_points.controllers.user.response;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class UserResponse {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private Date birthDate;
    private String rut;
    private String dv;
}
