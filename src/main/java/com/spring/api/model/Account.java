package com.spring.api.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "accounts")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter
    @Setter
    private Long id;

    @NotNull
    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    private String description;

    @NotNull
    @Getter
    @Setter
    private BigDecimal balance = new BigDecimal("0.00");

    @Getter
    @Setter
    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_accounts",
            joinColumns = {@JoinColumn(name = "account_id")},
            inverseJoinColumns = {@JoinColumn(name = "user_id")})
    private List<ApplicationUser> users = new ArrayList<>();

    @Getter
    @Setter
    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "account_transactions",
            joinColumns = {@JoinColumn(name = "account_id")},
            inverseJoinColumns = {@JoinColumn(name = "transaction_id")})
    private List<Transaction> transactions = new ArrayList<>();
}