package no.nav.brukernotifikasjon.schemas.builders.util;

import no.nav.brukernotifikasjon.schemas.builders.domain.Eventtype;
import no.nav.brukernotifikasjon.schemas.builders.exception.FieldValidationException;
import no.nav.brukernotifikasjon.schemas.builders.exception.UnknownEventtypeException;
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
    void skalKasteExceptionHvisLinkErLengreEnnMaxLengthHvisIkkePaakrevd() {
        assertThrows(FieldValidationException.class, () -> ValidationUtil.validateLinkAndConvertToString(new URL("https://ugyldig.url"), "testlink", 10, false));
    }

    @Test
    void skalKasteExceptionHvisLinkErLengreEnnMaxLengthHvisPaakrevd() {
        assertThrows(FieldValidationException.class, () -> ValidationUtil.validateLinkAndConvertToString(new URL("https://ugyldig.url"), "testlink", 10, true));
    }

    @Test
    void skalKasteExceptionHvisLinkErNullOgPaakrevd() {
        assertThrows(FieldValidationException.class, () -> ValidationUtil.validateLinkAndConvertToString(null, "testlink", 10, true));
    }

    @Test
    void skalIkkeKasteExceptionHvisLinkErNullOgIkkePaakrevd() {
        assertDoesNotThrow(() -> ValidationUtil.validateLinkAndConvertToString(null, "testlink", 10, false));
    }

    @Test
    void skalIkkeKasteExceptionHvisLinkErEnTomString() {
        assertDoesNotThrow(() -> ValidationUtil.validateLinkAndConvertToURL(""));
    }

    @Test
    void skalIkkeKasteExceptionHvisLinkErEnGyldigUrl() {
        assertDoesNotThrow(() -> ValidationUtil.validateLinkAndConvertToURL("http://dummyurl.no"));
    }

    @Test
    void skalKasteExceptionHvisLinkIkkeKanKonverteresTilUrl() {
        assertThrows(FieldValidationException.class, () -> ValidationUtil.validateLinkAndConvertToURL("ugyldigUrl"));
    }

    @Test
    void skalIkkeKasteExceptionHvisInputMatcherTypeneTilStatusGlobal() {
        assertDoesNotThrow(() -> ValidationUtil.validateStatusGlobal("FERDIG"));
        assertDoesNotThrow(() -> ValidationUtil.validateStatusGlobal("UNDER_BEHANDLING"));
        assertDoesNotThrow(() -> ValidationUtil.validateStatusGlobal("MOTTATT"));
        assertDoesNotThrow(() -> ValidationUtil.validateStatusGlobal("SENDT"));
    }

    @Test
    void skalKasteExceptionHvisInputIkkeMatcherTypeneTilStatusGlobal() {
        assertThrows(FieldValidationException.class, () -> ValidationUtil.validateStatusGlobal("noMatch"));
    }

    @Test
    void skalKasteExceptionHvisStatusGlobalErEnTomString() {
        assertThrows(FieldValidationException.class, () -> ValidationUtil.validateStatusGlobal(""));
    }

    @Test
    void skalKasteExceptionHvisStatusGlobalErNull() {
        assertThrows(FieldValidationException.class, () -> ValidationUtil.validateStatusGlobal(null));
    }

    @Test
    void skalIkkeKasteExceptionMedKjentTypeevent() {
        assertDoesNotThrow(() -> ValidationUtil.isLinkRequired(Eventtype.OPPGAVE));
        assertDoesNotThrow(() -> ValidationUtil.isLinkRequired(Eventtype.BESKJED));
        assertDoesNotThrow(() -> ValidationUtil.isLinkRequired(Eventtype.INNBOKS));
        assertDoesNotThrow(() -> ValidationUtil.isLinkRequired(Eventtype.STATUSOPPDATERING));
    }

    @Test
    void skalKasteExceptionHvisTypeeventErNull() {
        assertThrows(UnknownEventtypeException.class, () -> ValidationUtil.isLinkRequired(null));
    }

}
