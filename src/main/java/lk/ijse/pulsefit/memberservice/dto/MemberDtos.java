package lk.ijse.pulsefit.memberservice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Request/response payloads for the Member API, grouped in one file the
 * same way the rest of the PulseFit services do (XxxDtos convention).
 */
public class MemberDtos {

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MemberRequest {

        @NotBlank(message = "fullName is required")
        private String fullName;

        @NotBlank(message = "email is required")
        @Email(message = "email must be a valid address")
        private String email;

        private String phone;

        @NotNull(message = "membershipPlan is required (BASIC, STANDARD or PREMIUM)")
        private String membershipPlan;

        private LocalDate joinDate;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MemberResponse {
        private Long id;
        private String fullName;
        private String email;
        private String phone;
        private String membershipPlan;
        private LocalDate joinDate;
        private boolean active;
        private String photoUrl;
        private LocalDateTime createdAt;
    }
}
