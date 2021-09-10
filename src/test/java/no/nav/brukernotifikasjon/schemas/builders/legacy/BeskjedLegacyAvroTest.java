package no.nav.brukernotifikasjon.schemas.builders.legacy;

import no.nav.brukernotifikasjon.schemas.legacy.BeskjedLegacy;
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
        BeskjedLegacy beskjedLegacy = getBeskjedLegacyWithDefaultValues();
        assertThat(beskjedLegacy.getSikkerhetsnivaa(), is(expectedSikkerhetsnivaa));
    }

    @Test
    void skalSetteDefaultverdiForEksternVarsling() {
        BeskjedLegacy beskjedLegacy = getBeskjedLegacyWithDefaultValues();
        assertThat(beskjedLegacy.getEksternVarsling(), is(expectedEksternVarsling));
    }

    @Test
    void skalSetteNullSomDefaultverdiForSynligFremTil() {
        BeskjedLegacy beskjedLegacy = getBeskjedLegacyWithDefaultValues();
        assertThat(beskjedLegacy.getSynligFremTil(), is(nullValue()));
    }

    private BeskjedLegacy getBeskjedLegacyWithDefaultValues() {
        return BeskjedLegacy.newBuilder()
                .setTidspunkt(LocalDateTime.now().toEpochSecond(ZoneOffset.UTC))
                .setFodselsnummer("12345678901")
                .setGrupperingsId("3456789123456")
                .setTekst("Dette er informasjon du må lese")
                .setLink("https://gyldig.url")
                .build();
    }
}
