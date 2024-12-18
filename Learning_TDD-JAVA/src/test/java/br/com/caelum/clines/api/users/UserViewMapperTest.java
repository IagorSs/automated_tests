package br.com.caelum.clines.api.users;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import br.com.caelum.clines.shared.domain.User;

public class UserViewMapperTest {

    private final String NAME = "FULANO";
    private final String EMAIL = "fulano@email.com";
    private final String PASSWORD = "123";

    private UserViewMapper mapper;

    @Test
    void shouldConvertUserToUserView() {
        var user = new User(NAME, EMAIL, PASSWORD);
        mapper = new UserViewMapper();

        var userView = mapper.map(user);

        assertEquals(NAME, userView.getName());
        assertEquals(EMAIL, userView.getEmail());
    }
}
