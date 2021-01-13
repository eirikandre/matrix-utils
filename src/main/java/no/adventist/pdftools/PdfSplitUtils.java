package no.adventist.pdftools;

import org.apache.pdfbox.multipdf.Splitter;
import org.apache.pdfbox.pdmodel.PDDocument;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.List;

public class PdfSplitUtils {

    /**
     * Splitter pdf dokument slik at hver side blir ett separert dokument
     */
    public static void splitPdf(String input, String outputFolder) {
        File file = new File(input);

        PDDocument document;
        try {
            document = PDDocument.load(file);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load pdf document", e);
        }

        Splitter splitter = new Splitter();

        List<PDDocument> pages;
        try {
            pages = splitter.split(document);
        } catch (IOException e) {
            throw new RuntimeException("Failed to split document", e);
        }

        Iterator<PDDocument> iterator = pages.listIterator();

        int i = 0;
        while (iterator.hasNext()) {
            PDDocument pd = iterator.next();
            Path path = Paths.get(outputFolder, file.getName().replace(".pdf", ""), String.valueOf(i), ".pdf");

            ++i;

            try {
                pd.save(path.toFile());
            } catch (IOException e) {
                throw new RuntimeException("Failed to save file", e);
            }
        }
        try {
            document.close();
        } catch (IOException e) {
            throw new RuntimeException("Failed to close file", e);
        }
    }


}
