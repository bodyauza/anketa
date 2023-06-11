package com.project.anketa.services;

import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTSectPr;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class WordService {

    public void toWord(String name, String correctNumber, String email) throws IOException {

            String[] nmbArray = new String[0];
            final ArrayList<String> numberandnames = new ArrayList(Arrays.asList(nmbArray));

            String[] nmbArray1 = new String[0];
            final ArrayList<String> numberandnames1 = new ArrayList(Arrays.asList(nmbArray1));

            String[] nmbArray2 = new String[0];
            final ArrayList<String> numberandnames2 = new ArrayList(Arrays.asList(nmbArray2));

            File f = new File("D:\\Anketa.docx");

            f.createNewFile();

            if (f.exists()) {

                System.out.println("\n Добавлено в базу");


                numberandnames.add(name);
                numberandnames1.add(correctNumber);
                numberandnames2.add(email);

                //чтение файла
                FileInputStream fis;
                try {
                    fis = new FileInputStream(f.getAbsolutePath());
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }

                XWPFDocument docxModel = null;
                try {
                    docxModel = new XWPFDocument(fis);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                docxModel.createParagraph();

                String documentLine = docxModel.getDocument().toString();
                CTSectPr ctSectPr = docxModel.getDocument().getBody().addNewSectPr();
                XWPFParagraph bodyParagraph = docxModel.createParagraph();
                bodyParagraph.setAlignment(ParagraphAlignment.LEFT);

                //создание параграфа
                XWPFRun paragraphConfig = bodyParagraph.createRun();
                XWPFParagraph paragraph = docxModel.createParagraph();

                paragraphConfig.setItalic(true);
                paragraphConfig.setFontSize(20);
                paragraphConfig.setColor("170101");
                paragraphConfig.setFontSize(12);

                List<XWPFParagraph> paragraphs = docxModel.getParagraphs();

                paragraphConfig.setText(numberandnames.toString());
                paragraphConfig.setText(numberandnames1.toString());
                paragraphConfig.setText(numberandnames2.toString());

                try {
                    fis.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                //изменение файла
                try {
                    FileOutputStream outputStream = new FileOutputStream(f.getAbsolutePath());
                    docxModel.write(outputStream);
                } catch (Exception var21) {
                    throw new RuntimeException(var21);
                }

                //запуск Word
                Desktop desktop = null;
                if (Desktop.isDesktopSupported()) {
                    desktop = Desktop.getDesktop();

                    try {
                        desktop.open(new File(f.getAbsolutePath()));
                    } catch (IOException var4) {
                        var4.printStackTrace();
                    }
                }
            }
    }
}
