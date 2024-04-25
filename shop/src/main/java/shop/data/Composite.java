package shop.data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import shop.command.UndoableCommand;

public class Composite implements UndoableCommand{
    private boolean _runOnce;
    List<UndoableCommand> commandList = new ArrayList<UndoableCommand>();

    public void add(UndoableCommand c){
        commandList.add(c);
    }

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

    public void undo() {
        List<UndoableCommand> tmp = commandList;
        Collections.reverse(tmp);
        for(UndoableCommand c : tmp){
            c.undo();
        }
    }

    public void redo() {
        for(UndoableCommand c : commandList){
            c.redo();
        }
    }
    
}
