package com.ighor.api.e_commerce.model.entity;

import com.ighor.api.e_commerce.model.enums.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id",unique = true, nullable = false)
    private Long id;
    private String name;
    @Column(name = "user_email",unique = true, nullable = false)
    private String email;
    @Column(name = "user_password", nullable = false)
    private String password;
    private String phone;
    @Enumerated(EnumType.STRING)
    private Role role;
    private LocalDateTime createdAt;

    //FK de Address
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Address> addresses;

    //FK de Cart
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Cart cart;

    //FK de Order
    @OneToMany(mappedBy = "user")
    private List<Order> orders;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserPaymentMethod> paymentMethods = new ArrayList<>();

    // Método de conveniência
    public void addPaymentMethod(UserPaymentMethod paymentMethod) {
        paymentMethods.add(paymentMethod);
        paymentMethod.setUser(this);
    }


}
