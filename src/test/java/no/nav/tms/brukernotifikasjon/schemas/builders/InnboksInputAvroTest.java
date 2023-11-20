package no.nav.tms.brukernotifikasjon.schemas.builders;

import no.nav.tms.brukernotifikasjon.schemas.input.InnboksInput;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

import static java.util.Collections.emptyList;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;


public class InnboksInputAvroTest {

    private int expectedSikkerhetsnivaa = 4;
    private boolean expectedEksternVarsling = false;
    private List<String> expectedPrefererteKanaler = emptyList();

    @Test
    void skalSetteDefaultverdiForSikkerhetsnivaa() {
        InnboksInput innboks = getInnboksWithDefaultValues();
        assertThat(innboks.getSikkerhetsnivaa(), is(expectedSikkerhetsnivaa));
    }

    @Test
    void skalSetteDefaultverdiForEksternVarsling() {
        InnboksInput innboks = getInnboksWithDefaultValues();
        assertThat(innboks.getEksternVarsling(), is(expectedEksternVarsling));
    }

    @Test
    void skalSetteDefaultVerdiForPrefererteKanaler() {
        InnboksInput innboks = getInnboksWithDefaultValues();
        assertThat(innboks.getPrefererteKanaler(), is(expectedPrefererteKanaler));
    }

    @Test
    void skalSetteNullSomDefaultverdiEpostVarslingstekst() {
        InnboksInput innboks = getInnboksWithDefaultValues();
        assertThat(innboks.getEpostVarslingstekst(), is(nullValue()));
    }

    @Test
    void skalSetteNullSomDefaultverdiSmsVarslingstekst() {
        InnboksInput innboks = getInnboksWithDefaultValues();
        assertThat(innboks.getSmsVarslingstekst(), is(nullValue()));
    }

    @Test
    void skalSetteNullSomDefaultverdiEpostVarslingstittel() {
        InnboksInput innboks = getInnboksWithDefaultValues();
        assertThat(innboks.getEpostVarslingstittel(), is(nullValue()));
    }

    private InnboksInput getInnboksWithDefaultValues() {
        return InnboksInput.newBuilder()
                .setTidspunkt(LocalDateTime.now().toEpochSecond(ZoneOffset.UTC))
                .setTekst("Dette er informasjon du må lese")
                .setLink("https://gyldig.url")
                .build();
    }
}
