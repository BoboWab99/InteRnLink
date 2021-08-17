package RESOURCES;

import SERVICES.Helpers;

import java.io.*;

public class Testing {

    /*private static File createFiles() throws IOException {
        File newFile = new File("C:\\Users\\arnau\\IdeaProjects\\internlink\\src\\CVFILES\\newFile.docx");
        if (newFile.createNewFile()) {
            System.out.println("file created: " + newFile.getName());
            System.out.println("available in: " + newFile.getAbsolutePath());
            return newFile;
        }
        else {
            System.out.println("File already exists.");
            return null;
        }
    }*/

    /*private static void copyFileUsingStream(File source, File dest) throws IOException {
        InputStream fileInput = null;
        OutputStream fileOutput = null;
        try {
            fileInput = new FileInputStream(source);
            fileOutput = new FileOutputStream(dest);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = fileInput.read(buffer)) > 0) {
                fileOutput.write(buffer, 0, length);
            }
        }
        finally {
            assert fileInput != null;
            fileInput.close();
            assert fileOutput != null;
            fileOutput.close();
        }
    }*/

    public static void main(String[] args) {
        // createFiles();

        /*File readFrom = new File("C:\\Users\\arnau\\OneDrive - Strathmore University\\Documents\\Bobo-Lab2-Computer Networks.docx");
        File readTo = new File("C:\\Users\\arnau\\IdeaProjects\\internlink\\src\\CVFILES\\newFile.docx");

        long startTime = System.currentTimeMillis();
        copyFileUsingStream(readFrom, readTo);
        System.out.println("time taken to copy: " + (System.currentTimeMillis() - startTime) + "milliseconds.");*/

        try {
            final String filePath = "C:\\Users\\arnau\\Downloads\\text_document.txt";
            FileReader fileReader1 = new FileReader(new File(filePath));
            BufferedReader bufferedReader = new BufferedReader(fileReader1);
            StringBuilder stringBuilder = new StringBuilder();
            String txtLine;
            while ((txtLine = bufferedReader.readLine()) != null) {
                stringBuilder.append(txtLine).append("\n");
            }
            fileReader1.close();
            System.out.println("File: " + filePath);
            System.out.println(stringBuilder.toString());
        }
        catch (FileNotFoundException ex) {
            System.err.println("File not found");
            ex.printStackTrace();
        }
        catch (IOException ex) {
            System.err.println("Can't read this file");
            ex.printStackTrace();
        }
    }

}
