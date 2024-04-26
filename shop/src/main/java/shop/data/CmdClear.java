package shop.data;

import java.util.Map;

import shop.command.UndoableCommand;

/**
 * Implementation of command to clear the inventory.
 * @see Data
 */
final class CmdClear implements UndoableCommand {
  private boolean _runOnce;
  private InventorySet _inventory;
  private Map<Video,Record> _oldvalue;
  CmdClear(InventorySet inventory) {
    _inventory = inventory;
  }
  public boolean run() {
    if (_runOnce)
      return false;
    _runOnce = true;
    try {
      _oldvalue = _inventory.clear();
      _inventory.getHistory().add(this);
      return true;
    } catch (IllegalArgumentException e) {
      return false;
    } catch (ClassCastException e) {
      return false;
    }
  }
  public void undo() {
    _inventory.replaceMap(_oldvalue);
  }
  public void redo() {
    _inventory.clear();
  }
}
