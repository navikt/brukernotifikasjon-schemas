package no.nav.brukernotifikasjon.schemas.builders.input;

import no.nav.brukernotifikasjon.schemas.input.OppgaveInput;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class OppgaveInputAvroTest {

    private int expectedSikkerhetsnivaa = 4;
    private boolean expectedEksternVarsling = false;

    @Test
    void skalSetteDefaultverdiForSikkerhetsnivaa() {
        OppgaveInput oppgaveInput = getOppgaveInputWithDefaultValues();
        assertThat(oppgaveInput.getSikkerhetsnivaa(), is(expectedSikkerhetsnivaa));
    }

    @Test
    void skalSetteDefaultverdiForEksternVarsling() {
        OppgaveInput oppgaveInput = getOppgaveInputWithDefaultValues();
        assertThat(oppgaveInput.getEksternVarsling(), is(expectedEksternVarsling));
    }

    private OppgaveInput getOppgaveInputWithDefaultValues() {
        return OppgaveInput.newBuilder()
                .setTidspunkt(LocalDateTime.now().toEpochSecond(ZoneOffset.UTC))
                .setGrupperingsId("3456789123456")
                .setTekst("Du må sende nytt meldekort")
                .setLink("https://gyldig.url")
                .build();
    }
}
