package no.nav.brukernotifikasjon.schemas.builders;

import no.nav.brukernotifikasjon.schemas.input.OppgaveInput;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

import static java.util.Collections.emptyList;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class OppgaveInputAvroTest {

    private int expectedSikkerhetsnivaa = 4;
    private boolean expectedEksternVarsling = false;
    private List<String> expectedPrefererteKanaler = emptyList();

    @Test
    void skalSetteDefaultverdiForSikkerhetsnivaa() {
        OppgaveInput oppgave = getOppgaveWithDefaultValues();
        assertThat(oppgave.getSikkerhetsnivaa(), is(expectedSikkerhetsnivaa));
    }

    @Test
    void skalSetteDefaultverdiForEksternVarsling() {
        OppgaveInput oppgave = getOppgaveWithDefaultValues();
        assertThat(oppgave.getEksternVarsling(), is(expectedEksternVarsling));
    }

    @Test
    void skalSetteDefaultVerdiForPrefererteKanaler() {
        OppgaveInput oppgave = getOppgaveWithDefaultValues();
        assertThat(oppgave.getPrefererteKanaler(), is(expectedPrefererteKanaler));
    }

    @Test
    void skalSetteNullSomDefaultverdiForSynligFremTil() {
        OppgaveInput oppgave = getOppgaveWithDefaultValues();
        assertThat(oppgave.getSynligFremTil(), is(nullValue()));
    }

    private OppgaveInput getOppgaveWithDefaultValues() {
        return OppgaveInput.newBuilder()
                .setTidspunkt(LocalDateTime.now().toEpochSecond(ZoneOffset.UTC))
                .setTekst("Du må sende nytt meldekort")
                .setLink("https://gyldig.url")
                .build();
    }
}
