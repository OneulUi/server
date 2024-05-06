package com.swyg.oneului.service;

import com.swyg.oneului.model.Member;
import com.swyg.oneului.model.Ootd;
import com.swyg.oneului.model.BookMarkOotd;
import com.swyg.oneului.repository.BookMarkOotdRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class BookMarkOotdService {
    private final BookMarkOotdRepository bookMarkOotdRepository;

    @Transactional
    public void createBookMarkOotd(Member member, Ootd ootd) {
        BookMarkOotd bookMarkOotd = BookMarkOotd.builder()
                .member(member)
                .ootd(ootd)
                .build();

        bookMarkOotdRepository.save(bookMarkOotd);
    }

    @Transactional
    public void deleteBookMarkOotd(Member member, Ootd ootd) {
        bookMarkOotdRepository.deleteBookMarkOotdByMemberAndOotd(member, ootd);
    }

    public List<BookMarkOotd> findAllBookMarkOotdByMember(Member member) {
        return bookMarkOotdRepository.findBookMarkOotdByMember(member);
    }
}
