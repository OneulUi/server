package com.swyg.oneului.controller.doc;

import com.swyg.oneului.common.CommonApiResponse;
import com.swyg.oneului.dto.OotdDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Tag(name = "OOTD", description = "OOTD API")
public interface OotdControllerDoc {
    @Operation(summary = "업로드된 모든 OOTD를 조회하는 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공")})
    @GetMapping("/ootds")
    ResponseEntity<CommonApiResponse<List<OotdDTO.Response>>> getAllOotds();

    @Operation(summary = "OOTD 단건 조회 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "400", description = "유효하지 않은 인자 값")})
    @GetMapping("/ootds/{ootdId}")
    ResponseEntity<CommonApiResponse<OotdDTO.Response>> getOotdById(@PathVariable(name = "ootdId") Long ootdId);

    @Operation(summary = "신규 OOTD 등록 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "400", description = "유효하지 않은 인자 값")})
    @PostMapping("/ootds")
    ResponseEntity<CommonApiResponse<?>> createOotd(Authentication authentication,
                                                    @RequestPart(name = "image") MultipartFile image,
                                                    @RequestPart(name = "ootd") OotdDTO.Request ootdDTO);

    @Operation(summary = "기존에 등록된 OOTD 수정 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "400", description = "유효하지 않은 인자 값")})
    @PutMapping("/ootds")
    ResponseEntity<CommonApiResponse<?>> updateOotd(Authentication authentication,
                                                    @RequestPart(name = "image") MultipartFile image,
                                                    @RequestPart(name = "ootd") OotdDTO.Request ootdDTO) throws IOException;

    @Operation(summary = "기존에 등록된 OOTD 삭제 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "400", description = "유효하지 않은 인자 값")})
    @DeleteMapping("/ootds")
    ResponseEntity<CommonApiResponse<?>> deleteOotd(Authentication authentication,
                                                    @RequestPart(name = "ootd") OotdDTO.Request ootdDTO);

    @Operation(summary = "OOTD 이미지를 가져오기 위한 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "400", description = "유효하지 않은 인자 값")})
    @GetMapping("/ootds/images/{fileName}")
    ResponseEntity<byte[]> getOotdImageByFileName(@PathVariable(name = "fileName") String fileName);

    @Operation(summary = "북마크로 저장한 OOTD를 조회하는 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "400", description = "유효하지 않은 인자 값")})
    @GetMapping("/ootds/bookmarks")
    ResponseEntity<CommonApiResponse<List<OotdDTO.Response>>> getAllBookMarkOotd(Authentication authentication);

    @Operation(summary = "선택한 OOTD를 북마크로 저장하는 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "400", description = "유효하지 않은 인자 값")})
    @PostMapping("/ootds/bookmarks")
    ResponseEntity<CommonApiResponse<?>> createBookMarkOotd(Authentication authentication,
                                                            OotdDTO.Request ootdDTO);

    @Operation(summary = "선택한 OOTD를 북마크에서 삭제하는 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "400", description = "유효하지 않은 인자 값")})
    @DeleteMapping("/ootds/bookmarks")
    ResponseEntity<CommonApiResponse<?>> deleteBookMarkOotd(Authentication authentication,
                                                            OotdDTO.Request ootdDTO);

    @Operation(summary = "좋아요를 누른 OOTD를 조회하는 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "400", description = "유효하지 않은 인자 값")})
    @GetMapping("/ootds/likes")
    ResponseEntity<CommonApiResponse<List<OotdDTO.Response>>> getAllLikeOotd(Authentication authentication);

    @Operation(summary = "선택한 OOTD를 좋아요 보관함에 저장하는 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "400", description = "유효하지 않은 인자 값")})
    @PostMapping("/ootds/likes")
    ResponseEntity<CommonApiResponse<?>> createLikeOotd(Authentication authentication,
                                                        OotdDTO.Request ootdDTO);

    @Operation(summary = "선택한 OOTD를 좋아요 보관함에 삭제하는 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "400", description = "유효하지 않은 인자 값")})
    @DeleteMapping("/ootds/likes")
    ResponseEntity<CommonApiResponse<?>> deleteLikeOotd(Authentication authentication,
                                                        OotdDTO.Request ootdDTO);
}
