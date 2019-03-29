package com.spring.api.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;


@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "account_types")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class AccountType {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Getter
    @Setter
    private Long id;

    @NotNull
    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    @JsonIgnore
    @OneToMany(mappedBy = "account_type", fetch = FetchType.LAZY)
    private List<Account> accounts = new ArrayList<>();

    @Getter
    @Setter
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "account_type_transaction_types",
            joinColumns = {@JoinColumn(name = "account_type_id")},
            inverseJoinColumns = {@JoinColumn(name = "transaction_type_id")})
    @JsonIdentityReference(alwaysAsId = true)
    private List<TransactionType> transaction_types = new ArrayList<>();
}
