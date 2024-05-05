package com.swyg.oneului.service;

import com.swyg.oneului.exception.StorageException;
import com.swyg.oneului.model.Ootd;
import com.swyg.oneului.model.OotdImage;
import com.swyg.oneului.repository.OotdImageRepository;
import com.swyg.oneului.util.FileGenerator;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.file.ConfigurationSource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class OotdImageService {
    private final FileGenerator fileGenerator;
    private final OotdImageRepository ootdImageRepository;

    @Transactional
    public OotdImage createOotdImage(MultipartFile file) {
        // 파일 유효성 검증
        validateFile(file);

        // 파일 서버에 저장
        String fileName = fileGenerator.generateFileName(file.getOriginalFilename());
        Path absolutePath = fileGenerator.generateAbsolutePath(fileName);
        fileGenerator.saveImageFile(file, absolutePath);
        
        // 파일 객체 저장
        OotdImage ootdImage = OotdImage.builder()
                .fileName(fileName)
                .build();

        return ootdImageRepository.save(ootdImage);
    }

    @Transactional
    public void replaceOotdImage(Ootd ootd, MultipartFile image) {
        // 파일 유효성 검증
        validateFile(image);

        // 기존에 저장되어 있던 이미지 제거
        List<OotdImage> ootdImages = ootdImageRepository.findOotdImageByOotd(ootd);
        for (OotdImage ootdImage : ootdImages) {
            String ootdImageFileName = ootdImage.getFileName();
            fileGenerator.removeImageFile(ootdImageFileName);

            String fileName = fileGenerator.generateFileName(image.getOriginalFilename());
            Path absolutePath = fileGenerator.generateAbsolutePath(fileName);
            fileGenerator.saveImageFile(image, absolutePath);

            ootdImage.modifyFileName(fileName);
        }
    }

    @Transactional
    public void deleteOotdImage(Ootd ootd) {
        List<OotdImage> ootdImages = ootdImageRepository.findOotdImageByOotd(ootd);
        for (OotdImage ootdImage : ootdImages) {
            String ootdImagePath = ootdImage.getFileName();
            fileGenerator.removeImageFile(ootdImagePath);
            ootdImageRepository.delete(ootdImage);
        }
    }

    public ResponseEntity<byte[]> findOotdImageByfileName(String fileName) {
        try {
            Path filePath = fileGenerator.generateAbsolutePath(fileName);
            Resource resource = new UrlResource(filePath.toUri());
            if (!resource.exists()) {
                return ResponseEntity.notFound().build();
            }

            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-type", Files.probeContentType(filePath));
            byte[] bytes = Files.readAllBytes(filePath);

            return ResponseEntity.status(HttpStatus.OK).headers(headers).body(bytes);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    private void validateFile(MultipartFile file) {
        if (file.isEmpty()) {
            throw new StorageException("빈 파일을 저장할 수 없습니다.");
        }
    }
}
