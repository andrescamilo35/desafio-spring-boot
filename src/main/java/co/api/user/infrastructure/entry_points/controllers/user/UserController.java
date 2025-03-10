package co.api.user.infrastructure.entry_points.controllers.user;

import co.api.user.domain.model.common.ListResponse;
import co.api.user.domain.model.common.PageRequests;
import co.api.user.domain.model.common.exception.ErrorException;
import co.api.user.domain.model.user.User;
import co.api.user.domain.usecase.user.UserUseCase;
import co.api.user.infrastructure.entry_points.controllers.user.requests.UserRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/usuarios")
@RequiredArgsConstructor
@Tag(name = "API de Usuarios", description = "Operaciones relacionadas con la gestión de usuarios")
public class UserController {

    private final UserUseCase userUseCase;

    @Operation(summary = "Obtener todos los usuarios", description = "Recupera una lista paginada de todos los usuarios registrados en el sistema")
    @GetMapping
    public ResponseEntity<ListResponse<User>> getUserAll(@RequestParam int page, @RequestParam int size) throws Exception {

        PageRequests pageRequests = PageRequests.builder().page(page).size(size).build();

        return ResponseEntity.ok(userUseCase.findAll(pageRequests));
    }

    @Operation(summary = "Obtener usuario por ID", description = "Recupera los detalles de un usuario específico utilizando su ID")
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) throws ErrorException {
        return ResponseEntity.ok(userUseCase.findById(id));
    }

    @Operation(summary = "Crear un nuevo usuario", description = "Registra un nuevo usuario en el sistema")
    @PostMapping
    public ResponseEntity<User> createUser(@Valid @RequestBody UserRequest request) throws ErrorException {

        User user =  User.builder().
                        firstName(request.firstName()).
                        lastName(request.lastName()).
                        rut(request.rut()).
                        dv(request.dv()).
                        email(request.email()).
                        password(request.password()).
                        birthDate(request.birthDate()).
                        build();

        return ResponseEntity.ok(userUseCase.save(user));
    }

    @Operation(summary = "Actualizar un usuario existente", description = "Modifica los datos de un usuario utilizando su ID")
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @Valid @RequestBody UserRequest request) throws ErrorException {

        User user = User.builder().
                    id(id).
                    firstName(request.firstName()).
                    lastName(request.lastName()).
                    rut(request.rut()).
                    dv(request.dv()).
                    email(request.email()).
                    password(request.password()).
                    birthDate(request.birthDate()).
                    build();
        return ResponseEntity.ok(userUseCase.updateUser(user));
    }

    @Operation(summary = "Eliminar un usuario", description = "Desactiva un usuario del sistema utilizando su ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) throws ErrorException {
        return ResponseEntity.ok(userUseCase.deleteUser(id));
    }
}
