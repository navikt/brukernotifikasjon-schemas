package no.nav.brukernotifikasjon.schemas.builders.exception;

import java.util.HashMap;
import java.util.Map;

public class FieldValidationException extends RuntimeException {

    private Map<String, Object> context = new HashMap<>();

    public FieldValidationException(String message) {
        super(message);
    }

    public void addContext(String key, Object value) {
        context.put(key, value);
    }

    @Override
    public String toString() {
        if(context.isEmpty()) {
            return super.toString();
        } else {
            return super.toString() + ", context: " + context;
        }
    }

}
