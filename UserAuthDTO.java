package com.p3ngine.br.aimservice.dto;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Valid
@Data
public class UserAuthDTO {

    @NotNull
    @NotBlank(message = "Username is mandatory")
    public String username;

    @NotNull
    @NotBlank(message = "Password is mandatory")
    public String password;

    @NotNull
    @NotBlank(message = "Request Invalid")
    public String version;
}
