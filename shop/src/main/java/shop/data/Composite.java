package shop.data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import shop.command.UndoableCommand;

/**
 * The Composite class implements the UndoableCommand interface.
 * It is used to execute, undo and redo a list of commands as a single command.
 * This class follows the Composite Design Pattern.
 */
public class Composite implements UndoableCommand{
    private boolean _runOnce;
    List<UndoableCommand> commandList = new ArrayList<UndoableCommand>();

    /**
     * Adds a command to the list of commands.
     * @param c The command to be added.
     */
    public void add(UndoableCommand c){
        commandList.add(c);
    }

    /**
     * Executes all the commands in the list.
     * If a command throws an IllegalArgumentException or a ClassCastException, it stops executing the remaining commands and returns false.
     * @return true if all commands are executed successfully, false otherwise.
     */
    public boolean run() {
        if(_runOnce){
            return false;
        }
        _runOnce = true;
        for(UndoableCommand c : commandList){
            try {
                c.run();
            } catch (IllegalArgumentException e) {
                return false;
            } catch (ClassCastException e) {
                return false;
            }
        }
        return true;
    }

    /**
     * Undoes all the commands in the list in reverse order.
     */
    public void undo() {
        List<UndoableCommand> tmp = commandList;
        Collections.reverse(tmp);
        for(UndoableCommand c : tmp){
            c.undo();
        }
    }

    /**
     * Redoes all the commands in the list.
     */
    public void redo() {
        for(UndoableCommand c : commandList){
            c.redo();
        }
    }
    
}
