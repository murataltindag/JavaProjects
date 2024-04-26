package shop.main;

import java.util.function.Predicate;

/*
 * The InputSource interface is used to get input from the user.
 */
public interface InputSource {
    String validateInput(Predicate<String> validator, String prompt, String errorPrompt);
}
