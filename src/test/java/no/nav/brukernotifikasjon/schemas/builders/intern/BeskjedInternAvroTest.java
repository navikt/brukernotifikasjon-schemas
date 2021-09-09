package no.nav.brukernotifikasjon.schemas.builders.intern;

import no.nav.brukernotifikasjon.schemas.intern.BeskjedIntern;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class BeskjedInternAvroTest {

    private int expectedSikkerhetsnivaa = 4;
    private boolean expectedEksternVarsling = false;

    @Test
    void skalSetteDefaultverdiForSikkerhetsnivaa() {
        BeskjedIntern beskjedIntern = getBeskjedInternWithDefaultValues();
        assertThat(beskjedIntern.getSikkerhetsnivaa(), is(expectedSikkerhetsnivaa));
    }

    @Test
    void skalSetteDefaultverdiForEksternVarsling() {
        BeskjedIntern beskjedIntern = getBeskjedInternWithDefaultValues();
        assertThat(beskjedIntern.getEksternVarsling(), is(expectedEksternVarsling));
    }

    @Test
    void skalSetteNullSomDefaultverdiForSynligFremTil() {
        BeskjedIntern beskjedIntern = getBeskjedInternWithDefaultValues();
        assertThat(beskjedIntern.getSynligFremTil(), is(nullValue()));
    }

    private BeskjedIntern getBeskjedInternWithDefaultValues() {
        return BeskjedIntern.newBuilder()
                .setTidspunkt(LocalDateTime.now().toEpochSecond(ZoneOffset.UTC))
                .setFodselsnummer("12345678901")
                .setGrupperingsId("3456789123456")
                .setTekst("Dette er informasjon du må lese")
                .setLink("https://gyldig.url")
                .build();
    }
}
