package com.example.springrestapi.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
public class ErrorMapper {
    List<String> msg = new ArrayList<>();
}
