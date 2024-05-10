package com.swyg.oneului.service;

import com.swyg.oneului.model.LikeOotd;
import com.swyg.oneului.model.Member;
import com.swyg.oneului.model.Ootd;
import com.swyg.oneului.repository.LikeOotdRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class LikeOotdService {
    private final LikeOotdRepository likeOotdRepository;

    @Transactional
    public void createLikeOotd(Member member, Ootd ootd) {
        LikeOotd likeOotd = LikeOotd.builder()
                .member(member)
                .ootd(ootd)
                .build();

        likeOotdRepository.save(likeOotd);
    }

    @Transactional
    public void deleteLikeOotd(Member member, Ootd ootd) {
        likeOotdRepository.deleteLikeOotdByMemberAndOotd(member, ootd);
    }

    @Transactional
    public void deleteLikeOotdByOotd(Ootd ootd) {
        likeOotdRepository.deleteLikeOotdByOotd(ootd);
    }

    public List<LikeOotd> findAllLikeOotdByMember(Member member) {
        return likeOotdRepository.findAllLikeOotdByMember(member);
    }

    public List<LikeOotd> findAllLikeOotdByMemberAndOotd(Member member, Ootd ootd) {
        return likeOotdRepository.findAllLikeOotdByMemberAndOotd(member, ootd);
    }
}