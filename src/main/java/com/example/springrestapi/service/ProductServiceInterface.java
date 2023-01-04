package com.example.springrestapi.service;

import com.example.springrestapi.entity.Category;
import com.example.springrestapi.entity.Product;
import com.example.springrestapi.entity.Supplier;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public interface ProductServiceInterface {

    public Iterable<Product> findAll();
    public Product save(Product product);
    public Optional<Product> findById(Long id);
    public Optional<Product> update(Product product);
    public Boolean deleteById(Long id);
    public Boolean deleteAll();
    public Optional<Supplier> addSupplier(Long id , Supplier supplier);
    public Optional<Product> addCategory(Long id , Category category);

    public Optional<Product> findByName(String name);
    public List<Product> findByNameLike(String name);

    public List<Product> findBySupplier(Supplier id);
}
