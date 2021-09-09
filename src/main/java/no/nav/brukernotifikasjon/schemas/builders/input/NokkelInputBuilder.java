package no.nav.brukernotifikasjon.schemas.builders.input;


import no.nav.brukernotifikasjon.schemas.input.NokkelInput;
import no.nav.brukernotifikasjon.schemas.builders.util.ValidationUtil;

public class NokkelInputBuilder {

    private String eventId;
    private String fodselsnummer;
    private String namespace;
    private String appnavn;

    public NokkelInputBuilder withEventId(String eventId) {
        this.eventId = eventId;
        return this;
    }

    public NokkelInputBuilder withFodselsnummer(String fodselsnummer) {
        this.fodselsnummer = fodselsnummer;
        return this;
    }

    public NokkelInputBuilder withNamespace(String namespace) {
        this.namespace = namespace;
        return this;
    }

    public NokkelInputBuilder withAppnavn(String appnavn) {
        this.appnavn = appnavn;
        return this;
    }


    public NokkelInput build() {
        return new NokkelInput(
                ValidationUtil.validateEventId(eventId),
                ValidationUtil.validateFodselsnummer(fodselsnummer),
                ValidationUtil.validateNonNullFieldMaxLength(namespace, "namespace", ValidationUtil.MAX_LENGTH_NAMESPACE),
                ValidationUtil.validateNonNullFieldMaxLength(appnavn, "appnavn", ValidationUtil.MAX_LENGTH_APP_NAME)
        );
    }
}
