package no.nav.brukernotifikasjon.schemas.builders;

import no.nav.brukernotifikasjon.schemas.Beskjed;
import org.junit.jupiter.api.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class BeskjedBuilderTest {

    @Test
    void skalGodtaEventerMedGyldigeFeltverdier() throws MalformedURLException {
        String expectedFodselsnr = "12345678901";
        String expectedGrupperingsId = "3456789123456";
        int expectedSikkerhetsnivaa = 4;
        URL expectedLink = new URL("https://gyldig.url");
        String expectedTekst = "Dette er informasjon du må lese";
        LocalDateTime expectedTidspunkt = LocalDateTime.now();
        LocalDateTime expectedSynligFremTil = expectedTidspunkt.plusDays(2);

        BeskjedBuilder builder = new BeskjedBuilder()
                .withFodselsnummer(expectedFodselsnr)
                .withGrupperingsId(expectedGrupperingsId)
                .withSikkerhetsnivaa(expectedSikkerhetsnivaa)
                .withLink(expectedLink)
                .withTekst(expectedTekst)
                .withTidspunkt(expectedTidspunkt)
                .withSynligFremTil(expectedSynligFremTil);
        Beskjed beskjed = builder.build();

        assertThat(beskjed.getFodselsnummer(), is(expectedFodselsnr));
        assertThat(beskjed.getGrupperingsId(), is(expectedGrupperingsId));
        assertThat(beskjed.getSikkerhetsnivaa(), is(expectedSikkerhetsnivaa));
        assertThat(beskjed.getLink(), is(expectedLink.toString()));
        assertThat(beskjed.getTekst(), is(expectedTekst));
        long expectedTidspunktAsUtcLong = expectedTidspunkt.toInstant(ZoneOffset.UTC).toEpochMilli();
        assertThat(beskjed.getTidspunkt(), is(expectedTidspunktAsUtcLong));
        long expectedSynligFremTilAsUtcLong = expectedSynligFremTil.toInstant(ZoneOffset.UTC).toEpochMilli();
        assertThat(beskjed.getSynligFremTil(), is(expectedSynligFremTilAsUtcLong));
    }
}
