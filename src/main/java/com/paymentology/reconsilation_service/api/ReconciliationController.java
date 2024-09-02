package com.paymentology.reconsilation_service.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.paymentology.reconsilation_service.exception.ResponseParsingException;
import com.paymentology.reconsilation_service.model.ReconcileReportData;
import com.paymentology.reconsilation_service.service.ReconcileService;
import com.paymentology.reconsilation_service.util.FileUtils;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.io.File;

@Controller
@RequestMapping("api/v1/transaction")
public class ReconciliationController {

    private final ReconcileService reconcileService;

    public ReconciliationController(ReconcileService reconcileService) {
        this.reconcileService = reconcileService;
    }

    @PostMapping(value = "reconcile")
    public String reconcileReport(@RequestParam("file1") MultipartFile file1, @RequestParam("file2") MultipartFile file2, RedirectAttributes redirectAttrs) {
        FileUtils.validateMultipartCsv(file1);
        FileUtils.validateMultipartCsv(file2);
        ReconcileReportData reportData = reconcileService.getReconciledetails(file1, file2);
        String reportJson = "";
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.findAndRegisterModules();
            reportJson = objectMapper.writeValueAsString(reportData);
        } catch (JsonProcessingException e) {
            throw new ResponseParsingException(500, "unable to parse report response", e);
        }

        redirectAttrs.addFlashAttribute("report", reportJson);
        return "redirect:/";
    }


    @PostMapping(value = "reconcile/json", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ReconcileReportData reconcileReport(@RequestParam("file1") MultipartFile file1, @RequestParam("file2") MultipartFile file2) {
        FileUtils.validateMultipartCsv(file1);
        FileUtils.validateMultipartCsv(file2);
        return reconcileService.getReconciledetails(file1, file2);

    }


}
