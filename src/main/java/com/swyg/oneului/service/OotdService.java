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
    private final BookMarkOotdService bookMarkOotdService;
    private final LikeOotdService likeOotdService;

    public Ootd findOotdById(Long ootdId) {
        return ootdRepository.findById(ootdId)
                .orElseThrow(() -> new NoSuchElementException("해당 OOTD가 존재하지 않습니다."));
    }

    public Ootd findOotdByMemberAndOotdId(Member member, Long ootdId) {
        return ootdRepository.findOotdByMemberAndOotdId(member, ootdId);
    }

    public List<Ootd> findAllOotdsByMember(Member member) {
        return ootdRepository.findAllOotdsByMember(member);
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
    public void updateOotd(Ootd existingOotd, Ootd ootd, MultipartFile image) {
        existingOotd.modifyOotd(ootd);
        
        ootdImageService.replaceOotdImage(existingOotd, image);
    }

    @Transactional
    public void deleteOotdById(Ootd ootd) {
        likeOotdService.deleteLikeOotdByOotd(ootd);
        bookMarkOotdService.deleteBookMarkOotdByOotd(ootd);
        ootdImageService.deleteOotdImage(ootd);
        ootdRepository.delete(ootd);
    }

    public List<Ootd> findAll() {
        return ootdRepository.findAll();
    }

    public List<Ootd> findAllOotdsByTemperature(String temperature) {
        return ootdRepository.findAllOotdsByTemperature(temperature);
    }

    public List<Ootd> findAllOotdsByHumidity(String humidity) {
        return ootdRepository.findAllOotdsByHumidity(humidity);
    }

    public List<Ootd> findAllOotdsByTemperatureAndHumidity(String temperature, String humidity) {
        return ootdRepository.findAllOotdsByTemperatureAndHumidity(temperature, humidity);
    }
}