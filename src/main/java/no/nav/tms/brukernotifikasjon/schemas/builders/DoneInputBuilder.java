package no.nav.tms.brukernotifikasjon.schemas.builders;


import no.nav.tms.brukernotifikasjon.schemas.builders.util.ValidationUtil;
import no.nav.tms.brukernotifikasjon.schemas.input.DoneInput;

import java.time.LocalDateTime;

public class DoneInputBuilder {

    private LocalDateTime tidspunkt;

    public DoneInputBuilder withTidspunkt(LocalDateTime tidspunkt) {
        this.tidspunkt = tidspunkt;
        return this;
    }

    public DoneInput build() {
        return new DoneInput(
                ValidationUtil.localDateTimeToUtcTimestamp(tidspunkt, "tidspunkt", ValidationUtil.IS_REQUIRED_TIDSPUNKT)
        );
    }
}
