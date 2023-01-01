package com.example.springrestapi.service;

import com.example.springrestapi.entity.Product;
import com.example.springrestapi.repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@Transactional
public class ProductService {

    @Autowired
    private ProductRepository repository;
    public Iterable<Product> findAll(){
        return repository.findAll();
    }
    public Product save(Product product){
        return repository.save(product);
    }

    public Optional<Product> findById(Long id){
        return repository.findById(id);
    }

    public Product update(Product product){
        Optional<Product> id = repository.findById(product.getId());
        return id.map(product1 -> repository.save(product)).orElse(null);
    }
    public Boolean deleteById(Long id){
        Optional<Product> optional = repository.findById(id);
        if(optional.isPresent()){
            repository.deleteById(optional.get().getId());
            return true;
        }else{
            return false;
        }
    }

    public Boolean deleteAll(){
        List<Product> all = repository.findAll();
        if(all.size() == 0){
            return false;
        }else{
            repository.deleteAll();
            return true;
        }
    }
}
