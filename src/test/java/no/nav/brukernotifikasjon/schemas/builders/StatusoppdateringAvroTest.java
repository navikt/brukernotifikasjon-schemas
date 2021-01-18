package no.nav.brukernotifikasjon.schemas.builders;

import no.nav.brukernotifikasjon.schemas.Beskjed;
import no.nav.brukernotifikasjon.schemas.Statusoppdatering;
import no.nav.brukernotifikasjon.schemas.builders.domain.StatusGlobal;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class StatusoppdateringAvroTest {

    private int expectedSikkerhetsnivaa = 4;

    @Test
    void skalSetteDefaultverdiForSikkerhetsnivaa() {
        Statusoppdatering statusoppdatering = getStatusoppdateringWithDefaultValues();
        assertThat(statusoppdatering.getSikkerhetsnivaa(), is(expectedSikkerhetsnivaa));
    }

    @Test
    void skalSetteNullSomDefaultverdiForStatusIntern() {
        Statusoppdatering statusoppdatering = getStatusoppdateringWithDefaultValues();
        assertThat(statusoppdatering.getStatusIntern(), is(nullValue()));
    }

    private Statusoppdatering getStatusoppdateringWithDefaultValues() {
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
