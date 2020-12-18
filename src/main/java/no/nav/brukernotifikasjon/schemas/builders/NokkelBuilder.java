package no.nav.brukernotifikasjon.schemas.builders;

import de.huxhorn.sulky.ulid.ULID;
import no.nav.brukernotifikasjon.schemas.Nokkel;
import no.nav.brukernotifikasjon.schemas.builders.util.ValidationUtil;

public class NokkelBuilder {

    private String systembruker;
    private ULID.Value eventId;

    public NokkelBuilder withSystembruker(String systembruker) {
        this.systembruker = systembruker;
        return this;
    }

    public NokkelBuilder withEventId(ULID.Value eventId) {
        this.eventId = eventId;
        return this;
    }

    public Nokkel build() {
        return new Nokkel(
                ValidationUtil.validateNonNullFieldMaxLength(systembruker, "systembruker", ValidationUtil.MAX_LENGTH_SYSTEMBRUKER),
                ValidationUtil.validateNonNullField(eventId, "eventId").toString()
        );
    }
}
