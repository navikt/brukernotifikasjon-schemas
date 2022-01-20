package no.nav.brukernotifikasjon.schemas.builders.legacy;

import no.nav.brukernotifikasjon.schemas.Beskjed;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class BeskjedAvroTest {

    private final int expectedSikkerhetsnivaa = 4;
    private final boolean expectedEksternVarsling = false;

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
    void skalSetteNullSomDefaultverdiForSynligFremTil() {
        Beskjed beskjed = getBeskjedWithDefaultValues();
        assertThat(beskjed.getSynligFremTil(), is(nullValue()));
    }

    @Test
    void skalSetteNullSomDefaultverdiEpostVarslingstekst() {
        Beskjed beskjed = getBeskjedWithDefaultValues();
        assertThat(beskjed.getEpostVarslingstekst(), is(nullValue()));
    }

    @Test
    void skalSetteNullSomDefaultverdiSmsVarslingstekst() {
        Beskjed beskjed = getBeskjedWithDefaultValues();
        assertThat(beskjed.getSmsVarslingstekst(), is(nullValue()));
    }

    @Test
    void skalSetteNullSomDefaultverdiSmsVarslingstittel() {
        Beskjed beskjed = getBeskjedWithDefaultValues();
        assertThat(beskjed.getEpostVarslingstittel(), is(nullValue()));
    }

    private Beskjed getBeskjedWithDefaultValues() {
        return Beskjed.newBuilder()
                .setTidspunkt(LocalDateTime.now().toEpochSecond(ZoneOffset.UTC))
                .setFodselsnummer("12345678901")
                .setGrupperingsId("3456789123456")
                .setTekst("Dette er informasjon du må lese")
                .setLink("https://gyldig.url")
                .build();
    }
}
