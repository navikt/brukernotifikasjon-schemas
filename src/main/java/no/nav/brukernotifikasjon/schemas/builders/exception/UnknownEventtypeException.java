package no.nav.brukernotifikasjon.schemas.builders.exception;

import java.util.HashMap;
import java.util.Map;

public class UnknownEventtypeException extends RuntimeException {

    private Map<String, Object> context = new HashMap<>();

    public UnknownEventtypeException(String message) {
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
