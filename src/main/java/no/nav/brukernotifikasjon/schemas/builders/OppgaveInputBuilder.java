package no.nav.brukernotifikasjon.schemas.builders;


import no.nav.brukernotifikasjon.schemas.builders.domain.Eventtype;
import no.nav.brukernotifikasjon.schemas.builders.domain.PreferertKanal;
import no.nav.brukernotifikasjon.schemas.builders.legacy.OppgaveBuilder;
import no.nav.brukernotifikasjon.schemas.builders.util.ValidationUtil;
import no.nav.brukernotifikasjon.schemas.input.OppgaveInput;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public class OppgaveInputBuilder {

    private LocalDateTime tidspunkt;
    private LocalDateTime synligFremTil;
    private String tekst;
    private URL link;
    private Integer sikkerhetsnivaa;
    private Boolean eksternVarsling = false;
    private List<PreferertKanal> prefererteKanaler;
    private String epostVarslingstekst;
    private String smsVarslingstekst;

    public OppgaveInputBuilder withTidspunkt(LocalDateTime tidspunkt) {
        this.tidspunkt = tidspunkt;
        return this;
    }

    public OppgaveInputBuilder withSynligFremTil(LocalDateTime synligFremTil) {
        this.synligFremTil = synligFremTil;
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

    public OppgaveInputBuilder withEpostVarslingstekst(String epostVarslingstekst) {
        this.epostVarslingstekst = epostVarslingstekst;
        return this;
    }

    public OppgaveInputBuilder withSmsVarslingstekst(String smsVarslingstekst) {
        this.smsVarslingstekst = smsVarslingstekst;
        return this;
    }

    public OppgaveInput build() {
        return new OppgaveInput(
                ValidationUtil.localDateTimeToUtcTimestamp(tidspunkt, "tidspunkt", ValidationUtil.IS_REQUIRED_TIDSPUNKT),
                ValidationUtil.localDateTimeToUtcTimestamp(synligFremTil, "synligFremTil", ValidationUtil.IS_REQUIRED_SYNLIGFREMTIL),
                ValidationUtil.validateNonNullFieldMaxLength(tekst, "tekst", ValidationUtil.MAX_LENGTH_TEXT_OPPGAVE),
                ValidationUtil.validateLinkAndConvertToString(link, "link", ValidationUtil.MAX_LENGTH_LINK, ValidationUtil.isLinkRequired(Eventtype.OPPGAVE)),
                ValidationUtil.validateSikkerhetsnivaa(sikkerhetsnivaa),
                eksternVarsling,
                ValidationUtil.validatePrefererteKanaler(eksternVarsling, prefererteKanaler),
                ValidationUtil.validateNullableOrNotBlank(epostVarslingstekst, "epostVarslingstekst"),
                ValidationUtil.validateNullableMaxLength(smsVarslingstekst, "smsVarslingstekst", ValidationUtil.MAX_LENGTH_SMS_VARSLINGSTEKST)
        );
    }
}
