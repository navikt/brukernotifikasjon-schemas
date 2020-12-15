package no.nav.brukernotifikasjon.schemas.builders;

import no.nav.brukernotifikasjon.schemas.Statusoppdatering;
import no.nav.brukernotifikasjon.schemas.builders.domain.Eventtype;
import no.nav.brukernotifikasjon.schemas.builders.domain.StatusGlobal;
import no.nav.brukernotifikasjon.schemas.builders.util.ValidationUtil;

import java.net.URL;
import java.time.LocalDateTime;

public class StatusoppdateringBuilder {

    private LocalDateTime tidspunkt;
    private String grupperingsId;
    private URL link;
    private Integer sikkerhetsnivaa;
    private StatusGlobal statusGlobal;
    private String statusIntern;
    private String sakstema;
    private String fodselsnummer;

    public StatusoppdateringBuilder withTidspunkt(LocalDateTime tidspunkt) {
        this.tidspunkt = tidspunkt;
        return this;
    }

    public StatusoppdateringBuilder withGrupperingsId(String grupperingsId) {
        this.grupperingsId = grupperingsId;
        return this;
    }

    public StatusoppdateringBuilder withLink(URL link) {
        this.link = link;
        return this;
    }

    public StatusoppdateringBuilder withSikkerhetsnivaa(Integer sikkerhetsnivaa) {
        this.sikkerhetsnivaa = sikkerhetsnivaa;
        return this;
    }

    public StatusoppdateringBuilder withStatusGlobal(StatusGlobal statusGlobal) {
        this.statusGlobal = statusGlobal;
        return this;
    }

    public StatusoppdateringBuilder withStatusIntern(String statusIntern) {
        this.statusIntern = statusIntern;
        return this;
    }

    public StatusoppdateringBuilder withSakstema(String sakstema) {
        this.sakstema = sakstema;
        return this;
    }

    public StatusoppdateringBuilder withFodselsnummer(String fodselsnummer) {
        this.fodselsnummer = fodselsnummer;
        return this;
    }

    public Statusoppdatering build() {
        return new Statusoppdatering(
                ValidationUtil.localDateTimeToUtcTimestamp(tidspunkt, "tidspunkt", ValidationUtil.IS_REQUIRED_TIDSPUNKT),
                ValidationUtil.validateNonNullFieldMaxLength(grupperingsId, "grupperingsId", ValidationUtil.MAX_LENGTH_GRUPPERINGSID),
                ValidationUtil.validateLinkAndConvertToString(link, "link", ValidationUtil.MAX_LENGTH_LINK, ValidationUtil.isLinkRequired(Eventtype.STATUSOPPDATERING)),
                ValidationUtil.validateSikkerhetsnivaa(sikkerhetsnivaa),
                ValidationUtil.validateStatusGlobal(statusGlobal),
                ValidationUtil.validateNonNullFieldMaxLength(statusIntern, "statusIntern", ValidationUtil.MAX_LENGTH_STATUSINTERN),
                ValidationUtil.validateNonNullFieldMaxLength(sakstema, "sakstema", ValidationUtil.MAX_LENGTH_SAKSTEMA),
                ValidationUtil.validateFodselsnummer(fodselsnummer)
        );
    }
}
