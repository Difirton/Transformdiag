package com.difirton.transformdiag.entitys;

import lombok.*;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Entity
@Data
@Table(name = "transformers")
@RequiredArgsConstructor
public class Transformer {
    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private Long id;

    @Size(min = 5, message = "KKS should be more than 5 characters")
    @Column(name = "KKS")
    private String KKS;

    @NotEmpty(message = "Type should not be empty")
    @Column(name = "type")
    private String type;

    @NotEmpty(message = "Factory number should not be empty")
    @Column(name = "factory_number")
    private String factoryNumber;
}
