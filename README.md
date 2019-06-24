[![CircleCI](https://circleci.com/gh/navikt/dittnav-event-schemas.svg?style=svg)](https://circleci.com/gh/navikt/dittnav-event-schemas)

[![Published on Maven](https://img.shields.io/maven-metadata/v/http/central.maven.org/maven2/no/nav/personbruker/dittnav/event-schemas/maven-metadata.xml.svg)](http://central.maven.org/maven2/no/nav/personbruker/dittnav/event-schemas/)

# DittNAV event schemas

Skjemaer for DittNAV sine Kafka-topics, og brukes for å sende notifikasjoner til DittNAV som kafka-events.

# Kom i gang

Generering av typer basert på skjemaene:

1. `mvn clean install`

## For å gjøre en release

1) Kjør `mvn release:prepare` lokalt på utviklermaskinen. Du blir bedt om å oppgi et nytt versjonsnummer.
   Kommandoen vil da opprette nye commits og git tags for deg, og pushe disse automatisk.
2) Når denne kommandoen er ferdig, kan du nullstille arbeidsområdet; `git add --all && git reset --hard`.


# Henvendelser

Spørsmål knyttet til koden eller prosjektet kan rettes mot https://github.com/orgs/navikt/teams/personbruker

## For NAV-ansatte

Interne henvendelser kan sendes via Slack i kanalen #team-personbruker.
