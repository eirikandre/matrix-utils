package no.adventist.pdftools;

import financial.taxation.standardauditfile.no.AuditFile;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.File;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static no.adventist.pdftools.MatrixUtils.importRegnskapsdata;

public class RenamePdfToTransactionIdUtils {

    /**
     *  Teorien i denne metoden er å ta innbetalingsmeldingene fra DNB og matche de mot regnskapsdata for å finne riktig
     *  bilagsnummer som da settes som filnavn.
     */
    public static void renameDnbInnbetalingsmeldingTilRiktigBilagsnummer(String pathRegnskapsbilag, String pathPdfMappe) {
        try {
            File file = new File(pathPdfMappe);

            List<AuditFile.GeneralLedgerEntries.Journal> journalposter = importRegnskapsdata(pathRegnskapsbilag);

            if (!file.exists() || file.isFile()) {
                throw new RuntimeException("Path som er angitt er ikke en eksisterende mappe");
            }

            for (File listFile : Objects.requireNonNull(file.listFiles())) {
                PDDocument document = PDDocument.load(listFile);

                PDFTextStripper pts = new PDFTextStripper();
                String text = pts.getText(document);

                List<String> string = Arrays.asList(text.split("\n"));

                String navn = string.stream().filter(item -> item.startsWith("Navn:")).map(item -> item.replace("Navn:", "").trim()).findFirst().orElseThrow();
                String belop = string.stream().filter(item -> item.startsWith("Beløp:")).map(item -> item.replace("Beløp:", "").trim()).findFirst().orElseThrow();
                String dato = string.stream().filter(item -> item.startsWith("Bokført dato:")).map(item -> item.replace("Bokført dato:", "").trim()).findFirst().orElseThrow();

                Optional<AuditFile.GeneralLedgerEntries.Journal> first = journalposter.stream()
                        .filter(item -> item.getDescription().contains(navn)
                                && getStringDate(item).equals(dato)
                                && item.getTransaction().get(0).getLine().stream().anyMatch(line -> line.getDebitAmount().getAmount().equals(new BigDecimal(belop)))).findFirst();

                document.close();

                if (first.isPresent()) {
                    document.save(listFile.getParentFile().getAbsolutePath() + first.get().getJournalID() + "_" + listFile.getName());
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Feilet", e);
        }
    }

    private static String getStringDate(AuditFile.GeneralLedgerEntries.Journal item) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        return sdf.format(item.getTransaction().get(0).getTransactionDate());
    }
}
