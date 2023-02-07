import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Dinglemouse {
    public static String[] whoEatsWho(final String zoo) {
        List<String> animals = convertToList(zoo);
        List<String> result = prepareInitialResult(zoo);

        AtomicBoolean resultHasChanged = new AtomicBoolean(true);

        while (resultHasChanged.get()) {
            resultHasChanged.set(false);

            Stream.iterate(0, i -> i < animals.size(), i -> ++i)
                    .map(i -> Optional.of(getAnimalByIndex(animals, i))
                            .filter(animalToBeEaten -> RuleBook.isAnimalSupported(animalToBeEaten.name()))
                            .map(current -> Optional.of(getValidationContext(animals, current.name(), i - 1, integer -> integer > -1))
                                    .filter(ValidationContext::hasValidationResult)
                                    .orElse(getValidationContext(animals, current.name(), i + 1, integer -> integer < animals.size()))
                            ).orElse(ValidationContext.empty())
                    )
                    .filter(ValidationContext::hasValidationResult)
                    .findFirst()
                    .ifPresent(context -> {
                        context.getAnimalIndexToRemove().ifPresent(index -> animals.remove((int) index));
                        context.getActionToBeAddedToResult().ifPresent(result::add);
                        resultHasChanged.set(true);
                    });
        }
        return prepareResult(animals, result);
    }

    private static ValidationContext getValidationContext(List<String> animals, String current, int index, Predicate<Integer> predicate) {
        if (predicate.test(index)) {
            return ValidationContext.of(RuleBook.validate(current, getAnimalByIndex(animals, index)));
        } else {
            return ValidationContext.empty();
        }
    }

    private static Animal getAnimalByIndex(List<String> animals, int index) {
        return new Animal(animals.get(index), index);
    }

    private static ArrayList<String> prepareInitialResult(String zoo) {
        return new ArrayList<>(List.of(zoo));
    }

    private static List<String> convertToList(String zoo) {
        return Arrays.stream(zoo.split(","))
                .map(String::trim)
                .collect(Collectors.toList());
    }

    private static String[] prepareResult(List<String> animals, List<String> result) {
        result.add(String.join(",", animals));
        return result.toArray(new String[]{});
    }
}

class RuleBook {
    private static final Map<String, Set<String>> rules = Map.ofEntries(
            Map.entry("antelope", Set.of("grass")),
            Map.entry("big-fish", Set.of("little-fish")),
            Map.entry("bug", Set.of("leaves")),
            Map.entry("bear", Set.of("big-fish", "bug", "chicken", "cow", "leaves", "sheep")),
            Map.entry("chicken", Set.of("bug")),
            Map.entry("cow", Set.of("grass")),
            Map.entry("fox", Set.of("chicken", "sheep")),
            Map.entry("giraffe", Set.of("leaves")),
            Map.entry("lion", Set.of("antelope", "cow")),
            Map.entry("panda", Set.of("leaves")),
            Map.entry("sheep", Set.of("grass"))
    );

    static ValidationResult validate(String currentAnimal, Animal animalToBeChecked) {
        return Optional.ofNullable(currentAnimal)
                .filter(rules::containsKey)
                .map(currentAnimalPresentInRules -> validateRules(currentAnimalPresentInRules, animalToBeChecked))
                .orElseGet(ValidationResult::empty);
    }

    private static ValidationResult validateRules(String currentAnimal, Animal animalToBeChecked) {
        return rules.get(currentAnimal).stream()
                .filter(edibleAnimal -> edibleAnimal.equals(animalToBeChecked.name()))
                .findAny()
                .map(animalNameToBeRemoved -> ValidationResult.of(new Animal(animalNameToBeRemoved, animalToBeChecked.index()),
                        createActionToBeTaken(currentAnimal, animalNameToBeRemoved)))
                .orElse(ValidationResult.empty());
    }

    public static boolean isAnimalSupported(String currentAnimal) {
        return rules.containsKey(currentAnimal);
    }

    private static String createActionToBeTaken(String currentAnimal, String edibleAnimal) {
        return currentAnimal + " eats " + edibleAnimal;
    }
}

class ValidationResult {
    private final Animal animalToRemove;
    private final String actionToBeAddedToResult;

    private ValidationResult(Animal animalToRemove, String actionToBeAddedToResult) {
        this.animalToRemove = animalToRemove;
        this.actionToBeAddedToResult = actionToBeAddedToResult;
    }

    static ValidationResult of(Animal animalToRemove, String actionToBeAddedToResult) {
        return new ValidationResult(animalToRemove, actionToBeAddedToResult);
    }

    static ValidationResult empty() {
        return ValidationResult.of(null, null);
    }

    boolean isEmpty() {
        return this.animalToRemove == null && this.actionToBeAddedToResult == null;
    }

    public Optional<String> getAnimalNameToRemove() {
        return Optional.ofNullable(animalToRemove).map(Animal::name);
    }

    public Optional<Integer> getAnimalIndexToRemove() {
        return Optional.ofNullable(animalToRemove).map(Animal::index);
    }

    public Optional<String> getActionToBeAddedToResult() {
        return Optional.ofNullable(actionToBeAddedToResult);
    }
}

class ValidationContext {
    private final ValidationResult validationResult;

    private ValidationContext(ValidationResult validationResult) {
        this.validationResult = validationResult;
    }

    static ValidationContext empty() {
        return new ValidationContext(ValidationResult.empty());
    }

    static ValidationContext of(ValidationResult validationResult) {
        return new ValidationContext(validationResult);
    }

    boolean hasValidationResult() {
        return !this.validationResult.isEmpty();
    }

    public Optional<Integer> getAnimalIndexToRemove() {
        return this.validationResult.getAnimalIndexToRemove();
    }

    public Optional<String> getActionToBeAddedToResult() {
        return this.validationResult.getActionToBeAddedToResult();
    }
}

record Animal(String name, Integer index) {
}
