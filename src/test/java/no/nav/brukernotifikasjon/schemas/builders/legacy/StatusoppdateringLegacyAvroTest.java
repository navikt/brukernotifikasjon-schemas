package no.nav.brukernotifikasjon.schemas.builders.legacy;

import no.nav.brukernotifikasjon.schemas.builders.domain.StatusGlobal;
import no.nav.brukernotifikasjon.schemas.legacy.StatusoppdateringLegacy;
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
        StatusoppdateringLegacy statusoppdateringLegacy = getStatusoppdateringLegacyWithDefaultValues();
        assertThat(statusoppdateringLegacy.getSikkerhetsnivaa(), is(expectedSikkerhetsnivaa));
    }

    @Test
    void skalSetteNullSomDefaultverdiForStatusIntern() {
        StatusoppdateringLegacy statusoppdateringLegacy = getStatusoppdateringLegacyWithDefaultValues();
        assertThat(statusoppdateringLegacy.getStatusIntern(), is(nullValue()));
    }

    private StatusoppdateringLegacy getStatusoppdateringLegacyWithDefaultValues() {
        return StatusoppdateringLegacy.newBuilder()
                .setTidspunkt(LocalDateTime.now().toEpochSecond(ZoneOffset.UTC))
                .setGrupperingsId("3456789123456")
                .setLink("https://gyldig.url")
                .setStatusGlobal(StatusGlobal.UNDER_BEHANDLING.toString())
                .setSakstema("FP")
                .setFodselsnummer("12345678901")
                .build();
    }
}
