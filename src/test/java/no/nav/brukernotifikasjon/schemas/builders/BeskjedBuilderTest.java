package no.nav.brukernotifikasjon.schemas.builders;

import no.nav.brukernotifikasjon.schemas.Beskjed;
import no.nav.brukernotifikasjon.schemas.builders.exception.FieldValidationException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Collections;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BeskjedBuilderTest {

    private String expectedFodselsnr;
    private String expectedGrupperingsId;
    private int expectedSikkerhetsnivaa;
    private URL expectedLink;
    private String expectedTekst;
    private LocalDateTime expectedTidspunkt;
    private LocalDateTime expectedSynligFremTil;
    private Boolean expectedEksternVarsling;

    @BeforeAll
    void setUp() throws MalformedURLException {
        expectedFodselsnr = "12345678901";
        expectedGrupperingsId = "3456789123456";
        expectedSikkerhetsnivaa = 4;
        expectedLink = new URL("https://gyldig.url");
        expectedTekst = "Dette er informasjon du må lese";
        expectedTidspunkt = LocalDateTime.now(ZoneId.of("UTC"));
        expectedSynligFremTil = expectedTidspunkt.plusDays(2);
        expectedEksternVarsling = false;
    }

    @Test
    void skalGodtaEventerMedGyldigeFeltverdier() {
        BeskjedBuilder builder = getBuilderWithDefaultValues();
        Beskjed beskjed = builder.build();

        assertThat(beskjed.getFodselsnummer(), is(expectedFodselsnr));
        assertThat(beskjed.getGrupperingsId(), is(expectedGrupperingsId));
        assertThat(beskjed.getSikkerhetsnivaa(), is(expectedSikkerhetsnivaa));
        assertThat(beskjed.getLink(), is(expectedLink.toString()));
        assertThat(beskjed.getTekst(), is(expectedTekst));
        long expectedTidspunktAsUtcLong = expectedTidspunkt.toInstant(ZoneOffset.UTC).toEpochMilli();
        assertThat(beskjed.getTidspunkt(), is(expectedTidspunktAsUtcLong));
        long expectedSynligFremTilAsUtcLong = expectedSynligFremTil.toInstant(ZoneOffset.UTC).toEpochMilli();
        assertThat(beskjed.getSynligFremTil(), is(expectedSynligFremTilAsUtcLong));
        assertThat(beskjed.getEksternVarsling(), is(expectedEksternVarsling));
    }

    @Test
    void skalIkkeGodtaUgyldigFodselsnummer() {
        String tooLongFodselsnummer = String.join("", Collections.nCopies(11, "12"));
        BeskjedBuilder builder = getBuilderWithDefaultValues().withFodselsnummer(tooLongFodselsnummer);
        FieldValidationException exceptionThrown = assertThrows(FieldValidationException.class, () -> builder.build());
        assertThat(exceptionThrown.getMessage(), containsString("fodselsnummer"));
    }

    @Test
    void skalIkkeGodtaManglendeFodselsnummer() {
        BeskjedBuilder builder = getBuilderWithDefaultValues().withFodselsnummer(null);
        FieldValidationException exceptionThrown = assertThrows(FieldValidationException.class, () -> builder.build());
        assertThat(exceptionThrown.getMessage(), containsString("fodselsnummer"));
    }

    @Test
    void skalIkkeGodtaForLangGrupperingsId() {
        String tooLongGrupperingsId = String.join("", Collections.nCopies(101, "1"));
        BeskjedBuilder builder = getBuilderWithDefaultValues().withGrupperingsId(tooLongGrupperingsId);
        FieldValidationException exceptionThrown = assertThrows(FieldValidationException.class, () -> builder.build());
        assertThat(exceptionThrown.getMessage(), containsString("grupperingsId"));
    }

    @Test
    void skalIkkeGodtaManglendeGrupperingsId() {
        BeskjedBuilder builder = getBuilderWithDefaultValues().withGrupperingsId(null);
        FieldValidationException exceptionThrown = assertThrows(FieldValidationException.class, () -> builder.build());
        assertThat(exceptionThrown.getMessage(), containsString("grupperingsId"));
    }

    @Test
    void skalIkkeGodtaForLavtSikkerhetsnivaa() {
        int invalidSikkerhetsnivaa = 2;
        BeskjedBuilder builder = getBuilderWithDefaultValues().withSikkerhetsnivaa(invalidSikkerhetsnivaa);
        FieldValidationException exceptionThrown = assertThrows(FieldValidationException.class, () -> builder.build());
        assertThat(exceptionThrown.getMessage(), containsString("Sikkerhetsnivaa"));
    }

    @Test
    void skalIkkeGodtaForLangLink() throws MalformedURLException {
        URL invalidLink = new URL("https://" + String.join("", Collections.nCopies(201, "n")));
        BeskjedBuilder builder = getBuilderWithDefaultValues().withLink(invalidLink);
        FieldValidationException exceptionThrown = assertThrows(FieldValidationException.class, () -> builder.build());
        assertThat(exceptionThrown.getMessage(), containsString("link"));
    }

    @Test
    void skalGodtaManglendeLink() {
        BeskjedBuilder builder = getBuilderWithDefaultValues().withLink(null);
        assertDoesNotThrow(() -> builder.build());
    }

    @Test
    void skalIkkeGodtaForLangTekst() {
        String tooLongTekst = String.join("", Collections.nCopies(501, "n"));
        BeskjedBuilder builder = getBuilderWithDefaultValues().withTekst(tooLongTekst);
        FieldValidationException exceptionThrown = assertThrows(FieldValidationException.class, () -> builder.build());
        assertThat(exceptionThrown.getMessage(), containsString("tekst"));
    }

    @Test
    void skalIkkeGodtaTomTekst() {
        BeskjedBuilder builder = getBuilderWithDefaultValues().withTekst("");
        FieldValidationException exceptionThrown = assertThrows(FieldValidationException.class, () -> builder.build());
        assertThat(exceptionThrown.getMessage(), containsString("tekst"));
    }

    @Test
    void skalIkkeGodtaManglendeEventtidspunkt() {
        BeskjedBuilder builder = getBuilderWithDefaultValues().withTidspunkt(null);
        FieldValidationException exceptionThrown = assertThrows(FieldValidationException.class, () -> builder.build());
        assertThat(exceptionThrown.getMessage(), containsString("tidspunkt"));
    }

    @Test
    void skalGodtaMangledeSynligFremTil() {
        BeskjedBuilder builder = getBuilderWithDefaultValues().withSynligFremTil(null);
        assertDoesNotThrow(() -> builder.build());
    }

    private BeskjedBuilder getBuilderWithDefaultValues() {
        return new BeskjedBuilder()
                .withFodselsnummer(expectedFodselsnr)
                .withGrupperingsId(expectedGrupperingsId)
                .withSikkerhetsnivaa(expectedSikkerhetsnivaa)
                .withLink(expectedLink)
                .withTekst(expectedTekst)
                .withTidspunkt(expectedTidspunkt)
                .withSynligFremTil(expectedSynligFremTil);
    }
}
