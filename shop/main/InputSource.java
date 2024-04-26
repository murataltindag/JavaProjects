package shop.main;

import java.util.function.Predicate;

public interface InputSource {
    String validateInput(Predicate<String> validator, String prompt, String errorPrompt);
}
