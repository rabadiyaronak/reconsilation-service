package com.paymentology.reconsilation_service.service;

import com.paymentology.reconsilation_service.model.ReconcileReportData;
import org.springframework.web.multipart.MultipartFile;

public interface ReconcileService {

    ReconcileReportData getReconciledetails(MultipartFile file1, MultipartFile file);

}
