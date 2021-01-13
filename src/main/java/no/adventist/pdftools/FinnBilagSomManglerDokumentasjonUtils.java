package no.adventist.pdftools;

import financial.taxation.standardauditfile.no.AuditFile;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FinnBilagSomManglerDokumentasjonUtils {

    /**
     * Lister ut alle bilag som mangler dokumentasjon. Den sammenligner filnavn (bilagsnummer) mot listen med bilag
     */
    public static List<String> findMissing(String pathBilagsmappe, String pathRegnskapsdataXml) {
        List<String> bilagSomManglerDokumentasjon = new ArrayList<>();

        File files = new File(pathBilagsmappe);

        List<AuditFile.GeneralLedgerEntries.Journal> journalList = MatrixUtils.importRegnskapsdata(pathRegnskapsdataXml);

        List<String> list = Arrays.asList(files.list());

        for (AuditFile.GeneralLedgerEntries.Journal journal : journalList) {

            String description = journal.getDescription();
            AuditFile.GeneralLedgerEntries.Journal.Transaction transaction = journal.getTransaction().stream().findFirst().orElseThrow();
            String transactionId = transaction.getTransactionID();
            String transactionDate = transaction.getTransactionDate().toString();


            if (!description.contains("Vipps") && !description.contains("Fakturaoversikt Nettb") && !list.stream().anyMatch(item -> item.startsWith(transactionId))) {
                bilagSomManglerDokumentasjon.add(transactionId + " - " + transactionDate + " - " + description);
            }
        }
        return bilagSomManglerDokumentasjon;
    }
}
