package no.nav.brukernotifikasjon.schemas.builders.legacy;

import no.nav.brukernotifikasjon.schemas.builders.domain.PreferertKanal;
import no.nav.brukernotifikasjon.schemas.builders.exception.FieldValidationException;
import no.nav.brukernotifikasjon.schemas.legacy.OppgaveLegacy;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDateTime;
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
class OppgaveLegacyBuilderTest {

    private String expectedFodselsnr;
    private String expectedGrupperingsId;
    private int expectedSikkerhetsnivaa;
    private URL expectedLink;
    private String expectedTekst;
    private LocalDateTime expectedTidspunkt;
    private Boolean expectedEksternVarsling;
    private List<PreferertKanal> expectedPrefererteKanaler;

    @BeforeAll
    void setUp() throws MalformedURLException {
        expectedFodselsnr = "12345678901";
        expectedGrupperingsId = "3456789123456";
        expectedSikkerhetsnivaa = 4;
        expectedLink = new URL("https://gyldig.url");
        expectedTekst = "Du må sende nytt meldekort";
        expectedTidspunkt = LocalDateTime.now();
        expectedEksternVarsling = true;
        expectedPrefererteKanaler = asList(PreferertKanal.SMS);
    }

    @Test
    void skalGodtaEventerMedGyldigeFeltverdier() {
        OppgaveLegacyBuilder builder = getBuilderWithDefaultValues();
        OppgaveLegacy oppgaveLegacy = builder.build();

        assertThat(oppgaveLegacy.getFodselsnummer(), is(expectedFodselsnr));
        assertThat(oppgaveLegacy.getGrupperingsId(), is(expectedGrupperingsId));
        assertThat(oppgaveLegacy.getSikkerhetsnivaa(), is(expectedSikkerhetsnivaa));
        assertThat(oppgaveLegacy.getLink(), is(expectedLink.toString()));
        assertThat(oppgaveLegacy.getTekst(), is(expectedTekst));
        long expectedTidspunktAsUtcLong = expectedTidspunkt.toInstant(ZoneOffset.UTC).toEpochMilli();
        assertThat(oppgaveLegacy.getTidspunkt(), is(expectedTidspunktAsUtcLong));
        assertThat(oppgaveLegacy.getEksternVarsling(), is(expectedEksternVarsling));
        assertThat(oppgaveLegacy.getPrefererteKanaler(), is(expectedPrefererteKanaler.stream().map(preferertKanal -> preferertKanal.toString()).collect(toList())));
    }

    @Test
    void skalIkkeGodtaUgyldigFodselsnummer() {
        String tooLongFodselsnummer = String.join("", Collections.nCopies(11, "12"));
        OppgaveLegacyBuilder builder = getBuilderWithDefaultValues().withFodselsnummer(tooLongFodselsnummer);
        FieldValidationException exceptionThrown = assertThrows(FieldValidationException.class, () -> builder.build());
        assertThat(exceptionThrown.getMessage(), containsString("fodselsnummer"));
    }

    @Test
    void skalIkkeGodtaManglendeFodselsnummer() {
        OppgaveLegacyBuilder builder = getBuilderWithDefaultValues().withFodselsnummer(null);
        FieldValidationException exceptionThrown = assertThrows(FieldValidationException.class, () -> builder.build());
        assertThat(exceptionThrown.getMessage(), containsString("fodselsnummer"));
    }

    @Test
    void skalIkkeGodtaForLangGrupperingsId() {
        String tooLongGrupperingsId = String.join("", Collections.nCopies(101, "1"));
        OppgaveLegacyBuilder builder = getBuilderWithDefaultValues().withGrupperingsId(tooLongGrupperingsId);
        FieldValidationException exceptionThrown = assertThrows(FieldValidationException.class, () -> builder.build());
        assertThat(exceptionThrown.getMessage(), containsString("grupperingsId"));
    }

    @Test
    void skalIkkeGodtaManglendeGrupperingsId() {
        OppgaveLegacyBuilder builder = getBuilderWithDefaultValues().withGrupperingsId(null);
        FieldValidationException exceptionThrown = assertThrows(FieldValidationException.class, () -> builder.build());
        assertThat(exceptionThrown.getMessage(), containsString("grupperingsId"));
    }

    @Test
    void skalIkkeGodtaForLavtSikkerhetsnivaa() {
        int invalidSikkerhetsnivaa = 2;
        OppgaveLegacyBuilder builder = getBuilderWithDefaultValues().withSikkerhetsnivaa(invalidSikkerhetsnivaa);
        FieldValidationException exceptionThrown = assertThrows(FieldValidationException.class, () -> builder.build());
        assertThat(exceptionThrown.getMessage(), containsString("Sikkerhetsnivaa"));
    }

    @Test
    void skalIkkeGodtaForLangLink() throws MalformedURLException {
        URL invalidLink = new URL("https://" + String.join("", Collections.nCopies(201, "n")));
        OppgaveLegacyBuilder builder = getBuilderWithDefaultValues().withLink(invalidLink);
        FieldValidationException exceptionThrown = assertThrows(FieldValidationException.class, () -> builder.build());
        assertThat(exceptionThrown.getMessage(), containsString("link"));
    }

    @Test
    void skalIkkeGodtaManglendeLink() {
        OppgaveLegacyBuilder builder = getBuilderWithDefaultValues().withLink(null);
        FieldValidationException exceptionThrown = assertThrows(FieldValidationException.class, () -> builder.build());
        assertThat(exceptionThrown.getMessage(), containsString("link"));
    }

    @Test
    void skalIkkeGodtaForLangTekst() {
        String tooLongTekst = String.join("", Collections.nCopies(501, "n"));
        OppgaveLegacyBuilder builder = getBuilderWithDefaultValues().withTekst(tooLongTekst);
        FieldValidationException exceptionThrown = assertThrows(FieldValidationException.class, () -> builder.build());
        assertThat(exceptionThrown.getMessage(), containsString("tekst"));
    }

    @Test
    void skalIkkeGodtaTomTekst() {
        OppgaveLegacyBuilder builder = getBuilderWithDefaultValues().withTekst("");
        FieldValidationException exceptionThrown = assertThrows(FieldValidationException.class, () -> builder.build());
        assertThat(exceptionThrown.getMessage(), containsString("tekst"));
    }

    @Test
    void skalIkkeGodtaManglendeEventtidspunkt() {
        OppgaveLegacyBuilder builder = getBuilderWithDefaultValues().withTidspunkt(null);
        FieldValidationException exceptionThrown = assertThrows(FieldValidationException.class, () -> builder.build());
        assertThat(exceptionThrown.getMessage(), containsString("tidspunkt"));
    }

    @Test
    void skalIkkeGodtaPrefertKanalHvisIkkeEksternVarslingErSatt() {
        OppgaveLegacyBuilder builder = getBuilderWithDefaultValues()
                .withEksternVarsling(false)
                .withPrefererteKanaler(PreferertKanal.SMS);
        FieldValidationException exceptionThrown = assertThrows(FieldValidationException.class, () -> builder.build());
        assertThat(exceptionThrown.getMessage(), containsString("prefererteKanaler"));
    }

    @Test
    void skalGodtaManglendePreferertKanal() {
        OppgaveLegacyBuilder builder = getBuilderWithDefaultValues()
                .withEksternVarsling(true)
                .withPrefererteKanaler(null);
        assertDoesNotThrow(() -> builder.build());
    }

    private OppgaveLegacyBuilder getBuilderWithDefaultValues() {
        return new OppgaveLegacyBuilder()
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