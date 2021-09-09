package no.nav.brukernotifikasjon.schemas.builders.input;

import no.nav.brukernotifikasjon.schemas.builders.exception.FieldValidationException;
import no.nav.brukernotifikasjon.schemas.input.DoneInput;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Collections;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class DoneInputBuilderTest {

    private String expectedGrupperingsId = "3456789123456";
    private LocalDateTime expectedTidspunkt = LocalDateTime.now(ZoneId.of("UTC"));

    @Test
    void skalGodtaEventerMedGyldigeFeltverdier() {
        DoneInputBuilder builder = getBuilderWithDefaultValues();
        DoneInput doneInput = builder.build();

        assertThat(doneInput.getGrupperingsId(), is(expectedGrupperingsId));
        long expectedTidspunktAsUtcLong = expectedTidspunkt.toInstant(ZoneOffset.UTC).toEpochMilli();
        assertThat(doneInput.getTidspunkt(), is(expectedTidspunktAsUtcLong));
    }

    @Test
    void skalIkkeGodtaForLangGrupperingsId() {
        String tooLongGrupperingsId = String.join("", Collections.nCopies(101, "1"));
        DoneInputBuilder builder = getBuilderWithDefaultValues().withGrupperingsId(tooLongGrupperingsId);
        FieldValidationException exceptionThrown = assertThrows(FieldValidationException.class, () -> builder.build());
        assertThat(exceptionThrown.getMessage(), containsString("grupperingsId"));
    }

    @Test
    void skalIkkeGodtaManglendeGrupperingsId() {
        DoneInputBuilder builder = getBuilderWithDefaultValues().withGrupperingsId(null);
        FieldValidationException exceptionThrown = assertThrows(FieldValidationException.class, () -> builder.build());
        assertThat(exceptionThrown.getMessage(), containsString("grupperingsId"));
    }

    @Test
    void skalIkkeGodtaManglendeEventtidspunkt() {
        DoneInputBuilder builder = getBuilderWithDefaultValues().withTidspunkt(null);
        FieldValidationException exceptionThrown = assertThrows(FieldValidationException.class, () -> builder.build());
        assertThat(exceptionThrown.getMessage(), containsString("tidspunkt"));
    }

    private DoneInputBuilder getBuilderWithDefaultValues() {
        return new DoneInputBuilder()
                .withGrupperingsId(expectedGrupperingsId)
                .withTidspunkt(expectedTidspunkt);
    }
}
