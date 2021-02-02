package no.adventist.pdftools;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class SkrivBilagsnummerPdfUtils {

    public static void addBilagsnummerToPdfPages(String pathBilagsmappe, String outputFolder) throws Exception {
        File file = new File(pathBilagsmappe);

        List<File> filer = Arrays.asList(file.listFiles());

        for (File file1 : filer) {

            PDDocument document = PDDocument.load(file1);

            for (PDPage page : document.getPages()) {
                PDPageContentStream contentStream = new PDPageContentStream(document, page, PDPageContentStream.AppendMode.APPEND, true);

                contentStream.beginText();
                contentStream.setFont(PDType1Font.TIMES_ROMAN, 14);
                contentStream.newLineAtOffset(10, 10);
                contentStream.showText("Bilagsnummer: " + file1.getName().substring(0, 4));
                contentStream.endText();
                contentStream.close();
            }

            Path path = Paths.get(outputFolder, file1.getName().substring(0, 4) + ".pdf");

            document.save(path.toFile());
            document.close();
        }
    }

}
