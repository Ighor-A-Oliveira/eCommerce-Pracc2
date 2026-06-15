package com.ighor.api.e_commerce.model.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "categories")
@AllArgsConstructor
@NoArgsConstructor
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "category_id", unique = true, nullable = false)
    private Long id;
    private String name;
    private String slug;
    private String description;
    private Boolean active;

    //FK de Product
    @OneToMany(mappedBy = "category")
    private List<Product> products;
}
