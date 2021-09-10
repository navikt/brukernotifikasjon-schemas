package no.nav.brukernotifikasjon.schemas.builders.legacy;

import no.nav.brukernotifikasjon.schemas.builders.domain.Eventtype;
import no.nav.brukernotifikasjon.schemas.builders.domain.StatusGlobal;
import no.nav.brukernotifikasjon.schemas.builders.util.ValidationUtil;
import no.nav.brukernotifikasjon.schemas.legacy.StatusoppdateringLegacy;

import java.net.URL;
import java.time.LocalDateTime;

public class StatusoppdateringLegacyBuilder {

    private LocalDateTime tidspunkt;
    private String grupperingsId;
    private URL link;
    private Integer sikkerhetsnivaa;
    private StatusGlobal statusGlobal;
    private String statusIntern;
    private String sakstema;
    private String fodselsnummer;

    public StatusoppdateringLegacyBuilder withTidspunkt(LocalDateTime tidspunkt) {
        this.tidspunkt = tidspunkt;
        return this;
    }

    public StatusoppdateringLegacyBuilder withGrupperingsId(String grupperingsId) {
        this.grupperingsId = grupperingsId;
        return this;
    }

    public StatusoppdateringLegacyBuilder withLink(URL link) {
        this.link = link;
        return this;
    }

    public StatusoppdateringLegacyBuilder withSikkerhetsnivaa(Integer sikkerhetsnivaa) {
        this.sikkerhetsnivaa = sikkerhetsnivaa;
        return this;
    }

    public StatusoppdateringLegacyBuilder withStatusGlobal(StatusGlobal statusGlobal) {
        this.statusGlobal = statusGlobal;
        return this;
    }

    public StatusoppdateringLegacyBuilder withStatusIntern(String statusIntern) {
        this.statusIntern = statusIntern;
        return this;
    }

    public StatusoppdateringLegacyBuilder withSakstema(String sakstema) {
        this.sakstema = sakstema;
        return this;
    }

    public StatusoppdateringLegacyBuilder withFodselsnummer(String fodselsnummer) {
        this.fodselsnummer = fodselsnummer;
        return this;
    }

    public StatusoppdateringLegacy build() {
        return new StatusoppdateringLegacy(
                ValidationUtil.localDateTimeToUtcTimestamp(tidspunkt, "tidspunkt", ValidationUtil.IS_REQUIRED_TIDSPUNKT),
                ValidationUtil.validateNonNullFieldMaxLength(grupperingsId, "grupperingsId", ValidationUtil.MAX_LENGTH_GRUPPERINGSID),
                ValidationUtil.validateLinkAndConvertToString(link, "link", ValidationUtil.MAX_LENGTH_LINK, ValidationUtil.isLinkRequired(Eventtype.STATUSOPPDATERING)),
                ValidationUtil.validateSikkerhetsnivaa(sikkerhetsnivaa),
                ValidationUtil.validateStatusGlobal(statusGlobal),
                ValidationUtil.validateNonNullFieldMaxLength(statusIntern, "statusIntern", ValidationUtil.MAX_LENGTH_StatusIntern),
                ValidationUtil.validateNonNullFieldMaxLength(sakstema, "sakstema", ValidationUtil.MAX_LENGTH_SAKSTEMA),
                ValidationUtil.validateFodselsnummer(fodselsnummer)
        );
    }
}
