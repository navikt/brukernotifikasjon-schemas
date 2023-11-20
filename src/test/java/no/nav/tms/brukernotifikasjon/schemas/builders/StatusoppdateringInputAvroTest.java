package no.nav.tms.brukernotifikasjon.schemas.builders;

import no.nav.tms.brukernotifikasjon.schemas.builders.domain.StatusGlobal;
import no.nav.tms.brukernotifikasjon.schemas.input.StatusoppdateringInput;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class StatusoppdateringInputAvroTest {

    private int expectedSikkerhetsnivaa = 4;

    @Test
    void skalSetteDefaultverdiForSikkerhetsnivaa() {
        StatusoppdateringInput statusoppdatering = getStatusoppdateringWithDefaultValues();
        assertThat(statusoppdatering.getSikkerhetsnivaa(), is(expectedSikkerhetsnivaa));
    }

    @Test
    void skalSetteNullSomDefaultverdiForStatusIntern() {
        StatusoppdateringInput statusoppdatering = getStatusoppdateringWithDefaultValues();
        assertThat(statusoppdatering.getStatusIntern(), is(nullValue()));
    }

    private StatusoppdateringInput getStatusoppdateringWithDefaultValues() {
        return StatusoppdateringInput.newBuilder()
                .setTidspunkt(LocalDateTime.now().toEpochSecond(ZoneOffset.UTC))
                .setLink("https://gyldig.url")
                .setStatusGlobal(StatusGlobal.UNDER_BEHANDLING.toString())
                .setSakstema("FP")
                .build();
    }
}
