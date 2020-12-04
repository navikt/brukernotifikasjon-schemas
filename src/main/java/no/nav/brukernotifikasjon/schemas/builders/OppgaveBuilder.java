package no.nav.brukernotifikasjon.schemas.builders;

import no.nav.brukernotifikasjon.schemas.Oppgave;
import no.nav.brukernotifikasjon.schemas.builders.util.ValidationUtil;

import java.net.URL;
import java.time.LocalDateTime;

public class OppgaveBuilder {

    private LocalDateTime tidspunkt;
    private String fodselsnummer;
    private String grupperingsId;
    private String tekst;
    private URL link;
    private Integer sikkerhetsnivaa;

    public OppgaveBuilder withTidspunkt(LocalDateTime tidspunkt) {
        this.tidspunkt = tidspunkt;
        return this;
    }

    public OppgaveBuilder withFodselsnummer(String fodselsnummer) {
        this.fodselsnummer = fodselsnummer;
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

    public Oppgave build() {
        return new Oppgave(
                ValidationUtil.localDateTimeToUtcTimestamp(tidspunkt, "tidspunkt", true),
                ValidationUtil.validateFodselsnummer(fodselsnummer),
                ValidationUtil.validateNonNullFieldMaxLength(grupperingsId, "grupperingsId", 100),
                ValidationUtil.validateNonNullFieldMaxLength(tekst, "tekst", 500),
                ValidationUtil.validateLinkAndConvertToString(link, "link", 200, true),
                ValidationUtil.validateSikkerhetsnivaa(sikkerhetsnivaa)
        );
    }
}
