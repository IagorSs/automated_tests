package br.com.caelum.clines.api.users;

import lombok.Data;
import lombok.AllArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
class UserForm {
    @NotBlank
    private String name;

    @NotBlank
    private String email;

    @NotBlank
    private String password;
}
