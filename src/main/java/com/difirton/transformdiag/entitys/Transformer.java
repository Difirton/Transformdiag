package com.difirton.transformdiag.entitys;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Data
@Table(name = "transformers")
@NoArgsConstructor
public class Transformer {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @Column(name = "KKS")
    private String KKS;

    @Column(name = "type")
    private String type;

    @Column(name = "factory_number")
    private String factoryNumber;

    public Transformer(String KKS, String type, String factoryNumber) {
        this.KKS = KKS;
        this.type = type;
        this.factoryNumber = factoryNumber;
    }
}
