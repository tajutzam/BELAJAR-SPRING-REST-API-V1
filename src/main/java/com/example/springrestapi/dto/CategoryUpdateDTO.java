package com.example.springrestapi.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CategoryUpdateDTO {

    @NotNull(message = "Id tidak boleh kosong")
    private Long id;

    @NotEmpty(message = "Nama tidak boleh kosong")
    private String name;
}
