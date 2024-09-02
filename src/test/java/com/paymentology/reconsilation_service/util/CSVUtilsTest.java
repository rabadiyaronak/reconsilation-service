package com.paymentology.reconsilation_service.util;

import com.paymentology.reconsilation_service.exception.FileParsingException;
import com.paymentology.reconsilation_service.model.Transaction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CSVUtilsTest {

    @Test
    void convertToBeanSuccess() throws IOException {
        InputStream stream = getClass().getClassLoader().getResourceAsStream("transactions.csv");
        MultipartFile file = new MockMultipartFile("file", "file1", "text", stream);
        List<Transaction> transactions = CSVUtils.convertToBean(file);
        assertNotNull(transactions);
        assertFalse(transactions.isEmpty());
        assertEquals(1, transactions.size());
    }

    @Test
    void convertToBeanFailure() throws IOException {
        MultipartFile file = Mockito.mock(MultipartFile.class);
        Mockito.when(file.getInputStream()).thenThrow(new IOException("Test Exception"));

        FileParsingException t = Assertions.assertThrows(FileParsingException.class, () -> CSVUtils.convertToBean(file));

        assertNotNull(t.getMessage());
        assertEquals("Unable to parse input file", t.getMessage());
        assertEquals(422, t.getCode());
    }
}