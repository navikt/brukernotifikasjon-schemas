package no.nav.tms.brukernotifikasjon.schemas.builders;

import no.nav.tms.brukernotifikasjon.schemas.input.BeskjedInput;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

import static java.util.Collections.emptyList;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class BeskjedInputAvroTest {

    private int expectedSikkerhetsnivaa = 4;
    private boolean expectedEksternVarsling = false;
    private List<String> expectedPrefererteKanaler = emptyList();

    @Test
    void skalSetteDefaultverdiForSikkerhetsnivaa() {
        BeskjedInput beskjed = getBeskjedWithDefaultValues();
        assertThat(beskjed.getSikkerhetsnivaa(), is(expectedSikkerhetsnivaa));
    }

    @Test
    void skalSetteDefaultverdiForEksternVarsling() {
        BeskjedInput beskjed = getBeskjedWithDefaultValues();
        assertThat(beskjed.getEksternVarsling(), is(expectedEksternVarsling));
    }

    @Test
    void skalSetteDefaultVerdiForPrefererteKanaler() {
        BeskjedInput beskjed = getBeskjedWithDefaultValues();
        assertThat(beskjed.getPrefererteKanaler(), is(expectedPrefererteKanaler));
    }

    @Test
    void skalSetteNullSomDefaultverdiForSynligFremTil() {
        BeskjedInput beskjed = getBeskjedWithDefaultValues();
        assertThat(beskjed.getSynligFremTil(), is(nullValue()));
    }

    @Test
    void skalSetteNullSomDefaultverdiEpostVarslingstekst() {
        BeskjedInput beskjed = getBeskjedWithDefaultValues();
        assertThat(beskjed.getEpostVarslingstekst(), is(nullValue()));
    }

    @Test
    void skalSetteNullSomDefaultverdiEpostVarslingstittel() {
        BeskjedInput beskjed = getBeskjedWithDefaultValues();
        assertThat(beskjed.getEpostVarslingstittel(), is(nullValue()));
    }

    @Test
    void skalSetteNullSomDefaultverdiSmsVarslingstekst() {
        BeskjedInput beskjed = getBeskjedWithDefaultValues();
        assertThat(beskjed.getSmsVarslingstekst(), is(nullValue()));
    }

    private BeskjedInput getBeskjedWithDefaultValues() {
        return BeskjedInput.newBuilder()
                .setTidspunkt(LocalDateTime.now().toEpochSecond(ZoneOffset.UTC))
                .setTekst("Dette er informasjon du må lese")
                .setLink("https://gyldig.url")
                .build();
    }
}
