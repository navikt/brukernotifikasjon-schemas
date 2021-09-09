package no.nav.brukernotifikasjon.schemas.builders.input;

import no.nav.brukernotifikasjon.schemas.builders.domain.StatusGlobal;
import no.nav.brukernotifikasjon.schemas.input.StatusoppdateringInput;
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
        StatusoppdateringInput statusoppdateringInput = getStatusoppdateringInputWithDefaultValues();
        assertThat(statusoppdateringInput.getSikkerhetsnivaa(), is(expectedSikkerhetsnivaa));
    }

    @Test
    void skalSetteNullSomDefaultverdiForStatusIntern() {
        StatusoppdateringInput statusoppdateringInput = getStatusoppdateringInputWithDefaultValues();
        assertThat(statusoppdateringInput.getStatusIntern(), is(nullValue()));
    }

    private StatusoppdateringInput getStatusoppdateringInputWithDefaultValues() {
        return StatusoppdateringInput.newBuilder()
                .setTidspunkt(LocalDateTime.now().toEpochSecond(ZoneOffset.UTC))
                .setGrupperingsId("3456789123456")
                .setLink("https://gyldig.url")
                .setStatusGlobal(StatusGlobal.UNDER_BEHANDLING.toString())
                .setSakstema("FP")
                .build();
    }
}
