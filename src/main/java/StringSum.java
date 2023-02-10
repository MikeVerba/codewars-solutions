import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

public class StringSum {
    public static String sumStrings(String a, String b) {
        var additionSolution = AdditionSolution.initial(a, b);

        while (additionSolution.isNotReady()) {
            additionSolution = additionSolution.performAddition();
        }

        return additionSolution.getResult();
    }
}

class AdditionSolution {
    private final AtomicInteger currentIndex;
    private final AtomicInteger carry;
    private final StringBuilder result;
    private final Operand o1;
    private final Operand o2;

    private AdditionSolution(AtomicInteger currentIndex, AtomicInteger carry, StringBuilder result, Operand o1, Operand o2) {
        this.currentIndex = currentIndex;
        this.carry = carry;
        this.result = result;
        this.o1 = o1;
        this.o2 = o2;
    }

    public static AdditionSolution initial(String s1, String s2) {
        var o1 = createInitialOperand(s1);
        var o2 = createInitialOperand(s2);


        return new AdditionSolution(new AtomicInteger(0), new AtomicInteger(0), new StringBuilder(), o1, o2);
    }

    public static AdditionSolution of(AtomicInteger currentIndex, AtomicInteger carry, StringBuilder result, Operand o1, Operand o2) {
        return new AdditionSolution(currentIndex, carry, result, o1, o2);
    }

    public AdditionSolution performAddition() {
        int additionResult = getCurrentOperand(this.o1) + getCurrentOperand(this.o2) + carry.get();
        this.result.append(calculateNumberStoredAtCurrentPosition(additionResult));
        this.carry.set(calculateCarry(additionResult));
        return AdditionSolution.of(new AtomicInteger(this.currentIndex.incrementAndGet()), this.carry, this.result, this.o1, this.o2);
    }

    private int calculateNumberStoredAtCurrentPosition(int additionResult) {
        return additionResult % 10;
    }

    private int calculateCarry(int additionResult) {
        return additionResult / 10;
    }

    private int getCurrentOperand(Operand p) {
        return Integer.parseInt(String.valueOf(p.getNumber(currentIndex.get())));
    }

    public String getResult() {
        String toString = this.result.reverse().toString();
        return removeLeadingZeros(toString);
    }

    public boolean isNotReady() {
        int max = Math.max(o1.value().length(), o2.value().length());
        return !(currentIndex.get() > max && carry.get() == 0);
    }

    private static Operand createInitialOperand(String input) {
        return Optional.ofNullable(input)
                .map(AdditionSolution::removeLeadingZeros)
                .map(AdditionSolution::reverse)
                .map(Operand::new)
                .orElse(new Operand("0"));
    }

    private static String reverse(String input) {
        return new StringBuilder(input).reverse().toString();
    }

    private static String removeLeadingZeros(String s) {
        return s.replaceFirst("^0+(?!$)", "");

    }
}

record Operand(String value) {
    Character getNumber(int index) {
        return Optional.ofNullable(value)
                .filter(v -> index < v.length())
                .map(v -> v.charAt(index))
                .orElse('0');
    }
}




