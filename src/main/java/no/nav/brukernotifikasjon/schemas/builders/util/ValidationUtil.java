package no.nav.brukernotifikasjon.schemas.builders.util;

import no.nav.brukernotifikasjon.schemas.builders.domain.Eventtype;
import no.nav.brukernotifikasjon.schemas.builders.domain.PreferertKanal;
import no.nav.brukernotifikasjon.schemas.builders.domain.StatusGlobal;
import no.nav.brukernotifikasjon.schemas.builders.exception.FieldValidationException;
import no.nav.brukernotifikasjon.schemas.builders.exception.UnknownEventtypeException;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;

import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;

public class ValidationUtil {

    public static final int MAX_LENGTH_TEXT_BESKJED = 300;
    public static final int MAX_LENGTH_TEXT_OPPGAVE = 500;
    public static final int MAX_LENGTH_TEXT_INNBOKS = 500;
    public static final int MAX_LENGTH_SMS_VARSLINGSTEKST = 160;
    public static final int MAX_LENGTH_EPOST_VARSLINGSTEKST = 10_000;
    private static final int MAX_LENGTH_EPOST_VARSLINGSTTITTEL = 200;
    public static final int MAX_LENGTH_LINK = 200;
    public static final int MAX_LENGTH_GRUPPERINGSID = 100;
    public static final int MAX_LENGTH_EVENTID = 50;
    public static final int MAX_LENGTH_SYSTEMBRUKER = 100;
    public static final int MAX_LENGTH_STATUSINTERN = 100;
    public static final int MAX_LENGTH_SAKSTEMA = 50;
    public static final int MAX_LENGTH_FODSELSNUMMER = 11;
    public static final int MAX_LENGTH_UID = 100;
    public static final int MAX_LENGTH_APP_NAME = 100;
    public static final int MAX_LENGTH_NAMESPACE = 63;
    public static final boolean IS_REQUIRED_TIDSPUNKT = true;
    public static final boolean IS_REQUIRED_SYNLIGFREMTIL = false;

    private static Pattern elevenDigits = Pattern.compile("[\\d]{11}");

    private static String BASE_16 = "[0-9a-fA-F]";
    private static String UUID_PATTERN_STRING = String.format("^%1$s{8}-%1$s{4}-%1$s{4}-%1$s{4}-%1$s{12}$", BASE_16);
    private static Pattern UUID_PATTERN = Pattern.compile(UUID_PATTERN_STRING);

    private static String BASE_32_ULID = "[0-9ABCDEFGHJKMNPQRSTVWXYZabcdefghjkmnpqrstvwxyz]";
    private static String ULID_PATTERN_STRING = String.format("^[0-7]%1$s{25}$", BASE_32_ULID);
    private static Pattern ULID_PATTERN = Pattern.compile(ULID_PATTERN_STRING);

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

    public static List<String> validatePrefererteKanaler(boolean eksternVarsling, List<PreferertKanal> field) {
        String fieldName = "prefererteKanaler";
        if(field == null) {
            return emptyList();
        } else if(!eksternVarsling && !field.isEmpty()) {
            throw new FieldValidationException("Feltet " + fieldName + " kan ikke settes hvis eksternVarsling er satt til false.");
        } else if(field != null){
            List<String> prefererteKanaler = field.stream().map(preferertKanal -> preferertKanal.toString()).collect(toList());
            return prefererteKanaler;
        } else {
            return emptyList();
        }
    }

    public static String validateEventId(String eventId) {
        validateNonNullFieldMaxLength(eventId, "eventId", MAX_LENGTH_EVENTID);
        if (UUID_PATTERN.matcher(eventId).find()) {
            return eventId;
        } else if (ULID_PATTERN.matcher(eventId).find()) {
            return eventId;
        } else {
            throw new FieldValidationException("Feltet eventId må enten være en standard UUID eller ULID");
        }
    }

    static boolean isPossibleFodselsnummer(String field) {
        if (isCorrectLengthForFodselsnummer(field)) {
            return elevenDigits.matcher(field).find();
        } else {
            return false;
        }
    }

