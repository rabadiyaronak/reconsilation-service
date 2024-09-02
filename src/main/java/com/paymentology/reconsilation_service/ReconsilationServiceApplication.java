package com.paymentology.reconsilation_service;

import com.opencsv.bean.CsvToBeanBuilder;
import com.paymentology.reconsilation_service.exception.FileParsingException;
import com.paymentology.reconsilation_service.model.Transaction;
import com.paymentology.reconsilation_service.util.CSVUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;

import java.io.*;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.*;
import java.util.stream.Collectors;

@SpringBootApplication
public class ReconsilationServiceApplication {

    public static void main(String[] args) throws FileNotFoundException {

        SpringApplication.run(ReconsilationServiceApplication.class, args);
    }


}
