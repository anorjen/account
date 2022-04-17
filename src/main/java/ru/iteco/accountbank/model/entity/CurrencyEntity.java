package ru.iteco.accountbank.model.entity;

import lombok.*;

import javax.persistence.*;

@Builder
@Getter
@Setter
@ToString
@Entity
@Table(name = "currency", schema = "dict")
@NoArgsConstructor
@AllArgsConstructor
public class CurrencyEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;
}
