package no.nav.brukernotifikasjon.schemas.builders;

import de.huxhorn.sulky.ulid.ULID;
import no.nav.brukernotifikasjon.schemas.Nokkel;
import no.nav.brukernotifikasjon.schemas.builders.exception.FieldValidationException;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class NokkelBuilderTest {

    private String expectedSystembruker = "enSystemBruker";

    @Test
    void skalGodtaEventerMedGyldigeFeltverdier() {
        NokkelBuilder builder = getBuilderWithDefaultValues();
        Nokkel nokkel = builder.build();

        assertThat(nokkel.getSystembruker(), is(expectedSystembruker));
        assertThat(nokkel.getEventId().length(), is(26));
    }

    @Test
    void skalIkkeGodtaForLangSystembruker() {
        String tooLongSystembruker = String.join("", Collections.nCopies(101, "n"));
        NokkelBuilder builder = getBuilderWithDefaultValues().withSystembruker(tooLongSystembruker);
        FieldValidationException exceptionThrown = assertThrows(FieldValidationException.class, () -> builder.build());
        assertThat(exceptionThrown.getMessage(), containsString("systembruker"));
    }

    @Test
    void skalIkkeGodtaManglendeSystembruker() {
        NokkelBuilder builder = getBuilderWithDefaultValues().withSystembruker(null);
        FieldValidationException exceptionThrown = assertThrows(FieldValidationException.class, () -> builder.build());
        assertThat(exceptionThrown.getMessage(), containsString("systembruker"));
    }

    private NokkelBuilder getBuilderWithDefaultValues() {
        return new NokkelBuilder()
                .withSystembruker(expectedSystembruker)
                .withEventId(new ULID().nextValue());
    }

}
