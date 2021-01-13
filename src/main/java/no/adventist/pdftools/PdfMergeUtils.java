package no.adventist.pdftools;

import org.apache.pdfbox.io.MemoryUsageSetting;
import org.apache.pdfbox.multipdf.PDFMergerUtility;

import java.io.File;
import java.util.Arrays;
import java.util.List;

public class PdfMergeUtils {

    /**
     * Slår sammen en mappe med pdf-dokumenter til ett pdf-dokument
     */
    public static void mergeDocuments(String folderToMerge, String pathMergedPdf) throws Exception {
        File file = new File(folderToMerge);

        List<File> filer = Arrays.asList(file.listFiles());

        PDFMergerUtility pdfMerger = new PDFMergerUtility();

        pdfMerger.setDestinationFileName(pathMergedPdf);

        for (File file1 : filer) {
            pdfMerger.addSource(file1);
        }

        pdfMerger.mergeDocuments(MemoryUsageSetting.setupMainMemoryOnly());
    }

}
