[![CircleCI](https://circleci.com/gh/navikt/brukernotifikasjon-schemas.svg?style=svg)](https://circleci.com/gh/navikt/brukernotifikasjon-schemas)

[![Published on Maven](https://img.shields.io/maven-metadata/v/http/central.maven.org/maven2/no/nav/brukernotifikasjon-schemas/maven-metadata.xml.svg)](http://central.maven.org/maven2/no/nav/brukernotifikasjon-schemas/)

# Brukernotifikasjon-schemas

Avro-skjemaer for brukernotifikasjon-Kafka-topic-ene som bla DittNAV bruker for å vise notifikasjoner til sluttbrukere.

# Feltbeskrivelser

### Kafka key (Nokkel)

#### systembruker
Navn på systembruker som har produsert eventet.

#### eventId 
Den unike identifikatoren per event, og den må være unik innen for hver `systembruker` (produsent). Det er denne `eventID`-en som benyttes for å deaktivere eventer som er utført. Dette gjøres ved å sende et event av typen done, med referanse til det eventet som ikke skal vises på DittNAV lengre.



### Kafka events 
### Oppgave, Beskjed og Innboks
Beskrivelse av feltene som er felles for disse tre eventtypene.

#### tidspunkt
Et tidspunkt som noe skjedde, f.eks. da saksbehandlingen av en søknad var ferdig.

#### fodselsnummer
Fødselsnummeret til brukeren som eventet tilhører.

#### grupperingsId
Feltet grupperingsId brukes for å kunne samle alle eventer som hører til en sak, søknad eller et dokument. Dette er typisk en saksId, søknadsId eller dokumentId, men dere velger selv hvilken verdi dere putter der. Men det er viktig at det er samme verdi for alle eventer som skal gruppers samme. Typisk bruke av dette som vi ser for oss er oppbygging av tidslinjer.

#### tekst
Dette er teksten faktisk vises i eventet. Det er ikke noen støtte for å formatere teksten som settes i dette feltet.

#### link
Dette er lenken som blir aktivert i det en bruker trykker på selve eventet. Kan være en komplett URL inkludert protokoll, f.eks. `https`.

#### sikkerhetsnivaa
Angir sikkerhetsnivået for informasjonen som eventet innholder.
DittNAV søtter at en bruker er innlogget på nivå 3, hvis denne brukeren har eventer med nivå 4 så vil disse eventene bli "grået ut". Brukeren ser bare hvilken type event dette er, men ikke noe av innholdet. For å se innholdet må brukeren steppe opp til et høyere innloggingsnivå.

#### synligFremTil
Et tidspunkt på når eventet ikke skal være synlig mer, f.eks beskjeden skal kun være synlig 7 dager. SynligFramTil = null -> synlig for alltid, med mindre brukeren selv krysser den ut fra forsiden av DittNAV. 


### Done
Beskrivelse av feltene til eventet `done`.

#### tidspunkt
Et tidspunkt som noe skjedde, f.eks. da saksbehandlingen av en søknad var ferdig.

#### fodselsnummer
Fødselsnummeret til brukeren som eventet tilhører.

#### eventId
`EventId`-en til eventet som nå ikke skal være aktivt lengre. Eventet med den eventId-en vil ikke dukke opp på forsiden av DittNAV lengre, men vil fortsatt være tilgjengelig i historikken over eventer på DittNAV. 

#### grupperingsId
Dette feltet er med for eventtypen `done` for å sikre at man får med alle eventer knyttet til en `grupperingsId`, og settet av eventer blir ikke komplett uten å ha med `done`-eventene. Dette er bla viktig for å kunne generere tidslinjer ut i fra eventer.



### Statusoppdatering
TBA

# Generering av typer basert på skjemaene

1. `mvn clean install`

## For å gjøre en release

1) Kjør `mvn release:prepare` lokalt på utviklermaskinen. Du blir bedt om å oppgi et nytt versjonsnummer.
   Kommandoen vil da opprette nye commits og git tags for deg, og pushe disse automatisk.
2) Når denne kommandoen er ferdig, kan du nullstille arbeidsområdet; `git add --all && git reset --hard`.

# Henvendelser

Spørsmål knyttet til koden eller prosjektet kan rettes mot https://github.com/orgs/navikt/teams/personbruker

## For NAV-ansatte

Interne henvendelser kan sendes via Slack i kanalene #team-personbruker og #brukernotifikasjon_pilot 
