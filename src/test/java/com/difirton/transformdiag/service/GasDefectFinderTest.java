package com.difirton.transformdiag.service;

import com.difirton.transformdiag.db.entity.ChromatographicOilAnalysis;
import com.difirton.transformdiag.db.entity.Transformer;
import com.difirton.transformdiag.db.entity.TransformerCharacteristics;
import com.difirton.transformdiag.service.constant.TypeDefect;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class GasDefectFinderTest {

    @Test
    @DisplayName("Тест пример 1 из Приложения 2 РД 153-34 - дефект высокой температуры >700C")
    void testDetectVeryHighThermalDefect() {
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
        ChromatographicOilAnalysis chromatographicOilAnalysis = ChromatographicOilAnalysis.builder()
                .dateAnalysis(LocalDate.of(2022, 1, 1))
                .transformer(transformer)
                .carbonDioxideCO2(1500)
                .carbonMonoxideCO(200)
                .methaneCH4(160)
                .ethyleneC2H4(480)
                .acetyleneC2H2(30)
                .ethaneC2H6(47)
                .hydrogenGasH2(100)
                .build();
        GasDefectFinder gasDefectFinder = new GasDefectFinder(chromatographicOilAnalysis);
        TypeDefect actual = gasDefectFinder.detectTypeDefect();
        TypeDefect expected = TypeDefect.THERMAL_DEFECT_VERY_HIGH;
        assertEquals(actual, expected);
    }

    @Test
    @DisplayName("Тест пример 2 из Приложения 2 РД 153-34 - тест дефекта разряды большой мощности")
    void testDetectDischargeHighPowerDefect() {
        TransformerCharacteristics transformerCharacteristics = TransformerCharacteristics.builder()
                .power(10000)
                .upVoltage(110d)
                .downVoltage(10.5)
                .numberPhases(3)
                .build();
        Transformer transformer = Transformer.builder()
                .type("ТДТГ")
                .transformerCharacteristics(transformerCharacteristics)
                .build();
        ChromatographicOilAnalysis chromatographicOilAnalysis = ChromatographicOilAnalysis.builder()
                .dateAnalysis(LocalDate.of(2022, 1, 1))
                .transformer(transformer)
                .carbonDioxideCO2(4500)
                .carbonMonoxideCO(400)
                .methaneCH4(210)
                .ethyleneC2H4(270)
                .acetyleneC2H2(1340)
                .ethaneC2H6(6)
                .hydrogenGasH2(2000)
                .build();
        GasDefectFinder gasDefectFinder = new GasDefectFinder(chromatographicOilAnalysis);
        TypeDefect actual = gasDefectFinder.detectTypeDefect();
        TypeDefect expected = TypeDefect.DISCHARGE_HIGH_POWER;
        assertEquals(actual, expected);
    }

    @Test
    @DisplayName("Тест пример 3 из Приложения 2 РД 153-34 - тест дефекта разряды большой мощности")
    void testDetectDischargeHighPowerDefect2() {
        TransformerCharacteristics transformerCharacteristics = TransformerCharacteristics.builder()
                .power(31500)
                .upVoltage(110d)
                .downVoltage(10.5)
                .numberPhases(3)
                .build();
        Transformer transformer = Transformer.builder()
                .type("ТДТН")
                .transformerCharacteristics(transformerCharacteristics)
                .build();
        ChromatographicOilAnalysis chromatographicOilAnalysis = ChromatographicOilAnalysis.builder()
                .dateAnalysis(LocalDate.of(2022, 1, 1))
                .transformer(transformer)
                .carbonDioxideCO2(1620)
                .carbonMonoxideCO(50)
                .methaneCH4(24)
                .ethyleneC2H4(150)
                .acetyleneC2H2(400)
                .ethaneC2H6(6)
                .hydrogenGasH2(160)
                .build();
        GasDefectFinder gasDefectFinder = new GasDefectFinder(chromatographicOilAnalysis);
        TypeDefect actual = gasDefectFinder.detectTypeDefect();
        TypeDefect expected = TypeDefect.DISCHARGE_HIGH_POWER;
        assertEquals(actual, expected);
    }

    @Test
    @DisplayName("Тест пример 1 из Приложения 4 РД 153-34 - тест термический дефект средних температур")
    void testDetectThermalMediumDefect() {
        TransformerCharacteristics transformerCharacteristics = TransformerCharacteristics.builder()
                .power(63000)
                .upVoltage(110d)
                .downVoltage(10.5)
                .numberPhases(3)
                .build();
        Transformer transformer = Transformer.builder()
                .type("ТРДЦН")
                .transformerCharacteristics(transformerCharacteristics)
                .build();
        ChromatographicOilAnalysis chromatographicOilAnalysis = ChromatographicOilAnalysis.builder()
                .dateAnalysis(LocalDate.of(2022, 1, 1))
                .transformer(transformer)
                .carbonDioxideCO2(4800)
                .carbonMonoxideCO(50)
                .methaneCH4(840)
                .ethyleneC2H4(200)
                .acetyleneC2H2(0)
                .ethaneC2H6(110)
                .hydrogenGasH2(40)
                .build();
        GasDefectFinder gasDefectFinder = new GasDefectFinder(chromatographicOilAnalysis);
        TypeDefect actual = gasDefectFinder.detectTypeDefect();
        TypeDefect expected = TypeDefect.THERMAL_DEFECT_MEDIUM;
        assertEquals(actual, expected);
    }

    @Test
    @DisplayName("Тест пример 2 из Приложения 4 РД 153-34 - дефект высокой температуры >700C")
    void testDetectVeryHighThermalDefect2() {
        TransformerCharacteristics transformerCharacteristics = TransformerCharacteristics.builder()
                .power(240000)
                .upVoltage(220d)
                .middleVoltage(110d)
                .downVoltage(10.5)
                .numberPhases(3)
                .build();
        Transformer transformer = Transformer.builder()
                .type("АТДЦТГ")
                .transformerCharacteristics(transformerCharacteristics)
                .build();
        ChromatographicOilAnalysis chromatographicOilAnalysis = ChromatographicOilAnalysis.builder()
                .dateAnalysis(LocalDate.of(2022, 1, 1))
                .transformer(transformer)
                .carbonDioxideCO2(2400)
                .carbonMonoxideCO(190)
                .methaneCH4(900)
                .ethyleneC2H4(1670)
                .acetyleneC2H2(80)
                .ethaneC2H6(300)
                .hydrogenGasH2(100)
                .build();
        GasDefectFinder gasDefectFinder = new GasDefectFinder(chromatographicOilAnalysis);
        TypeDefect actual = gasDefectFinder.detectTypeDefect();
        TypeDefect expected = TypeDefect.THERMAL_DEFECT_VERY_HIGH;
        assertEquals(actual, expected);
    }

    @Test
    @DisplayName("Тест пример 3 из Приложения 4 РД 153-34 - дефект высокой температуры >700C")
    void testDetectVeryHighThermalDefect3() {
        TransformerCharacteristics transformerCharacteristics = TransformerCharacteristics.builder()
                .power(250000)
                .upVoltage(550d)
                .middleVoltage(220d)
                .downVoltage(10.5)
                .numberPhases(3)
                .build();
        Transformer transformer = Transformer.builder()
                .type("АТДЦТН")
                .transformerCharacteristics(transformerCharacteristics)
                .build();
        ChromatographicOilAnalysis chromatographicOilAnalysis = ChromatographicOilAnalysis.builder()
                .dateAnalysis(LocalDate.of(2022, 1, 1))
                .transformer(transformer)
                .carbonDioxideCO2(1900)
                .carbonMonoxideCO(160)
                .methaneCH4(1800)
                .ethyleneC2H4(3000)
                .acetyleneC2H2(0)
                .ethaneC2H6(430)
                .hydrogenGasH2(300)
                .build();
        GasDefectFinder gasDefectFinder = new GasDefectFinder(chromatographicOilAnalysis);
        TypeDefect actual = gasDefectFinder.detectTypeDefect();
        TypeDefect expected = TypeDefect.THERMAL_DEFECT_VERY_HIGH;
        assertEquals(actual, expected);
    }

    @Test
    @DisplayName("Тест пример 4 из Приложения 4 РД 153-34 - дефект высокой температуры >700C")
    void testDetectVeryHighThermalDefect4() {
        TransformerCharacteristics transformerCharacteristics = TransformerCharacteristics.builder()
                .power(40000)
                .upVoltage(110d)
                .downVoltage(10.5)
                .numberPhases(3)
                .build();
        Transformer transformer = Transformer.builder()
                .type("ТДТН")
                .transformerCharacteristics(transformerCharacteristics)
                .build();
        ChromatographicOilAnalysis chromatographicOilAnalysis = ChromatographicOilAnalysis.builder()
                .dateAnalysis(LocalDate.of(2022, 1, 1))
                .transformer(transformer)
                .carbonDioxideCO2(4500)
                .carbonMonoxideCO(400)
                .methaneCH4(360)
                .ethyleneC2H4(1520)
                .acetyleneC2H2(0)
                .ethaneC2H6(390)
                .hydrogenGasH2(110)
                .build();
        GasDefectFinder gasDefectFinder = new GasDefectFinder(chromatographicOilAnalysis);
        TypeDefect actual = gasDefectFinder.detectTypeDefect();
        TypeDefect expected = TypeDefect.THERMAL_DEFECT_VERY_HIGH;
        assertEquals(actual, expected);
    }

    @Test
    @DisplayName("Тест пример 5 из Приложения 4 РД 153-34 - тест дефекта разряды большой мощности")
    void testDetectDischargeHighPowerDefect3() {
        TransformerCharacteristics transformerCharacteristics = TransformerCharacteristics.builder()
                .power(80000)
                .upVoltage(220d)
                .middleVoltage(110d)
                .downVoltage(10.5)
                .numberPhases(1)
                .build();
        Transformer transformer = Transformer.builder()
                .type("ОДТГА")
                .transformerCharacteristics(transformerCharacteristics)
                .build();
        ChromatographicOilAnalysis chromatographicOilAnalysis = ChromatographicOilAnalysis.builder()
                .dateAnalysis(LocalDate.of(2022, 1, 1))
                .transformer(transformer)
                .carbonDioxideCO2(2700)
                .carbonMonoxideCO(640)
                .methaneCH4(190)
                .ethyleneC2H4(240)
                .acetyleneC2H2(130)
                .ethaneC2H6(23)
                .hydrogenGasH2(970)
                .build();
        GasDefectFinder gasDefectFinder = new GasDefectFinder(chromatographicOilAnalysis);
        TypeDefect actual = gasDefectFinder.detectTypeDefect();
        TypeDefect expected = TypeDefect.DISCHARGE_HIGH_POWER;
        assertEquals(actual, expected);
    }
}