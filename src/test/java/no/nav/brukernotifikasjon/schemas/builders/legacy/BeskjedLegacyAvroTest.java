package no.nav.brukernotifikasjon.schemas.builders.legacy;

import no.nav.brukernotifikasjon.schemas.Beskjed;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class BeskjedLegacyAvroTest {

    private int expectedSikkerhetsnivaa = 4;
    private boolean expectedEksternVarsling = false;

    @Test
    void skalSetteDefaultverdiForSikkerhetsnivaa() {
        Beskjed beskjed = getBeskjedLegacyWithDefaultValues();
        assertThat(beskjed.getSikkerhetsnivaa(), is(expectedSikkerhetsnivaa));
    }

    @Test
    void skalSetteDefaultverdiForEksternVarsling() {
        Beskjed beskjed = getBeskjedLegacyWithDefaultValues();
        assertThat(beskjed.getEksternVarsling(), is(expectedEksternVarsling));
    }

    @Test
    void skalSetteNullSomDefaultverdiForSynligFremTil() {
        Beskjed beskjed = getBeskjedLegacyWithDefaultValues();
        assertThat(beskjed.getSynligFremTil(), is(nullValue()));
    }

    private Beskjed getBeskjedLegacyWithDefaultValues() {
        return Beskjed.newBuilder()
                .setTidspunkt(LocalDateTime.now().toEpochSecond(ZoneOffset.UTC))
                .setFodselsnummer("12345678901")
                .setGrupperingsId("3456789123456")
                .setTekst("Dette er informasjon du må lese")
                .setLink("https://gyldig.url")
                .build();
    }
}
