package no.nav.brukernotifikasjon.schemas.builders.legacy;

import no.nav.brukernotifikasjon.schemas.Statusoppdatering;
import no.nav.brukernotifikasjon.schemas.builders.domain.StatusGlobal;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class StatusoppdateringLegacyAvroTest {

    private int expectedSikkerhetsnivaa = 4;

    @Test
    void skalSetteDefaultverdiForSikkerhetsnivaa() {
        Statusoppdatering statusoppdatering = getStatusoppdateringLegacyWithDefaultValues();
        assertThat(statusoppdatering.getSikkerhetsnivaa(), is(expectedSikkerhetsnivaa));
    }

    @Test
    void skalSetteNullSomDefaultverdiForStatusIntern() {
        Statusoppdatering statusoppdatering = getStatusoppdateringLegacyWithDefaultValues();
        assertThat(statusoppdatering.getStatusIntern(), is(nullValue()));
    }

    private Statusoppdatering getStatusoppdateringLegacyWithDefaultValues() {
        return Statusoppdatering.newBuilder()
                .setTidspunkt(LocalDateTime.now().toEpochSecond(ZoneOffset.UTC))
                .setGrupperingsId("3456789123456")
                .setLink("https://gyldig.url")
                .setStatusGlobal(StatusGlobal.UNDER_BEHANDLING.toString())
                .setSakstema("FP")
                .setFodselsnummer("12345678901")
                .build();
    }
}
