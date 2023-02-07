import java.util.stream.LongStream;

class Bud {
    private static final String NOTHING = "Nothing";

    public static String buddy(long start, long limit) {
        return LongStream.rangeClosed(start, limit)
                .mapToObj(i -> {
                            //perform buddy check
                            return LongStream.rangeClosed(start, limit)
                                    .mapToObj(candidate -> new Solution(i, candidate))
                                    .dropWhile(Solution::isNotValid)
                                    .findFirst()
                                    .orElse(Solution.empty());
                        }
                )
                .dropWhile(Solution::isEmpty)
                .dropWhile(Solution::isNotValid)
                .findFirst()
                .map(Solution::toString)
                .orElse(NOTHING);

        //calculateDivisors
        //calculateDivisorsSum


//        return NOTHING;
    }
}

record Solution(Long first, Long second) {
    public boolean isEmpty() {
        return first == null && second == null;
    }

    public static Solution empty() {
        return new Solution(null, null);
    }

    public boolean isValid() {
//        Let s(n) be the sum of these proper divisors of n.
//        Call buddy two positive integers such that the sum of the proper divisors of each number is one more than the other number:
//
//        (n, m) are a pair of buddy if s(m) = n + 1 and s(n) = m + 1
        if (first != null && second != null && !first.equals(second)) {
            long firstDivisorsSum = calculateDivisors(first);
            long secondDivisorsSum = calculateDivisors(second);
            return firstDivisorsSum == second + 1 && secondDivisorsSum == first + 1;
        } else return false;
    }

    public boolean isNotValid() {
        return !isValid();
    }

    @Override
    public String toString() {
        return "(" + first + " " + second + ')';
    }

    private static long calculateDivisors(long number) {
//        int sum = 0;
//        for (long i = 1; i <= Math.sqrt(number); i++) {
//            long t1 = i * (number / i - i + 1); // adding i every time it appears with numbers greater than or equal to itself
//            long t2 = (((number / i) * (number / i + 1)) / 2) - ((i * (i + 1)) / 2); // adding numbers that appear with i and are greater than i
//            sum += t1 + t2;
//        }
//        return sum;


        return LongStream.rangeClosed(1, number / 2)
                .filter(i -> number % i == 0)
                .sum();
    }
}