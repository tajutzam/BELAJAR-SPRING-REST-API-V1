package com.example.springrestapi.service;

import com.example.springrestapi.entity.Product;
import com.example.springrestapi.entity.Supplier;

import java.util.List;
import java.util.Optional;

public interface SupplierServiceInterface {

        public Supplier save(Supplier supplier);
        public List<Supplier> findAll();
        public Optional<Supplier> update(Supplier supplier);
        public Optional<Supplier> findOne(Long id);
        public Boolean remove(Long id);

        public Optional<List<Supplier>> findSupplierProductNull();

        public Optional<Supplier> findByEmail(String name);

        public Optional<List<Supplier>> findByNameOrEmail(String name , String email);

}
