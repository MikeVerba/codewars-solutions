import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BinaryOperator;

public class ValidParentheses {
    private static final Character OPENER = '(';
    private static final Character CLOSER = ')';

    public static boolean validParentheses(String parens) {
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

    record SolutionContext(AtomicReference<Integer> counter, AtomicReference<Boolean> shouldFail) {
        boolean isValid() {
            return counter.get().equals(0) && shouldFail.get().equals(false);
        }

        public static SolutionContext initial() {
            return new SolutionContext(new AtomicReference<>(0), new AtomicReference<>(false));
        }

        public SolutionContext processCharacter(SolutionContext context, Character character) {
            if (character.equals(OPENER)) {
                context.counter.getAndSet(context.counter.get() + 1);
            } else if (character.equals(CLOSER)) {
                context.counter.getAndSet(context.counter.get() - 1);
                if (context.counter.get().equals(-1)) {
                    context.shouldFail.set(true);
                }
            }
            return new SolutionContext(context.counter, context.shouldFail);
        }
    }
}
