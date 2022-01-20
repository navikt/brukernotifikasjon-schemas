package no.nav.brukernotifikasjon.schemas.builders.legacy;

import no.nav.brukernotifikasjon.schemas.Innboks;
import no.nav.brukernotifikasjon.schemas.builders.BeskjedInputBuilder;
import no.nav.brukernotifikasjon.schemas.builders.domain.PreferertKanal;
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
import java.util.List;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class InnboksBuilderTest {

    private String expectedFodselsnr;
    private String expectedGrupperingsId;
    private int expectedSikkerhetsnivaa;
    private URL expectedLink;
    private String expectedTekst;
    private LocalDateTime expectedTidspunkt;
    private LocalDateTime expectedSynligFremTil;
    private Boolean expectedEksternVarsling;
    private List<PreferertKanal> expectedPrefererteKanaler;

    @BeforeAll
    void setUp() throws MalformedURLException {
        expectedFodselsnr = "12345678901";
        expectedGrupperingsId = "3456789123456";
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
        InnboksBuilder builder = getBuilderWithDefaultValues();
        Innboks innboks = builder.build();

        assertThat(innboks.getFodselsnummer(), is(expectedFodselsnr));
        assertThat(innboks.getGrupperingsId(), is(expectedGrupperingsId));
        assertThat(innboks.getSikkerhetsnivaa(), is(expectedSikkerhetsnivaa));
        assertThat(innboks.getLink(), is(expectedLink.toString()));
        assertThat(innboks.getTekst(), is(expectedTekst));
        long expectedTidspunktAsUtcLong = expectedTidspunkt.toInstant(ZoneOffset.UTC).toEpochMilli();
        assertThat(innboks.getTidspunkt(), is(expectedTidspunktAsUtcLong));
        assertThat(innboks.getEksternVarsling(), is(expectedEksternVarsling));
        assertThat(innboks.getPrefererteKanaler(), is(expectedPrefererteKanaler.stream().map(preferertKanal -> preferertKanal.toString()).collect(toList())));
    }

    @Test
    void skalIkkeGodtaUgyldigFodselsnummer() {
        String tooLongFodselsnummer = String.join("", Collections.nCopies(11, "12"));
        InnboksBuilder builder = getBuilderWithDefaultValues().withFodselsnummer(tooLongFodselsnummer);
        FieldValidationException exceptionThrown = assertThrows(FieldValidationException.class, () -> builder.build());
        assertThat(exceptionThrown.getMessage(), containsString("fodselsnummer"));
    }

    @Test
    void skalIkkeGodtaManglendeFodselsnummer() {
        InnboksBuilder builder = getBuilderWithDefaultValues().withFodselsnummer(null);
        FieldValidationException exceptionThrown = assertThrows(FieldValidationException.class, () -> builder.build());
        assertThat(exceptionThrown.getMessage(), containsString("fodselsnummer"));
    }

    @Test
    void skalIkkeGodtaForLangGrupperingsId() {
        String tooLongGrupperingsId = String.join("", Collections.nCopies(101, "1"));
        InnboksBuilder builder = getBuilderWithDefaultValues().withGrupperingsId(tooLongGrupperingsId);
        FieldValidationException exceptionThrown = assertThrows(FieldValidationException.class, () -> builder.build());
        assertThat(exceptionThrown.getMessage(), containsString("grupperingsId"));
    }

    @Test
    void skalIkkeGodtaManglendeGrupperingsId() {
        InnboksBuilder builder = getBuilderWithDefaultValues().withGrupperingsId(null);
        FieldValidationException exceptionThrown = assertThrows(FieldValidationException.class, () -> builder.build());
        assertThat(exceptionThrown.getMessage(), containsString("grupperingsId"));
    }

    @Test
    void skalIkkeGodtaForLavtSikkerhetsnivaa() {
        int invalidSikkerhetsnivaa = 2;
        InnboksBuilder builder = getBuilderWithDefaultValues().withSikkerhetsnivaa(invalidSikkerhetsnivaa);
        FieldValidationException exceptionThrown = assertThrows(FieldValidationException.class, () -> builder.build());
        assertThat(exceptionThrown.getMessage(), containsString("Sikkerhetsnivaa"));
    }

    @Test
    void skalIkkeGodtaForLangLink() throws MalformedURLException {
        URL invalidLink = new URL("https://" + String.join("", Collections.nCopies(201, "n")));
        InnboksBuilder builder = getBuilderWithDefaultValues().withLink(invalidLink);
        FieldValidationException exceptionThrown = assertThrows(FieldValidationException.class, () -> builder.build());
        assertThat(exceptionThrown.getMessage(), containsString("link"));
    }

    @Test
    void skalGodtaManglendeLink() {
        InnboksBuilder builder = getBuilderWithDefaultValues().withLink(null);
        assertDoesNotThrow(() -> builder.build());
    }

    @Test
    void skalIkkeGodtaForLangTekst() {
        String tooLongTekst = String.join("", Collections.nCopies(501, "n"));
        InnboksBuilder builder = getBuilderWithDefaultValues().withTekst(tooLongTekst);
        FieldValidationException exceptionThrown = assertThrows(FieldValidationException.class, () -> builder.build());
        assertThat(exceptionThrown.getMessage(), containsString("tekst"));
    }

    @Test
    void skalIkkeGodtaTomTekst() {
        InnboksBuilder builder = getBuilderWithDefaultValues().withTekst("");
        FieldValidationException exceptionThrown = assertThrows(FieldValidationException.class, () -> builder.build());
        assertThat(exceptionThrown.getMessage(), containsString("tekst"));
    }

    @Test
    void skalIkkeGodtaManglendeEventtidspunkt() {
        InnboksBuilder builder = getBuilderWithDefaultValues().withTidspunkt(null);
        FieldValidationException exceptionThrown = assertThrows(FieldValidationException.class, () -> builder.build());
        assertThat(exceptionThrown.getMessage(), containsString("tidspunkt"));
    }

    @Test
    void skalIkkeGodtaPrefertKanalHvisIkkeEksternVarslingErSatt() {
        InnboksBuilder builder = getBuilderWithDefaultValues()
                .withEksternVarsling(false)
                .withPrefererteKanaler(PreferertKanal.SMS);
        FieldValidationException exceptionThrown = assertThrows(FieldValidationException.class, () -> builder.build());
        assertThat(exceptionThrown.getMessage(), containsString("prefererteKanaler"));
    }

    @Test
    void skalGodtaNullSomPreferertKanal() {
        InnboksBuilder builder = getBuilderWithDefaultValues()
                .withEksternVarsling(true)
                .withPrefererteKanaler(null);
        assertDoesNotThrow(() -> builder.build());
    }

    @Test
    void skalGodtaManglendePreferertKanal() {
        InnboksBuilder builder = new InnboksBuilder()
                .withFodselsnummer(expectedFodselsnr)
                .withGrupperingsId(expectedGrupperingsId)
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
        InnboksBuilder builder = getBuilderWithDefaultValues().withSmsVarslingstekst(tooLongSmsVarslingstekst);
        FieldValidationException exceptionThrown = assertThrows(FieldValidationException.class, () -> builder.build());
        assertThat(exceptionThrown.getMessage(), containsString("smsVarslingstekst"));
    }

    @Test
    void skalIkkeGodtaTomSmsVarslingstekst() {
        String tomSmsVarslingstekst = " ";
        InnboksBuilder builder = getBuilderWithDefaultValues().withSmsVarslingstekst(tomSmsVarslingstekst);
        FieldValidationException exceptionThrown = assertThrows(FieldValidationException.class, () -> builder.build());
        assertThat(exceptionThrown.getMessage(), containsString("smsVarslingstekst"));
    }

    @Test
    void skalIkkeGodtaSmsVarslingstekstHvisIkkeEksternVarslingErSatt() {
        InnboksBuilder builder = getBuilderWithDefaultValues()
                .withEksternVarsling(false)
                .withPrefererteKanaler(Collections.emptyList().toArray(new PreferertKanal[]{}))
                .withSmsVarslingstekst(String.join("", Collections.nCopies(100, "1")));
        FieldValidationException exceptionThrown = assertThrows(FieldValidationException.class, () -> builder.build());
        assertThat(exceptionThrown.getMessage(), containsString("smsVarslingstekst"));
    }

    @Test
    void skalIkkeGodtaTomEpostVarslingstekst() {
        String tomEpostVarslingstekst = " ";
        InnboksBuilder builder = getBuilderWithDefaultValues().withEpostVarslingstekst(tomEpostVarslingstekst);
        FieldValidationException exceptionThrown = assertThrows(FieldValidationException.class, () -> builder.build());
        assertThat(exceptionThrown.getMessage(), containsString("epostVarslingstekst"));
    }

    @Test
    void skalIkkeGodtaForLangEpostVarslingstekst() {
        String tooLongEpostVarslingstekst = String.join("", Collections.nCopies(10_001, "1"));
        InnboksBuilder builder = getBuilderWithDefaultValues().withEpostVarslingstekst(tooLongEpostVarslingstekst);
        FieldValidationException exceptionThrown = assertThrows(FieldValidationException.class, () -> builder.build());
        assertThat(exceptionThrown.getMessage(), containsString("epostVarslingstekst"));
    }

    @Test
    void skalIkkeGodtaEpostVarslingstekstHvisIkkeEksternVarslingErSatt() {
        InnboksBuilder builder = getBuilderWithDefaultValues()
                .withEksternVarsling(false)
                .withPrefererteKanaler(Collections.emptyList().toArray(new PreferertKanal[]{}))
                .withEpostVarslingstekst("<p>Hei!</p>");
        FieldValidationException exceptionThrown = assertThrows(FieldValidationException.class, () -> builder.build());
        assertThat(exceptionThrown.getMessage(), containsString("epostVarslingstekst"));
    }

    @Test
    void skalIkkeGodtaTomEpostVarslingstittel() {
        String tomEpostVarslingstittel = " ";
        InnboksBuilder builder = getBuilderWithDefaultValues().withEpostVarslingstittel(tomEpostVarslingstittel);
        FieldValidationException exceptionThrown = assertThrows(FieldValidationException.class, () -> builder.build());
        assertThat(exceptionThrown.getMessage(), containsString("epostVarslingstittel"));
    }

    @Test
    void skalIkkeGodtaForLangEpostVarslingstittel() {
        String tooLongEpostVarslingstittel = String.join("", Collections.nCopies(201, "1"));
        InnboksBuilder builder = getBuilderWithDefaultValues().withEpostVarslingstittel(tooLongEpostVarslingstittel);
        FieldValidationException exceptionThrown = assertThrows(FieldValidationException.class, () -> builder.build());
        assertThat(exceptionThrown.getMessage(), containsString("epostVarslingstittel"));
    }

    @Test
    void skalIkkeGodtaEpostVarslingstittelHvisIkkeEksternVarslingErSatt() {
        InnboksBuilder builder = getBuilderWithDefaultValues()
                .withEksternVarsling(false)
                .withPrefererteKanaler(Collections.emptyList().toArray(new PreferertKanal[]{}))
                .withEpostVarslingstittel("<p>Hei!</p>");
        FieldValidationException exceptionThrown = assertThrows(FieldValidationException.class, () -> builder.build());
        assertThat(exceptionThrown.getMessage(), containsString("epostVarslingstittel"));
    }

    private InnboksBuilder getBuilderWithDefaultValues() {
        return new InnboksBuilder()
                .withFodselsnummer(expectedFodselsnr)
                .withGrupperingsId(expectedGrupperingsId)
                .withSikkerhetsnivaa(expectedSikkerhetsnivaa)
                .withLink(expectedLink)
                .withTekst(expectedTekst)
                .withTidspunkt(expectedTidspunkt)
                .withEksternVarsling(expectedEksternVarsling)
                .withPrefererteKanaler(expectedPrefererteKanaler.toArray(new PreferertKanal[1]));
    }
}
