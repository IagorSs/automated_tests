package br.com.caelum.clines.api.users;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserFormMapperTest {
    private final String NAME = "FULANO";
    private final String EMAIL = "fulano@email.com";
    private final String PASSWORD = "123";

    private UserFormMapper mapper = new UserFormMapper();

    @Test
    void shouldConvertUserFormToUser() {
        var form = new UserForm(NAME, EMAIL, PASSWORD);

        var user = mapper.map(form);

        assertEquals(NAME, user.getName());
        assertEquals(EMAIL, user.getEmail());
        assertEquals(PASSWORD, user.getPassword());
    }
}
