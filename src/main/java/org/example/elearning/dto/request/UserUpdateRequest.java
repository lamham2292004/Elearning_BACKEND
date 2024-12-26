package org.example.elearning.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserUpdateRequest {
    @Size(min = 3, message = "USERNAME_INVALID")
    String username;
    String address;
    @Size(min = 8, message = "PASSWORD_INVALID")
    String password;
    @NotEmpty
    String phone;

}
