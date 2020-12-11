package no.nav.brukernotifikasjon.schemas.builders.util;

import no.nav.brukernotifikasjon.schemas.builders.exception.FieldValidationException;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.regex.Pattern;

public class ValidationUtil {

    private static Pattern elevenDigits = Pattern.compile("[\\d]{11}");

    public static String validateFodselsnummer(String fodselsnummer) {
        validateNonNullField(fodselsnummer, "fodselsnummer");
        if (!isPossibleFodselsnummer(fodselsnummer)) {
            FieldValidationException fve = new FieldValidationException("Feltet fodselsnummer kan kun innholde siffer, og maks antall er 11.");
            fve.addContext("rejectedFieldValue", fodselsnummer);
            throw fve;
        }
        return fodselsnummer;
    }

    public static String validateNonNullField(String field, String fieldName) {
        if (field == null || field.isEmpty()) {
            FieldValidationException fve = new FieldValidationException(fieldName + " var null eller tomt.");
            fve.addContext("nullOrEmpty", fieldName);
            throw fve;
        }
        return field;
    }

    public static <T> T validateNonNullField(T field, String fieldName) {
        if(field == null) {
            FieldValidationException fve = new FieldValidationException(fieldName + " var null.");
            fve.addContext("null", fieldName);
            throw fve;
        }
        return field;
    }

    public static int validateSikkerhetsnivaa(int sikkerhetsnivaa) {
        if (sikkerhetsnivaa == 3 || sikkerhetsnivaa == 4) {
            return sikkerhetsnivaa;

        } else {
            throw new FieldValidationException("Sikkerhetsnivaa kan bare være 3 eller 4.");
        }
    }

    public static Boolean validateEksternvarsling(Boolean eksternvarsling) {
        validateNonNullField(eksternvarsling, "Eksternvarsling");
        if (eksternvarsling == true || eksternvarsling == false) {
            return eksternvarsling;

        } else {
            throw new FieldValidationException("Eksternvarsling kan bare være true eller false.");
        }
    }

    public static Long localDateTimeToUtcTimestamp(LocalDateTime dataAndTime, String fieldName, boolean required) {
        if (dataAndTime != null) {
            return dataAndTime.toInstant(ZoneOffset.UTC).toEpochMilli();
        } else {
            if(required) {
                throw new FieldValidationException("Feltet " + fieldName + " kan ikke være null.");
            } else {
                return null;
            }
        }
    }

    public static String validateNonNullFieldMaxLength(String field, String fieldName, int maxLength) {
        validateNonNullField(field, fieldName);
        return validateMaxLength(field, fieldName, maxLength);
    }

    public static String validateLinkAndConvertToString(URL field, String fieldName, int maxLength, boolean required) {
        if(required && field == null) {
            throw new FieldValidationException("Feltet " + fieldName + " kan ikke være null.");
        } else if(field != null && field.toString().length() > maxLength) {
            throw new FieldValidationException("Feltet " + fieldName + " kan ikke inneholde mer enn " + maxLength + " tegn.");
        } else {
            return field != null ? field.toString() : null;
        }
    }

    static boolean isPossibleFodselsnummer(String field) {
        if (isCorrectLengthForFodselsnummer(field)) {
            return elevenDigits.matcher(field).find();
        } else {
            return false;
        }
    }

    private static boolean isCorrectLengthForFodselsnummer(String field) {
        return field.length() == 11;
    }

    private static String validateMaxLength(String field, String fieldName, int maxLength) {
        if (field.length() > maxLength) {
            FieldValidationException fve = new FieldValidationException("Feltet " + fieldName + " kan ikke inneholde mer enn $maxLength tegn.");
            fve.addContext("rejectedFieldValueLength", field.length());
            throw fve;
        }
        return field;
    }
}
