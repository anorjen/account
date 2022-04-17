package ru.iteco.accountbank.model.entity;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@Builder
@Getter
@Setter
@ToString
@Entity
@Table(name = "bank_book", schema = "bank")
@NoArgsConstructor
@AllArgsConstructor
public class BankBookEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "number", nullable = false)
    private String number;

    @Column(name = "amount", nullable = false)
    private BigDecimal amount;

    @ManyToOne
    @JoinColumn(name = "currency_id")
    private CurrencyEntity currency;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;
}
