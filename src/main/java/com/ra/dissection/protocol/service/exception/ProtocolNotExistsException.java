package com.ra.dissection.protocol.service.exception;

/**
 * @author lukaszkaleta
 * @since 12.05.13 22:33
 */
public class ProtocolNotExistsException extends RuntimeException {

    private final long dissectionProtocolId;

    public ProtocolNotExistsException(long dissectionProtocolId) {
        this.dissectionProtocolId = dissectionProtocolId;
    }
}
