package no.nav.brukernotifikasjon.schemas.builders;


import no.nav.brukernotifikasjon.schemas.builders.domain.Eventtype;
import no.nav.brukernotifikasjon.schemas.builders.domain.PreferertKanal;
import no.nav.brukernotifikasjon.schemas.builders.util.ValidationUtil;
import no.nav.brukernotifikasjon.schemas.input.OppgaveInput;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public class OppgaveInputBuilder {

    private LocalDateTime tidspunkt;
    private String tekst;
    private URL link;
    private Integer sikkerhetsnivaa;
    private Boolean eksternVarsling = false;
    private List<PreferertKanal> prefererteKanaler;

    public OppgaveInputBuilder withTidspunkt(LocalDateTime tidspunkt) {
        this.tidspunkt = tidspunkt;
        return this;
    }

    public OppgaveInputBuilder withTekst(String tekst) {
        this.tekst = tekst;
        return this;
    }

    public OppgaveInputBuilder withLink(URL link) {
        this.link = link;
        return this;
    }

    public OppgaveInputBuilder withSikkerhetsnivaa(Integer sikkerhetsnivaa) {
        this.sikkerhetsnivaa = sikkerhetsnivaa;
        return this;
    }

    public OppgaveInputBuilder withEksternVarsling(Boolean eksternVarsling) {
        this.eksternVarsling = eksternVarsling;
        return this;
    }

    public OppgaveInputBuilder withPrefererteKanaler(PreferertKanal... prefererteKanaler) {
        if(prefererteKanaler != null) {
            this.prefererteKanaler = Arrays.asList(prefererteKanaler);
        }
        return this;
    }

    public OppgaveInput build() {
        return new OppgaveInput(
                ValidationUtil.localDateTimeToUtcTimestamp(tidspunkt, "tidspunkt", ValidationUtil.IS_REQUIRED_TIDSPUNKT),
                ValidationUtil.validateNonNullFieldMaxLength(tekst, "tekst", ValidationUtil.MAX_LENGTH_TEXT_OPPGAVE),
                ValidationUtil.validateLinkAndConvertToString(link, "link", ValidationUtil.MAX_LENGTH_LINK, ValidationUtil.isLinkRequired(Eventtype.OPPGAVE)),
                ValidationUtil.validateSikkerhetsnivaa(sikkerhetsnivaa),
                eksternVarsling,
                ValidationUtil.validatePrefererteKanaler(eksternVarsling, prefererteKanaler)
        );
    }
}
