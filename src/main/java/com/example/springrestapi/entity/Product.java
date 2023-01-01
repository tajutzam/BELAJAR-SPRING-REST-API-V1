package com.example.springrestapi.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;

import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "product_name" , nullable = false , length = 255)
    private String productName;
    @Column(name = "product_desc" , nullable = false , length = 500)
    private String productDescription;
    @Column(name = "product_price" , nullable = false , length = 100)
    private double productPrice;
    @Column(name = "product_stock" , nullable = false , length = 100)
    private int stock;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Product product = (Product) o;
        return id != null && Objects.equals(id, product.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
