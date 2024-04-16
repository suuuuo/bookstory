package com.elice.bookstore.book.domain.service;

import java.io.File;
import java.io.IOException;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;


/**
 * FileUploadService.
 */
@Service
public class FileUploadService {

  @Value("${file.upload-dir}")
  private String uploadDir;

  /**
   * uploadFile .
   */
  public String uploadFile(MultipartFile file) throws IOException {
    if (file.isEmpty()) {
      return "파일을 선택해주세요.";
    }

    String originalFileName = StringUtils.cleanPath(file.getOriginalFilename());

    String fileExtension = "";
    int dotIndex = originalFileName.lastIndexOf('.');
    if (dotIndex > 0) {
      fileExtension = originalFileName.substring(dotIndex);
    }

    String fileName = UUID.randomUUID().toString() + fileExtension;

    File destFile = new File(uploadDir + File.separator + fileName);

    file.transferTo(destFile);

    return destFile.getAbsolutePath();
  }
}
