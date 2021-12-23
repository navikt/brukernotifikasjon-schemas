package no.nav.brukernotifikasjon.schemas.builders;

import no.nav.brukernotifikasjon.schemas.builders.domain.PreferertKanal;
import no.nav.brukernotifikasjon.schemas.builders.exception.FieldValidationException;
import no.nav.brukernotifikasjon.schemas.input.OppgaveInput;
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
class OppgaveInputBuilderTest {

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
        expectedTekst = "Du må sende nytt meldekort";
        expectedTidspunkt = LocalDateTime.now();
        expectedSynligFremTil = expectedTidspunkt.plusDays(2);
        expectedEksternVarsling = true;
        expectedPrefererteKanaler = asList(PreferertKanal.SMS);
    }

    @Test
    void skalGodtaEventerMedGyldigeFeltverdier() {
        OppgaveInputBuilder builder = getBuilderWithDefaultValues();
        OppgaveInput oppgave = builder.build();

        assertThat(oppgave.getSikkerhetsnivaa(), is(expectedSikkerhetsnivaa));
        assertThat(oppgave.getLink(), is(expectedLink.toString()));
        assertThat(oppgave.getTekst(), is(expectedTekst));
        long expectedTidspunktAsUtcLong = expectedTidspunkt.toInstant(ZoneOffset.UTC).toEpochMilli();
        assertThat(oppgave.getTidspunkt(), is(expectedTidspunktAsUtcLong));
        long expectedSynligFremTilAsUtcLong = expectedSynligFremTil.toInstant(ZoneOffset.UTC).toEpochMilli();
        assertThat(oppgave.getSynligFremTil(), is(expectedSynligFremTilAsUtcLong));
        assertThat(oppgave.getEksternVarsling(), is(expectedEksternVarsling));
        assertThat(oppgave.getPrefererteKanaler(), is(expectedPrefererteKanaler.stream().map(preferertKanal -> preferertKanal.toString()).collect(toList())));
    }

    @Test
    void skalIkkeGodtaForLavtSikkerhetsnivaa() {
        int invalidSikkerhetsnivaa = 2;
        OppgaveInputBuilder builder = getBuilderWithDefaultValues().withSikkerhetsnivaa(invalidSikkerhetsnivaa);
        FieldValidationException exceptionThrown = assertThrows(FieldValidationException.class, () -> builder.build());
        assertThat(exceptionThrown.getMessage(), containsString("Sikkerhetsnivaa"));
    }

    @Test
    void skalIkkeGodtaForLangLink() throws MalformedURLException {
        URL invalidLink = new URL("https://" + String.join("", Collections.nCopies(201, "n")));
        OppgaveInputBuilder builder = getBuilderWithDefaultValues().withLink(invalidLink);
        FieldValidationException exceptionThrown = assertThrows(FieldValidationException.class, () -> builder.build());
        assertThat(exceptionThrown.getMessage(), containsString("link"));
    }

    @Test
    void skalIkkeGodtaManglendeLink() {
        OppgaveInputBuilder builder = getBuilderWithDefaultValues().withLink(null);
        FieldValidationException exceptionThrown = assertThrows(FieldValidationException.class, () -> builder.build());
        assertThat(exceptionThrown.getMessage(), containsString("link"));
    }

    @Test
    void skalIkkeGodtaForLangTekst() {
        String tooLongTekst = String.join("", Collections.nCopies(501, "n"));
        OppgaveInputBuilder builder = getBuilderWithDefaultValues().withTekst(tooLongTekst);
        FieldValidationException exceptionThrown = assertThrows(FieldValidationException.class, () -> builder.build());
        assertThat(exceptionThrown.getMessage(), containsString("tekst"));
    }

    @Test
    void skalIkkeGodtaTomTekst() {
        OppgaveInputBuilder builder = getBuilderWithDefaultValues().withTekst("");
        FieldValidationException exceptionThrown = assertThrows(FieldValidationException.class, () -> builder.build());
        assertThat(exceptionThrown.getMessage(), containsString("tekst"));
    }

    @Test
    void skalIkkeGodtaManglendeEventtidspunkt() {
        OppgaveInputBuilder builder = getBuilderWithDefaultValues().withTidspunkt(null);
        FieldValidationException exceptionThrown = assertThrows(FieldValidationException.class, () -> builder.build());
        assertThat(exceptionThrown.getMessage(), containsString("tidspunkt"));
    }

    @Test
    void skalGodtaMangledeSynligFremTil() {
        OppgaveInputBuilder builder = getBuilderWithDefaultValues().withSynligFremTil(null);
        assertDoesNotThrow(() -> builder.build());
    }

    @Test
    void skalIkkeGodtaPrefertKanalHvisIkkeEksternVarslingErSatt() {
        OppgaveInputBuilder builder = getBuilderWithDefaultValues()
                .withEksternVarsling(false)
                .withPrefererteKanaler(PreferertKanal.SMS);
        FieldValidationException exceptionThrown = assertThrows(FieldValidationException.class, () -> builder.build());
        assertThat(exceptionThrown.getMessage(), containsString("prefererteKanaler"));
    }

    @Test
    void skalGodtaManglendePreferertKanal() {
        OppgaveInputBuilder builder = getBuilderWithDefaultValues()
                .withEksternVarsling(true)
                .withPrefererteKanaler(null);
        assertDoesNotThrow(() -> builder.build());
    }

    @Test
    void skalIkkeGodtaForLangSmsVarslingstekst() {
        String tooLongSmsVarslingstekst = String.join("", Collections.nCopies(161, "1"));
        OppgaveInputBuilder builder = getBuilderWithDefaultValues().withSmsVarslingstekst(tooLongSmsVarslingstekst);
        FieldValidationException exceptionThrown = assertThrows(FieldValidationException.class, () -> builder.build());
        assertThat(exceptionThrown.getMessage(), containsString("smsVarslingstekst"));
    }

    @Test
    void skalIkkeGodtaTomEpostVarslingstekst() {
        String tomEpostVarslingstekst = " ";
        OppgaveInputBuilder builder = getBuilderWithDefaultValues().withEpostVarslingstekst(tomEpostVarslingstekst);
        FieldValidationException exceptionThrown = assertThrows(FieldValidationException.class, () -> builder.build());
        assertThat(exceptionThrown.getMessage(), containsString("epostVarslingstekst"));
    }

    private OppgaveInputBuilder getBuilderWithDefaultValues() {
        return new OppgaveInputBuilder()
                .withSikkerhetsnivaa(expectedSikkerhetsnivaa)
                .withLink(expectedLink)
                .withTekst(expectedTekst)
                .withTidspunkt(expectedTidspunkt)
                .withSynligFremTil(expectedSynligFremTil)
                .withEksternVarsling(expectedEksternVarsling)
                .withPrefererteKanaler(expectedPrefererteKanaler.toArray(new PreferertKanal[1]));
    }
}
