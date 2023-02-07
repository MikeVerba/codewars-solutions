import java.util.Optional;
import java.util.Stack;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BinaryOperator;

public class BraceChecker {
    private static final Character NORMAL_OPENER = '(';
    private static final Character NORMAL_CLOSER = ')';
    private static final Character CURLY_OPENER = '{';
    private static final Character CURLY_CLOSER = '}';
    private static final Character SQUARE_OPENER = '[';
    private static final Character SQUARE_CLOSER = ']';
    public static final Stack<Character> openedBraces = new Stack<>();

    public boolean isValid(String parens) {
        BinaryOperator<SolutionContext> noOperator = (ctx1, ctx2) -> {
            throw new IllegalStateException("This should never be used");
        };

        SolutionContext context = parens.chars()
                .mapToObj(c -> (char) c)
                .reduce(SolutionContext.initial(),
                        (solutionContext, character) -> solutionContext.processCharacter(solutionContext, character),
                        noOperator);
        return Optional.ofNullable(context).filter(SolutionContext::isValid).isPresent();
    }

    record SolutionContext(AtomicReference<Integer> normalCounter,
                           AtomicReference<Integer> curlyCounter,
                           AtomicReference<Integer> squareCounter,
                           AtomicReference<Boolean> shouldFail) {
        boolean isValid() {
            return normalCounter.get().equals(0) &&
                    curlyCounter.get().equals(0) &&
                    squareCounter.get().equals(0) &&
                    shouldFail.get().equals(false);
        }

        public static SolutionContext initial() {
            return new SolutionContext(new AtomicReference<>(0),
                    new AtomicReference<>(0),
                    new AtomicReference<>(0),
                    new AtomicReference<>(false));
        }

        public SolutionContext processCharacter(SolutionContext context, Character character) {
            if (character.equals(NORMAL_OPENER)) {
                context.normalCounter.getAndSet(context.normalCounter.get() + 1);
                openedBraces.push(character);

            } else if (character.equals(NORMAL_CLOSER) && openedBraces.peek().equals(NORMAL_OPENER)) {
                context.normalCounter.getAndSet(context.normalCounter.get() - 1);
                openedBraces.pop();
                if (context.normalCounter.get().equals(-1)) {
                    context.shouldFail.set(true);
                }
            }

            if (character.equals(CURLY_OPENER)) {
                context.curlyCounter.getAndSet(context.curlyCounter.get() + 1);
                openedBraces.push(character);
            } else if (character.equals(CURLY_CLOSER) && openedBraces.peek().equals(CURLY_OPENER)) {
                context.curlyCounter.getAndSet(context.curlyCounter.get() - 1);
                openedBraces.pop();
                if (context.curlyCounter.get().equals(-1)) {
                    context.shouldFail.set(true);
                }
            }

            if (character.equals(SQUARE_OPENER)) {
                context.squareCounter.getAndSet(context.squareCounter.get() + 1);
                openedBraces.push(character);
            } else if (character.equals(SQUARE_CLOSER) && openedBraces.peek().equals(SQUARE_OPENER)) {
                context.squareCounter.getAndSet(context.squareCounter.get() - 1);
                openedBraces.pop();
                if (context.squareCounter.get().equals(-1)) {
                    context.shouldFail.set(true);
                }
            }
            return new SolutionContext(context.normalCounter,
                    context.curlyCounter,
                    context.squareCounter,
                    context.shouldFail);
        }
    }
}

