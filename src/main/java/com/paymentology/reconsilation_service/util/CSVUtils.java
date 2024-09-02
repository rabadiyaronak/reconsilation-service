package com.paymentology.reconsilation_service.util;

import com.opencsv.bean.CsvToBeanBuilder;
import com.paymentology.reconsilation_service.exception.FileParsingException;
import com.paymentology.reconsilation_service.model.Transaction;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;

public class CSVUtils {
    private CSVUtils() {
    }

    public static List<Transaction> convertToBean(MultipartFile file)  {
        try (Reader reader = new InputStreamReader(file.getInputStream())) {
            return new CsvToBeanBuilder<Transaction>(reader)
                    .withSkipLines(1)
                    .withType(Transaction.class)
                    .build()
                    .parse();
        } catch (IOException e) {
            throw new FileParsingException(422,"Unable to parse input file", e);
        }
    }

}
