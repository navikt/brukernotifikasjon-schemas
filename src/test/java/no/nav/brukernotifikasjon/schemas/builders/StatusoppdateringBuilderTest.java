package no.nav.brukernotifikasjon.schemas.builders;

import no.nav.brukernotifikasjon.schemas.Statusoppdatering;
import org.junit.jupiter.api.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class StatusoppdateringBuilderTest {

    @Test
    void skalGodtaEventerMedGyldigeFeltverdier() throws MalformedURLException {
        String expectedFodselsnr = "12345678901";
        String expectedGrupperingsId = "3456789123456";
        int expectedSikkerhetsnivaa = 4;
        URL expectedLink = new URL("https://gyldig.url");
        StatusGlobal expectedStatusGlobal = StatusGlobal.UNDER_BEHANDLING;
        String expectedStausIntern = "Vi behandler saken din";
        String expectedSakstema = "FP";
        LocalDateTime expectedTidspunkt = LocalDateTime.now();

        StatusoppdateringBuilder builder = new StatusoppdateringBuilder()
                .withFodselsnummer(expectedFodselsnr)
                .withGrupperingsId(expectedGrupperingsId)
                .withSikkerhetsnivaa(expectedSikkerhetsnivaa)
                .withLink(expectedLink)
                .withStatusGlobal(expectedStatusGlobal)
                .withStatusIntern(expectedStausIntern)
                .withSakstema(expectedSakstema)
                .withTidspunkt(expectedTidspunkt);
        Statusoppdatering statusoppdatering = builder.build();

        assertThat(statusoppdatering.getFodselsnummer(), is(expectedFodselsnr));
        assertThat(statusoppdatering.getGrupperingsId(), is(expectedGrupperingsId));
        assertThat(statusoppdatering.getSikkerhetsnivaa(), is(expectedSikkerhetsnivaa));
        assertThat(statusoppdatering.getLink(), is(expectedLink.toString()));
        assertThat(statusoppdatering.getStatusGlobal(), is(expectedStatusGlobal.toString()));
        assertThat(statusoppdatering.getStatusIntern(), is(expectedStausIntern));
        assertThat(statusoppdatering.getSakstema(), is(expectedSakstema));
        long expectedTidspunktAsUtcLong = expectedTidspunkt.toInstant(ZoneOffset.UTC).toEpochMilli();
        assertThat(statusoppdatering.getTidspunkt(), is(expectedTidspunktAsUtcLong));
    }
}
