package shop.command;

/*
 * The CommandHistoryFactory class is used to create a new CommandHistory object.
 * This class follows the Factory Design Pattern.
 */
public class CommandHistoryFactory {
  private CommandHistoryFactory() {}
  static public CommandHistory newCommandHistory() {
    return new CommandHistoryObj();
  }
}
