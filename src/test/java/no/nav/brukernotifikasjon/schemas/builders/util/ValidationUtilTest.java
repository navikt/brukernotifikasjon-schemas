package no.nav.brukernotifikasjon.schemas.builders.util;

import no.nav.brukernotifikasjon.schemas.builders.exception.FieldValidationException;
import org.junit.jupiter.api.Test;

import java.net.URL;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ValidationUtilTest {

    @Test
    void skalGodta11SifferSomSannsynligFodselsnummer() {
        String possibleFnr = "12345678901";

        assertThat(ValidationUtil.isPossibleFodselsnummer(possibleFnr), is(true));
    }

    @Test
    void skalAvviseOpplagtUgyldigeFodselsnummer() {
        String tooShort = "1234567890";
        String tooLong = "123456789012";
        String alphanumeric = "a1234567890";

        assertThat(ValidationUtil.isPossibleFodselsnummer(tooShort), is(false));
        assertThat(ValidationUtil.isPossibleFodselsnummer(tooLong), is(false));
        assertThat(ValidationUtil.isPossibleFodselsnummer(alphanumeric), is(false));
    }

    @Test
    void skalKasteExceptionHvisFeltErNull() {
        assertThrows(FieldValidationException.class, () -> ValidationUtil.validateNonNullField(null, "feltSomErNull"));
    }

    @Test
    void skalKasteExceptionHvisTekstfeltErTomt() {
        assertThrows(FieldValidationException.class, () -> ValidationUtil.validateNonNullField("", "feltSomErNull"));
    }

    @Test
    void skalKasteExceptionHvisSikkerhetsnivaaErLavereEnn3() {
        assertThrows(FieldValidationException.class, () -> ValidationUtil.validateNonNullField("", "feltSomErNull"));
    }

    @Test
    void skalKasteExceptionHvisPaakrevdTimestampErNull() {
        assertThrows(FieldValidationException.class, () -> ValidationUtil.localDateTimeToUtcTimestamp(null, "test", true));
    }

    @Test
    void skalIkkeKasteExceptionHvisIkkePaakrevdTimestampErNull() {
        assertDoesNotThrow(() -> ValidationUtil.localDateTimeToUtcTimestamp(null, "test", false));
    }

    @Test
    void skalKasteExceptionHvisFeltErLengreEnnMaxLength() {
        assertThrows(FieldValidationException.class, () -> ValidationUtil.validateNonNullFieldMaxLength("abc", "test", 2));
    }

    @Test
    void skalKasteExceptionHvisLinkErLengreEnnMaxLength() {
        assertThrows(FieldValidationException.class, () -> ValidationUtil.validateLink(new URL("https://ugyldig.url"), "testlink", 10));
    }

    @Test
    void skalKasteExceptionHvisLinkNull() {
        assertThrows(FieldValidationException.class, () -> ValidationUtil.validateLink(null, "testlink", 10));
    }
}
