package no.nav.brukernotifikasjon.schemas.builders.input;

import no.nav.brukernotifikasjon.schemas.builders.exception.FieldValidationException;
import no.nav.brukernotifikasjon.schemas.builders.util.ValidationUtil;
import no.nav.brukernotifikasjon.schemas.input.NokkelInput;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.UUID;

import static no.nav.brukernotifikasjon.schemas.builders.util.ValidationUtil.*;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class NokkelInputBuilderTest {

    private String expectedEventID = UUID.randomUUID().toString();
    private String expectedFodselsnr = "12345678901";
    private String expectedNamespace = "default";
    private String expectedAppnavn = "appName";

    @Test
    void skalGodtaEventerMedGyldigeFeltverdier() {
        NokkelInputBuilder builder = getBuilderWithDefaultValues();
        NokkelInput nokkelInput = builder.build();

        assertThat(nokkelInput.getEventId(), is(expectedEventID));
    }

    @Test
    void skalIkkeGodtaManglendeEventId() {
        NokkelInputBuilder builder = getBuilderWithDefaultValues().withEventId(null);
        FieldValidationException exceptionThrown = assertThrows(FieldValidationException.class, () -> builder.build());
        assertThat(exceptionThrown.getMessage(), containsString("eventId"));
    }

    @Test
    void skalIkkeGodtaForLangEventId() {
        String tooLongEventId = String.join("", Collections.nCopies(MAX_LENGTH_EVENTID + 1, "n"));
        NokkelInputBuilder builder = getBuilderWithDefaultValues().withEventId(tooLongEventId);

        FieldValidationException exceptionThrown = assertThrows(FieldValidationException.class, () -> builder.build());
        assertThat(exceptionThrown.getMessage(), containsString("eventId"));
    }

    @Test
    void skalIkkeGodtaEventIdMedFeilFormat() {
        String notUuidOrUlid = "eventId123";
        NokkelInputBuilder builder = getBuilderWithDefaultValues().withEventId(notUuidOrUlid);

        FieldValidationException exceptionThrown = assertThrows(FieldValidationException.class, () -> builder.build());
        assertThat(exceptionThrown.getMessage(), containsString("eventId"));
    }


    @Test
    void skalIkkeGodtaManglendeFodselsnummer() {
        NokkelInputBuilder builder = getBuilderWithDefaultValues().withFodselsnummer(null);
        FieldValidationException exceptionThrown = assertThrows(FieldValidationException.class, () -> builder.build());
        assertThat(exceptionThrown.getMessage(), containsString("fodselsnummer"));
    }

    @Test
    void skalIkkeGodtaForLangtFodselsnummer() {
        String tooLongFodselsnummer = String.join("", Collections.nCopies(MAX_LENGTH_FODSELSNUMMER + 1, "n"));
        NokkelInputBuilder builder = getBuilderWithDefaultValues().withFodselsnummer(tooLongFodselsnummer);

        FieldValidationException exceptionThrown = assertThrows(FieldValidationException.class, () -> builder.build());
        assertThat(exceptionThrown.getMessage(), containsString("fodselsnummer"));
    }

    @Test
    void skalIkkeGodtaManglendeNamespace() {
        NokkelInputBuilder builder = getBuilderWithDefaultValues().withNamespace(null);
        FieldValidationException exceptionThrown = assertThrows(FieldValidationException.class, () -> builder.build());
        assertThat(exceptionThrown.getMessage(), containsString("namespace"));
        assertThat(exceptionThrown.getMessage(), containsString("var null eller tomt"));
    }

    @Test
    void skalIkkeGodtaForLangtNamespace() {
        String tooLongNamespace = String.join("", Collections.nCopies(MAX_LENGTH_NAMESPACE + 1, "n"));
        NokkelInputBuilder builder = getBuilderWithDefaultValues().withNamespace(tooLongNamespace);

        FieldValidationException exceptionThrown = assertThrows(FieldValidationException.class, () -> builder.build());
        assertThat(exceptionThrown.getMessage(), containsString("namespace"));
    }

    @Test
    void skalIkkeGodtaManglendeAppnavn() {
        NokkelInputBuilder builder = getBuilderWithDefaultValues().withAppnavn(null);
        FieldValidationException exceptionThrown = assertThrows(FieldValidationException.class, () -> builder.build());
        assertThat(exceptionThrown.getMessage(), containsString("appnavn"));
        assertThat(exceptionThrown.getMessage(), containsString("var null eller tomt"));
    }

    @Test
    void skalIkkeGodtaForLangtAppnavn() {
        String tooLongAppnavn = String.join("", Collections.nCopies(MAX_LENGTH_APP_NAME + 1, "n"));
        NokkelInputBuilder builder = getBuilderWithDefaultValues().withAppnavn(tooLongAppnavn);

        FieldValidationException exceptionThrown = assertThrows(FieldValidationException.class, () -> builder.build());
        assertThat(exceptionThrown.getMessage(), containsString("appnavn"));
    }


    private NokkelInputBuilder getBuilderWithDefaultValues() {
        return new NokkelInputBuilder()
                .withEventId(expectedEventID)
                .withFodselsnummer(expectedFodselsnr)
                .withNamespace(expectedNamespace)
                .withAppnavn(expectedAppnavn);
    }

}
