package no.nav.brukernotifikasjon.schemas.builders;

import no.nav.brukernotifikasjon.schemas.Beskjed;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class BeskjedAvroTest {

    private int expectedSikkerhetsnivaa = 4;
    private boolean expectedEksternVarsling = false;

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
