package no.nav.brukernotifikasjon.schemas.builders;

import no.nav.brukernotifikasjon.schemas.Statusoppdatering;
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
                ValidationUtil.localDateTimeToUtcTimestamp(tidspunkt, "tidspunkt", true),
                ValidationUtil.validateNonNullFieldMaxLength(grupperingsId, "grupperingsId", 100),
                ValidationUtil.validateLink(link, "link", 200).toString(),
                ValidationUtil.validateSikkerhetsnivaa(sikkerhetsnivaa),
                ValidationUtil.validateNonNullField(statusGlobal, "statusGlobal").toString(),
                ValidationUtil.validateNonNullFieldMaxLength(statusIntern, "statusIntern", 100),
                ValidationUtil.validateNonNullFieldMaxLength(sakstema, "sakstema", 100),
                ValidationUtil.validateFodselsnummer(fodselsnummer)
        );
    }
}
