package com.example.springrestapi.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Data
public class SupplierUpdateDTO {

    @NotNull(message = "Id tidak boleh null")
    private Long id;

    @NotEmpty(message = "Supplier name tidak boleh kosong")
    private String name;

    @NotEmpty(message = "Email tidak boleh kosong")
    private String address;

    @Email(message = "email tidak valid")
    @NotEmpty(message = "Email tidak boleh kosong")
    private String email;

}
