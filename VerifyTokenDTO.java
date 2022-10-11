package com.p3ngine.br.aimservice.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class VerifyTokenDTO {

    private Boolean active;

}
