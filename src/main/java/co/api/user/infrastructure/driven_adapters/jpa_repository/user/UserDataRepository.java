package co.api.user.infrastructure.driven_adapters.jpa_repository.user;

import co.api.user.infrastructure.driven_adapters.jpa_repository.user.entities.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

public interface UserDataRepository extends CrudRepository<UserEntity, Long>, QueryByExampleExecutor<UserEntity> {

    Boolean existsByIdAndActiveTrue(Long id);
    Boolean existsByEmail(String email);
    @Query("SELECT u FROM UserEntity u WHERE " +
            "u.active = true ")
    Page<UserEntity> findAllPage(Pageable pageable);
}
