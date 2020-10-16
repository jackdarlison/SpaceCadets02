import java.util.HashMap;
import java.util.List;
import java.util.Arrays;

public class Solution {

  static HashMap<String, Integer> variables = new HashMap<String, Integer>();


  public static void main(String[] args) {
    String[] program = parse("multiply.txt");
    interprete(program);
  }

  public static String[] parse(String fileName) {
    String programText = FileReader.readFile(fileName);
    String[] tempProgramLines = programText.split("([;])");
    String[] programLines = new String[tempProgramLines.length];
    for (int i = 0; i < tempProgramLines.length; i++) {
      programLines[i] = tempProgramLines[i].trim();
    }
    return programLines;
  }

  public static void interprete(String[] program) {
    int pPtr = 0; //programPtr

    while (pPtr < program.length) {
      String[] line = program[pPtr].split("\\s");
      switch (line[0]) {
        case "clear":
          variables.put(line[1], 0);
          break;
        case "incr":
          if (variables.containsKey(line[1])) {
            variables.put(line[1], variables.get(line[1]) + 1);
          } else {
            variables.put(line[1], 0);
          }
          break;
        case "decr":
          if (variables.containsKey(line[1])) {
            variables.put(line[1], variables.get(line[1]) - 1);
          } else {
            variables.put(line[1], 0);
          }
          break;
        case "while":
          if (!variables.containsKey(line[1])) {
            variables.put(line[1], 0);
          }
          int wPtrStart = pPtr + 1;
          int wPtrEnd = 0;
          int tempPtr = 1;
          while (variables.get(line[1]) > 0) {
            wPtrStart = pPtr + 1;
            tempPtr = 0;
            int whileCounter = 1;
            int endCounter = 0;
            while (endCounter < whileCounter) {
              tempPtr++;
              if (program[pPtr + tempPtr].split("\\s")[0].equalsIgnoreCase("end")) {
                endCounter++;
              } else if (program[pPtr + tempPtr].split("\\s")[0].equalsIgnoreCase("while")) {
                whileCounter++;
              }
            }
            wPtrEnd = pPtr + tempPtr;
            //This recursively runs the contents of the while loops.
            //Achieved by sending the contents as its own program.
            interprete(Arrays.copyOfRange(program, wPtrStart, wPtrEnd));
          }
          //update the program pointer to after the while loop.
          if (variables.get(line[1]) <= 0) {
            pPtr += tempPtr;
          }
          break;
        case "end":
          break;
        //have added a print command to make testing nicer.
        case "print":
          if (variables.containsKey(line[1])) {
            System.out.println(variables.get(line[1]));
          } else {
            System.out.println("variable not found.");
          }
          break;
        default:
          System.out.println("invalid command");
          System.exit(-1);
      }
      pPtr++;
    }
  }
}
