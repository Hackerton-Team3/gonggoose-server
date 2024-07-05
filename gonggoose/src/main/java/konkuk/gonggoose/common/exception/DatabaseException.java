package konkuk.gonggoose.common.exception;

import konkuk.gonggoose.common.response.status.ResponseStatus;
import lombok.Getter;

@Getter
public class DatabaseException extends RuntimeException{

    private final ResponseStatus exceptionStatus;

    public DatabaseException(ResponseStatus exceptionStatus) {
        super(exceptionStatus.getMessage());
        this.exceptionStatus = exceptionStatus;
    }
}
