package com.example.springrestapi.repository;

import com.example.springrestapi.entity.Product;
import com.example.springrestapi.entity.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface SupplierRepository extends JpaRepository<Supplier , Long> {

   Optional<List<Supplier>> findByProductsIsNull();
   Optional<Supplier> findByEmail(String email);

   List<Supplier> findByNameOrEmailContains(String name , String email);

}
