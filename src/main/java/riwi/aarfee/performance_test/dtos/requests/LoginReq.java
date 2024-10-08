package riwi.aarfee.performance_test.dtos.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class LoginReq {

    @Schema(description = "Email address of the user",
            example = "andres.jimenez@test.io",
            defaultValue = "andres.jimenez@test.io")
    @Email(message = "Email should be valid")
    @NotBlank(message = "Email is mandatory")
    private String email;

    @Schema(description = "Password of the user",
            example = "123456",
            defaultValue = "123456")
    @NotBlank(message = "Password is mandatory")
    @Size(min = 6, message = "Password must be at least 6 characters")
    private String password;

}
