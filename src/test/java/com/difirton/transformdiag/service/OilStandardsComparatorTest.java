package com.difirton.transformdiag.service;

import com.difirton.transformdiag.db.entity.ChromatographicOilAnalysis;
import com.difirton.transformdiag.db.entity.PhysicalChemicalOilAnalysis;
import com.difirton.transformdiag.service.constant.OilGas;
import com.difirton.transformdiag.service.constant.PhysicalChemicalOilParameter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.difirton.transformdiag.service.constant.OilGas.*;
import static com.difirton.transformdiag.service.constant.PhysicalChemicalOilParameter.*;

class OilStandardsComparatorTest {

    @Test
    @DisplayName("Тест определения превышния концентрации газов для трансформатора до 750кВ, превышены 4 газа")
    void testCompareWithStandardGasContent() {
        OilStandardsComparator comparator = new OilStandardsComparator(550d);
        ChromatographicOilAnalysis chromatographicOilAnalysis = ChromatographicOilAnalysis.builder()
                .carbonDioxideCO2(3000)
                .carbonMonoxideCO(200)
                .methaneCH4(250)
                .ethyleneC2H4(60)
                .acetyleneC2H2(15)
                .ethaneC2H6(70)
                .hydrogenGasH2(30)
                .build();
        List<OilGas> actual = comparator.compareWithStandardGasContent(chromatographicOilAnalysis);
        List<OilGas> expected = List.of(CH4, C2H2, C2H6, CO2);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Тест определения превышния концентрации газов для трансформатора до 750кВ, превышен 1 газ")
    void testCompareWithStandardGasContent2() {
        OilStandardsComparator comparator = new OilStandardsComparator(550d);
        ChromatographicOilAnalysis chromatographicOilAnalysis = ChromatographicOilAnalysis.builder()
                .carbonDioxideCO2(2000)
                .carbonMonoxideCO(10)
                .methaneCH4(50)
                .ethyleneC2H4(20)
                .acetyleneC2H2(5)
                .ethaneC2H6(30)
                .hydrogenGasH2(150)
                .build();
        List<OilGas> actual = comparator.compareWithStandardGasContent(chromatographicOilAnalysis);
        List<OilGas> expected = List.of(H2);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Тест определения превышния концентрации газов для трансформатора до 750кВ, нет превышений")
    void testCompareWithStandardGasContent3() {
        OilStandardsComparator comparator = new OilStandardsComparator(550d);
        ChromatographicOilAnalysis chromatographicOilAnalysis = ChromatographicOilAnalysis.builder()
                .carbonDioxideCO2(2000)
                .carbonMonoxideCO(10)
                .methaneCH4(50)
                .ethyleneC2H4(20)
                .acetyleneC2H2(5)
                .ethaneC2H6(30)
                .hydrogenGasH2(50)
                .build();
        List<OilGas> actual = comparator.compareWithStandardGasContent(chromatographicOilAnalysis);
        List<OilGas> expected = List.of();
        Assertions.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Тест определения превышния концентрации газов для трансформатора до 750кВ, превышены все показатели")
    void testCompareWithStandardGasContent4() {
        OilStandardsComparator comparator = new OilStandardsComparator(550d);
        ChromatographicOilAnalysis chromatographicOilAnalysis = ChromatographicOilAnalysis.builder()
                .carbonDioxideCO2(2001)
                .carbonMonoxideCO(501)
                .methaneCH4(101)
                .ethyleneC2H4(101)
                .acetyleneC2H2(11)
                .ethaneC2H6(51)
                .hydrogenGasH2(101)
                .build();
        List<OilGas> actual = comparator.compareWithStandardGasContent(chromatographicOilAnalysis);
        List<OilGas> expected = List.of(H2, CH4, C2H2, C2H4, C2H6, CO, CO2);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Тест определения превышния концентрации газов для трансформатора свыше 750кВ, превышены 4 газа")
    void testCompareWithStandardGasContent5() {
        OilStandardsComparator comparator = new OilStandardsComparator(750d);
        ChromatographicOilAnalysis chromatographicOilAnalysis = ChromatographicOilAnalysis.builder()
                .carbonDioxideCO2(3000)
                .carbonMonoxideCO(200)
                .methaneCH4(250)
                .ethyleneC2H4(60)
                .acetyleneC2H2(15)
                .ethaneC2H6(70)
                .hydrogenGasH2(30)
                .build();
        List<OilGas> actual = comparator.compareWithStandardGasContent(chromatographicOilAnalysis);
        List<OilGas> expected = List.of(CH4, C2H2, C2H4, C2H6);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Тест определения превышния концентрации газов для трансформатора свыше 750кВ, превышен 1 газ")
    void testCompareWithStandardGasContent6() {
        OilStandardsComparator comparator = new OilStandardsComparator(750d);
        ChromatographicOilAnalysis chromatographicOilAnalysis = ChromatographicOilAnalysis.builder()
                .carbonDioxideCO2(2000)
                .carbonMonoxideCO(100)
                .methaneCH4(50)
                .ethyleneC2H4(20)
                .acetyleneC2H2(5)
                .ethaneC2H6(5)
                .hydrogenGasH2(20)
                .build();
        List<OilGas> actual = comparator.compareWithStandardGasContent(chromatographicOilAnalysis);
        List<OilGas> expected = List.of(CH4);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Тест определения превышния концентрации газов для трансформатора свыше 750кВ, нет превышений")
    void testCompareWithStandardGasContent7() {
        OilStandardsComparator comparator = new OilStandardsComparator(750d);
        ChromatographicOilAnalysis chromatographicOilAnalysis = ChromatographicOilAnalysis.builder()
                .carbonDioxideCO2(4000)
                .carbonMonoxideCO(500)
                .methaneCH4(20)
                .ethyleneC2H4(20)
                .acetyleneC2H2(10)
                .ethaneC2H6(10)
                .hydrogenGasH2(30)
                .build();
        List<OilGas> actual = comparator.compareWithStandardGasContent(chromatographicOilAnalysis);
        List<OilGas> expected = List.of();
        Assertions.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Тест определения превышния концентрации газов для трансформатора до 750кВ, превышены все показатели")
    void testCompareWithStandardGasContent8() {
        OilStandardsComparator comparator = new OilStandardsComparator(750d);
        ChromatographicOilAnalysis chromatographicOilAnalysis = ChromatographicOilAnalysis.builder()
                .carbonDioxideCO2(4001)
                .carbonMonoxideCO(501)
                .methaneCH4(21)
                .ethyleneC2H4(21)
                .acetyleneC2H2(11)
                .ethaneC2H6(11)
                .hydrogenGasH2(31)
                .build();
        List<OilGas> actual = comparator.compareWithStandardGasContent(chromatographicOilAnalysis);
        List<OilGas> expected = List.of(H2, CH4, C2H2, C2H4, C2H6, CO, CO2);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Тест определения превышния показателей масла для трансформатора до 150кВ, все показатели  норме")
    void compareWithStandardOilParameters() {
        OilStandardsComparator comparator = new OilStandardsComparator(150d);
        PhysicalChemicalOilAnalysis physicalChemicalOilAnalysis = PhysicalChemicalOilAnalysis.builder()
                .flashPoint(125d)
                .acidNumber(0.15)
                .cleanlinessClass(10)
                .moistureContent(0.002)
                .breakdownVoltage(40d)
                .dielectricLossTangent(12d)
                .build();
        List<PhysicalChemicalOilParameter> actual = comparator.
                compareWithStandardOilParameters(physicalChemicalOilAnalysis);
        List<PhysicalChemicalOilParameter> expected = List.of();
        Assertions.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Тест определения превышния показателей масла для трансформатора до 150кВ, превышены все показатели")
    void compareWithStandardOilParameters2() {
        OilStandardsComparator comparator = new OilStandardsComparator(150d);
        PhysicalChemicalOilAnalysis physicalChemicalOilAnalysis = PhysicalChemicalOilAnalysis.builder()
                .flashPoint(124d)
                .acidNumber(0.16)
                .cleanlinessClass(11)
                .moistureContent(0.003)
                .breakdownVoltage(39d)
                .dielectricLossTangent(13d)
                .build();
        List<PhysicalChemicalOilParameter> actual = comparator.
                compareWithStandardOilParameters(physicalChemicalOilAnalysis);
        List<PhysicalChemicalOilParameter> expected = List.of(FLASH_POINT, ACID_NUMBER, CLEANLINESS_CLASS,
                MOISTURE_CONTENT, BREAKDOWN_VOLTAGE, DIELECTRIC_LOSS_TANGENT);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Тест определения превышния показателей масла для трансформатора до 150кВ, превышены 3 показателя")
    void compareWithStandardOilParameters3() {
        OilStandardsComparator comparator = new OilStandardsComparator(150d);
        PhysicalChemicalOilAnalysis physicalChemicalOilAnalysis = PhysicalChemicalOilAnalysis.builder()
                .flashPoint(140d)
                .acidNumber(0.1)
                .cleanlinessClass(10)
                .moistureContent(0.020)
                .breakdownVoltage(35d)
                .dielectricLossTangent(20d)
                .build();
        List<PhysicalChemicalOilParameter> actual = comparator.
                compareWithStandardOilParameters(physicalChemicalOilAnalysis);
        List<PhysicalChemicalOilParameter> expected = List.of(MOISTURE_CONTENT, BREAKDOWN_VOLTAGE,
                DIELECTRIC_LOSS_TANGENT);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Тест определения превышния показателей масла для трансформатора до 550кВ, все показатели  норме")
    void compareWithStandardOilParameters4() {
        OilStandardsComparator comparator = new OilStandardsComparator(550d);
        PhysicalChemicalOilAnalysis physicalChemicalOilAnalysis = PhysicalChemicalOilAnalysis.builder()
                .flashPoint(125d)
                .acidNumber(0.15)
                .cleanlinessClass(10)
                .moistureContent(0.002)
                .breakdownVoltage(50d)
                .dielectricLossTangent(8d)
                .build();
        List<PhysicalChemicalOilParameter> actual = comparator.
                compareWithStandardOilParameters(physicalChemicalOilAnalysis);
        List<PhysicalChemicalOilParameter> expected = List.of();
        Assertions.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Тест определения превышния показателей масла для трансформатора до 550кВ, превышены все показатели")
    void compareWithStandardOilParameters5() {
        OilStandardsComparator comparator = new OilStandardsComparator(550d);
        PhysicalChemicalOilAnalysis physicalChemicalOilAnalysis = PhysicalChemicalOilAnalysis.builder()
                .flashPoint(124d)
                .acidNumber(0.16)
                .cleanlinessClass(11)
                .moistureContent(0.0021)
                .breakdownVoltage(49d)
                .dielectricLossTangent(9d)
                .build();
        List<PhysicalChemicalOilParameter> actual = comparator.
                compareWithStandardOilParameters(physicalChemicalOilAnalysis);
        List<PhysicalChemicalOilParameter> expected = List.of(FLASH_POINT, ACID_NUMBER, CLEANLINESS_CLASS,
                MOISTURE_CONTENT, BREAKDOWN_VOLTAGE, DIELECTRIC_LOSS_TANGENT);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Тест определения превышния показателей масла для трансформатора до 550кВ, превышены 3 показателя")
    void compareWithStandardOilParameters6() {
        OilStandardsComparator comparator = new OilStandardsComparator(550d);
        PhysicalChemicalOilAnalysis physicalChemicalOilAnalysis = PhysicalChemicalOilAnalysis.builder()
                .flashPoint(140d)
                .acidNumber(0.1)
                .cleanlinessClass(10)
                .moistureContent(0.020)
                .breakdownVoltage(45d)
                .dielectricLossTangent(10d)
                .build();
        List<PhysicalChemicalOilParameter> actual = comparator.
                compareWithStandardOilParameters(physicalChemicalOilAnalysis);
        List<PhysicalChemicalOilParameter> expected = List.of(MOISTURE_CONTENT, BREAKDOWN_VOLTAGE,
                DIELECTRIC_LOSS_TANGENT);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Тест определения превышния показателей масла для трансформатора свыше 750кВ, все показатели  норме")
    void compareWithStandardOilParameters7() {
        OilStandardsComparator comparator = new OilStandardsComparator(750d);
        PhysicalChemicalOilAnalysis physicalChemicalOilAnalysis = PhysicalChemicalOilAnalysis.builder()
                .flashPoint(125d)
                .acidNumber(0.15)
                .cleanlinessClass(10)
                .moistureContent(0.002)
                .breakdownVoltage(60d)
                .dielectricLossTangent(3d)
                .build();
        List<PhysicalChemicalOilParameter> actual = comparator.
                compareWithStandardOilParameters(physicalChemicalOilAnalysis);
        List<PhysicalChemicalOilParameter> expected = List.of();
        Assertions.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Тест определения превышния показателей масла для трансформатора свыше 750кВ, превышены все показатели")
    void compareWithStandardOilParameters8() {
        OilStandardsComparator comparator = new OilStandardsComparator(750d);
        PhysicalChemicalOilAnalysis physicalChemicalOilAnalysis = PhysicalChemicalOilAnalysis.builder()
                .flashPoint(124d)
                .acidNumber(0.16)
                .cleanlinessClass(11)
                .moistureContent(0.0021)
                .breakdownVoltage(59d)
                .dielectricLossTangent(4d)
                .build();
        List<PhysicalChemicalOilParameter> actual = comparator.
                compareWithStandardOilParameters(physicalChemicalOilAnalysis);
        List<PhysicalChemicalOilParameter> expected = List.of(FLASH_POINT, ACID_NUMBER, CLEANLINESS_CLASS,
                MOISTURE_CONTENT, BREAKDOWN_VOLTAGE, DIELECTRIC_LOSS_TANGENT);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Тест определения превышния показателей масла для трансформатора свыше 750кВ, превышены 3 показателя")
    void compareWithStandardOilParameters9() {
        OilStandardsComparator comparator = new OilStandardsComparator(750d);
        PhysicalChemicalOilAnalysis physicalChemicalOilAnalysis = PhysicalChemicalOilAnalysis.builder()
                .flashPoint(140d)
                .acidNumber(0.1)
                .cleanlinessClass(10)
                .moistureContent(0.020)
                .breakdownVoltage(55d)
                .dielectricLossTangent(5d)
                .build();
        List<PhysicalChemicalOilParameter> actual = comparator.
                compareWithStandardOilParameters(physicalChemicalOilAnalysis);
        List<PhysicalChemicalOilParameter> expected = List.of(MOISTURE_CONTENT, BREAKDOWN_VOLTAGE,
                DIELECTRIC_LOSS_TANGENT);
        Assertions.assertEquals(expected, actual);
    }
}