[![CircleCI](https://circleci.com/gh/navikt/brukernotifikasjon-schemas.svg?style=svg)](https://circleci.com/gh/navikt/brukernotifikasjon-schemas)

[![Published on Maven](https://img.shields.io/maven-metadata/v/http/central.maven.org/maven2/no/nav/brukernotifikasjon-schemas/maven-metadata.xml.svg)](http://central.maven.org/maven2/no/nav/brukernotifikasjon-schemas/)

# Brukernotifikasjon-schemas

Skjemaer for brukernotifikasjon-Kafka-topic-ene som bla DittNAV bruker for å vise notifikasjoner til sluttbrukere.

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
