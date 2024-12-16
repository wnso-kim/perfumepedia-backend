package com.perfumepedia.perfumepedia.domain.request.controller;

import com.perfumepedia.perfumepedia.domain.perfume.service.PerfumeService;
import com.perfumepedia.perfumepedia.domain.perfume.service.RequestPerfumeService;
import com.perfumepedia.perfumepedia.domain.perfumeNote.dto.PerfumeDetailResponse;
import com.perfumepedia.perfumepedia.domain.perfumeNote.dto.RequestPerfumeDetailReq;
import com.perfumepedia.perfumepedia.domain.perfumeNote.service.RequestPerfumeNoteService;
import com.perfumepedia.perfumepedia.domain.request.dto.RequestListDto;
import com.perfumepedia.perfumepedia.domain.request.service.RequestService;
import com.perfumepedia.perfumepedia.global.enums.NoneResponse;
import com.perfumepedia.perfumepedia.global.response.Response;
import com.perfumepedia.perfumepedia.global.response.SuccessResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@Tag(name = "향수 요청", description = "향수 요청 관련 API")
public class RequestController {

    private final RequestPerfumeService requestPerfumeService;
    private final RequestService requestService;
    private final RequestPerfumeNoteService requestPerfumeNoteService;
    private final PerfumeService perfumeService;


    @Autowired
    public RequestController(RequestPerfumeService requestPerfumeService, RequestService requestService,
                             RequestPerfumeNoteService requestPerfumeNoteService, PerfumeService perfumeService) {
        this.requestPerfumeService = requestPerfumeService;
        this.requestService = requestService;
        this.requestPerfumeNoteService = requestPerfumeNoteService;
        this.perfumeService = perfumeService;
    }


    @Operation(summary = "향수 등록 요청", description = "유저가 향수의 정보를 작성해 등록 요청합니다.")

    @PostMapping("/perfumes/users")
    public ResponseEntity<Response<NoneResponse>> registerPerfumeRequest(
            @RequestBody RequestPerfumeDetailReq reqPerfumeDetailReq
//            @AuthenticationPrincipal Long userId
    ) {
        SuccessResponse<NoneResponse> response = requestPerfumeService.registerPerfumeRequest(reqPerfumeDetailReq, 1L);
        return Response.success(response);
    }


    @Operation(summary = "향수 수정 요청", description = "유저가 향수의 정보를 작성해 수정 요청합니다.")
    @PostMapping("/perfumes/users/{perfumeId}/update")
    public ResponseEntity<Response<NoneResponse>> updatePerfumeRequest(
            @RequestBody RequestPerfumeDetailReq reqPerfumeDetailReq,
            @PathVariable Long perfumeId
//            @AuthenticationPrincipal Long userId
    ) {
        SuccessResponse<NoneResponse> response = requestPerfumeService.updatePerfumeRequest(reqPerfumeDetailReq, perfumeId, 1L);
        return Response.success(response);
    }


    @Operation(summary = "향수 삭제 요청", description = "유저가 삭제를 원하는 향수를 삭제 요청합니다.")
    @PostMapping("/perfumes/users/{perfumeId}/delete")
    public ResponseEntity<Response<NoneResponse>> deletePerfumeRequest(
            @PathVariable Long perfumeId
//            @AuthenticationPrincipal Long userId
    ) {
        SuccessResponse<NoneResponse> response = requestPerfumeService.deletePerfumeRequest(perfumeId, 1L);
        return Response.success(response);
    }


    @Operation(summary = "향수 요청 개수 조회", description = "관리자가 등록된 향수 요청개수를 조회합니다.")
    @GetMapping("/perfumes/admins/request-counts")
    public ResponseEntity<Response<Map<String, Long>>> getRequestCounts() {
        SuccessResponse<Map<String, Long>> response = requestService.getRequestCount();
        return Response.success(response);
    }


    @Operation(summary = "등록 요청 목록 조회", description = "관리자가 등록 요청된 향수의 목록을 조회합니다.")
    @GetMapping("/perfumes/admins/requests/register")
    public ResponseEntity<Response<List<RequestListDto>>> getRegisterRequests() {
        SuccessResponse<List<RequestListDto>> response = requestService.getRegisterRequests();
        return Response.success(response);
    }


    @Operation(summary = "등록 요청 목록 조회", description = "관리자가 수정 요청된 향수의 목록을 조회합니다.")
    @GetMapping("/perfumes/admins/requests/update")
    public ResponseEntity<Response<List<RequestListDto>>> getUpdateRequests() {
        SuccessResponse<List<RequestListDto>> response = requestService.getUpdateRequests();
        return Response.success(response);
    }


    @Operation(summary = "삭제 요청 목록 조회", description = "관리자가 삭제 요청된 향수의 목록을 조회합니다.")
    @GetMapping("/perfumes/admins/requests/delete")
    public ResponseEntity<Response<List<RequestListDto>>> getDeleteRequests() {
        SuccessResponse<List<RequestListDto>> response = requestService.getDeleteRequests();
        return Response.success(response);
    }


    @Operation(summary = "등록 요청 향수 상세 조회", description = "관리자가 등록 요청된 향수의 세부정보를 조회합니다.(수정 요청조회에도 사용)")

    @GetMapping("/perfumes/admins/register/{perfumeId}")
    public ResponseEntity<Response<PerfumeDetailResponse>> getRegisterRequestDetail(
            @RequestParam String requestType,
            @PathVariable Long perfumeId
    ) {
        SuccessResponse<PerfumeDetailResponse> response = requestPerfumeNoteService.getRegisterRequestDetail(perfumeId, requestType);
        return Response.success(response);
    }


    @Operation(summary = "삭제 요청 상세 조회", description = "관리자가 삭제 요청된 향수의 세부정보를 조회합니다.(수정 요청조회에도 사용)")
    @GetMapping("/perfumes/admins/update/{perfumeId}")
    public ResponseEntity<Response<PerfumeDetailResponse>> getUpdateRequestDetail(
            @RequestParam String requestType,
            @PathVariable Long perfumeId
    ) {
        SuccessResponse<PerfumeDetailResponse> response = requestPerfumeNoteService.getDeleteRequestDetail(perfumeId, requestType);
        return Response.success(response);
    }


    @Operation(summary = "요청 수락", description = "관리자가 요청을 수락합니다.")
    @PostMapping("/perfumes/admins/{requestId}/accept")
    public ResponseEntity<Response<NoneResponse>> acceptPerfumeRequest(@PathVariable Long requestId
//            ,@AuthenticationPrincipal User user
    ) { // 추후에 User 받아와야함
//        SuccessResponse<NoneResponse> response = perfumeService.acceptPerfumeRequest(requestId, user.role);
        SuccessResponse<NoneResponse> response = perfumeService.acceptPerfumeRequest(requestId);
        return Response.success(response);
    }


    @Operation(summary = "요청 수락", description = "관리자가 요청을 거절합니다.")
    @PostMapping("/perfumes/admins/{requestId}/reject")
    public ResponseEntity<Response<NoneResponse>> rejectPerfumeRequest(@PathVariable Long requestId) {
        SuccessResponse<NoneResponse> response = perfumeService.rejectPerfumeRequest(requestId);
        return Response.success(response);
    }


}
