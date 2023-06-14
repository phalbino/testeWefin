package com.TesteWefin.Api.response;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
public class GenericResponse {

    private Integer status;

    private String message;

    private String details;

    private Timestamp timestamp;

    private Request request;

}
