package no.nav.tms.brukernotifikasjon.schemas.builders;

import no.nav.tms.brukernotifikasjon.schemas.builders.exception.FieldValidationException;
import no.nav.tms.brukernotifikasjon.schemas.input.DoneInput;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class DoneInputBuilderTest {

    private LocalDateTime expectedTidspunkt = LocalDateTime.now(ZoneId.of("UTC"));

    @Test
    void skalGodtaEventerMedGyldigeFeltverdier() {
        DoneInputBuilder builder = getBuilderWithDefaultValues();
        DoneInput done = builder.build();

        long expectedTidspunktAsUtcLong = expectedTidspunkt.toInstant(ZoneOffset.UTC).toEpochMilli();
        assertThat(done.getTidspunkt(), is(expectedTidspunktAsUtcLong));
    }


    @Test
    void skalIkkeGodtaManglendeEventtidspunkt() {
        DoneInputBuilder builder = getBuilderWithDefaultValues().withTidspunkt(null);
        FieldValidationException exceptionThrown = assertThrows(FieldValidationException.class, () -> builder.build());
        assertThat(exceptionThrown.getMessage(), containsString("tidspunkt"));
    }

    private DoneInputBuilder getBuilderWithDefaultValues() {
        return new DoneInputBuilder()
                .withTidspunkt(expectedTidspunkt);
    }
}
