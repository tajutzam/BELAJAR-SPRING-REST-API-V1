package com.example.springrestapi.service;

import com.example.springrestapi.entity.Product;
import com.example.springrestapi.entity.Supplier;
import com.example.springrestapi.repository.ProductRepository;
import com.example.springrestapi.repository.SupplierRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class SupplierServiceImpl implements SupplierServiceInterface {

    @Autowired
    private SupplierRepository supplierRepository;
    @Autowired
    private ProductRepository productRepository;
    @Override
    public Supplier save(Supplier supplier) {
        return supplierRepository.save(supplier);
    }
    @Override
    public List<Supplier> findAll() {
        return supplierRepository.findAll();
    }
    @Override
    public Optional<Supplier> update(Supplier supplier) {
        Optional<Supplier> id = supplierRepository.findById(supplier.getId());
       return id.map(value -> supplierRepository.save(supplier));
    }
    @Override
    public Optional<Supplier> findOne(Long id) {
        return supplierRepository.findById(id);
    }
    @Override
    public Boolean remove(Long id) {
        Optional<Supplier> supplierOptional = supplierRepository.findById(id);
        return  supplierOptional.map(value ->{
            supplierRepository.deleteById(id);
            return true;
        }).orElse(false);
    }
    @Override
    public Optional<List<Supplier>> findSupplierProductNull() {
        return supplierRepository.findByProductsIsNull();
    }

    @Override
    public Optional<Supplier> findByEmail(String name) {
        Optional<Supplier> email = supplierRepository.findByEmail(name);
        System.out.println(email);
        return email;
    }

    @Override
    public Optional<List<Supplier>> findByNameOrEmail(String name, String email) {
        return Optional.ofNullable(supplierRepository.findByNameOrEmailContains(name , email));
    }
}
