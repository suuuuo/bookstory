package com.elice.bookstore.book.domain.service;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class FileUploadServiceTest {

    @InjectMocks
    private FileUploadService fileUploadService;

    @TempDir
    static Path sharedTempDir;

    @BeforeEach
    void setUp() {
        // 임시 업로드 디렉토리 설정
        fileUploadService.setUploadDir(sharedTempDir.toString());
    }

    @Test
    void uploadFile_ShouldSaveFileCorrectly() throws IOException {
        // Given
        MultipartFile mockFile = new MockMultipartFile(
                "file",
                "test.txt",
                "text/plain",
                "Hello World".getBytes(StandardCharsets.UTF_8)
        );

        // When
        String resultPath = fileUploadService.uploadFile(mockFile);

        // Then
        assertThat(resultPath).contains(sharedTempDir.toString());
        File uploadedFile = new File(resultPath);
        assertThat(uploadedFile.exists()).isTrue();
        assertThat(uploadedFile.length()).isGreaterThan(0);

    }
}
