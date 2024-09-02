package com.paymentology.reconsilation_service.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class FrontEndController {

    @GetMapping
    public String greet(@RequestParam(name = "report", required = false) String report, Model model) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();
        String json = report ;
        if(model.containsAttribute("report")) {
            json = String.valueOf(model.getAttribute("report"));
        }
        if (StringUtils.isNotEmpty(json)) {
            JsonNode node = objectMapper.readTree(json);

            model.addAttribute("report", node.get("data"));
        }
        return "fileUploadForm";
    }
}
