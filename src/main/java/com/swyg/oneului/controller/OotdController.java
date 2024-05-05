package com.swyg.oneului.controller;

import com.swyg.oneului.common.CommonApiResponse;
import com.swyg.oneului.dto.OotdDTO;
import com.swyg.oneului.model.Member;
import com.swyg.oneului.model.Ootd;
import com.swyg.oneului.service.MemberService;
import com.swyg.oneului.service.OotdImageService;
import com.swyg.oneului.service.OotdService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class OotdController {
    private final OotdService ootdService;
    private final OotdImageService ootdImageService;
    private final MemberService memberService;

    @GetMapping("/ootds")
    public ResponseEntity<CommonApiResponse<List<OotdDTO>>> getAllOotd() {
        List<Ootd> ootds = ootdService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(CommonApiResponse.createSuccess(OotdDTO.listOf(ootds)));
    }

    @GetMapping("/ootds/{ootdId}")
    public ResponseEntity<CommonApiResponse<OotdDTO>> getOotdById(@PathVariable(name = "ootdId") Long ootdId) {
        Ootd ootd = ootdService.findOotdById(ootdId);
        return ResponseEntity.status(HttpStatus.OK).body(CommonApiResponse.createSuccess(OotdDTO.of(ootd)));
    }

    @PostMapping("/ootds")
    public ResponseEntity<CommonApiResponse<?>> createOotd(Authentication authentication,
                                                           @RequestPart(name = "image", required = false) MultipartFile image,
                                                           @RequestPart(name = "ootd") OotdDTO ootdDTO) {
        // 현재 로그인한 사용자 조회
        String loginId = authentication.getName();
        Member member = memberService.findMemberByLoginId(loginId);

        // Ootd 사진 및 내용 저장
        Ootd ootd = Ootd.of(ootdDTO);
        ootdService.createOotd(member, ootd, image);

        return ResponseEntity.status(HttpStatus.OK).body(CommonApiResponse.createSuccessWithNoContent());
    }

    @PutMapping("/ootds")
    public ResponseEntity<CommonApiResponse<?>> updateOotd(Authentication authentication,
                                                           @RequestPart(name = "image", required = false) MultipartFile image,
                                                           @RequestPart(name = "ootd") OotdDTO ootdDTO) throws IOException {
        // 현재 로그인한 사용자 조회
        String loginId = authentication.getName();
        Member member = memberService.findMemberByLoginId(loginId);

        // Ootd 정보 업데이트
        Ootd ootd = Ootd.of(ootdDTO);
        ootdService.updateOotd(ootd, image);

        return ResponseEntity.status(HttpStatus.OK).body(CommonApiResponse.createSuccessWithNoContent());
    }

    @DeleteMapping("/ootds")
    public void deleteOotd(Authentication authentication,
                           @RequestPart(name = "ootd") OotdDTO ootdDTO) {
        Ootd ootd = Ootd.of(ootdDTO);
        ootdService.deleteOotdByOotdId(ootd);
    }

    @GetMapping("/ootds/images/{fileName}")
    public ResponseEntity<byte[]> getOotdImageByFileName(@PathVariable(name = "fileName") String fileName) {
        return ootdImageService.findOotdImageByfileName(fileName);
    }
}