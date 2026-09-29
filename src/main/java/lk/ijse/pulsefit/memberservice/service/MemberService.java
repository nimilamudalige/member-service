package lk.ijse.pulsefit.memberservice.service;

import lk.ijse.pulsefit.memberservice.dto.MemberDtos.MemberRequest;
import lk.ijse.pulsefit.memberservice.dto.MemberDtos.MemberResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface MemberService {
    MemberResponse create(MemberRequest request);
    List<MemberResponse> findAll();
    MemberResponse findById(Long id);
    MemberResponse update(Long id, MemberRequest request);
    void delete(Long id);
    MemberResponse uploadPhoto(Long id, MultipartFile file) throws IOException;
}
