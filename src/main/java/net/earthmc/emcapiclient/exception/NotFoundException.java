package net.earthmc.emcapiclient.exception;

/**
 * Thrown when the requested response does not exist
 * <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Status/404">...</a>
 */
public class NotFoundException extends RuntimeException {
    public NotFoundException(String message) {
        super(message);
    }
}
