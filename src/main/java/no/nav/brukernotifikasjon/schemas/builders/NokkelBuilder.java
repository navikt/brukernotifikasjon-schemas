package no.nav.brukernotifikasjon.schemas.builders;


import no.nav.brukernotifikasjon.schemas.Nokkel;
import no.nav.brukernotifikasjon.schemas.builders.util.ValidationUtil;

public class NokkelBuilder {

    private String eventId;
    private String grupperingsId;
    private String fodselsnummer;
    private String namespace;
    private String appnavn;

    public NokkelBuilder withEventId(String eventId) {
        this.eventId = eventId;
        return this;
    }

    public NokkelBuilder withGrupperingsId(String grupperingsId) {
        this.grupperingsId = grupperingsId;
        return this;
    }

    public NokkelBuilder withFodselsnummer(String fodselsnummer) {
        this.fodselsnummer = fodselsnummer;
        return this;
    }

    public NokkelBuilder withNamespace(String namespace) {
        this.namespace = namespace;
        return this;
    }

    public NokkelBuilder withAppnavn(String appnavn) {
        this.appnavn = appnavn;
        return this;
    }


    public Nokkel build() {
        return new Nokkel(
                ValidationUtil.validateEventId(eventId),
                ValidationUtil.validateNonNullFieldMaxLength(grupperingsId, "grupperingsId", ValidationUtil.MAX_LENGTH_GRUPPERINGSID),
                ValidationUtil.validateFodselsnummer(fodselsnummer),
                ValidationUtil.validateNonNullFieldMaxLength(namespace, "namespace", ValidationUtil.MAX_LENGTH_NAMESPACE),
                ValidationUtil.validateNonNullFieldMaxLength(appnavn, "appnavn", ValidationUtil.MAX_LENGTH_APP_NAME)
        );
    }
}
