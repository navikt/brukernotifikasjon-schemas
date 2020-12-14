package no.nav.brukernotifikasjon.schemas.builders.util;

import no.nav.brukernotifikasjon.schemas.builders.domain.Eventtype;
import no.nav.brukernotifikasjon.schemas.builders.domain.StatusGlobal;
import no.nav.brukernotifikasjon.schemas.builders.exception.FieldValidationException;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.regex.Pattern;

public class ValidationUtil {

    public static final int MAX_TEXT_LENGTH_BESKJED = 300;
    public static final int MAX_TEXT_LENGTH_OPPGAVE = 500;
    public static final int MAX_LINK_LENGTH = 200;
    public static final int MAX_GRUPPERINGSID_LENGTH = 100;
    public static final int MAX_EVENTID_LENGTH = 50;
    public static final int MAX_SYSTEMBRUKER_LENGTH = 100;
    public static final int MAX_STATUSINTERN_LENGTH = 100;
    public static final int MAX_SAKSTEMA_LENGTH = 50;
    public static final boolean IS_TIDSPUNKT_REQUIRED = true;
    public static final boolean IS_SYNLIGFREMTIL_REQUIRED = false;

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

    public static Boolean validateEksternvarsling(Boolean eksternvarsling) {
        validateNonNullField(eksternvarsling, "Eksternvarsling");
        if (eksternvarsling == true || eksternvarsling == false) {
            return eksternvarsling;
        } else {
            throw new FieldValidationException("Eksternvarsling kan bare være true eller false.");
        }
    }

    public static String validateStatusGlobal(String statusGlobal) {
        try {
            return StatusGlobal.valueOf(statusGlobal).toString();
        } catch (Exception exception) {
            throw new FieldValidationException("StatusGlobal må matche en av de 4 statusene du finner i builders/domain/StatusGlobal. Verdien som ble sendt inn: " + statusGlobal.toString() + ", matcher ikke dette.");
        }
    }

    public static boolean isLinkRequired(Eventtype eventtype) {
        if (eventtype == Eventtype.OPPGAVE) {
            return true;
        } else if (eventtype == Eventtype.BESKJED || eventtype == Eventtype.STATUSOPPDATERING) {
            return false;
        } else {
            throw new FieldValidationException("Vi finner ikke denne eventtypen, og dermed vet vi ikke om link er obligatorisk. Blir det sendt inn en av eventtypene som ligger i builders/domain/Eventtype?");
        }
    }

    public static Long localDateTimeToUtcTimestamp(LocalDateTime dataAndTime, String fieldName, boolean required) {
        if (dataAndTime != null) {
            try {
                return dataAndTime.toInstant(ZoneOffset.UTC).toEpochMilli();
            } catch (Exception exception) {
                throw new FieldValidationException("Feltet " + fieldName + "kunne ikke konvertere fra LocalDateTime til UtcTimestamp.");
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

    public static String validateLinkAndConvertToString(URL field, String fieldName, int maxLength, boolean required) {
        if (required && field == null) {
            throw new FieldValidationException("Feltet " + fieldName + " kan ikke være null.");
        } else if (field != null && field.toString().length() > maxLength) {
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
