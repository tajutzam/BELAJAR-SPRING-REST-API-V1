package com.example.springrestapi.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class SupplierDTO {


    @NotEmpty(message = "Supplier name tidak boleh kosong")
    private String name;

    @NotEmpty(message = "Email tidak boleh kosong")
    private String address;

    @Email(message = "email tidak valid")
    @NotEmpty(message = "Email tidak boleh kosong")
    private String email;

}
