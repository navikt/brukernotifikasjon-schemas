package no.nav.brukernotifikasjon.schemas.builders.legacy;

import no.nav.brukernotifikasjon.schemas.Beskjed;
import no.nav.brukernotifikasjon.schemas.Innboks;
import no.nav.brukernotifikasjon.schemas.Oppgave;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class OppgaveAvroTest {

    private int expectedSikkerhetsnivaa = 4;
    private boolean expectedEksternVarsling = false;

    @Test
    void skalSetteDefaultverdiForSikkerhetsnivaa() {
        Oppgave oppgave = getOppgaveWithDefaultValues();
        assertThat(oppgave.getSikkerhetsnivaa(), is(expectedSikkerhetsnivaa));
    }

    @Test
    void skalSetteDefaultverdiForEksternVarsling() {
        Oppgave oppgave = getOppgaveWithDefaultValues();
        assertThat(oppgave.getEksternVarsling(), is(expectedEksternVarsling));
    }

    @Test
    void skalSetteNullSomDefaultverdiForSynligFremTil() {
        Oppgave oppgave = getOppgaveWithDefaultValues();
        assertThat(oppgave.getSynligFremTil(), is(nullValue()));
    }

    @Test
    void skalSetteNullSomDefaultverdiEpostVarslingstekst() {
        Oppgave oppgave = getOppgaveWithDefaultValues();
        assertThat(oppgave.getEpostVarslingstekst(), is(nullValue()));
    }

    @Test
    void skalSetteNullSomDefaultverdiSmsVarslingstittel() {
        Oppgave oppgave = getOppgaveWithDefaultValues();
        assertThat(oppgave.getEpostVarslingstittel(), is(nullValue()));
    }

    @Test
    void skalSetteNullSomDefaultverdiSmsVarslingstekst() {
        Oppgave oppgave = getOppgaveWithDefaultValues();
        assertThat(oppgave.getSmsVarslingstekst(), is(nullValue()));
    }

    private Oppgave getOppgaveWithDefaultValues() {
        return Oppgave.newBuilder()
                .setTidspunkt(LocalDateTime.now().toEpochSecond(ZoneOffset.UTC))
                .setFodselsnummer("12345678901")
                .setGrupperingsId("3456789123456")
                .setTekst("Du må sende nytt meldekort")
                .setLink("https://gyldig.url")
                .build();
    }
}
