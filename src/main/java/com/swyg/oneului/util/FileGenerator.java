package com.swyg.oneului.util;

import com.swyg.oneului.exception.StorageException;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@RequiredArgsConstructor
@Component
public class FileGenerator {
    private static final String DELIMITER = "_";
    private static final String URL_FORMAT = "%s/%s";
    private static final String STORE_FILE_OUTSIDE_CURRENT_DIRECTORY_MESSAGE = "유효하지 않은 저장 경로입니다.";
    private final ResourceLoader resourceLoader;

    public Path generateAbsolutePath(String fileName) {
        try {
            ClassPathResource resource = new ClassPathResource("uploads/");
            File file = resource.getFile();
            Path absolutePath = Paths.get(file.getAbsolutePath());
            return absolutePath.resolve(Paths.get(fileName)).normalize().toAbsolutePath();
        } catch (IOException e) {
            throw new StorageException("파일이 존재하는 경로를 찾지 못했습니다.");
        }
    }

    public Path generateReadAbsolutePath(String fileName) {
        Path readAbsolutePath = Paths.get("C:\\Users\\ADMIN\\Documents\\GitHub\\server\\build\\resources\\main\\uploads\\");
        return readAbsolutePath.resolve(Paths.get(fileName)).normalize().toAbsolutePath();
    }

    public String generateFileName(String fileName) {
        return UUID.randomUUID() + DELIMITER + fileName;
    }

    public void saveImageFile(MultipartFile file, Path targetPath) {
        try (InputStream inputStream = file.getInputStream()) {
            Files.copy(inputStream, targetPath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new StorageException("파일 저장 중 문제가 발생했습니다. 다시 시도해주세요.");
        }
    }

    public void removeImageFile(String fileName) {
        try {
            Path targetPath = generateAbsolutePath(fileName);
            Files.delete(targetPath);
        } catch (IOException e) {
            throw new StorageException("파일 삭제 중 문제가 발생했습니다.");
        }
    }
}