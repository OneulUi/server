package com.swyg.oneului.controller;

import com.swyg.oneului.common.CommonApiResponse;
import com.swyg.oneului.controller.doc.OotdControllerDoc;
import com.swyg.oneului.dto.OotdDTO;
import com.swyg.oneului.model.BookMarkOotd;
import com.swyg.oneului.model.LikeOotd;
import com.swyg.oneului.model.Member;
import com.swyg.oneului.model.Ootd;
import com.swyg.oneului.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class OotdController implements OotdControllerDoc {
    private final OotdService ootdService;
    private final OotdImageService ootdImageService;
    private final LikeOotdService likeOotdService;
    private final BookMarkOotdService bookMarkOotdService;
    private final MemberService memberService;

    @GetMapping("/ootds")
    public ResponseEntity<CommonApiResponse<List<OotdDTO.Response>>> getAllOotds() {
        List<Ootd> ootds = ootdService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(CommonApiResponse.createSuccess(OotdDTO.Response.listOf(ootds)));
    }

    @GetMapping("/ootds/{ootdId}")
    public ResponseEntity<CommonApiResponse<OotdDTO.Response>> getOotdById(@PathVariable(name = "ootdId") Long ootdId) {
        Ootd ootd = ootdService.findOotdById(ootdId);
        return ResponseEntity.status(HttpStatus.OK).body(CommonApiResponse.createSuccess(OotdDTO.Response.of(ootd)));
    }

    @PostMapping("/ootds")
    public ResponseEntity<CommonApiResponse<?>> createOotd(Authentication authentication,
                                                           @RequestPart(name = "image") MultipartFile image,
                                                           @RequestPart(name = "ootd") OotdDTO.Request ootdDTO) {
        // 현재 로그인한 사용자 조회
        String loginId = authentication.getName();
        Member member = memberService.findMemberByLoginId(loginId);
        if (ObjectUtils.isEmpty(member)) {
            throw new RuntimeException("로그인을 다시 진행해 주세요.");
        }

        // Ootd 사진 및 내용 저장
        Ootd ootd = OotdDTO.Request.toEntity(ootdDTO);
        ootdService.createOotd(member, ootd, image);

        return ResponseEntity.status(HttpStatus.OK).body(CommonApiResponse.createSuccessWithNoContent());
    }

    @PutMapping("/ootds")
    public ResponseEntity<CommonApiResponse<?>> updateOotd(Authentication authentication,
                                                           @RequestPart(name = "image") MultipartFile image,
                                                           @RequestPart(name = "ootd") OotdDTO.Request ootdDTO) throws IOException {
        // 현재 로그인한 사용자 조회
        String loginId = authentication.getName();
        Member member = memberService.findMemberByLoginId(loginId);

        // Ootd 정보 업데이트
        Ootd ootd = OotdDTO.Request.toEntity(ootdDTO);
        ootdService.updateOotd(ootd, image);

        return ResponseEntity.status(HttpStatus.OK).body(CommonApiResponse.createSuccessWithNoContent());
    }

    @DeleteMapping("/ootds")
    public ResponseEntity<CommonApiResponse<?>> deleteOotd(Authentication authentication,
                           @RequestPart(name = "ootd") OotdDTO.Request ootdDTO) {
        Ootd ootd = OotdDTO.Request.toEntity(ootdDTO);
        ootdService.deleteOotdById(ootd);

        return ResponseEntity.status(HttpStatus.OK).body(CommonApiResponse.createSuccessWithNoContent());
    }

    @GetMapping("/ootds/images/{fileName}")
    public ResponseEntity<byte[]> getOotdImageByFileName(@PathVariable(name = "fileName") String fileName) {
        return ootdImageService.findOotdImageByfileName(fileName);
    }

    @GetMapping("/ootds/bookmarks")
    public ResponseEntity<CommonApiResponse<List<OotdDTO.Response>>> getAllBookMarkOotd(Authentication authentication) {
        String loginId = authentication.getName();
        Member member = memberService.findMemberByLoginId(loginId);

        List<BookMarkOotd> bookMarkOotds = bookMarkOotdService.findAllBookMarkOotdByMember(member);

        List<Ootd> ootds = new ArrayList<>();
        for (BookMarkOotd bookMarkOotd : bookMarkOotds) {
            ootds.add(bookMarkOotd.getOotd());
        }

        return ResponseEntity.status(HttpStatus.OK).body(CommonApiResponse.createSuccess(OotdDTO.Response.listOf(ootds)));
    }

    @PostMapping("/ootds/bookmarks")
    public ResponseEntity<CommonApiResponse<?>> createBookMarkOotd(Authentication authentication,
                                                                   OotdDTO.Request ootdDTO) {
        String loginId = authentication.getName();
        Member member = memberService.findMemberByLoginId(loginId);

        Long ootdId = ootdDTO.getOotdId();
        Ootd ootd = ootdService.findOotdById(ootdId);

        bookMarkOotdService.createBookMarkOotd(member, ootd);

        return ResponseEntity.status(HttpStatus.OK).body(CommonApiResponse.createSuccessWithNoContent());
    }

    @DeleteMapping("/ootds/bookmarks")
    public ResponseEntity<CommonApiResponse<?>> deleteBookMarkOotd(Authentication authentication,
                                                                   OotdDTO.Request ootdDTO) {
        String loginId = authentication.getName();
        Member member = memberService.findMemberByLoginId(loginId);

        Long ootdId = ootdDTO.getOotdId();
        Ootd ootd = ootdService.findOotdById(ootdId);

        bookMarkOotdService.deleteBookMarkOotd(member, ootd);

        return ResponseEntity.status(HttpStatus.OK).body(CommonApiResponse.createSuccessWithNoContent());
    }

    @GetMapping("/ootds/likes")
    public ResponseEntity<CommonApiResponse<List<OotdDTO.Response>>> getAllLikeOotd(Authentication authentication) {
        String loginId = authentication.getName();
        Member member = memberService.findMemberByLoginId(loginId);

        List<LikeOotd> likeOotds = likeOotdService.findAllLikeOotdByMember(member);

        List<Ootd> ootds = new ArrayList<>();
        for (LikeOotd likeOotd : likeOotds) {
            ootds.add(likeOotd.getOotd());
        }

        return ResponseEntity.status(HttpStatus.OK).body(CommonApiResponse.createSuccess(OotdDTO.Response.listOf(ootds)));
    }

    @PostMapping("/ootds/likes")
    public ResponseEntity<CommonApiResponse<?>> createLikeOotd(Authentication authentication,
                                                               OotdDTO.Request ootdDTO) {
        String loginId = authentication.getName();
        Member member = memberService.findMemberByLoginId(loginId);

        Long ootdId = ootdDTO.getOotdId();
        Ootd ootd = ootdService.findOotdById(ootdId);

        likeOotdService.createLikeOotd(member, ootd);

        return ResponseEntity.status(HttpStatus.OK).body(CommonApiResponse.createSuccessWithNoContent());
    }

    @DeleteMapping("/ootds/likes")
    public ResponseEntity<CommonApiResponse<?>> deleteLikeOotd(Authentication authentication,
                                                               OotdDTO.Request ootdDTO) {
        String loginId = authentication.getName();
        Member member = memberService.findMemberByLoginId(loginId);

        Long ootdId = ootdDTO.getOotdId();
        Ootd ootd = ootdService.findOotdById(ootdId);

        likeOotdService.deleteLikeOotd(member, ootd);

        return ResponseEntity.status(HttpStatus.OK).body(CommonApiResponse.createSuccessWithNoContent());
    }
}