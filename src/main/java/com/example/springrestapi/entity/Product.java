package com.example.springrestapi.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.Hibernate;

import java.util.Objects;
import java.util.Set;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @NotEmpty(message = "Nama product tidak boleh kosong")
    @Column(name = "product_name" , nullable = false , length = 255)
    private String productName;

    @NotEmpty(message = "Deskripsi product tidak boleh kosong")
    @Column(name = "product_desc" , nullable = false , length = 500)
    private String productDescription;

    @Min(value = 1 , message = "Harga tidak boleh kosong")
    @NotNull(message = "Harga tidak boleh null")
    @Column(name = "product_price" , nullable = false , length = 100)
    private double productPrice;

    @Min(value = 1 , message = "Product stok tidak boleh kosong")
    @NotNull(message = "Stok tidak boleh null")
    @Column(name = "product_stock" , nullable = false , length = 100)
    private Integer stock;

    @ManyToOne(cascade = CascadeType.ALL)
    private Category category;
    @ManyToMany()
    @JoinTable(name = "tbl_supplier_product" , joinColumns = @JoinColumn(name = "product_id") ,
            inverseJoinColumns = @JoinColumn(name = "supplier_id"))
    @JsonManagedReference
    private Set<Supplier> suppliers;

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
