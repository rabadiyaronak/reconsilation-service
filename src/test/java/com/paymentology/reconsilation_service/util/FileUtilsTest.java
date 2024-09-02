package com.paymentology.reconsilation_service.util;

import com.paymentology.reconsilation_service.exception.RequestTranslationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import static org.junit.jupiter.api.Assertions.*;

class FileUtilsTest {

    FileUtils fileUtils = new FileUtils();

    @Test
    public void validateMultipartCSVWithEmptyFile() {
        MultipartFile file = new MockMultipartFile("file", (byte[]) null);
        RequestTranslationException t = Assertions.assertThrows(RequestTranslationException.class, () -> FileUtils.validateMultipartCsv(file));

        assertNotNull(t.getMessage());
        assertEquals("Input file is Empty.", t.getMessage());
        assertEquals(400, t.getCode());
    }


    @Test
    public void validateMultipartCSVWithInvalidFileType() {
        MultipartFile file = new MockMultipartFile("file", "file.pdf", "pdf", new byte[]{1, 2, 3, 4});
        RequestTranslationException t = Assertions.assertThrows(RequestTranslationException.class, () -> FileUtils.validateMultipartCsv(file));

        assertNotNull(t.getMessage());
        assertEquals("Unsupported File Type pdf", t.getMessage());
        assertEquals(400, t.getCode());
    }


    @Test
    public void validateMultipartCSV() {
        MultipartFile file = new MockMultipartFile("file", "file.csv", "csv", new byte[]{1, 2, 3, 4});
        try {
            FileUtils.validateMultipartCsv(file);
        } catch (Exception e) {
            fail("Exception Should not be thrown for the test");
        }
    }

}