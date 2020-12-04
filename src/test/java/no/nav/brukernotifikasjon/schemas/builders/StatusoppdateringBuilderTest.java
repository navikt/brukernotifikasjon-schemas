package no.nav.brukernotifikasjon.schemas.builders;

import no.nav.brukernotifikasjon.schemas.Statusoppdatering;
import no.nav.brukernotifikasjon.schemas.builders.domain.StatusGlobal;
import no.nav.brukernotifikasjon.schemas.builders.exception.FieldValidationException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Collections;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class StatusoppdateringBuilderTest {

    private String expectedFodselsnr;
    private String expectedGrupperingsId;
    private int expectedSikkerhetsnivaa;
    private URL expectedLink;
    private StatusGlobal expectedStatusGlobal;
    private String expectedStausIntern;
    private String expectedSakstema;
    private LocalDateTime expectedTidspunkt;

    @BeforeAll
    void setUp() throws MalformedURLException {
        expectedFodselsnr = "12345678901";
        expectedGrupperingsId = "3456789123456";
        expectedSikkerhetsnivaa = 4;
        expectedLink = new URL("https://gyldig.url");
        expectedStatusGlobal = StatusGlobal.UNDER_BEHANDLING;
        expectedStausIntern = "Vi behandler saken din";
        expectedSakstema = "FP";
        expectedTidspunkt = LocalDateTime.now();
    }

    @Test
    void skalGodtaEventerMedGyldigeFeltverdier() {
        StatusoppdateringBuilder builder = getBuilderWithDefaultValues();
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

    @Test
    void skalIkkeGodtaUgyldigFodselsnummer() {
        String tooLongFodselsnummer = String.join("", Collections.nCopies(11, "12"));
        StatusoppdateringBuilder builder = getBuilderWithDefaultValues().withFodselsnummer(tooLongFodselsnummer);
        FieldValidationException exceptionThrown = assertThrows(FieldValidationException.class, () -> builder.build());
        assertThat(exceptionThrown.getMessage(), containsString("fodselsnummer"));
    }

    @Test
    void skalIkkeGodtaManglendeFodselsnummer() {
        StatusoppdateringBuilder builder = getBuilderWithDefaultValues().withFodselsnummer(null);
        FieldValidationException exceptionThrown = assertThrows(FieldValidationException.class, () -> builder.build());
        assertThat(exceptionThrown.getMessage(), containsString("fodselsnummer"));
    }

    @Test
    void skalIkkeGodtaForLangGrupperingsId() {
        String tooLongGrupperingsId = String.join("", Collections.nCopies(101, "1"));
        StatusoppdateringBuilder builder = getBuilderWithDefaultValues().withGrupperingsId(tooLongGrupperingsId);
        FieldValidationException exceptionThrown = assertThrows(FieldValidationException.class, () -> builder.build());
        assertThat(exceptionThrown.getMessage(), containsString("grupperingsId"));
    }

    @Test
    void skalIkkeGodtaManglendeGrupperingsId() {
        StatusoppdateringBuilder builder = getBuilderWithDefaultValues().withGrupperingsId(null);
        FieldValidationException exceptionThrown = assertThrows(FieldValidationException.class, () -> builder.build());
        assertThat(exceptionThrown.getMessage(), containsString("grupperingsId"));
    }

    @Test
    void skalIkkeGodtaForLavtSikkerhetsnivaa() {
        int invalidSikkerhetsnivaa = 2;
        StatusoppdateringBuilder builder = getBuilderWithDefaultValues().withSikkerhetsnivaa(invalidSikkerhetsnivaa);
        FieldValidationException exceptionThrown = assertThrows(FieldValidationException.class, () -> builder.build());
        assertThat(exceptionThrown.getMessage(), containsString("Sikkerhetsnivaa"));
    }

    @Test
    void skalIkkeGodtaForLangLink() throws MalformedURLException {
        URL invalidLink = new URL("https://" + String.join("", Collections.nCopies(201, "n")));
        StatusoppdateringBuilder builder = getBuilderWithDefaultValues().withLink(invalidLink);
        FieldValidationException exceptionThrown = assertThrows(FieldValidationException.class, () -> builder.build());
        assertThat(exceptionThrown.getMessage(), containsString("link"));
    }

    @Test
    void skalGodtaManglendeLink() {
        StatusoppdateringBuilder builder = getBuilderWithDefaultValues().withLink(null);
        assertDoesNotThrow(() -> builder.build());
    }

    @Test
    void skalIkkeGodtaManglendeStatusGlobal() {
        StatusoppdateringBuilder builder = getBuilderWithDefaultValues().withStatusGlobal(null);
        FieldValidationException exceptionThrown = assertThrows(FieldValidationException.class, () -> builder.build());
        assertThat(exceptionThrown.getMessage(), containsString("statusGlobal"));
    }

    @Test
    void skalIkkeGodtaForLangStatusIntern() {
        String tooLongStatusIntern = String.join("", Collections.nCopies(101, "n"));
        StatusoppdateringBuilder builder = getBuilderWithDefaultValues().withStatusIntern(tooLongStatusIntern);
        FieldValidationException exceptionThrown = assertThrows(FieldValidationException.class, () -> builder.build());
        assertThat(exceptionThrown.getMessage(), containsString("statusIntern"));
    }

    @Test
    void skalIkkeGodtaManglendeStatusIntern() {
        StatusoppdateringBuilder builder = getBuilderWithDefaultValues().withStatusIntern(null);
        FieldValidationException exceptionThrown = assertThrows(FieldValidationException.class, () -> builder.build());
        assertThat(exceptionThrown.getMessage(), containsString("statusIntern"));
    }

    @Test
    void skalIkkeGodtaForLangSakstema() {
        String tooLongSakstema = String.join("", Collections.nCopies(51, "n"));
        StatusoppdateringBuilder builder = getBuilderWithDefaultValues().withSakstema(tooLongSakstema);
        FieldValidationException exceptionThrown = assertThrows(FieldValidationException.class, () -> builder.build());
        assertThat(exceptionThrown.getMessage(), containsString("sakstema"));
    }

    @Test
    void skalIkkeGodtaManglendeSakstema() {
        String tooLongSakstema = String.join("", Collections.nCopies(51, "n"));
        StatusoppdateringBuilder builder = getBuilderWithDefaultValues().withSakstema(tooLongSakstema);
        FieldValidationException exceptionThrown = assertThrows(FieldValidationException.class, () -> builder.build());
        assertThat(exceptionThrown.getMessage(), containsString("sakstema"));
    }

    @Test
    void skalIkkeGodtaManglendeEventtidspunkt() {
        StatusoppdateringBuilder builder = getBuilderWithDefaultValues().withTidspunkt(null);
        FieldValidationException exceptionThrown = assertThrows(FieldValidationException.class, () -> builder.build());
        assertThat(exceptionThrown.getMessage(), containsString("tidspunkt"));
    }

    private StatusoppdateringBuilder getBuilderWithDefaultValues() {
        return new StatusoppdateringBuilder()
                .withFodselsnummer(expectedFodselsnr)
                .withGrupperingsId(expectedGrupperingsId)
                .withSikkerhetsnivaa(expectedSikkerhetsnivaa)
                .withLink(expectedLink)
                .withStatusGlobal(expectedStatusGlobal)
                .withStatusIntern(expectedStausIntern)
                .withSakstema(expectedSakstema)
                .withTidspunkt(expectedTidspunkt);
    }
}
