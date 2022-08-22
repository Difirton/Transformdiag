package com.difirton.transformdiag.web.converter;

import com.difirton.transformdiag.service.dto.TransformerDefectDto;
import com.difirton.transformdiag.web.dto.response.TransformerDefectResponseDto;
import org.springframework.stereotype.Component;
import org.springframework.core.convert.converter.Converter;

@Component
public class TransformerDefectDtoToTransformerDefectResponseDtoConverter
        implements Converter<TransformerDefectDto, TransformerDefectResponseDto> {

    @Override
    public TransformerDefectResponseDto convert(TransformerDefectDto source) {
        return TransformerDefectResponseDto.builder()
                .gasesOutOfLimit(source.getGasesOutOfLimit())
                .defineOilParameterDefects(source.getDefineOilParameterDefects())
                .defineDefect(source.getDefineDefect())
                .isDamagedPaperInsulation(source.isDamagedPaperInsulation())
                .recommendedDaysBetweenOilSampling(source.getRecommendedDaysBetweenOilSampling()).build();
    }
}
