package no.nav.tms.brukernotifikasjon.schemas.builders;


import no.nav.tms.brukernotifikasjon.schemas.builders.util.ValidationUtil;
import no.nav.tms.brukernotifikasjon.schemas.input.NokkelInput;

public class NokkelInputBuilder {

    private String eventId;
    private String grupperingsId;
    private String fodselsnummer;
    private String namespace;
    private String appnavn;

    public NokkelInputBuilder withEventId(String eventId) {
        this.eventId = eventId;
        return this;
    }

    public NokkelInputBuilder withGrupperingsId(String grupperingsId) {
        this.grupperingsId = grupperingsId;
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
                ValidationUtil.validateNonNullFieldMaxLength(grupperingsId, "grupperingsId", ValidationUtil.MAX_LENGTH_GRUPPERINGSID),
                ValidationUtil.validateFodselsnummer(fodselsnummer),
                ValidationUtil.validateNonNullFieldMaxLength(namespace, "namespace", ValidationUtil.MAX_LENGTH_NAMESPACE),
                ValidationUtil.validateNonNullFieldMaxLength(appnavn, "appnavn", ValidationUtil.MAX_LENGTH_APP_NAME)
        );
    }
}
