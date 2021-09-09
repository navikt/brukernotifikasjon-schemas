package no.nav.brukernotifikasjon.schemas.builders.input;

import no.nav.brukernotifikasjon.schemas.input.BeskjedInput;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class InnboksInputAvroTest {

    private int expectedSikkerhetsnivaa = 4;
    private boolean expectedEksternVarsling = false;

    @Test
    void skalSetteDefaultverdiForSikkerhetsnivaa() {
        BeskjedInput beskjedInput = getBeskjedInputWithDefaultValues();
        assertThat(beskjedInput.getSikkerhetsnivaa(), is(expectedSikkerhetsnivaa));
    }

    @Test
    void skalSetteDefaultverdiForEksternVarsling() {
        BeskjedInput beskjedInput = getBeskjedInputWithDefaultValues();
        assertThat(beskjedInput.getEksternVarsling(), is(expectedEksternVarsling));
    }

    @Test
    void skalSetteNullSomDefaultverdiForSynligFremTil() {
        BeskjedInput beskjedInput = getBeskjedInputWithDefaultValues();
        assertThat(beskjedInput.getSynligFremTil(), is(nullValue()));
    }

    private BeskjedInput getBeskjedInputWithDefaultValues() {
        return BeskjedInput.newBuilder()
                .setTidspunkt(LocalDateTime.now().toEpochSecond(ZoneOffset.UTC))
                .setGrupperingsId("3456789123456")
                .setTekst("Dette er informasjon du må lese")
                .setLink("https://gyldig.url")
                .build();
    }
}
