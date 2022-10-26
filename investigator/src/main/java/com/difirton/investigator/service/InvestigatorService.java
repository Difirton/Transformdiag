package com.difirton.investigator.service;

import com.difirton.investigator.db.entity.TransformerStatus;

public interface InvestigatorService {

    TransformerStatus getNewTransformStatus(Long transformerId);

    String getReportOfTransformDefects(Long transformerId);
}
