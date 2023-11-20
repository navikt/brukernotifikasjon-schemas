package no.nav.tms.brukernotifikasjon.schemas.builders;


import no.nav.tms.brukernotifikasjon.schemas.builders.domain.Eventtype;
import no.nav.tms.brukernotifikasjon.schemas.builders.domain.StatusGlobal;
import no.nav.tms.brukernotifikasjon.schemas.builders.util.ValidationUtil;
import no.nav.tms.brukernotifikasjon.schemas.input.StatusoppdateringInput;

import java.net.URL;
import java.time.LocalDateTime;

public class StatusoppdateringInputBuilder {

    private LocalDateTime tidspunkt;
    private URL link;
    private Integer sikkerhetsnivaa;
    private StatusGlobal statusGlobal;
    private String statusIntern;
    private String sakstema;

    public StatusoppdateringInputBuilder withTidspunkt(LocalDateTime tidspunkt) {
        this.tidspunkt = tidspunkt;
        return this;
    }

    public StatusoppdateringInputBuilder withLink(URL link) {
        this.link = link;
        return this;
    }

    public StatusoppdateringInputBuilder withSikkerhetsnivaa(Integer sikkerhetsnivaa) {
        this.sikkerhetsnivaa = sikkerhetsnivaa;
        return this;
    }

    public StatusoppdateringInputBuilder withStatusGlobal(StatusGlobal statusGlobal) {
        this.statusGlobal = statusGlobal;
        return this;
    }

    public StatusoppdateringInputBuilder withStatusIntern(String statusIntern) {
        this.statusIntern = statusIntern;
        return this;
    }

    public StatusoppdateringInputBuilder withSakstema(String sakstema) {
        this.sakstema = sakstema;
        return this;
    }

    public StatusoppdateringInput build() {
        return new StatusoppdateringInput(
                ValidationUtil.localDateTimeToUtcTimestamp(tidspunkt, "tidspunkt", ValidationUtil.IS_REQUIRED_TIDSPUNKT),
                ValidationUtil.validateLinkAndConvertToString(link, "link", ValidationUtil.MAX_LENGTH_LINK, ValidationUtil.isLinkRequired(Eventtype.STATUSOPPDATERING)),
                ValidationUtil.validateSikkerhetsnivaa(sikkerhetsnivaa),
                ValidationUtil.validateStatusGlobal(statusGlobal),
                ValidationUtil.validateNonNullFieldMaxLength(statusIntern, "statusIntern", ValidationUtil.MAX_LENGTH_STATUSINTERN),
                ValidationUtil.validateNonNullFieldMaxLength(sakstema, "sakstema", ValidationUtil.MAX_LENGTH_SAKSTEMA)
        );
    }
}
