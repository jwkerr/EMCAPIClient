package net.earthmc.emcapiclient.exception;

/**
 * Thrown when there is a client-side error in an HTTP request
 * <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Status/400">...</a>
 */
public class BadRequestException extends RuntimeException {
    public BadRequestException(String message) {
        super(message);
    }
}
