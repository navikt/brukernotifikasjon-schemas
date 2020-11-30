package no.nav.brukernotifikasjon.schemas.builders.util;

import no.nav.brukernotifikasjon.schemas.builders.util.ValidationUtil;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

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

}
