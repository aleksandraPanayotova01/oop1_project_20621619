import java.io.*;
import java.nio.charset.StandardCharsets;

public class CopyContent {
    public void copyContent(String readFrom,String writeTo){
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(readFrom), StandardCharsets.UTF_8));
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(writeTo), StandardCharsets.UTF_8));

            String line;

            while ((line = reader.readLine()) != null) {
                writer.write(line + "\n");
            }

            reader.close();
            writer.close();
        }  catch (Exception e) {
            System.out.println("Please check parameters of command!");
        }
    }

}
