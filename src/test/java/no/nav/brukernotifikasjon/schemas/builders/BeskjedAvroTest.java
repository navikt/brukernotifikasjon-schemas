package no.nav.brukernotifikasjon.schemas.builders;

import no.nav.brukernotifikasjon.schemas.Beskjed;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

import static java.util.Collections.emptyList;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class BeskjedAvroTest {

    private int expectedSikkerhetsnivaa = 4;
    private boolean expectedEksternVarsling = false;
    private List<String> expectedPrefererteKanaler = emptyList();

    @Test
    void skalSetteDefaultverdiForSikkerhetsnivaa() {
        Beskjed beskjed = getBeskjedWithDefaultValues();
        assertThat(beskjed.getSikkerhetsnivaa(), is(expectedSikkerhetsnivaa));
    }

    @Test
    void skalSetteDefaultverdiForEksternVarsling() {
        Beskjed beskjed = getBeskjedWithDefaultValues();
        assertThat(beskjed.getEksternVarsling(), is(expectedEksternVarsling));
    }

    @Test
    void skalSetteDefaultVerdiForPrefererteKanaler() {
        Beskjed beskjed = getBeskjedWithDefaultValues();
        assertThat(beskjed.getPrefererteKanaler(), is(expectedPrefererteKanaler));
    }

    @Test
    void skalSetteNullSomDefaultverdiForSynligFremTil() {
        Beskjed beskjed = getBeskjedWithDefaultValues();
        assertThat(beskjed.getSynligFremTil(), is(nullValue()));
    }

    private Beskjed getBeskjedWithDefaultValues() {
        return Beskjed.newBuilder()
                .setTidspunkt(LocalDateTime.now().toEpochSecond(ZoneOffset.UTC))
                .setTekst("Dette er informasjon du må lese")
                .setLink("https://gyldig.url")
                .build();
    }
}
