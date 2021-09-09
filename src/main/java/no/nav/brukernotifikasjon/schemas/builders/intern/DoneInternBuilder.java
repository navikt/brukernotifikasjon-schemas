package no.nav.brukernotifikasjon.schemas.builders.intern;

import no.nav.brukernotifikasjon.schemas.intern.DoneIntern;
import no.nav.brukernotifikasjon.schemas.builders.util.ValidationUtil;

import java.time.LocalDateTime;

public class DoneInternBuilder {

    private LocalDateTime tidspunkt;
    private String fodselsnummer;
    private String grupperingsId;

    public DoneInternBuilder withTidspunkt(LocalDateTime tidspunkt) {
        this.tidspunkt = tidspunkt;
        return this;
    }

    public DoneInternBuilder withFodselsnummer(String fodselsnummer) {
        this.fodselsnummer = fodselsnummer;
        return this;
    }

    public DoneInternBuilder withGrupperingsId(String grupperingsId) {
        this.grupperingsId = grupperingsId;
        return this;
    }

    public DoneIntern build() {
        return new DoneIntern(
                ValidationUtil.localDateTimeToUtcTimestamp(tidspunkt, "tidspunkt", ValidationUtil.IS_REQUIRED_TIDSPUNKT),
                ValidationUtil.validateFodselsnummer(fodselsnummer),
                ValidationUtil.validateNonNullFieldMaxLength(grupperingsId, "grupperingsId", ValidationUtil.MAX_LENGTH_GRUPPERINGSID)
        );
    }
}
