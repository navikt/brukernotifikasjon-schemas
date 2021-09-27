package no.nav.brukernotifikasjon.schemas.builders;

import no.nav.brukernotifikasjon.schemas.Innboks;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

import static java.util.Collections.emptyList;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class InnboksAvroTest {

    private int expectedSikkerhetsnivaa = 4;
    private boolean expectedEksternVarsling = false;
    private List<String> expectedPrefererteKanaler = emptyList();

    @Test
    void skalSetteDefaultverdiForSikkerhetsnivaa() {
        Innboks innboks = getInnboksWithDefaultValues();
        assertThat(innboks.getSikkerhetsnivaa(), is(expectedSikkerhetsnivaa));
    }

    @Test
    void skalSetteDefaultverdiForEksternVarsling() {
        Innboks innboks = getInnboksWithDefaultValues();
        assertThat(innboks.getEksternVarsling(), is(expectedEksternVarsling));
    }

    @Test
    void skalSetteDefaultVerdiForPrefererteKanaler() {
        Innboks innboks = getInnboksWithDefaultValues();
        assertThat(innboks.getPrefererteKanaler(), is(expectedPrefererteKanaler));
    }

    private Innboks getInnboksWithDefaultValues() {
        return Innboks.newBuilder()
                .setFodselsnummer("12345678901")
                .setTidspunkt(LocalDateTime.now().toEpochSecond(ZoneOffset.UTC))
                .setGrupperingsId("3456789123456")
                .setTekst("Dette er informasjon du må lese")
                .setLink("https://gyldig.url")
                .build();
    }

}
