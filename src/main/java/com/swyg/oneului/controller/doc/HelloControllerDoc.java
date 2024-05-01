package com.swyg.oneului.controller.doc;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

public interface HelloControllerDoc {
    @Operation(summary = "API Test입니다. CORS 뜨면 말씀주세요!")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "성공")
            })
    String hello();
}
