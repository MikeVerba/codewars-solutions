import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;
import java.util.Scanner;

public class CodigaSandbox {
    public static void main(String[] args) {
//        Scanner scan = new Scanner(System.in);
//        String input = scan.nextLine();
//        String output = new StringBuilder(input).reverse().toString();
//        System.out.print(output);

        try {
            Desktop.getDesktop().browse(new URI("http://www.github.com"));
        } catch (IOException | URISyntaxException e1) {
            e1.printStackTrace();
        }

    }
}

record MyRecord(String name, String value) {
}

class Result {
    private final String first;
    private final String second;

    private Result(String first, String second) {
        this.first = first;
        this.second = second;
    }

    static Result of(String first, String second) {
        return new Result(first, second);
    }

    static Result empty() {
        return Result.of(null, null);
    }

    boolean isEmpty() {
        return this.first == null && this.second == null;
    }

    public Optional<String> getFirst() {
        return Optional.ofNullable(first);
    }

    public Optional<String> getSecond() {
        return Optional.ofNullable(second);
    }
}


