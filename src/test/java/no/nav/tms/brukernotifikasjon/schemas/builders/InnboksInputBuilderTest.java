package no.nav.tms.brukernotifikasjon.schemas.builders;

import no.nav.tms.brukernotifikasjon.schemas.builders.domain.PreferertKanal;
import no.nav.tms.brukernotifikasjon.schemas.builders.exception.FieldValidationException;
import no.nav.tms.brukernotifikasjon.schemas.input.InnboksInput;
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
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class InnboksInputBuilderTest {

    private int expectedSikkerhetsnivaa;
    private URL expectedLink;
    private String expectedTekst;
    private LocalDateTime expectedTidspunkt;
    private Boolean expectedEksternVarsling;
    private List<PreferertKanal> expectedPrefererteKanaler;

    @BeforeAll
    void setUp() throws MalformedURLException {
        expectedSikkerhetsnivaa = 4;
        expectedLink = new URL("https://gyldig.url");
        expectedTekst = "Dette er informasjon du må lese";
        expectedTidspunkt = LocalDateTime.now(ZoneId.of("UTC"));
        expectedEksternVarsling = true;
        expectedPrefererteKanaler = asList(PreferertKanal.SMS);
    }

    @Test
    void skalGodtaEventerMedGyldigeFeltverdier() {
        InnboksInputBuilder builder = getBuilderWithDefaultValues();
        InnboksInput innboks = builder.build();

        assertThat(innboks.getSikkerhetsnivaa(), is(expectedSikkerhetsnivaa));
        assertThat(innboks.getLink(), is(expectedLink.toString()));
        assertThat(innboks.getTekst(), is(expectedTekst));
        long expectedTidspunktAsUtcLong = expectedTidspunkt.toInstant(ZoneOffset.UTC).toEpochMilli();
        assertThat(innboks.getTidspunkt(), is(expectedTidspunktAsUtcLong));
    }

    @Test
    void skalIkkeGodtaForLavtSikkerhetsnivaa() {
        int invalidSikkerhetsnivaa = 2;
        InnboksInputBuilder builder = getBuilderWithDefaultValues().withSikkerhetsnivaa(invalidSikkerhetsnivaa);
        FieldValidationException exceptionThrown = assertThrows(FieldValidationException.class, () -> builder.build());
        assertThat(exceptionThrown.getMessage(), containsString("Sikkerhetsnivaa"));
    }

    @Test
    void skalIkkeGodtaForLangLink() throws MalformedURLException {
        URL invalidLink = new URL("https://" + String.join("", Collections.nCopies(201, "n")));
        InnboksInputBuilder builder = getBuilderWithDefaultValues().withLink(invalidLink);
        FieldValidationException exceptionThrown = assertThrows(FieldValidationException.class, () -> builder.build());
        assertThat(exceptionThrown.getMessage(), containsString("link"));
    }

    @Test
    void skalGodtaManglendeLink() {
        InnboksInputBuilder builder = getBuilderWithDefaultValues().withLink(null);
        assertDoesNotThrow(() -> builder.build());
    }

    @Test
    void skalIkkeGodtaForLangTekst() {
        String tooLongTekst = String.join("", Collections.nCopies(501, "n"));
        InnboksInputBuilder builder = getBuilderWithDefaultValues().withTekst(tooLongTekst);
        FieldValidationException exceptionThrown = assertThrows(FieldValidationException.class, () -> builder.build());
        assertThat(exceptionThrown.getMessage(), containsString("tekst"));
    }

    @Test
    void skalIkkeGodtaTomTekst() {
        InnboksInputBuilder builder = getBuilderWithDefaultValues().withTekst("");
        FieldValidationException exceptionThrown = assertThrows(FieldValidationException.class, () -> builder.build());
        assertThat(exceptionThrown.getMessage(), containsString("tekst"));
    }

    @Test
    void skalIkkeGodtaManglendeEventtidspunkt() {
        InnboksInputBuilder builder = getBuilderWithDefaultValues().withTidspunkt(null);
        FieldValidationException exceptionThrown = assertThrows(FieldValidationException.class, () -> builder.build());
        assertThat(exceptionThrown.getMessage(), containsString("tidspunkt"));
    }

    @Test
    void skalIkkeGodtaPrefertKanalHvisIkkeEksternVarslingErSatt() {
        InnboksInputBuilder builder = getBuilderWithDefaultValues()
                .withEksternVarsling(false)
                .withPrefererteKanaler(PreferertKanal.SMS);
        FieldValidationException exceptionThrown = assertThrows(FieldValidationException.class, () -> builder.build());
        assertThat(exceptionThrown.getMessage(), containsString("prefererteKanaler"));
    }

    @Test
    void skalGodtaManglendePreferertKanal() {
        InnboksInputBuilder builder = new InnboksInputBuilder()
                .withSikkerhetsnivaa(expectedSikkerhetsnivaa)
                .withLink(expectedLink)
                .withTekst(expectedTekst)
                .withTidspunkt(expectedTidspunkt)
                .withEksternVarsling(true);
        assertDoesNotThrow(() -> builder.build());
    }

    @Test
    void skalIkkeGodtaForLangSmsVarslingstekst() {
        String tooLongSmsVarslingstekst = String.join("", Collections.nCopies(161, "1"));
        InnboksInputBuilder builder = getBuilderWithDefaultValues().withSmsVarslingstekst(tooLongSmsVarslingstekst);
        FieldValidationException exceptionThrown = assertThrows(FieldValidationException.class, () -> builder.build());
        assertThat(exceptionThrown.getMessage(), containsString("smsVarslingstekst"));
    }

    @Test
    void skalIkkeGodtaTomSmsVarslingstekst() {
        String tomSmsVarslingstekst = " ";
        InnboksInputBuilder builder = getBuilderWithDefaultValues().withSmsVarslingstekst(tomSmsVarslingstekst);
        FieldValidationException exceptionThrown = assertThrows(FieldValidationException.class, () -> builder.build());
        assertThat(exceptionThrown.getMessage(), containsString("smsVarslingstekst"));
    }

    @Test
    void skalIkkeGodtaSmsVarslingstekstHvisIkkeEksternVarslingErSatt() {
        InnboksInputBuilder builder = getBuilderWithDefaultValues()
                .withEksternVarsling(false)
                .withPrefererteKanaler(Collections.emptyList().toArray(new PreferertKanal[]{}))
                .withSmsVarslingstekst(String.join("", Collections.nCopies(100, "1")));
        FieldValidationException exceptionThrown = assertThrows(FieldValidationException.class, () -> builder.build());
        assertThat(exceptionThrown.getMessage(), containsString("smsVarslingstekst"));
    }

    @Test
    void skalIkkeGodtaTomEpostVarslingstekst() {
        String tomEpostVarslingstekst = " ";
        InnboksInputBuilder builder = getBuilderWithDefaultValues().withEpostVarslingstekst(tomEpostVarslingstekst);
        FieldValidationException exceptionThrown = assertThrows(FieldValidationException.class, () -> builder.build());
        assertThat(exceptionThrown.getMessage(), containsString("epostVarslingstekst"));
    }

    @Test
    void skalIkkeGodtaForLangEpostVarslingstekst() {
        String tooLongEpostVarslingstekst = String.join("", Collections.nCopies(4_001, "1"));
        InnboksInputBuilder builder = getBuilderWithDefaultValues().withEpostVarslingstekst(tooLongEpostVarslingstekst);
        FieldValidationException exceptionThrown = assertThrows(FieldValidationException.class, () -> builder.build());
        assertThat(exceptionThrown.getMessage(), containsString("epostVarslingstekst"));
    }

    @Test
    void skalIkkeGodtaEpostVarslingstekstHvisIkkeEksternVarslingErSatt() {
        InnboksInputBuilder builder = getBuilderWithDefaultValues()
                .withEksternVarsling(false)
                .withPrefererteKanaler(Collections.emptyList().toArray(new PreferertKanal[]{}))
                .withEpostVarslingstekst("<p>Hei!</p>");
        FieldValidationException exceptionThrown = assertThrows(FieldValidationException.class, () -> builder.build());
        assertThat(exceptionThrown.getMessage(), containsString("epostVarslingstekst"));
    }

    @Test
    void skalIkkeGodtaTomEpostVarslingstittel() {
        String tomEpostVarslingstittel = " ";
        InnboksInputBuilder builder = getBuilderWithDefaultValues().withEpostVarslingstittel(tomEpostVarslingstittel);
        FieldValidationException exceptionThrown = assertThrows(FieldValidationException.class, () -> builder.build());
        assertThat(exceptionThrown.getMessage(), containsString("epostVarslingstittel"));
    }

    @Test
    void skalIkkeGodtaForLangEpostVarslingstittel() {
        String tooLongEpostVarslingstittel = String.join("", Collections.nCopies(41, "1"));
        InnboksInputBuilder builder = getBuilderWithDefaultValues().withEpostVarslingstittel(tooLongEpostVarslingstittel);
        FieldValidationException exceptionThrown = assertThrows(FieldValidationException.class, () -> builder.build());
        assertThat(exceptionThrown.getMessage(), containsString("epostVarslingstittel"));
    }

    @Test
    void skalIkkeGodtaEpostVarslingstittelHvisIkkeEksternVarslingErSatt() {
        InnboksInputBuilder builder = getBuilderWithDefaultValues()
                .withEksternVarsling(false)
                .withPrefererteKanaler(Collections.emptyList().toArray(new PreferertKanal[]{}))
                .withEpostVarslingstittel("<p>Hei!</p>");
        FieldValidationException exceptionThrown = assertThrows(FieldValidationException.class, () -> builder.build());
        assertThat(exceptionThrown.getMessage(), containsString("epostVarslingstittel"));
    }

    private InnboksInputBuilder getBuilderWithDefaultValues() {
        return new InnboksInputBuilder()
                .withSikkerhetsnivaa(expectedSikkerhetsnivaa)
                .withLink(expectedLink)
                .withTekst(expectedTekst)
                .withTidspunkt(expectedTidspunkt)
                .withEksternVarsling(expectedEksternVarsling)
                .withPrefererteKanaler(expectedPrefererteKanaler.toArray(new PreferertKanal[1]));
    }
}
