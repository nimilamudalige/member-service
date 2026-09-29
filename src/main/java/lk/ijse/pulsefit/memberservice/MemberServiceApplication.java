package lk.ijse.pulsefit.memberservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * PulseFit - Member Service.
 * Owns gym member profiles (Cloud SQL / MySQL) and member profile-photo
 * uploads (Google Cloud Storage). Registers with Eureka as MEMBER-SERVICE
 * and is called directly by booking-service to validate a member exists
 * before a booking is created.
 */
@SpringBootApplication
public class MemberServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(MemberServiceApplication.class, args);
    }
}
