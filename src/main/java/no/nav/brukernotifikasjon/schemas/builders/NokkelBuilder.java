package no.nav.brukernotifikasjon.schemas.builders;

import no.nav.brukernotifikasjon.schemas.Nokkel;
import no.nav.brukernotifikasjon.schemas.builders.util.ValidationUtil;

public class NokkelBuilder {

    private String systembruker;
    private String eventId;

    public NokkelBuilder withSystembruker(String systembruker) {
        this.systembruker = systembruker;
        return this;
    }

    public NokkelBuilder withEventId(String eventId) {
        this.eventId = eventId;
        return this;
    }

    public Nokkel build() {
        return new Nokkel(
                ValidationUtil.validateNonNullFieldMaxLength(systembruker, "systembruker", ValidationUtil.MAX_LENGTH_SYSTEMBRUKER),
                ValidationUtil.validateNonNullFieldMaxLength(eventId, "eventId", ValidationUtil.MAX_LENGTH_EVENTID)
        );
    }
}
