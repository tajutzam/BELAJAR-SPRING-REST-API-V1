package com.example.springrestapi.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class SearchDTO {
     @NotEmpty(message = "keyword tidak boleh kosong")
     private String key;
     private Long id;
     @Email(message = "Email not valid")
     private String email;
}
