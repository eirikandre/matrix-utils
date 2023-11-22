Prosjektet er ikke i bruk lenger, og derfor arkivert.


# Matrix utils

Prosjektet automatiserer en del av arbeidet med å sammenstille dokumentasjon for regnskap ført i Matrix

Tanken bak er noe slik
* Eksportere dokumentasjon fra DNB bedriftsnettbank samlet i en pdf (Manuell jobb)
* Eksportere regnskapsbilag som SAF-T xml
* Splitte opp alle sidene (PdfSplitUtils)
* Automatisk gi innbetalingsmelindger bilagsnummer som filnavn (RenamePDfToTransactionIdUtils)
* Organisere hvert dokument slik at bilagsnummer funnet i regnskapsprogrammet blir satt til filnavnet
* Finne hvilke bilag som mangler (FinnBilagSomManglerDokumentasjon)
* Slå sammen alle dokumentene til en pdf fil (PdfMergeUtils)