    public static String validateMaxLength(String field, String fieldName, int maxLength) {
        if (field.length() > maxLength) {
            FieldValidationException fve = new FieldValidationException("Feltet " + fieldName + " kan ikke inneholde mer enn " + maxLength + " tegn.");
            fve.addContext("rejectedFieldValueLength", field.length());
            throw fve;
        }
        return field;
    }

    private static boolean isCorrectLengthForFodselsnummer(String field) {
        return field.length() == MAX_LENGTH_FODSELSNUMMER;
    }

    public static String validateNullableMaxLength(String field, String fieldName, int maxLength) {
        if (field == null) {
            return null;
        }
        return validateMaxLength(field, fieldName, maxLength);
    }

    public static String validateNullableOrNotBlank(String field, String fieldName) {
        if (field == null) {
            return null;
        }
        if (field.trim().isEmpty()) {
            throw new FieldValidationException("Feltet " + fieldName + " må enten være null eller ikke tom.");
        }
        return field;
    }

    public static String validateEpostVarslingstekst(Boolean eksternVarsling, String epostVarslingstekst) {
        if (epostVarslingstekst == null) {
            return null;
        }
        if (!eksternVarsling) {
            throw new FieldValidationException("Feltet epostVarslingstekst kan ikke settes hvis eksternVarsling er satt til false.");
        }
        if (epostVarslingstekst.trim().isEmpty()) {
            throw new FieldValidationException("Feltet epostVarslingstekst må enten være null eller ikke tom.");
        }
        if (epostVarslingstekst.length() > MAX_LENGTH_EPOST_VARSLINGSTEKST) {
            FieldValidationException fve = new FieldValidationException("Feltet epostVarslingstekst kan ikke inneholde mer enn " + MAX_LENGTH_EPOST_VARSLINGSTEKST + " tegn.");
            fve.addContext("rejectedFieldValueLength", epostVarslingstekst.length());
            throw fve;
        }
        return epostVarslingstekst;
    }

    public static String validateSmsVarslingstekst(Boolean eksternVarsling, String smsVarslingstekst) {
        if (smsVarslingstekst == null) {
            return null;
        }
        if (!eksternVarsling) {
            throw new FieldValidationException("Feltet smsVarslingstekst kan ikke settes hvis eksternVarsling er satt til false.");
        }
        if (smsVarslingstekst.trim().isEmpty()) {
            throw new FieldValidationException("Feltet smsVarslingstekst må enten være null eller ikke tom.");
        }
        if (smsVarslingstekst.length() > MAX_LENGTH_SMS_VARSLINGSTEKST) {
            FieldValidationException fve = new FieldValidationException("Feltet smsVarslingstekst kan ikke inneholde mer enn " + MAX_LENGTH_SMS_VARSLINGSTEKST + " tegn.");
            fve.addContext("rejectedFieldValueLength", smsVarslingstekst.length());
            throw fve;
        }
        return smsVarslingstekst;
    }

    public static String validateEpostVarslingstittel(Boolean eksternVarsling, String epostVarslingstittel) {
        if (epostVarslingstittel == null) {
            return null;
        }
        if (!eksternVarsling) {
            throw new FieldValidationException("Feltet epostVarslingstittel kan ikke settes hvis eksternVarsling er satt til false.");
        }
        if (epostVarslingstittel.trim().isEmpty()) {
            throw new FieldValidationException("Feltet epostVarslingstittel må enten være null eller ikke tom.");
        }
        if (epostVarslingstittel.length() > MAX_LENGTH_EPOST_VARSLINGSTTITTEL) {
            FieldValidationException fve = new FieldValidationException("Feltet epostVarslingstittel kan ikke inneholde mer enn " + MAX_LENGTH_SMS_VARSLINGSTEKST + " tegn.");
            fve.addContext("rejectedFieldValueLength", epostVarslingstittel.length());
            throw fve;
        }
        return epostVarslingstittel;
    }
}
