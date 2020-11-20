package no.nav.brukernotifikasjon.schemas.builders;

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

    public static boolean isPossibleFodselsnummer(String field) {
        if (isCorrectLengthForFodselsnummer(field)) {
            return elevenDigits.matcher(field).find();

        } else {
            return false;
        }
    }

    private static boolean isCorrectLengthForFodselsnummer(String field) {
        return field.length() == 11;
    }

    public static String validateNonNullFieldMaxLength(String field, String fieldName, int maxLength) {
        validateNonNullField(field, fieldName);
        return validateMaxLength(field, fieldName, maxLength);
    }

    private static String validateMaxLength(String field, String fieldName, int maxLength) {
        if (field.length() > maxLength) {
            FieldValidationException fve = new FieldValidationException("Feltet " + fieldName + " kan ikke inneholde mer enn $maxLength tegn.");
            fve.addContext("rejectedFieldValueLength", field.length());
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

    public static Long localDateTimeToUtcTimestamp(LocalDateTime dataAndTime) {
        if (dataAndTime != null) {
            return dataAndTime.toInstant(ZoneOffset.UTC).toEpochMilli();

        } else {
            return null;
        }
    }

}
