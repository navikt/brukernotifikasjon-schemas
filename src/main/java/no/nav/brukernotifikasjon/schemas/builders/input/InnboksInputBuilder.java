package no.nav.brukernotifikasjon.schemas.builders.input;

import no.nav.brukernotifikasjon.schemas.input.InnboksInput;
import no.nav.brukernotifikasjon.schemas.builders.domain.Eventtype;
import no.nav.brukernotifikasjon.schemas.builders.util.ValidationUtil;

import java.net.URL;
import java.time.LocalDateTime;

public class InnboksInputBuilder {

    private LocalDateTime tidspunkt;
    private String grupperingsId;
    private String tekst;
    private URL link;
    private Integer sikkerhetsnivaa;

    public InnboksInputBuilder withTidspunkt(LocalDateTime tidspunkt) {
        this.tidspunkt = tidspunkt;
        return this;
    }

    public InnboksInputBuilder withGrupperingsId(String grupperingsId) {
        this.grupperingsId = grupperingsId;
        return this;
    }

    public InnboksInputBuilder withTekst(String tekst) {
        this.tekst = tekst;
        return this;
    }

    public InnboksInputBuilder withLink(URL link) {
        this.link = link;
        return this;
    }

    public InnboksInputBuilder withSikkerhetsnivaa(Integer sikkerhetsnivaa) {
        this.sikkerhetsnivaa = sikkerhetsnivaa;
        return this;
    }

    public InnboksInput build() {
        return new InnboksInput(
                ValidationUtil.localDateTimeToUtcTimestamp(tidspunkt, "tidspunkt", ValidationUtil.IS_REQUIRED_TIDSPUNKT),
                ValidationUtil.validateNonNullFieldMaxLength(grupperingsId, "grupperingsId", ValidationUtil.MAX_LENGTH_GRUPPERINGSID),
                ValidationUtil.validateNonNullFieldMaxLength(tekst, "tekst", ValidationUtil.MAX_LENGTH_TEXT_INNBOKS),
                ValidationUtil.validateLinkAndConvertToString(link, "link", ValidationUtil.MAX_LENGTH_LINK, ValidationUtil.isLinkRequired(Eventtype.INNBOKS)),
                ValidationUtil.validateSikkerhetsnivaa(sikkerhetsnivaa)
        );
    }
}
