package no.nav.brukernotifikasjon.schemas.builders;

import no.nav.brukernotifikasjon.schemas.Beskjed;
import no.nav.brukernotifikasjon.schemas.builders.util.ValidationUtil;

import java.net.URL;
import java.time.LocalDateTime;

public class BeskjedBuilder {

    private LocalDateTime tidspunkt;
    private LocalDateTime synligFremTil;
    private String fodselsnummer;
    private String grupperingsId;
    private String tekst;
    private URL link;
    private Integer sikkerhetsnivaa;

    public BeskjedBuilder withTidspunkt(LocalDateTime tidspunkt) {
        this.tidspunkt = tidspunkt;
        return this;
    }

    public BeskjedBuilder withSynligFremTil(LocalDateTime synligFremTil) {
        this.synligFremTil = synligFremTil;
        return this;
    }

    public BeskjedBuilder withFodselsnummer(String fodselsnummer) {
        this.fodselsnummer = fodselsnummer;
        return this;
    }

    public BeskjedBuilder withGrupperingsId(String grupperingsId) {
        this.grupperingsId = grupperingsId;
        return this;
    }

    public BeskjedBuilder withTekst(String tekst) {
        this.tekst = tekst;
        return this;
    }

    public BeskjedBuilder withLink(URL link) {
        this.link = link;
        return this;
    }

    public BeskjedBuilder withSikkerhetsnivaa(Integer sikkerhetsnivaa) {
        this.sikkerhetsnivaa = sikkerhetsnivaa;
        return this;
    }

    public Beskjed build() {
        return new Beskjed(
                ValidationUtil.localDateTimeToUtcTimestamp(tidspunkt),
                ValidationUtil.localDateTimeToUtcTimestamp(synligFremTil),
                ValidationUtil.validateFodselsnummer(fodselsnummer),
                ValidationUtil.validateNonNullFieldMaxLength(grupperingsId, "grupperingsId", 100),
                ValidationUtil.validateNonNullFieldMaxLength(tekst, "tekst", 500),
                link != null ? link.toString() : null,
                ValidationUtil.validateSikkerhetsnivaa(sikkerhetsnivaa)
        );
    }
}
