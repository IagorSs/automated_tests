package br.com.caelum.clines.api.users;

import br.com.caelum.clines.shared.domain.User;
import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends Repository<User, Long> {
    List<User> findAll();

    Optional<User> findByEmail(String email);

    Optional<User> findById(Long id);

    void save(User user);
}
