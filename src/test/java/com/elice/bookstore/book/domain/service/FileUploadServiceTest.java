package com.elice.bookstore.book.domain.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doReturn;

@SpringBootTest
public class FileUploadServiceTest {

    @Autowired
    private FileUploadService fileUploadService;

    private Path tempDir;

    @BeforeEach
    public void setUp() throws IOException {
        tempDir = Files.createTempDirectory("upload_test");
    }

    @AfterEach
    public void tearDown() throws IOException {
        Files.walk(tempDir)
                .map(Path::toFile)
                .forEach(File::delete);
    }

    @Test
    public void whenFileIsUploaded_thenReturnsPath() throws IOException {
        // 임시 디렉토리를 uploadDir로 사용하기 위해 설정값을 임시 경로로 변경합니다.
        fileUploadService.setUploadDir(tempDir.toString());

        MultipartFile file = new MockMultipartFile("file", "test.txt", "text/plain", "Spring Framework".getBytes());

        String filePath = fileUploadService.uploadFile(file);

        File savedFile = new File(filePath);
        assertTrue(savedFile.exists());
        assertTrue(filePath.startsWith(tempDir.toString()));
    }

}
