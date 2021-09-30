package no.nav.brukernotifikasjon.schemas.builders.legacy;

import no.nav.brukernotifikasjon.schemas.Nokkel;
import no.nav.brukernotifikasjon.schemas.builders.exception.FieldValidationException;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.UUID;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class NokkelLegacyBuilderTest {

    private String expectedSystembruker = "enSystemBruker";
    private String expectedEventID = UUID.randomUUID().toString();

    @Test
    void skalGodtaEventerMedGyldigeFeltverdier() {
        NokkelLegacyBuilder builder = getBuilderWithDefaultValues();
        Nokkel nokkel = builder.build();

        assertThat(nokkel.getSystembruker(), is(expectedSystembruker));
        assertThat(nokkel.getEventId(), is(expectedEventID));
    }

    @Test
    void skalIkkeGodtaForLangSystembruker() {
        String tooLongSystembruker = String.join("", Collections.nCopies(101, "n"));
        NokkelLegacyBuilder builder = getBuilderWithDefaultValues().withSystembruker(tooLongSystembruker);
        FieldValidationException exceptionThrown = assertThrows(FieldValidationException.class, () -> builder.build());
        assertThat(exceptionThrown.getMessage(), containsString("systembruker"));
    }

    @Test
    void skalIkkeGodtaManglendeSystembruker() {
        NokkelLegacyBuilder builder = getBuilderWithDefaultValues().withSystembruker(null);
        FieldValidationException exceptionThrown = assertThrows(FieldValidationException.class, () -> builder.build());
        assertThat(exceptionThrown.getMessage(), containsString("systembruker"));
    }

    private NokkelLegacyBuilder getBuilderWithDefaultValues() {
        return new NokkelLegacyBuilder()
                .withSystembruker(expectedSystembruker)
                .withEventId(expectedEventID);
    }

}
