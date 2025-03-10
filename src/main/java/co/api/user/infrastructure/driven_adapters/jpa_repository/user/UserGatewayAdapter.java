package co.api.user.infrastructure.driven_adapters.jpa_repository.user;

import co.api.user.domain.model.common.ListResponse;
import co.api.user.domain.model.common.PageRequests;
import co.api.user.domain.model.user.User;
import co.api.user.domain.model.user.gateways.UserGateway;
import co.api.user.infrastructure.driven_adapters.jpa_repository.helper.AdapterOperations;
import co.api.user.infrastructure.driven_adapters.jpa_repository.user.entities.UserEntity;
import lombok.extern.slf4j.Slf4j;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

@Repository
@Slf4j
public class UserGatewayAdapter extends AdapterOperations<User, UserEntity, Long, UserDataRepository>
        implements UserGateway {

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public UserGatewayAdapter(UserDataRepository repository, ObjectMapper mapper) {
        super(repository, mapper, d -> mapper.map(d, User.class));
    }

    @Override
    public User create(User user) {
        log.info("Registrando usuario con email: {}", user.getEmail());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return toEntity(repository.save(toData(user)));
    }

    @Override
    public ListResponse<User> findAll(PageRequests pageRequests) throws Exception {
        try {
            Pageable pageable = PageRequest.of(
                    pageRequests.getPage(),
                    pageRequests.getSize(),
                    pageRequests.getSort() != null && !pageRequests.getSort().isEmpty()
                            ? Sort.by(pageRequests.getSort())
                            : Sort.unsorted()
            );
            Page<UserEntity> userEntityPage = repository.findAllPage(pageable);
            return new ListResponse<>(
                    userEntityPage.getTotalPages(),
                    userEntityPage.getNumber(),
                    userEntityPage.map(this::toEntity).toList()
            );
        } catch (Exception e) {
            log.warn("Error listando los usuarios", e);
            throw new Exception(e);
        }
    }

    @Override
    public boolean existsById(Long id) {
        return repository.existsByIdAndActiveTrue(id);
    }

    @Override
    public User update(User userUpdate) {
        log.info("Actualizando usuario con ID: {}", userUpdate.getId());

        return repository.findById(userUpdate.getId())
                .map(existingUser -> {
                    if (userUpdate.getPassword() != null && !passwordEncoder.matches(userUpdate.getPassword(), existingUser.getPassword())) {
                        existingUser.setPassword(passwordEncoder.encode(userUpdate.getPassword()));
                    }
                    return toEntity(repository.save(toData(userUpdate)));
                })
                .orElseThrow(() -> {
                    log.error("El usuario con ID {} no existe.", userUpdate.getId());
                    return new IllegalArgumentException("El usuario con el id " + userUpdate.getId() + " no existe.");
                });
    }

    @Override
    public boolean existByEmail(String email) {
        return repository.existsByEmail(email);
    }
}
