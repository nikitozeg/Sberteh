package my.company.steps;

import java.io.*;

/**
 * @author Nikita Ivanov tazg@ya.ru
 *         Date: 14.02.16
 */
public class DataFile {
    public BufferedReader br;
    String[] values = new String[10];
    String inputPath = System.getProperty("user.dir");  //relative path to input data file

    public DataFile() throws FileNotFoundException {

        br = new BufferedReader(new FileReader(inputPath + "\\1.txt"));
    }

    public int getCountLines() throws IOException {

        InputStream is = new BufferedInputStream(new FileInputStream(inputPath + "\\1.txt"));
        try {
            byte[] c = new byte[1024];
            int count = 0;
            int readChars = 0;
            boolean empty = true;
            while ((readChars = is.read(c)) != -1) {
                empty = false;
                for (int i = 0; i < readChars; ++i) {
                    if (c[i] == '\n') {
                        ++count;
                    }
                }
            }
            return (count == 0 && !empty) ? 1 : count;
        } finally {
            is.close();
        }
    }

    public String[] getCurrentLine() {

        try {
            values =  br.readLine().split(";");  //Separating values by special character ;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return values;

    }

}

