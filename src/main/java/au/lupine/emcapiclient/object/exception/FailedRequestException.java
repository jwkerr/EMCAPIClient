package au.lupine.emcapiclient.object.exception;

public class FailedRequestException extends RuntimeException {

    private final int statusCode;
    private final String body;

    public FailedRequestException(int statusCode, String body) {
        this.statusCode = statusCode;
        this.body = body;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getBody() {
        return body;
    }
}
