package no.nav.brukernotifikasjon.schemas.builders;

import no.nav.brukernotifikasjon.schemas.Done;
import no.nav.brukernotifikasjon.schemas.builders.util.ValidationUtil;

import java.time.LocalDateTime;

public class DoneBuilder {

    private LocalDateTime tidspunkt;
    private String fodselsnummer;
    private String grupperingsId;

    public DoneBuilder withTidspunkt(LocalDateTime tidspunkt) {
        this.tidspunkt = tidspunkt;
        return this;
    }

    public DoneBuilder withFodselsnummer(String fodselsnummer) {
        this.fodselsnummer = fodselsnummer;
        return this;
    }

    public DoneBuilder withGrupperingsId(String grupperingsId) {
        this.grupperingsId = grupperingsId;
        return this;
    }

    public Done build() {
        return new Done(
                ValidationUtil.localDateTimeToUtcTimestamp(tidspunkt, "tidspunkt", ValidationUtil.IS_REQUIRED_TIDSPUNKT),
                ValidationUtil.validateFodselsnummer(fodselsnummer),
                ValidationUtil.validateNonNullFieldMaxLength(grupperingsId, "grupperingsId", ValidationUtil.MAX_LENGTH_GRUPPERINGSID)
        );
    }
}
