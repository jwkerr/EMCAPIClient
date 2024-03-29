package net.earthmc.emcapiclient.exception;

/**
 * Thrown when the server times out before the request is fulfilled
 * <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Status/504">...</a>
 */
public class GatewayTimeoutException extends RuntimeException {
    public GatewayTimeoutException(String message) {
        super(message);
    }
}
