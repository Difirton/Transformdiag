package com.difirton.transformer.db.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(value = "transformer", ignoreUnknown = true)
@EqualsAndHashCode(exclude = "transformer")
@Builder
@Entity
@Table(name = "transformer_status")
public class TransformerStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "transformerStatus")
    @JoinColumn(name = "transformer_id", referencedColumnName = "id")
    @ToString.Exclude
    private Transformer transformer;

    @JsonProperty("is_damaged_paper_insulation")
    @Column(name = "is_damaged_paper_insulation")
    private Boolean isDamagedPaperInsulation;

    @JsonProperty("recommended_days_between_oil_sampling")
    @Column(name = "recommended_days_between_oil_sampling")
    private Integer recommendedDaysBetweenOilSampling;
}
