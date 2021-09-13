package no.nav.brukernotifikasjon.schemas.builders;

import no.nav.brukernotifikasjon.schemas.Innboks;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;


public class InnboksAvroTest {

    private int expectedSikkerhetsnivaa = 4;

    @Test
    void skalSetteDefaultverdiForSikkerhetsnivaa() {
        Innboks innboks = getInnboksWithDefaultValues();
        assertThat(innboks.getSikkerhetsnivaa(), is(expectedSikkerhetsnivaa));
    }

    private Innboks getInnboksWithDefaultValues() {
        return Innboks.newBuilder()
                .setTidspunkt(LocalDateTime.now().toEpochSecond(ZoneOffset.UTC))
                .setTekst("Dette er informasjon du må lese")
                .setLink("https://gyldig.url")
                .build();
    }
}
