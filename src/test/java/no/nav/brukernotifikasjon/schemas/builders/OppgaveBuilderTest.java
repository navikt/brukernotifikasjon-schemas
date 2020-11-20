package no.nav.brukernotifikasjon.schemas.builders;

import no.nav.brukernotifikasjon.schemas.Oppgave;
import org.junit.jupiter.api.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

class OppgaveBuilderTest {

    @Test
    void skalGodtaEventerMedGyldigeFeltverdier() throws MalformedURLException {
        String expectedFodselsnr = "12345678901";
        String expectedGrupperingsId = "3456789123456";
        int expectedSikkerhetsnivaa = 4;
        URL expectedLink = new URL("https://gyldig.url");
        String expectedTekst = "Du må sende nytt meldekort";
        LocalDateTime expectedTidspunkt = LocalDateTime.now();

        OppgaveBuilder builder = new OppgaveBuilder()
                .withFodselsnummer(expectedFodselsnr)
                .withGrupperingsId(expectedGrupperingsId)
                .withSikkerhetsnivaa(expectedSikkerhetsnivaa)
                .withLink(expectedLink)
                .withTekst(expectedTekst)
                .withTidspunkt(expectedTidspunkt);
        Oppgave oppgave = builder.build();

        assertThat(oppgave.getFodselsnummer(), is(expectedFodselsnr));
        assertThat(oppgave.getGrupperingsId(), is(expectedGrupperingsId));
        assertThat(oppgave.getSikkerhetsnivaa(), is(expectedSikkerhetsnivaa));
        assertThat(oppgave.getLink(), is(expectedLink.toString()));
        assertThat(oppgave.getTekst(), is(expectedTekst));
        assertThat(oppgave.getTidspunkt(), is(notNullValue()));
        long expectedTidspunktAsUtcLong = expectedTidspunkt.toInstant(ZoneOffset.UTC).toEpochMilli();
        assertThat(oppgave.getTidspunkt(), is(expectedTidspunktAsUtcLong));
    }

}
