package com.paymentology.reconsilation_service.util;

import com.paymentology.reconsilation_service.exception.RequestTranslationException;
import org.apache.commons.io.FilenameUtils;
import org.springframework.web.multipart.MultipartFile;

public class FileUtils {

    public static void validateMultipartCsv(MultipartFile file) {
        if (file.isEmpty()) {
            throw new RequestTranslationException(400, "Input file is Empty.");
        }

        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        if (!"CSV".equalsIgnoreCase(extension)) {
            throw new RequestTranslationException(400, String.format("Unsupported File Type %s", extension));
        }
    }
}
