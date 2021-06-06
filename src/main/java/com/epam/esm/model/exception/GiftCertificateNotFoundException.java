package com.epam.esm.model.exception;

public class GiftCertificateNotFoundException extends Exception {
    public GiftCertificateNotFoundException() {
        super();
    }

    public GiftCertificateNotFoundException(String message) {
        super(message);
    }

    public GiftCertificateNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public GiftCertificateNotFoundException(Throwable cause) {
        super(cause);
    }
}
