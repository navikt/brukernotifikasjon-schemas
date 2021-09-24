package no.nav.brukernotifikasjon.schemas.builders;

import no.nav.brukernotifikasjon.schemas.Innboks;
import no.nav.brukernotifikasjon.schemas.builders.domain.Eventtype;
import no.nav.brukernotifikasjon.schemas.builders.domain.PreferertKanal;
import no.nav.brukernotifikasjon.schemas.builders.util.ValidationUtil;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public class InnboksBuilder {

    private LocalDateTime tidspunkt;
    private String tekst;
    private URL link;
    private Integer sikkerhetsnivaa;
    private Boolean eksternVarsling = false;
    private List<PreferertKanal> prefererteKanaler;

    public InnboksBuilder withTidspunkt(LocalDateTime tidspunkt) {
        this.tidspunkt = tidspunkt;
        return this;
    }

    public InnboksBuilder withTekst(String tekst) {
        this.tekst = tekst;
        return this;
    }

    public InnboksBuilder withLink(URL link) {
        this.link = link;
        return this;
    }

    public InnboksBuilder withSikkerhetsnivaa(Integer sikkerhetsnivaa) {
        this.sikkerhetsnivaa = sikkerhetsnivaa;
        return this;
    }

    public InnboksBuilder withEksternVarsling(Boolean eksternVarsling) {
        this.eksternVarsling = eksternVarsling;
        return this;
    }

    public InnboksBuilder withPrefererteKanaler(PreferertKanal... prefererteKanaler) {
        if(prefererteKanaler != null) {
            this.prefererteKanaler = Arrays.asList(prefererteKanaler);
        }
        return this;
    }

    public Innboks build() {
        return new Innboks(
                ValidationUtil.localDateTimeToUtcTimestamp(tidspunkt, "tidspunkt", ValidationUtil.IS_REQUIRED_TIDSPUNKT),
                ValidationUtil.validateNonNullFieldMaxLength(tekst, "tekst", ValidationUtil.MAX_LENGTH_TEXT_INNBOKS),
                ValidationUtil.validateLinkAndConvertToString(link, "link", ValidationUtil.MAX_LENGTH_LINK, ValidationUtil.isLinkRequired(Eventtype.INNBOKS)),
                ValidationUtil.validateSikkerhetsnivaa(sikkerhetsnivaa),
                eksternVarsling,
                ValidationUtil.validatePrefererteKanaler(eksternVarsling, prefererteKanaler)
        );
    }
}
