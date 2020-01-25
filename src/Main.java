import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) throws IOException {
        Path path = Paths.get("file/movementList.csv");
        List<Operation> operations = parseDataFromFile(path);

        double sumIncome = operations.stream().mapToDouble(s -> s.getIncome()).sum();
        double sumExpense = operations.stream().mapToDouble(s -> s.getExpense()).sum();
        System.out.println("Общий доход по выписке= " + sumIncome + " руб.");
        System.out.println("Общий расход по выписке= " + sumExpense + " руб.");

        for (String operation : getUniqueOperations(operations)) {
            System.out.print(operation + " - ");
            double sumEveryUniqueOperation = operations.stream()
                    .filter(s -> s.getDescription().indexOf(operation) > -1)
                    .mapToDouble(s -> s.getExpense()).sum();
            System.out.println(sumEveryUniqueOperation + " руб.");
        }
    }

    //<--------------------------------------------------------------------------
    private static List<Operation> parseDataFromFile(Path path) {
        List<Operation> operations = new ArrayList<>();
        try {
            List<String> data = Files.readAllLines(path);
            data.remove(0);
            for (String line : data) {
                String[] elements = line.split(",");
                if (elements.length == 8) {
                    operations.add(new Operation(elements[5], Double.parseDouble(elements[6]), Double.parseDouble(elements[7])));
                } else if (elements.length == 9) {
                    operations.add(new Operation(elements[5], Double.parseDouble(elements[6]),
                            Double.parseDouble(elements[7].replaceAll("\\D", "") + "."
                                    + elements[8].replaceAll("\\D", ""))));
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return operations;
    }

    private static List<String> getUniqueOperations(List<Operation> operations) {
        List<String> uniqueOperations = new ArrayList<>();
        uniqueOperations.addAll(getAllOperationtsWithSymbol(operations, "/"));
        uniqueOperations.addAll(getAllOperationtsWithSymbol(operations, "\\"));
        return uniqueOperations;
    }

    private static List<String> getAllOperationtsWithSymbol(List<Operation> operations, String symbol) {
        List<String> opers = operations.stream()
                .map(s -> s.getDescription().split("\\s{3,}"))
                .filter(s -> s[1].contains(symbol))
                .map(s -> s[1].substring(s[1].indexOf(symbol)))
                .distinct().collect(Collectors.toList());
        return opers;
    }


}

