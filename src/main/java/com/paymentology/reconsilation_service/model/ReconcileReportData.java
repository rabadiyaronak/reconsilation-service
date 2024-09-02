package com.paymentology.reconsilation_service.model;

import lombok.Data;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

@Data
public class ReconcileReportData {

    Set<FileResponseMetaData> data = new LinkedHashSet<>();
}
