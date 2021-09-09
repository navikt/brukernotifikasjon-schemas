package no.nav.brukernotifikasjon.schemas.builders;


import no.nav.brukernotifikasjon.schemas.Oppgave;
import no.nav.brukernotifikasjon.schemas.builders.domain.Eventtype;
import no.nav.brukernotifikasjon.schemas.builders.domain.PreferertKanal;
import no.nav.brukernotifikasjon.schemas.builders.util.ValidationUtil;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public class OppgaveBuilder {

    private LocalDateTime tidspunkt;
    private String grupperingsId;
    private String tekst;
    private URL link;
    private Integer sikkerhetsnivaa;
    private Boolean eksternVarsling = false;
    private List<PreferertKanal> prefererteKanaler;

    public OppgaveBuilder withTidspunkt(LocalDateTime tidspunkt) {
        this.tidspunkt = tidspunkt;
        return this;
    }

    public OppgaveBuilder withGrupperingsId(String grupperingsId) {
        this.grupperingsId = grupperingsId;
        return this;
    }

    public OppgaveBuilder withTekst(String tekst) {
        this.tekst = tekst;
        return this;
    }

    public OppgaveBuilder withLink(URL link) {
        this.link = link;
        return this;
    }

    public OppgaveBuilder withSikkerhetsnivaa(Integer sikkerhetsnivaa) {
        this.sikkerhetsnivaa = sikkerhetsnivaa;
        return this;
    }

    public OppgaveBuilder withEksternVarsling(Boolean eksternVarsling) {
        this.eksternVarsling = eksternVarsling;
        return this;
    }

    public OppgaveBuilder withPrefererteKanaler(PreferertKanal... prefererteKanaler) {
        if(prefererteKanaler != null) {
            this.prefererteKanaler = Arrays.asList(prefererteKanaler);
        }
        return this;
    }

    public Oppgave build() {
        return new Oppgave(
                ValidationUtil.localDateTimeToUtcTimestamp(tidspunkt, "tidspunkt", ValidationUtil.IS_REQUIRED_TIDSPUNKT),
                ValidationUtil.validateNonNullFieldMaxLength(grupperingsId, "grupperingsId", ValidationUtil.MAX_LENGTH_GRUPPERINGSID),
                ValidationUtil.validateNonNullFieldMaxLength(tekst, "tekst", ValidationUtil.MAX_LENGTH_TEXT_OPPGAVE),
                ValidationUtil.validateLinkAndConvertToString(link, "link", ValidationUtil.MAX_LENGTH_LINK, ValidationUtil.isLinkRequired(Eventtype.OPPGAVE)),
                ValidationUtil.validateSikkerhetsnivaa(sikkerhetsnivaa),
                eksternVarsling,
                ValidationUtil.validatePrefererteKanaler(eksternVarsling, prefererteKanaler)
        );
    }
}
