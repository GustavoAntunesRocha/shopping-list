package br.com.antunes.gustavo.shoppinglistapi.dto;

import br.com.antunes.gustavo.shoppinglistapi.entity.Role;
import br.com.antunes.gustavo.shoppinglistapi.entity.UserEntity;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {

    private int id;

    @NotBlank
    private String email;

    private Role role;

    public UserDTO() {
    }

    public UserDTO(int id, String email, Role role) {
        this.id = id;
        this.email = email;
        this.role = role;
    }

    public static UserDTO fromUser(UserEntity user) {
        return new UserDTO(user.getId(), user.getEmail(), user.getRole());
    }

}
