package com.example.spring_mvc_data_crud.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @OneToOne()
    private Product product;
    private String comment;
    private Integer quantity;
    @ManyToOne
    @JoinColumn(name="sale_id", nullable=false)
    private Sale sale;

}
