package com.umitsen.onlinebookstore.Entity;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    private String name;
    private String email;
    private String password;
    @Getter
    private boolean admin;

}
