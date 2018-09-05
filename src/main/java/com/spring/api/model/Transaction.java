package com.spring.api.model;

import com.fasterxml.jackson.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "transactions")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Getter
    @Setter
    private Long id;

    @Getter
    @Setter
    private String description;

    @NotNull
    @Getter
    @Setter
    private BigDecimal amount;

    @Getter
    @Setter
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "merchant_id",
            foreignKey = @ForeignKey(name = "MERCHANT_ID_FK")
    )
//    @JsonIdentityReference(alwaysAsId = true)
//    @JsonProperty(value = "merchant_id")
    private Merchant merchant;

    @Getter
    @Setter
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id",
            foreignKey = @ForeignKey(name = "ACCOUNT_ID_FK")
    )
    private Account account;

    @Getter
    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id",
            foreignKey = @ForeignKey(name = "CATEGORY_ID_FK")
    )
//    @JsonIdentityReference(alwaysAsId = true)
//    @JsonProperty(value = "category_id")
    private Category category;
}