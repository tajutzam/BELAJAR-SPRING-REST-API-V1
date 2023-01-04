package com.example.springrestapi.repository;

import com.example.springrestapi.entity.Product;
import com.example.springrestapi.entity.Supplier;
import jakarta.websocket.server.PathParam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product , Long> {
    @Query("SELECT p FROM Product p WHERE p.productName =:productName")
    public Optional<Product> findProductByProductName(@Param(value = "productName") String name);

    @Query("SELECT p FROM Product p WHERE p.productName LIKE :productName")
    public List<Product> findProductByProductNameLike(@Param(value = "productName") String name);

    @Query("select p from Product p where :supplier member of p.suppliers ")
    public List<Product> findByProductBySupplier(@Param(value = "supplier") Supplier supplier);
}
