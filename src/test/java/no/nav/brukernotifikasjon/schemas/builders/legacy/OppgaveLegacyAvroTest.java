package no.nav.brukernotifikasjon.schemas.builders.legacy;

import no.nav.brukernotifikasjon.schemas.legacy.OppgaveLegacy;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class OppgaveLegacyAvroTest {

    private int expectedSikkerhetsnivaa = 4;
    private boolean expectedEksternVarsling = false;

    @Test
    void skalSetteDefaultverdiForSikkerhetsnivaa() {
        OppgaveLegacy oppgaveLegacy = getOppgaveLegacyWithDefaultValues();
        assertThat(oppgaveLegacy.getSikkerhetsnivaa(), is(expectedSikkerhetsnivaa));
    }

    @Test
    void skalSetteDefaultverdiForEksternVarsling() {
        OppgaveLegacy oppgaveLegacy = getOppgaveLegacyWithDefaultValues();
        assertThat(oppgaveLegacy.getEksternVarsling(), is(expectedEksternVarsling));
    }

    private OppgaveLegacy getOppgaveLegacyWithDefaultValues() {
        return OppgaveLegacy.newBuilder()
                .setTidspunkt(LocalDateTime.now().toEpochSecond(ZoneOffset.UTC))
                .setFodselsnummer("12345678901")
                .setGrupperingsId("3456789123456")
                .setTekst("Du må sende nytt meldekort")
                .setLink("https://gyldig.url")
                .build();
    }
}
