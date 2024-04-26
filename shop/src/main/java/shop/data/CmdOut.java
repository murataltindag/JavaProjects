	package shop.data;

import shop.command.UndoableCommand;

/**
 * Implementation of command to check out a video.
 * @see Data
 */
final class CmdOut implements UndoableCommand {
  private boolean _runOnce;
  private InventorySet _inventory;
  private Video _video;
  private Record _oldvalue;
  CmdOut(InventorySet inventory, Video video) {
    _inventory = inventory;
    _video = video;
  }
  public boolean run() {
    if (_runOnce)
      return false;
    _runOnce = true;
    try {
      _oldvalue = _inventory.checkOut(_video);
      _inventory.getHistory().add(this);
      return true;
    } catch (IllegalArgumentException e) {
      return false;
    } catch (ClassCastException e) {
      return false;
    }
  }
  public void undo() {
    _inventory.replaceEntry(_video,_oldvalue);
  }
  public void redo() {
    _inventory.checkOut(_video);
  }
}
