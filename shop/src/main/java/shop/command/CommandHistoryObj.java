package shop.command;
import java.util.Stack;

/*
  * The CommandHistoryObj class implements the CommandHistory interface.
  * It is used to store the history of commands executed, undone and redone.
  * This class follows the Command Design Pattern.
 */
final class CommandHistoryObj implements CommandHistory {
  // The undo stack stores the commands that have been executed.
  Stack<UndoableCommand> _undoStack = new Stack<UndoableCommand>();
  // The redo stack stores the commands that have been undone.
  Stack<UndoableCommand> _redoStack = new Stack<UndoableCommand>();
  RerunnableCommand _undoCmd = new RerunnableCommand () {
      public boolean run () {
        boolean result = !_undoStack.empty();
        if (result) {
          // Undo
          UndoableCommand cmd = _undoStack.pop();
          cmd.undo();
          _redoStack.push(cmd); 
        }
        return result;
      }
    };
  RerunnableCommand _redoCmd = new RerunnableCommand () {
      public boolean run () {
        boolean result = !_redoStack.empty();
        if (result) {
          // Redo
          UndoableCommand cmd = _redoStack.pop();
          cmd.redo();
          _undoStack.push(cmd);
        }
        return result;
      }
    };

  /*
   * Adds a command to the undo stack.
   * @param cmd The command to be added.
   */
  public void add(UndoableCommand cmd) {
    _undoStack.push(cmd);
    _redoStack.clear();
  }
  
  /*
   * Returns the undo command.
   * @return The undo command.
   */
  public RerunnableCommand getUndo() {
    return _undoCmd;
  }
  
  /*
   * Returns the redo command.
   * @return The redo command.
   */
  public RerunnableCommand getRedo() {
    return _redoCmd;
  }
  
  // For testing
  UndoableCommand topUndoCommand() {
    if (_undoStack.empty())
      return null;
    else
      return _undoStack.peek();
  }
  // For testing
  UndoableCommand topRedoCommand() {
    if (_redoStack.empty())
      return null;
    else
      return _redoStack.peek();
  }
}
