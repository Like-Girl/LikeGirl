package cn.likegirl.common.core.support.validate;

/**
 * @author LikeGirl
 */
@SuppressWarnings("serial")
public class ValidateException extends RuntimeException {
    public ValidateException() {
    }

    public ValidateException(Throwable ex) {
        super(ex);
    }

    public ValidateException(String message) {
        super(message);
    }

    public ValidateException(String message, Throwable ex) {
        super(message, ex);
    }

}
