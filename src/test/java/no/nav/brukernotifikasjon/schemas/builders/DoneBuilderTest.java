package no.nav.brukernotifikasjon.schemas.builders;

import no.nav.brukernotifikasjon.schemas.Done;
import no.nav.brukernotifikasjon.schemas.builders.exception.FieldValidationException;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Collections;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class DoneBuilderTest {

    private String expectedFodselsnr = "12345678901";
    private String expectedGrupperingsId = "3456789123456";
    private LocalDateTime expectedTidspunkt = LocalDateTime.now(ZoneId.of("UTC"));

    @Test
    void skalGodtaEventerMedGyldigeFeltverdier() {
        DoneBuilder builder = getBuilderWithDefaultValues();
        Done done = builder.build();

        assertThat(done.getFodselsnummer(), is(expectedFodselsnr));
        assertThat(done.getGrupperingsId(), is(expectedGrupperingsId));
        long expectedTidspunktAsUtcLong = expectedTidspunkt.toInstant(ZoneOffset.UTC).toEpochMilli();
        assertThat(done.getTidspunkt(), is(expectedTidspunktAsUtcLong));
    }

    @Test
    void skalIkkeGodtaForLangtFodselsnummer() {
        String tooLongFodselsnummer = String.join("", Collections.nCopies(11, "12"));
        DoneBuilder builder = getBuilderWithDefaultValues().withFodselsnummer(tooLongFodselsnummer);
        FieldValidationException exceptionThrown = assertThrows(FieldValidationException.class, () -> builder.build());
        assertThat(exceptionThrown.getMessage(), containsString("fodselsnummer"));
    }

    @Test
    void skalIkkeGodtaManglendeFodselsnummer() {
        DoneBuilder builder = getBuilderWithDefaultValues().withFodselsnummer(null);
        FieldValidationException exceptionThrown = assertThrows(FieldValidationException.class, () -> builder.build());
        assertThat(exceptionThrown.getMessage(), containsString("fodselsnummer"));
    }

    @Test
    void skalIkkeGodtaForLangGrupperingsId() {
        String tooLongGrupperingsId = String.join("", Collections.nCopies(101, "1"));
        DoneBuilder builder = getBuilderWithDefaultValues().withGrupperingsId(tooLongGrupperingsId);
        FieldValidationException exceptionThrown = assertThrows(FieldValidationException.class, () -> builder.build());
        assertThat(exceptionThrown.getMessage(), containsString("grupperingsId"));
    }

    @Test
    void skalIkkeGodtaManglendeGrupperingsId() {
        DoneBuilder builder = getBuilderWithDefaultValues().withGrupperingsId(null);
        FieldValidationException exceptionThrown = assertThrows(FieldValidationException.class, () -> builder.build());
        assertThat(exceptionThrown.getMessage(), containsString("grupperingsId"));
    }

    @Test
    void skalIkkeGodtaManglendeEventtidspunkt() {
        DoneBuilder builder = getBuilderWithDefaultValues().withTidspunkt(null);
        FieldValidationException exceptionThrown = assertThrows(FieldValidationException.class, () -> builder.build());
        assertThat(exceptionThrown.getMessage(), containsString("tidspunkt"));
    }

    private DoneBuilder getBuilderWithDefaultValues() {
        return new DoneBuilder()
                .withFodselsnummer(expectedFodselsnr)
                .withGrupperingsId(expectedGrupperingsId)
                .withTidspunkt(expectedTidspunkt);
    }
}
