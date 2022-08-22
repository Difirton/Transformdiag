package com.difirton.transformdiag.service;

import com.difirton.transformdiag.db.entity.Transformer;
import com.difirton.transformdiag.db.entity.TransformerCharacteristics;
import com.difirton.transformdiag.db.repository.TransformerCharacteristicsRepository;
import com.difirton.transformdiag.db.repository.TransformerRepository;
import com.difirton.transformdiag.error.EmptyListOfAnalysisException;
import com.difirton.transformdiag.error.TransformerNotFoundException;
import com.difirton.transformdiag.service.constant.OilGas;
import com.difirton.transformdiag.service.constant.PhysicalChemicalOilParameter;
import com.difirton.transformdiag.service.constant.TypeDefect;
import com.difirton.transformdiag.service.dto.TransformerDefectDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class TransformerService {
    private TransformerRepository transformerRepository;
    private TransformerCharacteristicsRepository transformerCharacteristicsRepository;

    @Autowired
    public TransformerService(TransformerRepository transformerRepository,
                              TransformerCharacteristicsRepository transformerCharacteristicsRepository) {
        this.transformerRepository = transformerRepository;
        this.transformerCharacteristicsRepository = transformerCharacteristicsRepository;
    }

    public List<Transformer> getAllTransformer() {
        return transformerRepository.findAll();
    }

    @Transactional
    public Transformer saveTransformer(Transformer transformer, TransformerCharacteristics characteristics) {
        characteristics.setTransformer(transformer);
        transformerCharacteristicsRepository.save(characteristics);
        return transformerRepository.save(transformer);
    }

    public Transformer getTransformerById(Long id) {
        return transformerRepository.findById(id).orElseThrow(() -> new TransformerNotFoundException(id));
    }

    public Transformer updateTransformer(Long id, Transformer transformer) {
        return transformerRepository.findById(id)
                .map(t -> {
                    t.setKKS(transformer.getKKS());
                    t.setType(transformer.getType());
                    t.setFactoryNumber(transformer.getFactoryNumber());
                    return transformerRepository.save(t);
                })
                .orElseThrow(() -> new TransformerNotFoundException(id));
    }

    public TransformerCharacteristics getTransformerCharacteristicsById(Long id) {
        return transformerCharacteristicsRepository.findByTransformerId(id);
    }

    public void deleteTransformerById(Long id) {
        transformerRepository.deleteById(id);
    }

    @Transactional
    public Transformer saveTransformer(Transformer transformer) {
        transformerCharacteristicsRepository.save(transformer.getTransformerCharacteristics());
        return transformerRepository.save(transformer);
    }

    @Transactional
    public TransformerDefectDto getTransformDefects(Long transformerId) {
        TransformerDefectInvestigator transformerDefectInvestigator =
                new TransformerDefectInvestigator(transformerRepository.findById(transformerId)
                        .orElseThrow(() -> new TransformerNotFoundException(transformerId)));
        Optional<TransformerDefectDto> report = transformerDefectInvestigator.checkTransformer();
        return report.orElseThrow(() -> new EmptyListOfAnalysisException(transformerId));
    }

    @Transactional
    public String getReportOfTransformDefects(Long transformerId) {
        TransformerDefectInvestigator transformerDefectInvestigator =
                new TransformerDefectInvestigator(transformerRepository.findById(transformerId)
                        .orElseThrow(() -> new TransformerNotFoundException(transformerId)));
        Optional<TransformerDefectDto> report = transformerDefectInvestigator.checkTransformer();
        if (report.isEmpty()) {
            return "Transformer performance is normal";
        } else {
            return this.generateReport(report.get());
        }
    }

    private String generateReport(TransformerDefectDto transformerDefectDto) {
        StringBuilder sb = new StringBuilder();
        if (!transformerDefectDto.getGasesOutOfLimit().isEmpty()) {
            sb.append("It is necessary to degas the oil in the transformer, an excess of the normal gas content in " +
                    "gases was revealed: ");
            for (OilGas oilGas : transformerDefectDto.getGasesOutOfLimit()) {
                sb.append(oilGas);
                sb.append(", ");
            }
        }
        if (!transformerDefectDto.getDefineOilParameterDefects().isEmpty()) {
            sb.append("\n It is necessary to replace or treat transformer oil, inappropriate indicators: ");
            for (PhysicalChemicalOilParameter oilParameter : transformerDefectDto.getDefineOilParameterDefects()) {
                sb.append(oilParameter);
                sb.append(", ");
            }
        }
        if (transformerDefectDto.getDefineDefect() != TypeDefect.NORMAL) {
            sb.append("\n Important! As a result of gas analysis, a defect was detected: ");
            sb.append(transformerDefectDto.getDefineDefect());
            if (transformerDefectDto.isDamagedPaperInsulation()) {
                sb.append("\n Paper insulation hurt");
            }
            sb.append("\n The re-extraction of oil must be carried out after ");
            sb.append(transformerDefectDto.getRecommendedDaysBetweenOilSampling());
        }
        return sb.toString();
    }
}
