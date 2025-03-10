package co.api.user.domain.model.user.gateways;


import co.api.user.domain.model.common.ListResponse;
import co.api.user.domain.model.common.PageRequests;
import co.api.user.domain.model.user.User;

public interface UserGateway {

    User findById(Long id);

    User create(User user);

    ListResponse<User> findAll(PageRequests pageRequests) throws Exception;

    boolean existsById(Long id);

    User update(User user);

    boolean existByEmail(String email);
}
