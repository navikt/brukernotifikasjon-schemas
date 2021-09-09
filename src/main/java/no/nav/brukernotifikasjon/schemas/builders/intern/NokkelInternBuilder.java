package no.nav.brukernotifikasjon.schemas.builders.intern;

import no.nav.brukernotifikasjon.schemas.intern.NokkelIntern;
import no.nav.brukernotifikasjon.schemas.builders.util.ValidationUtil;

public class NokkelInternBuilder {

    private String systembruker;
    private String eventId;

    public NokkelInternBuilder withSystembruker(String systembruker) {
        this.systembruker = systembruker;
        return this;
    }

    public NokkelInternBuilder withEventId(String eventId) {
        this.eventId = eventId;
        return this;
    }

    public NokkelIntern build() {
        return new NokkelIntern(
                ValidationUtil.validateNonNullFieldMaxLength(systembruker, "systembruker", ValidationUtil.MAX_LENGTH_SYSTEMBRUKER),
                ValidationUtil.validateNonNullFieldMaxLength(eventId, "eventId", ValidationUtil.MAX_LENGTH_EVENTID)
        );
    }
}
