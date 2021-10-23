package com.example.lesson7task1.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.xml.ws.soap.Addressing;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserDto {
    @NotNull(message = "FullName ni kiriting")
    private String fullName;
    @NotNull(message = "username ni kiriting")
    private String username;
    @NotNull(message = "password ni kiriting")
    private String password;

    private Long roleId;

}
