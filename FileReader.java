import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class FileReader {
  public static String readFile(String name) {
    try {
      File file = new File(name);
      Scanner scanner = new Scanner(file);
      StringBuffer stringBuffer = new StringBuffer();
      while (scanner.hasNextLine()) {
        stringBuffer.append(scanner.nextLine());
      }
      scanner.close();
      return stringBuffer.toString();
    } catch (FileNotFoundException FNFE){
      System.out.println("File not found");
      return null;
    }

  }
}
