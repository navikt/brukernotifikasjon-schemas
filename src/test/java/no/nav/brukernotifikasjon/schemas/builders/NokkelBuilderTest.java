package no.nav.brukernotifikasjon.schemas.builders;

import no.nav.brukernotifikasjon.schemas.Nokkel;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class NokkelBuilderTest {

    @Test
    void skalGodtaEventerMedGyldigeFeltverdier() {
        String expectedSystembruker = "enSystemBruker";

        NokkelBuilder builder = new NokkelBuilder()
                .withSystembruker(expectedSystembruker);
        Nokkel nokkel = builder.build();

        assertThat(nokkel.getSystembruker(), is(expectedSystembruker));
        assertThat(nokkel.getEventId().length(), is(26));
    }

}
