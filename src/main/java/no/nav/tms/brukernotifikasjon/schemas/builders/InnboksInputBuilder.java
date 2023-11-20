package no.nav.tms.brukernotifikasjon.schemas.builders;

import no.nav.tms.brukernotifikasjon.schemas.builders.domain.Eventtype;
import no.nav.tms.brukernotifikasjon.schemas.builders.domain.PreferertKanal;
import no.nav.tms.brukernotifikasjon.schemas.builders.util.ValidationUtil;
import no.nav.tms.brukernotifikasjon.schemas.input.InnboksInput;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public class InnboksInputBuilder {

    private LocalDateTime tidspunkt;
    private String tekst;
    private URL link;
    private Integer sikkerhetsnivaa;
    private Boolean eksternVarsling = false;
    private List<PreferertKanal> prefererteKanaler;
    private String epostVarslingstekst;
    private String epostVarslingstittel;
    private String smsVarslingstekst;

    public InnboksInputBuilder withTidspunkt(LocalDateTime tidspunkt) {
        this.tidspunkt = tidspunkt;
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

    public InnboksInputBuilder withEksternVarsling(Boolean eksternVarsling) {
        this.eksternVarsling = eksternVarsling;
        return this;
    }

    public InnboksInputBuilder withPrefererteKanaler(PreferertKanal... prefererteKanaler) {
        if(prefererteKanaler != null) {
            this.prefererteKanaler = Arrays.asList(prefererteKanaler);
        }
        return this;
    }

    public InnboksInputBuilder withEpostVarslingstekst(String epostVarslingstekst) {
        this.epostVarslingstekst = epostVarslingstekst;
        return this;
    }

    public InnboksInputBuilder withEpostVarslingstittel(String epostVarslingstittel) {
        this.epostVarslingstittel = epostVarslingstittel;
        return this;
    }

    public InnboksInputBuilder withSmsVarslingstekst(String smsVarslingstekst) {
        this.smsVarslingstekst = smsVarslingstekst;
        return this;
    }

    public InnboksInput build() {
        return new InnboksInput(
                ValidationUtil.localDateTimeToUtcTimestamp(tidspunkt, "tidspunkt", ValidationUtil.IS_REQUIRED_TIDSPUNKT),
                ValidationUtil.validateNonNullFieldMaxLength(tekst, "tekst", ValidationUtil.MAX_LENGTH_TEXT_INNBOKS),
                ValidationUtil.validateLinkAndConvertToString(link, "link", ValidationUtil.MAX_LENGTH_LINK, ValidationUtil.isLinkRequired(Eventtype.INNBOKS)),
                ValidationUtil.validateSikkerhetsnivaa(sikkerhetsnivaa),
                eksternVarsling,
                ValidationUtil.validatePrefererteKanaler(eksternVarsling, prefererteKanaler),
                ValidationUtil.validateEpostVarslingstekst(eksternVarsling, epostVarslingstekst),
                ValidationUtil.validateEpostVarslingstittel(eksternVarsling, epostVarslingstittel),
                ValidationUtil.validateSmsVarslingstekst(eksternVarsling, smsVarslingstekst)
        );
    }
}
