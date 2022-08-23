package com.difirton.transformdiag.web.converter;

import com.difirton.transformdiag.db.entity.TransformerStatus;
import com.difirton.transformdiag.web.dto.response.TransformerDefectResponseDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class TransformerDefectDtoToTransformerDefectResponseDtoConverter
        implements Converter<TransformerStatus, TransformerDefectResponseDto> {

    @Override
    public TransformerDefectResponseDto convert(TransformerStatus source) {
        return TransformerDefectResponseDto.builder()
                .gasesOutOfLimit(source.getGasesOutOfLimit())
                .defineOilParameterDefects(source.getDefineOilParameterDefects())
                .defineDefect(source.getDefineDefect())
                .isDamagedPaperInsulation(source.getIsDamagedPaperInsulation())
                .recommendedDaysBetweenOilSampling(source.getRecommendedDaysBetweenOilSampling()).build();
    }
}
