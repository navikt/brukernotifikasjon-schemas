package no.nav.brukernotifikasjon.schemas.builders.intern;

import no.nav.brukernotifikasjon.schemas.intern.OppgaveIntern;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class OppgaveInternAvroTest {

    private int expectedSikkerhetsnivaa = 4;
    private boolean expectedEksternVarsling = false;

    @Test
    void skalSetteDefaultverdiForSikkerhetsnivaa() {
        OppgaveIntern oppgaveIntern = getOppgaveInternWithDefaultValues();
        assertThat(oppgaveIntern.getSikkerhetsnivaa(), is(expectedSikkerhetsnivaa));
    }

    @Test
    void skalSetteDefaultverdiForEksternVarsling() {
        OppgaveIntern oppgaveIntern = getOppgaveInternWithDefaultValues();
        assertThat(oppgaveIntern.getEksternVarsling(), is(expectedEksternVarsling));
    }

    private OppgaveIntern getOppgaveInternWithDefaultValues() {
        return OppgaveIntern.newBuilder()
                .setTidspunkt(LocalDateTime.now().toEpochSecond(ZoneOffset.UTC))
                .setFodselsnummer("12345678901")
                .setGrupperingsId("3456789123456")
                .setTekst("Du må sende nytt meldekort")
                .setLink("https://gyldig.url")
                .build();
    }
}
