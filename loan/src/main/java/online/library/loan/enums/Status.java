package online.library.loan.enums;

import lombok.Getter;

@Getter
public enum Status {
    REQUESTED("Requested"),
    IN_PROGRESS("InProgress"),
    FINISHED("Finished");

    private String status;

    Status(String status) {
        this.status = status;
    }
}
