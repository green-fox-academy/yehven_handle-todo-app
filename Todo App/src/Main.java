import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class Main {


    public static void main(String[] args) {
        if (args.length == 0) {
            printUsage();
        }
        else if (args.length != 0 && args[0].equals("-l")){
            listTasks();
        }
        else if (args.length != 0 && args[0].equals("-a")){
            addTasks(args);
        }
        else if (args.length != 0 && args[0].equals("-r")){
            removeTasks(args);
        }
        else if (args.length != 0 && args[0].equals("-c")){
            checkTasks (args);
        }

    }

    private static void printUsage() {
        System.out.println("Command Line Todo application\n" +
                "=============================\n" +
                "\n" +
                "Command line arguments:\n" +
                " -l   Lists all the tasks\n" +
                " -a   Adds a new task\n" +
                " -r   Removes an task\n" +
                " -c   Completes an task");
    }

    private static void listTasks (){
        try {
            Path listPath = Paths.get("D:/Greenfox/Greenfox2/yehven_handle-todo-app/Todo App/src/my-file.txt");
            List<String> lines = Files.readAllLines(listPath);
            int taskNumber = 1;
            if (lines.get(0) != null) {
                for (int i = 0; i < lines.size(); i++) {
                    System.out.println(taskNumber + " - " + lines.get(i));
                    taskNumber++;
                }
            }
            else {
                System.out.println("No todos for today! :)");
            }
        } catch (Exception e) {
            System.out.println("Unable to read the file.");
        }
    }

    private static void addTasks(String args[]){
        try {
            Path listPath = Paths.get("D:/Greenfox/Greenfox2/yehven_handle-todo-app/Todo App/src/my-file.txt");
            List<String> lines = Files.readAllLines(listPath);
            lines.add(args [1]);
            Files.write(listPath, lines);
        } catch (Exception e) {
            System.out.println("Unable to add to file");
        }
    }

    public static void removeTasks(String[] args) {
        try {
            Path listPath = Paths.get("D:/Greenfox/Greenfox2/yehven_handle-todo-app/Todo App/src/my-file.txt");
            List<String> lines = Files.readAllLines(listPath);
            int taskLineNumber = Integer.parseInt(args[1]);
            if (lines.size() < taskLineNumber) {
                System.out.println("Unable to remove from file: index out of bound");
            } else if (lines.size() >= 2 && lines.size() >= taskLineNumber) {
                lines.remove(taskLineNumber - 1);
            }
            Files.write(listPath, lines, Charset.defaultCharset());

        } catch (NumberFormatException notANumber) {
            System.out.println("Unable to remove from file: index not a number");
        } catch (Exception noIndex) {
            System.out.println("Unable to remove from file: no index provided");
        }
    }
    public static void checkTasks (String[] args) {
        try {
            Path listPath = Paths.get("D:/Greenfox/Greenfox2/yehven_handle-todo-app/Todo App/src/my-file.txt");
            List<String> lines = Files.readAllLines(listPath);

            int argLineNumber = Integer.parseInt(args[1]);
            int lineNr = 0;

            if (argLineNumber > lines.size()) {
                System.out.println("Unable to check: index is out of bound");
            }

            for (int i=0; i < lines.size(); i++) {
                if (!lines.get(i).startsWith("[ ]") && !lines.get(i).startsWith("[X]") ) {
                    String buffer1 = lines.get(i);
                    lines.remove(i);
                    lines.add(i, "[ ]" + buffer1);
                }
            }

            for (int i=0; i < lines.size(); i++) {
                if (lines.get(i).startsWith("[X]")) {
                    String buffer = lines.get(i);
                    lines.remove(i);
                    lines.add(i, buffer);
                    lineNr++;
                } else if (lines.get(i).startsWith("[ ]") && (lineNr != argLineNumber -1)) {
                    String buffer = lines.get(i);
                    lines.remove(i);
                    lines.add(i, buffer);
                    lineNr++;
                } else if (lines.get(i).startsWith("[ ]") && (lineNr == argLineNumber -1)) {
                    String buffer = lines.get(i).substring(3);
                    lines.remove(i);
                    lines.add(i, "[X]" + buffer);
                    lineNr++;
                }
            }
            Files.write(listPath, lines, Charset.defaultCharset());
        } catch (NumberFormatException notANumber) {
            System.out.println("Unable to remove: index not a number");
        } catch (Exception noIndex) {
            System.out.println("Unable to remove: no index provided");
        }
    }


}
