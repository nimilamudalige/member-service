package lk.ijse.pulsefit.memberservice.exception;

public class DuplicateMemberException extends RuntimeException {
    public DuplicateMemberException(String email) {
        super("A member with email '" + email + "' already exists");
    }
}
