import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class NextBiggerNumber {
    public static long nextBiggerNumber(long n) {

        List<Integer> integers = String.valueOf(n).chars()
                .mapToObj(c -> (char) c)
                .map(Integer::valueOf)
                .collect(Collectors.toList());


        integers.stream().forEach(i -> {
            integers.stream().forEach(j -> {


                    }

            );
                }
        );



        return n;

    }
}
