package com.example.springrestapi.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import org.hibernate.Hibernate;

import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Supplier  implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "supplierName" , length = 255 , nullable = false)
    private String name;
    //@NotEmpty(message = "Supplier address tidak boleh kosong")
    @Column(name = "supplierAdress" , length = 255 , nullable = false)
    private String address;
//    @Email(message = "email tidak valid")
//    @NotEmpty(message = "Email tidak boleh kosong")
    @Column(name = "supplierEmail" , length = 255 , nullable = false , unique = true)
    private String email;

    @ManyToMany(mappedBy = "suppliers")
    @JsonBackReference
    @ToString.Exclude
    private Set<Product> products;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Supplier supplier = (Supplier) o;
        return id != null && Objects.equals(id, supplier.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
