package br.tulli.jm.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

public class PdfCreator {

  public static FileInputStream createPDF(String filename, String ...PDFtext) throws Exception {
    Document document = new Document();
    File file = new File(filename + ".pdf");
    file.createNewFile();
    PdfWriter.getInstance(document, new FileOutputStream(file));
    document.open();
    for (String text : PDFtext) {
      document.add(new Paragraph(text));
    }
    document.close();
    return new FileInputStream(file);
  }
}
