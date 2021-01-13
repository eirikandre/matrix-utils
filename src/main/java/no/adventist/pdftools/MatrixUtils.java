package no.adventist.pdftools;

import financial.taxation.standardauditfile.no.AuditFile;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.util.List;

public class MatrixUtils {

    /**
     * Filen som skal mates inn her får du fra Matrix -> vedlikehold -> eksporter -> Eksport av regnskapsdata til SAF-T
     */
    public static List<AuditFile.GeneralLedgerEntries.Journal> importRegnskapsdata(String pathRegnskapsdataXml) {
        File saf = new File(pathRegnskapsdataXml);

        try {
            JAXBContext context = JAXBContext.newInstance(AuditFile.class);

            Unmarshaller un = context.createUnmarshaller();
            AuditFile auditFile = (AuditFile) un.unmarshal(saf);
            return auditFile.getGeneralLedgerEntries().getJournal();
        } catch (JAXBException e) {
            throw new RuntimeException("Feilet ved lesing av SAF-T fil", e);
        }
    }

}
