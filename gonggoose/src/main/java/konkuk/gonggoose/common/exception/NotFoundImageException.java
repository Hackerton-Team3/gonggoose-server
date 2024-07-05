package konkuk.gonggoose.common.exception;

import konkuk.gonggoose.common.response.status.ResponseStatus;
import lombok.Getter;

@Getter
public class NotFoundImageException extends RuntimeException {

    private final ResponseStatus exceptionStatus;

    public NotFoundImageException(ResponseStatus exceptionStatus) {
        super(exceptionStatus.getMessage());
        this.exceptionStatus = exceptionStatus;
    }
}
