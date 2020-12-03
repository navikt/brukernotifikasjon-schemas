package no.nav.brukernotifikasjon.schemas.builders;

import no.nav.brukernotifikasjon.schemas.Done;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class DoneBuilderTest {

    @Test
    void skalGodtaEventerMedGyldigeFeltverdier() {
        String expectedFodselsnr = "12345678901";
        String expectedGrupperingsId = "3456789123456";
        LocalDateTime expectedTidspunkt = LocalDateTime.now();

        DoneBuilder builder = new DoneBuilder()
                .withFodselsnummer(expectedFodselsnr)
                .withGrupperingsId(expectedGrupperingsId)
                .withTidspunkt(expectedTidspunkt);
        Done done = builder.build();

        assertThat(done.getFodselsnummer(), is(expectedFodselsnr));
        assertThat(done.getGrupperingsId(), is(expectedGrupperingsId));
        long expectedTidspunktAsUtcLong = expectedTidspunkt.toInstant(ZoneOffset.UTC).toEpochMilli();
        assertThat(done.getTidspunkt(), is(expectedTidspunktAsUtcLong));
    }
}
