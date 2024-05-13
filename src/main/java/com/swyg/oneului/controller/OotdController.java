package com.swyg.oneului.controller;

import com.swyg.oneului.common.CommonApiResponse;
import com.swyg.oneului.controller.doc.OotdControllerDoc;
import com.swyg.oneului.dto.BookMarkOotdDTO;
import com.swyg.oneului.dto.LikeOotdDTO;
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

    @PostMapping("/ootds")
    public ResponseEntity<CommonApiResponse<?>> createOotd(Authentication authentication,
                                                           @RequestPart(name = "image") MultipartFile image,
                                                           @RequestPart(name = "ootd") OotdDTO.Create ootdDTO) {
        String loginId = authentication.getName();
        Member member = memberService.findMemberByLoginId(loginId);

        Ootd ootd = OotdDTO.Create.toEntity(ootdDTO);
        ootdService.createOotd(member, ootd, image);

        return ResponseEntity.status(HttpStatus.OK).body(CommonApiResponse.createSuccessWithNoContent());
    }

    @GetMapping("/ootds/{ootdId}")
    public ResponseEntity<CommonApiResponse<OotdDTO.Response>> getOotdById(@PathVariable(name = "ootdId") Long ootdId) {
        Ootd ootd = ootdService.findOotdById(ootdId);
        return ResponseEntity.status(HttpStatus.OK).body(CommonApiResponse.createSuccess(OotdDTO.Response.of(ootd)));
    }

    @PutMapping("/ootds/{ootdId}")
    public ResponseEntity<CommonApiResponse<?>> updateOotd(Authentication authentication,
                                                           @PathVariable(name = "ootdId") Long ootdId,
                                                           @RequestPart(name = "image") MultipartFile image,
                                                           @RequestPart(name = "ootd") OotdDTO.Update ootdDTO) {
        String loginId = authentication.getName();
        Member member = memberService.findMemberByLoginId(loginId);

        Ootd existingOotd = ootdService.findOotdByMemberAndOotdId(member, ootdId);
        Ootd ootd = OotdDTO.Update.toEntity(ootdDTO);
        ootdService.updateOotd(existingOotd, ootd, image);

        return ResponseEntity.status(HttpStatus.OK).body(CommonApiResponse.createSuccessWithNoContent());
    }

    @DeleteMapping("/ootds/{ootdId}")
    public ResponseEntity<CommonApiResponse<?>> deleteOotd(Authentication authentication,
                                                           @PathVariable(name = "ootdId") Long ootdId) {

        String loginId = authentication.getName();
        Member member = memberService.findMemberByLoginId(loginId);

        Ootd ootd = ootdService.findOotdByMemberAndOotdId(member, ootdId);
        ootdService.deleteOotdById(ootd);

        return ResponseEntity.status(HttpStatus.OK).body(CommonApiResponse.createSuccessWithNoContent());
    }

    @GetMapping("/ootds/images/{fileName}")
    public ResponseEntity<byte[]> getOotdImageByFileName(@PathVariable(name = "fileName") String fileName) {

        return ootdImageService.findOotdImageByfileName(fileName);
    }

    @GetMapping("/ootds/temperature")
    public ResponseEntity<CommonApiResponse<List<OotdDTO.Response>>> getAllOotdsByTemperature(@RequestParam(name = "temperature") String temperature) {
        List<Ootd> ootds = ootdService.findAllOotdsByTemperature(temperature);
        return ResponseEntity.status(HttpStatus.OK).body(CommonApiResponse.createSuccess(OotdDTO.Response.listOf(ootds)));
    }

    @GetMapping("/ootds/humidity")
    public ResponseEntity<CommonApiResponse<List<OotdDTO.Response>>> getAllOotdsByHumidity(@RequestParam(name = "humidity") String humidity) {
        List<Ootd> ootds = ootdService.findAllOotdsByHumidity(humidity);
        return ResponseEntity.status(HttpStatus.OK).body(CommonApiResponse.createSuccess(OotdDTO.Response.listOf(ootds)));
    }

    @GetMapping("/ootds/weather")
    public ResponseEntity<CommonApiResponse<List<OotdDTO.Response>>> getAllOotdsByTemperatureAndHumidity(@RequestParam(name = "temperature") String temperature,
                                                                                                         @RequestParam(name = "humidity") String humidity) {
        List<Ootd> ootds = ootdService.findAllOotdsByTemperatureAndHumidity(temperature, humidity);
        return ResponseEntity.status(HttpStatus.OK).body(CommonApiResponse.createSuccess(OotdDTO.Response.listOf(ootds)));
    }

    @GetMapping("/ootds/bookmarks")
    public ResponseEntity<CommonApiResponse<List<OotdDTO.Response>>> getAllBookMarkOotd(Authentication authentication) {
        String loginId = authentication.getName();
        Member member = memberService.findMemberByLoginId(loginId);

        List<BookMarkOotd> bookMarkOotds = bookMarkOotdService.findAllBookMarkOotdsByMember(member);

        List<Ootd> ootds = new ArrayList<>();
        for (BookMarkOotd bookMarkOotd : bookMarkOotds) {
            ootds.add(bookMarkOotd.getOotd());
        }

        return ResponseEntity.status(HttpStatus.OK).body(CommonApiResponse.createSuccess(OotdDTO.Response.listOf(ootds)));
    }

    @PostMapping("/ootds/bookmarks")
    public ResponseEntity<CommonApiResponse<?>> createBookMarkOotd(Authentication authentication,
                                                                   BookMarkOotdDTO.Create ootdDTO) {
        String loginId = authentication.getName();
        Member member = memberService.findMemberByLoginId(loginId);

        Long ootdId = ootdDTO.getOotdId();
        Ootd ootd = ootdService.findOotdById(ootdId);

        List<BookMarkOotd> bookMarkOotds = bookMarkOotdService.findAllBookMarkOotdsByMemberAndOotd(member, ootd);
        if (bookMarkOotds.isEmpty()) {
            bookMarkOotdService.createBookMarkOotd(member, ootd);
        }

        return ResponseEntity.status(HttpStatus.OK).body(CommonApiResponse.createSuccessWithNoContent());
    }

    @DeleteMapping("/ootds/bookmarks/{ootdId}")
    public ResponseEntity<CommonApiResponse<?>> deleteBookMarkOotd(Authentication authentication,
                                                                   @PathVariable(name = "ootdId") Long ootdId) {
        String loginId = authentication.getName();
        Member member = memberService.findMemberByLoginId(loginId);

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
                                                               LikeOotdDTO.Create ootdDTO) {
        String loginId = authentication.getName();
        Member member = memberService.findMemberByLoginId(loginId);

        Long ootdId = ootdDTO.getOotdId();
        Ootd ootd = ootdService.findOotdById(ootdId);

        List<LikeOotd> likeOotds = likeOotdService.findAllLikeOotdByMemberAndOotd(member, ootd);
        if (likeOotds.isEmpty()) {
            likeOotdService.createLikeOotd(member, ootd);
        }

        return ResponseEntity.status(HttpStatus.OK).body(CommonApiResponse.createSuccessWithNoContent());
    }

    @DeleteMapping("/ootds/likes/{ootdId}")
    public ResponseEntity<CommonApiResponse<?>> deleteLikeOotd(Authentication authentication,
                                                               @PathVariable(name = "ootdId") Long ootdId) {
        String loginId = authentication.getName();
        Member member = memberService.findMemberByLoginId(loginId);

        Ootd ootd = ootdService.findOotdById(ootdId);
        likeOotdService.deleteLikeOotd(member, ootd);

        return ResponseEntity.status(HttpStatus.OK).body(CommonApiResponse.createSuccessWithNoContent());
    }
}