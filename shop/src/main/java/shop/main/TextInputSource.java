package shop.main;

import java.util.Scanner;
import java.util.function.Predicate;

public class TextInputSource implements InputSource {
    private Scanner scanner = new Scanner(System.in);

    
    @Override
    public String validateInput(Predicate<String> validator, String prompt, String errorPrompt) {
        String input;
        System.out.println(prompt);
        input = scanner.nextLine();
        if(validator.test(input)){
            return input;
        }
        do {
            System.out.println(errorPrompt);
            input = scanner.nextLine();
        } while (!validator.test(input));
        return input;
    }
}