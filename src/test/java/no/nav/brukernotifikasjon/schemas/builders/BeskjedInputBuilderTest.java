package no.nav.brukernotifikasjon.schemas.builders;

import no.nav.brukernotifikasjon.schemas.builders.domain.PreferertKanal;
import no.nav.brukernotifikasjon.schemas.builders.exception.FieldValidationException;
import no.nav.brukernotifikasjon.schemas.input.BeskjedInput;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Collections;
import java.util.List;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BeskjedInputBuilderTest {

    private int expectedSikkerhetsnivaa;
    private URL expectedLink;
    private String expectedTekst;
    private LocalDateTime expectedTidspunkt;
    private LocalDateTime expectedSynligFremTil;
    private Boolean expectedEksternVarsling;
    private List<PreferertKanal> expectedPrefererteKanaler;

    @BeforeAll
    void setUp() throws MalformedURLException {
        expectedSikkerhetsnivaa = 4;
        expectedLink = new URL("https://gyldig.url");
        expectedTekst = "Dette er informasjon du må lese";
        expectedTidspunkt = LocalDateTime.now(ZoneId.of("UTC"));
        expectedSynligFremTil = expectedTidspunkt.plusDays(2);
        expectedEksternVarsling = true;
        expectedPrefererteKanaler = asList(PreferertKanal.SMS);
    }

    @Test
    void skalGodtaEventerMedGyldigeFeltverdier() {
        BeskjedInputBuilder builder = getBuilderWithDefaultValues();
        BeskjedInput beskjed = builder.build();

        assertThat(beskjed.getSikkerhetsnivaa(), is(expectedSikkerhetsnivaa));
        assertThat(beskjed.getLink(), is(expectedLink.toString()));
        assertThat(beskjed.getTekst(), is(expectedTekst));
        long expectedTidspunktAsUtcLong = expectedTidspunkt.toInstant(ZoneOffset.UTC).toEpochMilli();
        assertThat(beskjed.getTidspunkt(), is(expectedTidspunktAsUtcLong));
        long expectedSynligFremTilAsUtcLong = expectedSynligFremTil.toInstant(ZoneOffset.UTC).toEpochMilli();
        assertThat(beskjed.getSynligFremTil(), is(expectedSynligFremTilAsUtcLong));
        assertThat(beskjed.getEksternVarsling(), is(expectedEksternVarsling));
        assertThat(beskjed.getPrefererteKanaler(), is(expectedPrefererteKanaler.stream().map(preferertKanal -> preferertKanal.toString()).collect(toList())));
    }

    @Test
    void skalIkkeGodtaForLavtSikkerhetsnivaa() {
        int invalidSikkerhetsnivaa = 2;
        BeskjedInputBuilder builder = getBuilderWithDefaultValues().withSikkerhetsnivaa(invalidSikkerhetsnivaa);
        FieldValidationException exceptionThrown = assertThrows(FieldValidationException.class, () -> builder.build());
        assertThat(exceptionThrown.getMessage(), containsString("Sikkerhetsnivaa"));
    }

    @Test
    void skalIkkeGodtaForLangLink() throws MalformedURLException {
        URL invalidLink = new URL("https://" + String.join("", Collections.nCopies(201, "n")));
        BeskjedInputBuilder builder = getBuilderWithDefaultValues().withLink(invalidLink);
        FieldValidationException exceptionThrown = assertThrows(FieldValidationException.class, () -> builder.build());
        assertThat(exceptionThrown.getMessage(), containsString("link"));
    }

    @Test
    void skalGodtaManglendeLink() {
        BeskjedInputBuilder builder = getBuilderWithDefaultValues().withLink(null);
        assertDoesNotThrow(() -> builder.build());
    }

    @Test
    void skalIkkeGodtaForLangTekst() {
        String tooLongTekst = String.join("", Collections.nCopies(501, "n"));
        BeskjedInputBuilder builder = getBuilderWithDefaultValues().withTekst(tooLongTekst);
        FieldValidationException exceptionThrown = assertThrows(FieldValidationException.class, () -> builder.build());
        assertThat(exceptionThrown.getMessage(), containsString("tekst"));
    }

    @Test
    void skalIkkeGodtaTomTekst() {
        BeskjedInputBuilder builder = getBuilderWithDefaultValues().withTekst("");
        FieldValidationException exceptionThrown = assertThrows(FieldValidationException.class, () -> builder.build());
        assertThat(exceptionThrown.getMessage(), containsString("tekst"));
    }

    @Test
    void skalIkkeGodtaManglendeEventtidspunkt() {
        BeskjedInputBuilder builder = getBuilderWithDefaultValues().withTidspunkt(null);
        FieldValidationException exceptionThrown = assertThrows(FieldValidationException.class, () -> builder.build());
        assertThat(exceptionThrown.getMessage(), containsString("tidspunkt"));
    }

    @Test
    void skalGodtaMangledeSynligFremTil() {
        BeskjedInputBuilder builder = getBuilderWithDefaultValues().withSynligFremTil(null);
        assertDoesNotThrow(() -> builder.build());
    }

    @Test
    void skalIkkeGodtaPrefertKanalHvisIkkeEksternVarslingErSatt() {
        BeskjedInputBuilder builder = getBuilderWithDefaultValues()
                .withEksternVarsling(false)
                .withPrefererteKanaler(PreferertKanal.SMS);
        FieldValidationException exceptionThrown = assertThrows(FieldValidationException.class, () -> builder.build());
        assertThat(exceptionThrown.getMessage(), containsString("prefererteKanaler"));
    }

    @Test
    void skalGodtaNullSomPreferertKanal() {
        BeskjedInputBuilder builder = getBuilderWithDefaultValues()
                .withEksternVarsling(true)
                .withPrefererteKanaler(null);
        assertDoesNotThrow(() -> builder.build());
    }

    @Test
    void skalGodtaManglendePreferertKanal() {
        BeskjedInputBuilder builder = new BeskjedInputBuilder()
                .withSikkerhetsnivaa(expectedSikkerhetsnivaa)
                .withLink(expectedLink)
                .withTekst(expectedTekst)
                .withTidspunkt(expectedTidspunkt)
                .withSynligFremTil(expectedSynligFremTil)
                .withEksternVarsling(true);
        assertDoesNotThrow(() -> builder.build());
    }

    @Test
    void skalIkkeGodtaForLangSmsVarslingstekst() {
        String tooLongSmsVarslingstekst = String.join("", Collections.nCopies(161, "1"));
        BeskjedInputBuilder builder = getBuilderWithDefaultValues().withSmsVarslingstekst(tooLongSmsVarslingstekst);
        FieldValidationException exceptionThrown = assertThrows(FieldValidationException.class, () -> builder.build());
        assertThat(exceptionThrown.getMessage(), containsString("smsVarslingstekst"));
    }

    @Test
    void skalIkkeGodtaTomSmsVarslingstekst() {
        String tomSmsVarslingstekst = " ";
        BeskjedInputBuilder builder = getBuilderWithDefaultValues().withSmsVarslingstekst(tomSmsVarslingstekst);
        FieldValidationException exceptionThrown = assertThrows(FieldValidationException.class, () -> builder.build());
        assertThat(exceptionThrown.getMessage(), containsString("smsVarslingstekst"));
    }

    @Test
    void skalIkkeGodtaSmsVarslingstekstHvisIkkeEksternVarslingErSatt() {
        BeskjedInputBuilder builder = getBuilderWithDefaultValues()
                .withEksternVarsling(false)
                .withPrefererteKanaler(Collections.emptyList().toArray(new PreferertKanal[]{}))
                .withSmsVarslingstekst(String.join("", Collections.nCopies(100, "1")));
        FieldValidationException exceptionThrown = assertThrows(FieldValidationException.class, () -> builder.build());
        assertThat(exceptionThrown.getMessage(), containsString("smsVarslingstekst"));
    }

    @Test
    void skalIkkeGodtaTomEpostVarslingstekst() {
        String tomEpostVarslingstekst = " ";
        BeskjedInputBuilder builder = getBuilderWithDefaultValues().withEpostVarslingstekst(tomEpostVarslingstekst);
        FieldValidationException exceptionThrown = assertThrows(FieldValidationException.class, () -> builder.build());
        assertThat(exceptionThrown.getMessage(), containsString("epostVarslingstekst"));
    }

    @Test
    void skalIkkeGodtaForLangEpostVarslingstekst() {
        String tooLongEpostVarslingstekst = String.join("", Collections.nCopies(4_001, "1"));
        BeskjedInputBuilder builder = getBuilderWithDefaultValues().withEpostVarslingstekst(tooLongEpostVarslingstekst);
        FieldValidationException exceptionThrown = assertThrows(FieldValidationException.class, () -> builder.build());
        assertThat(exceptionThrown.getMessage(), containsString("epostVarslingstekst"));
    }

    @Test
    void skalIkkeGodtaEpostVarslingstekstHvisIkkeEksternVarslingErSatt() {
        BeskjedInputBuilder builder = getBuilderWithDefaultValues()
                .withEksternVarsling(false)
                .withPrefererteKanaler(Collections.emptyList().toArray(new PreferertKanal[]{}))
                .withEpostVarslingstekst("<p>Hei!</p>");
        FieldValidationException exceptionThrown = assertThrows(FieldValidationException.class, () -> builder.build());
        assertThat(exceptionThrown.getMessage(), containsString("epostVarslingstekst"));
    }

    @Test
    void skalIkkeGodtaTomEpostVarslingstittel() {
        String tomEpostVarslingstittel = " ";
        BeskjedInputBuilder builder = getBuilderWithDefaultValues().withEpostVarslingstittel(tomEpostVarslingstittel);
        FieldValidationException exceptionThrown = assertThrows(FieldValidationException.class, () -> builder.build());
        assertThat(exceptionThrown.getMessage(), containsString("epostVarslingstittel"));
    }

    @Test
    void skalIkkeGodtaForLangEpostVarslingstittel() {
        String tooLongEpostVarslingstittel = String.join("", Collections.nCopies(41, "1"));
        BeskjedInputBuilder builder = getBuilderWithDefaultValues().withEpostVarslingstittel(tooLongEpostVarslingstittel);
        FieldValidationException exceptionThrown = assertThrows(FieldValidationException.class, () -> builder.build());
        assertThat(exceptionThrown.getMessage(), containsString("epostVarslingstittel"));
    }

    @Test
    void skalIkkeGodtaEpostVarslingstittelHvisIkkeEksternVarslingErSatt() {
        BeskjedInputBuilder builder = getBuilderWithDefaultValues()
                .withEksternVarsling(false)
                .withPrefererteKanaler(Collections.emptyList().toArray(new PreferertKanal[]{}))
                .withEpostVarslingstittel("<p>Hei!</p>");
        FieldValidationException exceptionThrown = assertThrows(FieldValidationException.class, () -> builder.build());
        assertThat(exceptionThrown.getMessage(), containsString("epostVarslingstittel"));
    }

    private BeskjedInputBuilder getBuilderWithDefaultValues() {
        return new BeskjedInputBuilder()
                .withSikkerhetsnivaa(expectedSikkerhetsnivaa)
                .withLink(expectedLink)
                .withTekst(expectedTekst)
                .withTidspunkt(expectedTidspunkt)
                .withSynligFremTil(expectedSynligFremTil)
                .withEksternVarsling(expectedEksternVarsling)
                .withPrefererteKanaler(expectedPrefererteKanaler.toArray(new PreferertKanal[1]));
    }
}
