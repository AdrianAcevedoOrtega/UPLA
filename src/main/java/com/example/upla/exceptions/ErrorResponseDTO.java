package com.example.upla.exceptions;

import lombok.Builder;

@Builder
public class ErrorResponseDTO {
    int estado;
    String mensaje;


}
