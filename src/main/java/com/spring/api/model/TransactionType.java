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
import java.util.ArrayList;
import java.util.List;


@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "transaction_types")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class TransactionType {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Getter
    @Setter
    private Long id;

    @NotNull
    @Getter
    @Setter
    private String name;

    @NotNull
    @Getter
    @Setter
    private Boolean is_income;

    @Getter
    @Setter
    @JsonIgnore
    @OneToMany(mappedBy = "transaction_type", fetch = FetchType.LAZY)
    private List<Transaction> transactions = new ArrayList<>();

    @Getter
    @Setter
    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "account_type_transaction_types",
            joinColumns = {@JoinColumn(name = "transaction_type_id")},
            inverseJoinColumns = {@JoinColumn(name = "account_type_id")})
    private List<AccountType> account_types = new ArrayList<>();
}
