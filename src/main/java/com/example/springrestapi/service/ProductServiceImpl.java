package com.example.springrestapi.service;

import com.example.springrestapi.entity.Category;
import com.example.springrestapi.entity.Product;
import com.example.springrestapi.entity.Supplier;
import com.example.springrestapi.repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Objects;
import java.util.Optional;


@Service
@Transactional
public class ProductServiceImpl  implements ProductServiceInterface{
    @Autowired

    private ProductRepository repository;

    @Override
    public Iterable<Product> findAll(){
        return repository.findAll();
    }
    @Override
    public Product save(Product product){
        return this.repository.save(product);
    }

    @Override
    public Optional<Product> findById(Long id){
        return this.repository.findById(id);
    }

    @Override
    public Optional<Product> update(Product product){
        Optional<Product> id = repository.findById(product.getId());
        return id.map(product1 -> repository.save(product));
    }
    @Override
    public Boolean deleteById(Long id){
        Optional<Product> optional = repository.findById(id);
        if(optional.isPresent()){
            repository.deleteById(optional.get().getId());
            return true;
        }else{
            return false;
        }
    }
    @Override
    public Boolean deleteAll(){
        List<Product> all = repository.findAll();
        if(all.size() == 0){
            return false;
        }else{
            repository.deleteAll();
            return true;
        }
    }
    @Override
    public Optional<Supplier> addSupplier(Long id, Supplier supplier) {
        findById(id).map(product -> product.getSuppliers().add(supplier)).orElse(null);
            return Optional.ofNullable(supplier);
    }
    @Override
    public Optional<Product> addCategory(Long id, Category category) {
        Optional<Product> optionalProduct = findById(id);
        optionalProduct.ifPresent(product -> product.setCategory(category));
        return optionalProduct;
    }
    @Override
    public Optional<Product> findByName(String name) {
        return repository.findProductByProductName(name);
    }
    @Override
    public List<Product> findByNameLike(String name) {
        return repository.findProductByProductNameLike("%"+name+"%");
    }
    @Override
    public List<Product> findBySupplier(Supplier supplier) {
        List<Product> productList = repository.findByProductBySupplier(supplier);
        productList.forEach(product -> product.getSuppliers().removeIf(tempSup -> !Objects.equals(tempSup.getId(), supplier.getId()))
        );
        return productList;
    }
}
