package com.swyg.oneului.service;

import com.swyg.oneului.model.Member;
import com.swyg.oneului.model.Ootd;
import com.swyg.oneului.model.OotdImage;
import com.swyg.oneului.repository.OotdRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.NoSuchElementException;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class OotdService {
    private final OotdRepository ootdRepository;
    private final OotdImageService ootdImageService;

    public Ootd findOotdById(Long ootdId) {
        return ootdRepository.findById(ootdId)
                .orElseThrow(() -> new NoSuchElementException("해당 ID의 OOTD가 존재하지 않습니다."));
    }

    public List<Ootd> findAllOotds() {
        return ootdRepository.findAll();
    }

    @Transactional
    public void createOotd(Member member, Ootd ootd, MultipartFile image) {
        Ootd savedOotd = ootdRepository.save(ootd);
        member.addOotd(savedOotd);

        OotdImage savedOotdImage = ootdImageService.createOotdImage(image);
        savedOotd.addOotdImage(savedOotdImage);
    }

    @Transactional
    public void updateOotd(Ootd ootd, MultipartFile image) {
        // OOTD 내용 수정
        Long ootdId = ootd.getOotdId();
        Ootd existingOotd = ootdRepository.findById(ootdId)
                .orElseThrow(() -> new NoSuchElementException("해당 ID의 OOTD가 존재하지 않습니다."));
        existingOotd.updateOotd(ootd);
        
        // OOTD 이미지 수정
        ootdImageService.replaceOotdImage(existingOotd, image);
    }

    @Transactional
    public void deleteOotdByOotdId(Ootd ootd) {
        ootdImageService.deleteOotdImage(ootd);
        ootdRepository.delete(ootd);
    }

    public List<Ootd> findAll() {
        return ootdRepository.findAll();
    }
}