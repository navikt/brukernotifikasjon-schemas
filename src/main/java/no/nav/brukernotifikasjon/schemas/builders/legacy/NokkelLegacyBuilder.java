package no.nav.brukernotifikasjon.schemas.builders.legacy;

import no.nav.brukernotifikasjon.schemas.Nokkel;
import no.nav.brukernotifikasjon.schemas.builders.util.ValidationUtil;

public class NokkelLegacyBuilder {

    private String systembruker;
    private String eventId;

    public NokkelLegacyBuilder withSystembruker(String systembruker) {
        this.systembruker = systembruker;
        return this;
    }

    public NokkelLegacyBuilder withEventId(String eventId) {
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
