package lk.ijse.pulsefit.memberservice.service;

import lk.ijse.pulsefit.memberservice.dto.MemberDtos.MemberRequest;
import lk.ijse.pulsefit.memberservice.dto.MemberDtos.MemberResponse;
import lk.ijse.pulsefit.memberservice.entity.Member;
import lk.ijse.pulsefit.memberservice.entity.MembershipPlan;
import lk.ijse.pulsefit.memberservice.exception.DuplicateMemberException;
import lk.ijse.pulsefit.memberservice.exception.MemberNotFoundException;
import lk.ijse.pulsefit.memberservice.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@Transactional
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final CloudStorageService cloudStorageService;

    public MemberServiceImpl(MemberRepository memberRepository, CloudStorageService cloudStorageService) {
        this.memberRepository = memberRepository;
        this.cloudStorageService = cloudStorageService;
    }

    @Override
    public MemberResponse create(MemberRequest request) {
        if (memberRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateMemberException(request.getEmail());
        }
        Member member = Member.builder()
                .fullName(request.getFullName())
                .email(request.getEmail())
                .phone(request.getPhone())
                .membershipPlan(MembershipPlan.valueOf(request.getMembershipPlan().toUpperCase()))
                .joinDate(request.getJoinDate())
                .active(true)
                .build();
        return toResponse(memberRepository.save(member));
    }

    @Override
    @Transactional(readOnly = true)
    public List<MemberResponse> findAll() {
        return memberRepository.findAll().stream().map(this::toResponse).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public MemberResponse findById(Long id) {
        return toResponse(getOrThrow(id));
    }

    @Override
    public MemberResponse update(Long id, MemberRequest request) {
        Member member = getOrThrow(id);
        member.setFullName(request.getFullName());
        member.setEmail(request.getEmail());
        member.setPhone(request.getPhone());
        member.setMembershipPlan(MembershipPlan.valueOf(request.getMembershipPlan().toUpperCase()));
        if (request.getJoinDate() != null) {
            member.setJoinDate(request.getJoinDate());
        }
        return toResponse(memberRepository.save(member));
    }

    @Override
    public void delete(Long id) {
        if (!memberRepository.existsById(id)) {
            throw new MemberNotFoundException(id);
        }
        memberRepository.deleteById(id);
    }

    @Override
    public MemberResponse uploadPhoto(Long id, MultipartFile file) throws IOException {
        Member member = getOrThrow(id);
        String url = cloudStorageService.uploadPhoto(id, file);
        member.setPhotoUrl(url);
        return toResponse(memberRepository.save(member));
    }

    private Member getOrThrow(Long id) {
        return memberRepository.findById(id).orElseThrow(() -> new MemberNotFoundException(id));
    }

    private MemberResponse toResponse(Member member) {
        return new MemberResponse(
                member.getId(),
                member.getFullName(),
                member.getEmail(),
                member.getPhone(),
                member.getMembershipPlan().name(),
                member.getJoinDate(),
                member.isActive(),
                member.getPhotoUrl(),
                member.getCreatedAt()
        );
    }
}
