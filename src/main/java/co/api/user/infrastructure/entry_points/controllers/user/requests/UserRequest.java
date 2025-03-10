package co.api.user.infrastructure.entry_points.controllers.user.requests;

import jakarta.validation.constraints.*;
import lombok.Builder;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;

@Builder
public record UserRequest(

        @NotBlank(message = "El nombre no puede estar vacío")
        String firstName,

        @NotBlank(message = "El apellido no puede estar vacío")
        String lastName,

        @NotNull(message = "El RUT no puede estar vacío")
        Long rut,

        @NotBlank(message = "El dígito verificador no puede estar vacío")
        @Pattern(regexp = "^[0-9kK]$", message = "El dígito verificador debe ser un número o la letra 'K'")
        String dv,

        @NotNull(message = "La fecha de nacimiento no puede estar vacía")
        @Past(message = "La fecha de nacimiento debe ser en el pasado")
        LocalDate birthDate,

        @Email
        @NotBlank(message = "El correo electrónico no puede estar vacío")
        String email,

        @NotBlank(message = "La contraseña no puede estar vacía")
        @Length(min = 8, max = 20, message = "La contraseña debe tener entre 8 y 20 caracteres.")
        @Pattern(
                regexp = "^(?!.*\\s)(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–\\[{}\\]:;',?/*~$^+=<>]).{8,20}$",
                message = """
            La contraseña debe cumplir con los siguientes requisitos:
            - Al menos un carácter especial.
            - Al menos una letra mayúscula.
            - Al menos una letra minúscula.
            - Al menos un número.
            """
        )
        String password,

        @Builder.Default
        Boolean active

) {}
