package co.api.user.domain.model.user;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class User {

    private Long id;

    private String firstName;

    private String lastName;

    private Long rut;

    private String dv;

    private LocalDate birthDate;

    private String email;

    private String password;

    @Builder.Default
    private Boolean active = Boolean.TRUE;
}
