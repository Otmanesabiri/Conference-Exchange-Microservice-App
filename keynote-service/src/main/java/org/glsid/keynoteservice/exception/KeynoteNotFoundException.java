package org.glsid.keynoteservice.exception;

public class KeynoteNotFoundException extends RuntimeException {

    public KeynoteNotFoundException(Long id) {
        super("Keynote with id " + id + " not found");
    }

    public KeynoteNotFoundException(String email) {
        super("Keynote with email " + email + " not found");
    }
}
