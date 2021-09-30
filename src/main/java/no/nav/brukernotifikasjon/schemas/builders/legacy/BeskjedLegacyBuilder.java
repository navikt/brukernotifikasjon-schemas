package no.nav.brukernotifikasjon.schemas.builders.legacy;

import no.nav.brukernotifikasjon.schemas.Beskjed;
import no.nav.brukernotifikasjon.schemas.builders.domain.Eventtype;
import no.nav.brukernotifikasjon.schemas.builders.domain.PreferertKanal;
import no.nav.brukernotifikasjon.schemas.builders.util.ValidationUtil;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public class BeskjedLegacyBuilder {

    private LocalDateTime tidspunkt;
    private LocalDateTime synligFremTil;
    private String fodselsnummer;
    private String grupperingsId;
    private String tekst;
    private URL link;
    private Integer sikkerhetsnivaa;
    private Boolean eksternVarsling = false;
    private List<PreferertKanal> prefererteKanaler;

    public BeskjedLegacyBuilder withTidspunkt(LocalDateTime tidspunkt) {
        this.tidspunkt = tidspunkt;
        return this;
    }

    public BeskjedLegacyBuilder withSynligFremTil(LocalDateTime synligFremTil) {
        this.synligFremTil = synligFremTil;
        return this;
    }

    public BeskjedLegacyBuilder withFodselsnummer(String fodselsnummer) {
        this.fodselsnummer = fodselsnummer;
        return this;
    }

    public BeskjedLegacyBuilder withGrupperingsId(String grupperingsId) {
        this.grupperingsId = grupperingsId;
        return this;
    }

    public BeskjedLegacyBuilder withTekst(String tekst) {
        this.tekst = tekst;
        return this;
    }

    public BeskjedLegacyBuilder withLink(URL link) {
        this.link = link;
        return this;
    }

    public BeskjedLegacyBuilder withSikkerhetsnivaa(Integer sikkerhetsnivaa) {
        this.sikkerhetsnivaa = sikkerhetsnivaa;
        return this;
    }

    public BeskjedLegacyBuilder withEksternVarsling(Boolean eksternVarsling) {
        this.eksternVarsling = eksternVarsling;
        return this;
    }

    public BeskjedLegacyBuilder withPrefererteKanaler(PreferertKanal... prefererteKanaler) {
        if(prefererteKanaler != null) {
            this.prefererteKanaler = Arrays.asList(prefererteKanaler);
        }
        return this;
    }

    public Beskjed build() {
        return new Beskjed(
                ValidationUtil.localDateTimeToUtcTimestamp(tidspunkt, "tidspunkt", ValidationUtil.IS_REQUIRED_TIDSPUNKT),
                ValidationUtil.localDateTimeToUtcTimestamp(synligFremTil, "synligFremTil", ValidationUtil.IS_REQUIRED_SYNLIGFREMTIL),
                ValidationUtil.validateFodselsnummer(fodselsnummer),
                ValidationUtil.validateNonNullFieldMaxLength(grupperingsId, "grupperingsId", ValidationUtil.MAX_LENGTH_GRUPPERINGSID),
                ValidationUtil.validateNonNullFieldMaxLength(tekst, "tekst", ValidationUtil.MAX_LENGTH_TEXT_BESKJED),
                ValidationUtil.validateLinkAndConvertToString(link, "link", ValidationUtil.MAX_LENGTH_LINK, ValidationUtil.isLinkRequired(Eventtype.BESKJED)),
                ValidationUtil.validateSikkerhetsnivaa(sikkerhetsnivaa),
                eksternVarsling,
                ValidationUtil.validatePrefererteKanaler(eksternVarsling, prefererteKanaler)
        );
    }
}
