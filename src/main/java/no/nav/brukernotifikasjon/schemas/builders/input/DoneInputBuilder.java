package no.nav.brukernotifikasjon.schemas.builders.input;


import no.nav.brukernotifikasjon.schemas.builders.util.ValidationUtil;
import no.nav.brukernotifikasjon.schemas.input.DoneInput;

import java.time.LocalDateTime;

public class DoneInputBuilder {

    private LocalDateTime tidspunkt;
    private String grupperingsId;

    public DoneInputBuilder withTidspunkt(LocalDateTime tidspunkt) {
        this.tidspunkt = tidspunkt;
        return this;
    }

    public DoneInputBuilder withGrupperingsId(String grupperingsId) {
        this.grupperingsId = grupperingsId;
        return this;
    }

    public DoneInput build() {
        return new DoneInput(
                ValidationUtil.localDateTimeToUtcTimestamp(tidspunkt, "tidspunkt", ValidationUtil.IS_REQUIRED_TIDSPUNKT),
                ValidationUtil.validateNonNullFieldMaxLength(grupperingsId, "grupperingsId", ValidationUtil.MAX_LENGTH_GRUPPERINGSID)
        );
    }
}
