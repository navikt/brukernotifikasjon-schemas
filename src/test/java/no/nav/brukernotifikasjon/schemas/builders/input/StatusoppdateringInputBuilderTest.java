package no.nav.brukernotifikasjon.schemas.builders.input;

import no.nav.brukernotifikasjon.schemas.builders.domain.StatusGlobal;
import no.nav.brukernotifikasjon.schemas.builders.exception.FieldValidationException;
import no.nav.brukernotifikasjon.schemas.input.StatusoppdateringInput;
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
public class StatusoppdateringInputBuilderTest {

    private String expectedGrupperingsId;
    private int expectedSikkerhetsnivaa;
    private URL expectedLink;
    private StatusGlobal expectedStatusGlobal;
    private String expectedStausIntern;
    private String expectedSakstema;
    private LocalDateTime expectedTidspunkt;

    @BeforeAll
    void setUp() throws MalformedURLException {
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
        StatusoppdateringInputBuilder builder = getBuilderWithDefaultValues();
        StatusoppdateringInput statusoppdateringInput = builder.build();

        assertThat(statusoppdateringInput.getGrupperingsId(), is(expectedGrupperingsId));
        assertThat(statusoppdateringInput.getSikkerhetsnivaa(), is(expectedSikkerhetsnivaa));
        assertThat(statusoppdateringInput.getLink(), is(expectedLink.toString()));
        assertThat(statusoppdateringInput.getStatusGlobal(), is(expectedStatusGlobal.toString()));
        assertThat(statusoppdateringInput.getStatusIntern(), is(expectedStausIntern));
        assertThat(statusoppdateringInput.getSakstema(), is(expectedSakstema));
        long expectedTidspunktAsUtcLong = expectedTidspunkt.toInstant(ZoneOffset.UTC).toEpochMilli();
        assertThat(statusoppdateringInput.getTidspunkt(), is(expectedTidspunktAsUtcLong));
    }

    @Test
    void skalIkkeGodtaForLangGrupperingsId() {
        String tooLongGrupperingsId = String.join("", Collections.nCopies(101, "1"));
        StatusoppdateringInputBuilder builder = getBuilderWithDefaultValues().withGrupperingsId(tooLongGrupperingsId);
        FieldValidationException exceptionThrown = assertThrows(FieldValidationException.class, () -> builder.build());
        assertThat(exceptionThrown.getMessage(), containsString("grupperingsId"));
    }

    @Test
    void skalIkkeGodtaManglendeGrupperingsId() {
        StatusoppdateringInputBuilder builder = getBuilderWithDefaultValues().withGrupperingsId(null);
        FieldValidationException exceptionThrown = assertThrows(FieldValidationException.class, () -> builder.build());
        assertThat(exceptionThrown.getMessage(), containsString("grupperingsId"));
    }

    @Test
    void skalIkkeGodtaForLavtSikkerhetsnivaa() {
        int invalidSikkerhetsnivaa = 2;
        StatusoppdateringInputBuilder builder = getBuilderWithDefaultValues().withSikkerhetsnivaa(invalidSikkerhetsnivaa);
        FieldValidationException exceptionThrown = assertThrows(FieldValidationException.class, () -> builder.build());
        assertThat(exceptionThrown.getMessage(), containsString("Sikkerhetsnivaa"));
    }

    @Test
    void skalIkkeGodtaForLangLink() throws MalformedURLException {
        URL invalidLink = new URL("https://" + String.join("", Collections.nCopies(201, "n")));
        StatusoppdateringInputBuilder builder = getBuilderWithDefaultValues().withLink(invalidLink);
        FieldValidationException exceptionThrown = assertThrows(FieldValidationException.class, () -> builder.build());
        assertThat(exceptionThrown.getMessage(), containsString("link"));
    }

    @Test
    void skalGodtaManglendeLink() {
        StatusoppdateringInputBuilder builder = getBuilderWithDefaultValues().withLink(null);
        assertDoesNotThrow(() -> builder.build());
    }

    @Test
    void skalIkkeGodtaManglendeStatusGlobal() {
        StatusoppdateringInputBuilder builder = getBuilderWithDefaultValues().withStatusGlobal(null);
        FieldValidationException exceptionThrown = assertThrows(FieldValidationException.class, () -> builder.build());
        assertThat(exceptionThrown.getMessage(), containsString("statusGlobal"));
    }

    @Test
    void skalIkkeGodtaForLangStatusIntern() {
        String tooLongStatusIntern = String.join("", Collections.nCopies(101, "n"));
        StatusoppdateringInputBuilder builder = getBuilderWithDefaultValues().withStatusIntern(tooLongStatusIntern);
        FieldValidationException exceptionThrown = assertThrows(FieldValidationException.class, () -> builder.build());
        assertThat(exceptionThrown.getMessage(), containsString("statusIntern"));
    }

    @Test
    void skalIkkeGodtaManglendeStatusIntern() {
        StatusoppdateringInputBuilder builder = getBuilderWithDefaultValues().withStatusIntern(null);
        FieldValidationException exceptionThrown = assertThrows(FieldValidationException.class, () -> builder.build());
        assertThat(exceptionThrown.getMessage(), containsString("statusIntern"));
    }

    @Test
    void skalIkkeGodtaForLangSakstema() {
        String tooLongSakstema = String.join("", Collections.nCopies(51, "n"));
        StatusoppdateringInputBuilder builder = getBuilderWithDefaultValues().withSakstema(tooLongSakstema);
        FieldValidationException exceptionThrown = assertThrows(FieldValidationException.class, () -> builder.build());
        assertThat(exceptionThrown.getMessage(), containsString("sakstema"));
    }

    @Test
    void skalIkkeGodtaManglendeSakstema() {
        String tooLongSakstema = String.join("", Collections.nCopies(51, "n"));
        StatusoppdateringInputBuilder builder = getBuilderWithDefaultValues().withSakstema(tooLongSakstema);
        FieldValidationException exceptionThrown = assertThrows(FieldValidationException.class, () -> builder.build());
        assertThat(exceptionThrown.getMessage(), containsString("sakstema"));
    }

    @Test
    void skalIkkeGodtaManglendeEventtidspunkt() {
        StatusoppdateringInputBuilder builder = getBuilderWithDefaultValues().withTidspunkt(null);
        FieldValidationException exceptionThrown = assertThrows(FieldValidationException.class, () -> builder.build());
        assertThat(exceptionThrown.getMessage(), containsString("tidspunkt"));
    }

    private StatusoppdateringInputBuilder getBuilderWithDefaultValues() {
        return new StatusoppdateringInputBuilder()
                .withGrupperingsId(expectedGrupperingsId)
                .withSikkerhetsnivaa(expectedSikkerhetsnivaa)
                .withLink(expectedLink)
                .withStatusGlobal(expectedStatusGlobal)
                .withStatusIntern(expectedStausIntern)
                .withSakstema(expectedSakstema)
                .withTidspunkt(expectedTidspunkt);
    }
}
