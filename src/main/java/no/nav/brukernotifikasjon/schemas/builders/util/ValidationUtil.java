package no.nav.brukernotifikasjon.schemas.builders.util;

import no.nav.brukernotifikasjon.schemas.builders.domain.Eventtype;
import no.nav.brukernotifikasjon.schemas.builders.domain.StatusGlobal;
import no.nav.brukernotifikasjon.schemas.builders.exception.FieldValidationException;
import no.nav.brukernotifikasjon.schemas.builders.exception.UnknownEventtypeException;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.regex.Pattern;

public class ValidationUtil {

    public static final int MAX_LENGTH_TEXT_BESKJED = 300;
    public static final int MAX_LENGTH_TEXT_OPPGAVE = 500;
    public static final int MAX_LENGTH_TEXT_INNBOKS = 500;
    public static final int MAX_LENGTH_LINK = 200;
    public static final int MAX_LENGTH_GRUPPERINGSID = 100;
    public static final int MAX_LENGTH_EVENTID = 50;
    public static final int MAX_LENGTH_SYSTEMBRUKER = 100;
    public static final int MAX_LENGTH_STATUSINTERN = 100;
    public static final int MAX_LENGTH_SAKSTEMA = 50;
    public static final int MAX_LENGTH_FODSELSNUMMER = 11;
    public static final int MAX_LENGTH_UID = 100;
    public static final boolean IS_REQUIRED_TIDSPUNKT = true;
    public static final boolean IS_REQUIRED_SYNLIGFREMTIL = false;

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
        if (field == null) {
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

    public static <T> String validateStatusGlobal(T field) {
        validateNonNullField(field, "statusGlobal");
        try {
            return StatusGlobal.valueOf(field.toString()).toString();
        } catch (Exception exception) {
            FieldValidationException fve = new FieldValidationException("StatusGlobal må matche en av de 4 statusene du finner i builders/domain/StatusGlobal. Verdien som ble sendt inn: " + field.toString() + ", matcher ikke dette.");
            fve.addContext("Exception", exception);
            throw fve;
        }
    }

    public static boolean isLinkRequired(Eventtype eventtype) {
        if (Eventtype.OPPGAVE == eventtype) {
            return true;
        } else if (Eventtype.BESKJED == eventtype || Eventtype.STATUSOPPDATERING == eventtype || Eventtype.INNBOKS == eventtype) {
            return false;
        } else {
            throw new UnknownEventtypeException("Vi finner ikke denne eventtypen, og dermed vet vi ikke om link er obligatorisk. Blir det sendt inn en av eventtypene som ligger i builders/domain/Eventtype?");
        }
    }

    public static Long localDateTimeToUtcTimestamp(LocalDateTime dataAndTime, String fieldName, boolean required) {
        if (dataAndTime != null) {
            try {
                return dataAndTime.toInstant(ZoneOffset.UTC).toEpochMilli();
            } catch (Exception exception) {
                FieldValidationException fve = new FieldValidationException("Feltet " + fieldName + "kunne ikke konvertere fra LocalDateTime til UtcTimestamp.");
                fve.addContext("Exception", exception);
                throw fve;
            }
        } else {
            if (required) {
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

    public static URL validateLinkAndConvertToURL(String link) {
        if (link.equals("")) {
            return null;
        }

        try {
            return new URL(link);
        } catch (Exception exception) {
            FieldValidationException fve = new FieldValidationException("Kunne ikke konvertere link til URL.");
            fve.addContext("link-felt", link);
            fve.addContext("exception", exception);
            throw fve;
        }
    }

    public static String validateLinkAndConvertToString(URL field, String fieldName, int maxLength, boolean required) {
        if (required && field == null) {
            throw new FieldValidationException("Feltet " + fieldName + " kan ikke være null.");
        } else if (field != null && field.toString().length() > maxLength) {
            throw new FieldValidationException("Feltet " + fieldName + " kan ikke inneholde mer enn " + maxLength + " tegn.");
        } else {
            return field != null ? field.toString() : "";
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
        return field.length() == MAX_LENGTH_FODSELSNUMMER;
    }

    public static String validateMaxLength(String field, String fieldName, int maxLength) {
        if (field.length() > maxLength) {
            FieldValidationException fve = new FieldValidationException("Feltet " + fieldName + " kan ikke inneholde mer enn $maxLength tegn.");
            fve.addContext("rejectedFieldValueLength", field.length());
            throw fve;
        }
        return field;
    }
}
