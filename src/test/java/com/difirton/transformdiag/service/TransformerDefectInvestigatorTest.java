package com.difirton.transformdiag.service;

import com.difirton.transformdiag.db.entity.ChromatographicOilAnalysis;
import com.difirton.transformdiag.db.entity.Transformer;
import com.difirton.transformdiag.db.entity.TransformerCharacteristics;
import com.difirton.transformdiag.db.entity.TransformerStatus;
import com.difirton.transformdiag.service.constant.TypeDefect;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static com.difirton.transformdiag.service.constant.OilGas.CO;

class TransformerDefectInvestigatorTest {
    TransformerDefectInvestigator investigator;

    @Test
    @DisplayName("Тест трансформатора до 750 кВ с нормальным газосодержанием")
    void testCheckTransformer() {
        TransformerCharacteristics transformerCharacteristics = TransformerCharacteristics.builder()
                .power(400000)
                .upVoltage(330d)
                .downVoltage(20d)
                .numberPhases(3)
                .build();
        Transformer transformer = Transformer.builder()
                .type("ТДЦГ")
                .transformerCharacteristics(transformerCharacteristics)
                .build();
        transformer.setChromatographicOilAnalyses(List.of(
                ChromatographicOilAnalysis.builder()
                        .dateAnalysis(LocalDate.of(2022, 1, 1))
                        .transformer(transformer)
                        .carbonDioxideCO2(500)
                        .carbonMonoxideCO(4000)
                        .methaneCH4(20)
                        .ethyleneC2H4(1)
                        .acetyleneC2H2(10)
                        .ethaneC2H6(10)
                        .hydrogenGasH2(30)
                        .build(),
                ChromatographicOilAnalysis.builder()
                        .dateAnalysis(LocalDate.of(2022, 6, 1))
                        .transformer(transformer)
                        .carbonDioxideCO2(500)
                        .carbonMonoxideCO(4000)
                        .methaneCH4(20)
                        .ethyleneC2H4(1)
                        .acetyleneC2H2(10)
                        .ethaneC2H6(10)
                        .hydrogenGasH2(30)
                        .build()
        ));
        investigator = new TransformerDefectInvestigator(transformer);
        TransformerStatus actualStatus = investigator.checkTransformer();
        Assertions.assertEquals(List.of(CO), actualStatus.getGasesOutOfLimit());
        Assertions.assertEquals(TypeDefect.NORMAL, actualStatus.getDefineDefect());
        Assertions.assertFalse(actualStatus.getIsDamagedPaperInsulation());
        Assertions.assertEquals(List.of(), actualStatus.getDefineOilParameterDefects());
        Assertions.assertEquals(180, actualStatus.getRecommendedDaysBetweenOilSampling());
    }

    @Test
    @DisplayName("Тест трансформатора до 750 кВ с уменьшенным периодом между отборами")
    void testCheckTransformer2() {
        TransformerCharacteristics transformerCharacteristics = TransformerCharacteristics.builder()
                .power(400000)
                .upVoltage(330d)
                .downVoltage(20d)
                .numberPhases(3)
                .build();
        Transformer transformer = Transformer.builder()
                .type("ТДЦГ")
                .transformerCharacteristics(transformerCharacteristics)
                .build();
        transformer.setChromatographicOilAnalyses(List.of(
                ChromatographicOilAnalysis.builder()
                        .dateAnalysis(LocalDate.of(2022, 1, 1))
                        .transformer(transformer)
                        .carbonDioxideCO2(200)
                        .carbonMonoxideCO(400)
                        .methaneCH4(10)
                        .ethyleneC2H4(5)
                        .acetyleneC2H2(0)
                        .ethaneC2H6(10)
                        .hydrogenGasH2(10)
                        .build(),
                ChromatographicOilAnalysis.builder()
                        .dateAnalysis(LocalDate.of(2022, 7, 1))
                        .transformer(transformer)
                        .carbonDioxideCO2(250)
                        .carbonMonoxideCO(550)
                        .methaneCH4(15)
                        .ethyleneC2H4(5)
                        .acetyleneC2H2(0)
                        .ethaneC2H6(10)
                        .hydrogenGasH2(20)
                        .build()
        ));
        investigator = new TransformerDefectInvestigator(transformer);
        TransformerStatus actualStatus = investigator.checkTransformer();
        Assertions.assertEquals(List.of(CO), actualStatus.getGasesOutOfLimit());
        Assertions.assertEquals(TypeDefect.NORMAL, actualStatus.getDefineDefect());
        Assertions.assertFalse(actualStatus.getIsDamagedPaperInsulation());
        Assertions.assertEquals(List.of(), actualStatus.getDefineOilParameterDefects());
        Assertions.assertEquals(61, actualStatus.getRecommendedDaysBetweenOilSampling());
    }
}