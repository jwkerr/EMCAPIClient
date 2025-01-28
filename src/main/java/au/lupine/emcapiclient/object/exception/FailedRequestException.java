package au.lupine.emcapiclient.object.exception;

public class FailedRequestException extends RuntimeException {

    private final int statusCode;

    public FailedRequestException(int statusCode) {
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }
}
