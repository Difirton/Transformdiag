package com.difirton.transformdiag.service;

import com.difirton.transformdiag.db.entity.*;
import com.difirton.transformdiag.service.constant.TypeDefect;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static com.difirton.transformdiag.service.constant.OilGas.*;
import static com.difirton.transformdiag.service.constant.PhysicalChemicalOilParameter.*;

class TransformerDefectInvestigatorTest {
    private ChromatographicOilAnalysis analysisWithinLimitValuesBefore750kV;
    private ChromatographicOilAnalysis analysisWithinLimitValuesAfter750kV;
    private ChromatographicOilAnalysis analysisBeyondBoundaryValuesBefore750kV;
    private ChromatographicOilAnalysis analysisBeyondBoundaryValuesAfter750kV;

    @BeforeEach
    void setUp() {
        analysisWithinLimitValuesBefore750kV = ChromatographicOilAnalysis.builder()
                .dateAnalysis(LocalDate.of(2022, 1, 1))
                .carbonDioxideCO2(2000)
                .carbonMonoxideCO(500)
                .methaneCH4(100)
                .ethyleneC2H4(100)
                .acetyleneC2H2(10)
                .ethaneC2H6(50)
                .hydrogenGasH2(100)
                .build();
        analysisWithinLimitValuesAfter750kV = ChromatographicOilAnalysis.builder()
                .dateAnalysis(LocalDate.of(2022, 1, 1))
                .carbonDioxideCO2(4000)
                .carbonMonoxideCO(500)
                .methaneCH4(20)
                .ethyleneC2H4(20)
                .acetyleneC2H2(10)
                .ethaneC2H6(10)
                .hydrogenGasH2(30)
                .build();
        analysisBeyondBoundaryValuesBefore750kV = ChromatographicOilAnalysis.builder()
                .dateAnalysis(LocalDate.of(2022, 7, 1))
                .carbonDioxideCO2(2001)
                .carbonMonoxideCO(501)
                .methaneCH4(101)
                .ethyleneC2H4(101)
                .acetyleneC2H2(11)
                .ethaneC2H6(51)
                .hydrogenGasH2(101)
                .build();
        analysisBeyondBoundaryValuesAfter750kV = ChromatographicOilAnalysis.builder()
                .dateAnalysis(LocalDate.of(2022, 7, 1))
                .carbonDioxideCO2(4001)
                .carbonMonoxideCO(501)
                .methaneCH4(21)
                .ethyleneC2H4(21)
                .acetyleneC2H2(11)
                .ethaneC2H6(11)
                .hydrogenGasH2(31)
                .build();
    }

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
        analysisWithinLimitValuesBefore750kV.setTransformer(transformer);
        transformer.setChromatographicOilAnalyses(List.of(
                analysisWithinLimitValuesBefore750kV,
                ChromatographicOilAnalysis.builder()
                        .dateAnalysis(LocalDate.of(2022, 7, 1))
                        .transformer(transformer)
                        .carbonDioxideCO2(2000)
                        .carbonMonoxideCO(500)
                        .methaneCH4(100)
                        .ethyleneC2H4(100)
                        .acetyleneC2H2(10)
                        .ethaneC2H6(50)
                        .hydrogenGasH2(100)
                        .build()
        ));
        investigator = new TransformerDefectInvestigator(transformer);
        TransformerStatus actualStatus = investigator.checkTransformer();
        Assertions.assertEquals(TypeDefect.NORMAL, actualStatus.getDefineDefect());
        Assertions.assertEquals(List.of(), actualStatus.getGasesOutOfLimit());
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
        Assertions.assertTrue(actualStatus.getRecommendedDaysBetweenOilSampling() < 180);
    }

    @Test
    @DisplayName("Тест трансформатора до 750 кВ с превышением граничных концентраций всех газов")
    void testCheckTransformer3() {
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
                analysisWithinLimitValuesBefore750kV,
                analysisBeyondBoundaryValuesBefore750kV
        ));
        investigator = new TransformerDefectInvestigator(transformer);
        TransformerStatus actualStatus = investigator.checkTransformer();
        Assertions.assertEquals(List.of(H2, CH4, C2H2, C2H4, C2H6, CO, CO2), actualStatus.getGasesOutOfLimit());
        Assertions.assertEquals(180, actualStatus.getRecommendedDaysBetweenOilSampling());
    }

    @Test
    @DisplayName("Тест трансформатора свыше 750 кВ с превышением граничных концентраций всех газов")
    void testCheckTransformer4() {
        TransformerCharacteristics transformerCharacteristics = TransformerCharacteristics.builder()
                .power(400000)
                .upVoltage(750d)
                .downVoltage(20d)
                .numberPhases(3)
                .build();
        Transformer transformer = Transformer.builder()
                .type("ТДЦ")
                .transformerCharacteristics(transformerCharacteristics)
                .build();
        transformer.setChromatographicOilAnalyses(List.of(
                analysisWithinLimitValuesAfter750kV,
                analysisBeyondBoundaryValuesAfter750kV
        ));
        investigator = new TransformerDefectInvestigator(transformer);
        TransformerStatus actualStatus = investigator.checkTransformer();
        Assertions.assertEquals(List.of(H2, CH4, C2H2, C2H4, C2H6, CO, CO2), actualStatus.getGasesOutOfLimit());
        Assertions.assertEquals(180, actualStatus.getRecommendedDaysBetweenOilSampling());
    }

    @Test
    @DisplayName("Тест трансформатора до 150 кВ без превышением граничных значений параметров масла")
    void testCheckTransformer5() {
        TransformerCharacteristics transformerCharacteristics = TransformerCharacteristics.builder()
                .power(40000)
                .upVoltage(110d)
                .downVoltage(10d)
                .numberPhases(3)
                .build();
        Transformer transformer = Transformer.builder()
                .type("ТДН")
                .transformerCharacteristics(transformerCharacteristics)
                .build();
        transformer.setChromatographicOilAnalyses(List.of(analysisWithinLimitValuesBefore750kV));
        transformer.setPhysicalChemicalOilAnalyses(List.of(
                PhysicalChemicalOilAnalysis.builder()
                        .dateAnalysis(LocalDate.of(2022, 1, 1))
                        .transformer(transformer)
                        .flashPoint(125d)
                        .acidNumber(0.15)
                        .cleanlinessClass(10)
                        .moistureContent(0.002)
                        .breakdownVoltage(40d)
                        .dielectricLossTangent(12d)
                        .build()));
        investigator = new TransformerDefectInvestigator(transformer);
        TransformerStatus actualStatus = investigator.checkTransformer();
        Assertions.assertEquals(List.of(), actualStatus.getDefineOilParameterDefects());
    }

    @Test
    @DisplayName("Тест трансформатора до 150 кВ с превышением граничных значений всех параметров масла")
    void testCheckTransformer6() {
        TransformerCharacteristics transformerCharacteristics = TransformerCharacteristics.builder()
                .power(40000)
                .upVoltage(110d)
                .downVoltage(10d)
                .numberPhases(3)
                .build();
        Transformer transformer = Transformer.builder()
                .type("ТДН")
                .transformerCharacteristics(transformerCharacteristics)
                .build();
        transformer.setChromatographicOilAnalyses(List.of(analysisWithinLimitValuesBefore750kV));
        transformer.setPhysicalChemicalOilAnalyses(List.of(
                PhysicalChemicalOilAnalysis.builder()
                        .dateAnalysis(LocalDate.of(2022, 7, 1))
                        .transformer(transformer)
                        .flashPoint(124d)
                        .acidNumber(0.16)
                        .cleanlinessClass(11)
                        .moistureContent(0.003)
                        .breakdownVoltage(39d)
                        .dielectricLossTangent(13d)
                        .build()
        ));
        investigator = new TransformerDefectInvestigator(transformer);
        TransformerStatus actualStatus = investigator.checkTransformer();
        Assertions.assertEquals(List.of(FLASH_POINT, ACID_NUMBER, CLEANLINESS_CLASS, MOISTURE_CONTENT, BREAKDOWN_VOLTAGE,
                DIELECTRIC_LOSS_TANGENT), actualStatus.getDefineOilParameterDefects());
    }

    @Test
    @DisplayName("Тест трансформатора от 150 до 550 кВ без превышением граничных значений параметров масла")
    void testCheckTransformer7() {
        TransformerCharacteristics transformerCharacteristics = TransformerCharacteristics.builder()
                .power(40000)
                .upVoltage(330d)
                .downVoltage(10d)
                .numberPhases(3)
                .build();
        Transformer transformer = Transformer.builder()
                .type("ТДН")
                .transformerCharacteristics(transformerCharacteristics)
                .build();
        transformer.setChromatographicOilAnalyses(List.of(analysisWithinLimitValuesBefore750kV));
        transformer.setPhysicalChemicalOilAnalyses(List.of(
                PhysicalChemicalOilAnalysis.builder()
                        .dateAnalysis(LocalDate.of(2022, 1, 1))
                        .transformer(transformer)
                        .flashPoint(125d)
                        .acidNumber(0.15)
                        .cleanlinessClass(10)
                        .moistureContent(0.002)
                        .breakdownVoltage(50d)
                        .dielectricLossTangent(8d)
                        .build()));
        investigator = new TransformerDefectInvestigator(transformer);
        TransformerStatus actualStatus = investigator.checkTransformer();
        Assertions.assertEquals(List.of(), actualStatus.getDefineOilParameterDefects());
    }

    @Test
    @DisplayName("Тест трансформатора от 150 до 550 кВ с превышением граничных значений всех параметров масла")
    void testCheckTransformer8() {
        TransformerCharacteristics transformerCharacteristics = TransformerCharacteristics.builder()
                .power(40000)
                .upVoltage(330d)
                .downVoltage(10d)
                .numberPhases(3)
                .build();
        Transformer transformer = Transformer.builder()
                .type("ТДН")
                .transformerCharacteristics(transformerCharacteristics)
                .build();
        transformer.setChromatographicOilAnalyses(List.of(analysisWithinLimitValuesBefore750kV));
        transformer.setPhysicalChemicalOilAnalyses(List.of(
                PhysicalChemicalOilAnalysis.builder()
                        .dateAnalysis(LocalDate.of(2022, 7, 1))
                        .transformer(transformer)
                        .flashPoint(124d)
                        .acidNumber(0.16)
                        .cleanlinessClass(11)
                        .moistureContent(0.003)
                        .breakdownVoltage(49d)
                        .dielectricLossTangent(9d)
                        .build()
        ));
        investigator = new TransformerDefectInvestigator(transformer);
        TransformerStatus actualStatus = investigator.checkTransformer();
        Assertions.assertEquals(List.of(FLASH_POINT, ACID_NUMBER, CLEANLINESS_CLASS, MOISTURE_CONTENT, BREAKDOWN_VOLTAGE,
                DIELECTRIC_LOSS_TANGENT), actualStatus.getDefineOilParameterDefects());
    }

    @Test
    @DisplayName("Тест трансформатора свыше 550 кВ без превышением граничных значений параметров масла")
    void testCheckTransformer9() {
        TransformerCharacteristics transformerCharacteristics = TransformerCharacteristics.builder()
                .power(40000)
                .upVoltage(750d)
                .downVoltage(10d)
                .numberPhases(3)
                .build();
        Transformer transformer = Transformer.builder()
                .type("ТДН")
                .transformerCharacteristics(transformerCharacteristics)
                .build();
        transformer.setChromatographicOilAnalyses(List.of(analysisWithinLimitValuesBefore750kV));
        transformer.setPhysicalChemicalOilAnalyses(List.of(
                PhysicalChemicalOilAnalysis.builder()
                        .dateAnalysis(LocalDate.of(2022, 1, 1))
                        .transformer(transformer)
                        .flashPoint(125d)
                        .acidNumber(0.15)
                        .cleanlinessClass(10)
                        .moistureContent(0.002)
                        .breakdownVoltage(60d)
                        .dielectricLossTangent(3d)
                        .build()));
        investigator = new TransformerDefectInvestigator(transformer);
        TransformerStatus actualStatus = investigator.checkTransformer();
        Assertions.assertEquals(List.of(), actualStatus.getDefineOilParameterDefects());
    }

    @Test
    @DisplayName("Тест трансформатора свыше 550 кВ с превышением граничных значений всех параметров масла")
    void testCheckTransformer10() {
        TransformerCharacteristics transformerCharacteristics = TransformerCharacteristics.builder()
                .power(40000)
                .upVoltage(750d)
                .downVoltage(10d)
                .numberPhases(3)
                .build();
        Transformer transformer = Transformer.builder()
                .type("ТДН")
                .transformerCharacteristics(transformerCharacteristics)
                .build();
        transformer.setChromatographicOilAnalyses(List.of(analysisWithinLimitValuesBefore750kV));
        transformer.setPhysicalChemicalOilAnalyses(List.of(
                PhysicalChemicalOilAnalysis.builder()
                        .dateAnalysis(LocalDate.of(2022, 7, 1))
                        .transformer(transformer)
                        .flashPoint(124d)
                        .acidNumber(0.16)
                        .cleanlinessClass(11)
                        .moistureContent(0.003)
                        .breakdownVoltage(59d)
                        .dielectricLossTangent(4d)
                        .build()
        ));
        investigator = new TransformerDefectInvestigator(transformer);
        TransformerStatus actualStatus = investigator.checkTransformer();
        Assertions.assertEquals(List.of(FLASH_POINT, ACID_NUMBER, CLEANLINESS_CLASS, MOISTURE_CONTENT, BREAKDOWN_VOLTAGE,
                DIELECTRIC_LOSS_TANGENT), actualStatus.getDefineOilParameterDefects());
    }

    @Test
    @DisplayName("Тест выявления повреждения бумажной изоляции, СО2/СО < 5 - изоляция повреждена")
    void testCheckTransformer11() {
        TransformerCharacteristics transformerCharacteristics = TransformerCharacteristics.builder()
                .power(400000)
                .upVoltage(330d)
                .downVoltage(10d)
                .numberPhases(3)
                .build();
        Transformer transformer = Transformer.builder()
                .type("ТРДН")
                .transformerCharacteristics(transformerCharacteristics)
                .build();
        analysisBeyondBoundaryValuesBefore750kV.setCarbonMonoxideCO(500);
        analysisBeyondBoundaryValuesBefore750kV.setCarbonDioxideCO2(2000);
        transformer.setChromatographicOilAnalyses(List.of(
                analysisWithinLimitValuesBefore750kV,
                analysisBeyondBoundaryValuesBefore750kV
        ));
        investigator = new TransformerDefectInvestigator(transformer);
        TransformerStatus actualStatus = investigator.checkTransformer();
        Assertions.assertTrue(actualStatus.getIsDamagedPaperInsulation());
    }

    @Test
    @DisplayName("Тест отсутствия повреждения бумажной изоляции, 5 < СО2/СО < 13 - изоляция не повреждена")
    void testCheckTransformer12() {
        TransformerCharacteristics transformerCharacteristics = TransformerCharacteristics.builder()
                .power(400000)
                .upVoltage(330d)
                .downVoltage(10d)
                .numberPhases(3)
                .build();
        Transformer transformer = Transformer.builder()
                .type("ТРДН")
                .transformerCharacteristics(transformerCharacteristics)
                .build();
        analysisBeyondBoundaryValuesBefore750kV.setCarbonMonoxideCO(1000);
        analysisBeyondBoundaryValuesBefore750kV.setCarbonDioxideCO2(8000);
        transformer.setChromatographicOilAnalyses(List.of(
                analysisWithinLimitValuesBefore750kV,
                analysisBeyondBoundaryValuesBefore750kV
        ));
        investigator = new TransformerDefectInvestigator(transformer);
        TransformerStatus actualStatus = investigator.checkTransformer();
        Assertions.assertFalse(actualStatus.getIsDamagedPaperInsulation());
    }

    @Test
    @DisplayName("Тест выявления повреждения бумажной изоляции, СО2/СО > 13 - изоляция повреждена")
    void testCheckTransformer13() {
        TransformerCharacteristics transformerCharacteristics = TransformerCharacteristics.builder()
                .power(400000)
                .upVoltage(330d)
                .downVoltage(10d)
                .numberPhases(3)
                .build();
        Transformer transformer = Transformer.builder()
                .type("ТРДН")
                .transformerCharacteristics(transformerCharacteristics)
                .build();
        analysisBeyondBoundaryValuesBefore750kV.setCarbonMonoxideCO(100);
        analysisBeyondBoundaryValuesBefore750kV.setCarbonDioxideCO2(1400);
        transformer.setChromatographicOilAnalyses(List.of(
                analysisWithinLimitValuesBefore750kV,
                analysisBeyondBoundaryValuesBefore750kV
        ));
        investigator = new TransformerDefectInvestigator(transformer);
        TransformerStatus actualStatus = investigator.checkTransformer();
        Assertions.assertTrue(actualStatus.getIsDamagedPaperInsulation());
    }
}