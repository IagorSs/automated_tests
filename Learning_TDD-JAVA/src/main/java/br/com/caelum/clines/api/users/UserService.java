package br.com.caelum.clines.api.users;

import lombok.AllArgsConstructor;

import java.util.List;

import org.springframework.stereotype.Service;

import br.com.caelum.clines.shared.domain.User;
import br.com.caelum.clines.shared.exceptions.ResourceAlreadyExistsException;
import br.com.caelum.clines.shared.exceptions.ResourceNotFoundException;

import static java.util.stream.Collectors.toList;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository repository;
    private final UserViewMapper viewMapper;
    private final UserFormMapper formMapper;

    public UserView showUserById(Long id) {

        User user = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cannot find user"));

        return viewMapper.map(user);
    }

    List<UserView> findAll() {
        return repository.findAll().stream().map(viewMapper::map).collect(toList());
    }

    public Long createUser(UserForm form) {
        repository
                .findByEmail(form.getEmail())
                .ifPresent(user -> {
                    throw new ResourceAlreadyExistsException("User already exists");
                });

        User user = formMapper.map(form);

        repository.save(user);

        return user.getId();
    }
}
