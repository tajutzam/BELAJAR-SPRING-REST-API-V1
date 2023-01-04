package com.example.springrestapi.service;

import com.example.springrestapi.entity.Category;
import com.mysql.cj.log.Log;

import java.util.List;
import java.util.Optional;

public interface CategoryServiceInterface {

    public Category save(Category category);

    public List<Category> findAll();

    public Category update(Category category);

    public Optional<Category> findById(Long id);

    public void remove(Long id);

}
