package co.api.user.domain.usecase.user;

import co.api.user.domain.model.common.ListResponse;
import co.api.user.domain.model.common.PageRequests;
import co.api.user.domain.model.common.exception.CodeError;
import co.api.user.domain.model.common.exception.ErrorException;
import co.api.user.domain.model.user.User;
import co.api.user.domain.model.user.gateways.UserGateway;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class UserUseCase {

    private final UserGateway userRepository;

    public ListResponse<User> findAll(PageRequests pageRequests) throws Exception {
        return userRepository.findAll(pageRequests);
    }

    public User save(User user) throws ErrorException {
        log.info("Guardando usuario con email: {}", user.getEmail());

        if (userRepository.existByEmail(user.getEmail())) {
            log.warn("Intento de registro con email ya existente: {}", user.getEmail());
            throw new ErrorException("El email " + user.getEmail() + " ya existe.", CodeError.BAD_REQUEST.getCode());
        }

        user.setActive(true);
        return userRepository.create(user);
    }

    public User findById(Long id) throws ErrorException {
        log.info("Buscando usuario con ID: {}", id);

        if (!userRepository.existsById(id)) {
            log.warn("Usuario no encontrado con ID: {}", id);
            throw new ErrorException("El usuario con el id " + id + " no existe.", CodeError.NOT_FOUND.getCode());
        }

        return userRepository.findById(id);
    }

    public User updateUser(User userUpdate) throws ErrorException {
        if (!userRepository.existsById(userUpdate.getId())) {
            log.warn("Intento de actualización para usuario inexistente: {}", userUpdate.getId());
            throw new ErrorException("El usuario con el id " + userUpdate.getId() + " no existe.", CodeError.NOT_FOUND.getCode());
        }

        User user = userRepository.findById(userUpdate.getId());

        if (!user.getEmail().equals(userUpdate.getEmail()) && userRepository.existByEmail(userUpdate.getEmail())) {
            log.warn("Intento de actualización con email duplicado: {}", userUpdate.getEmail());
            throw new ErrorException("El correo " + userUpdate.getEmail() + " ya existe.", CodeError.BAD_REQUEST.getCode());
        }

        return userRepository.update(userUpdate);
    }

    public String deleteUser(Long id) throws ErrorException {
        log.info("Eliminando usuario con ID: {}", id);

        if (!userRepository.existsById(id)) {
            log.warn("Intento de eliminación para usuario inexistente: {}", id);
            throw new ErrorException("El usuario con el id " + id + " no existe.", CodeError.NOT_FOUND.getCode());
        }

        User user = userRepository.findById(id);
        user.setActive(false);
        userRepository.update(user);

        log.info("Usuario con ID {} eliminado correctamente.", id);
        return "El usuario con el ID " + id + " fue eliminado correctamente.";
    }
}
