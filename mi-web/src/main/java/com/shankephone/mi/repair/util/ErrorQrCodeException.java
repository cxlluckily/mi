package com.shankephone.mi.repair.util;

/**
 * 错误的唯一码异常
 *
 * @author 司徒彬
 * @date 2018/8/16 15:47
 */
public class ErrorQrCodeException extends Exception {
    /**
     * Constructs a new exception with the specified detail message.  The
     * cause is not initialized, and may subsequently be initialized by
     * a call to {@link #initCause}.
     *
     * @param message the detail message. The detail message is saved for
     *                later retrieval by the {@link #getMessage()} method.
     */
    public ErrorQrCodeException(String message) {
        super(message);
    }
}
