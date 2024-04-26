package shop.data;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import shop.command.CommandHistory;
import shop.command.CommandHistoryFactory;

/**
 * Implementation of Inventory interface.
 * @see Data
 */
final class InventorySet implements Inventory {
  // Chose to use Map of Record, rather than RecordObj, because of
  // Java's broken generic types.  The story is too sad to retell, but
  // involves the fact that Iterable<? extends Record> is not a valid
  // type, and that Iterator<RecordObj> is not a subtype of
  // Iterator<Record>.
  //
  // Seems like the best approach for Java generics is to use the
  // external representation internally and downcast when necessary.
  private Map<Video,Record> _data;
  private final CommandHistory _history;

  InventorySet() {
    _data = new HashMap<Video,Record>();
    _history = CommandHistoryFactory.newCommandHistory();
  }

  /**
   * If <code>record</code> is null, then delete record for <code>video</code>;
   * otherwise replace record for <code>video</code>.
   */
  void replaceEntry(Video video, Record record) {
    _data.remove(video);
    if (record != null)
      _data.put(video,((RecordObj)record).copy());
  }

  /**
   * Overwrite the map.
   */
  void replaceMap(Map<Video,Record> data) {
    _data = data;
  }


  public int size() {
    return _data.size();
  }

  public Record get(Video v) {
    if(!_data.containsKey(v)){
      return null;
    }
    //return a copy
    Record rec = _data.get(v);
    return copy(rec);
  }

  private Record copy(Record r) {
    return new RecordObj(r.video(), r.numOwned(), r.numOut(), r.numRentals());
  }

  public Iterator<Record> iterator() {
    return Collections.unmodifiableCollection(_data.values()).iterator();
  }

  public Iterator<Record> iterator(Comparator<Record> comparator) {
    return Collections.unmodifiableCollection(_data.values()).stream().sorted(comparator).iterator();
  }

  /**
   * Add or remove copies of a video from the inventory.
   * If a video record is not already present (and change is
   * positive), a record is created. 
   * If a record is already present, <code>numOwned</code> is
   * modified using <code>change</code>.
   * If <code>change</code> brings the number of copies to be less
   * than one, the record is removed from the inventory.
   * @param video the video to be added.
   * @param change the number of copies to add (or remove if negative).
   * @throws IllegalArgumentException if video null or change is zero
   */
  Record addNumOwned(Video video, int change) {
    if(!_data.containsKey(video) && change <= 0){
      System.out.println("Inventory Error: Tried to remove non-existent video");
      throw new IllegalArgumentException();
    } else if(!_data.containsKey(video)){
      _data.put(video, new RecordObj(video, change, 0, 0));
      return null;
    } else {
      Record rec = _data.get(video);
      if(rec.numOwned() + change == 0){
        _data.remove(video);
      } else if(rec.numOwned() + change < 0){
        throw new IllegalArgumentException();
      } else {
        _data.put(video, new RecordObj(video, rec.numOwned() + change, 0, 0));
      }
      return rec;
    }  
  }

  /**
   * Check out a video.
   * @param video the video to be checked out.
   * @throws IllegalArgumentException if video has no record or numOut
   * equals numOwned.
   */
  Record checkOut(Video video) {
    if(!_data.containsKey(video)){
      System.out.println("Inventory Error: Video does not exist in the inventory.");
      throw new IllegalArgumentException();
    } else {
      Record rec = _data.get(video);
      if(rec.numOut() == rec.numOwned()){
        System.out.println("Inventory Error: All copies of the video are already checked out.");
        throw new IllegalArgumentException();
      } else {
        _data.put(video, new RecordObj(video, rec.numOwned(), rec.numOut() + 1, rec.numRentals() + 1));
        return rec;
      }
    }
  }
  
  /**
   * Check in a video.
   * @param video the video to be checked in.
   * @throws IllegalArgumentException if video has no record or numOut
   * non-positive.
   */
  Record checkIn(Video video) {
    if(!_data.containsKey(video)){
      throw new IllegalArgumentException();
    } else {
      Record rec = _data.get(video);
      if(rec.numOut() <= 0){
        throw new IllegalArgumentException();
      } else {
        _data.put(video, new RecordObj(video, rec.numOwned(), rec.numOut() - 1, rec.numRentals()));
        return rec;
      }
    }
  }
  
  /**
   * Remove all records from the inventory.
   */
  Map<Video, Record> clear() {
    Map<Video, Record> oldData = _data;
    _data.clear();  
    return oldData;
  }

  /**
   * Return a reference to the history.
   */
  CommandHistory getHistory() {
    return _history;
  }

  public String toString() {
    StringBuffer buffer = new StringBuffer();
    if(_data.isEmpty()){
      return new String("  No videos in inventory\n");
    }
    buffer.append("Database:\n");
    for (Record r : _data.values()) {
      buffer.append("  ");
      buffer.append(r);
      buffer.append("\n");
    }
    return buffer.toString();
  }


  /**
   * Implementation of Record interface.
   *
   * <p>This is a utility class for Inventory.  Fields are mutable and
   * package-private.</p>
   *
   * <p><b>Class Invariant:</b> No two instances may reference the same Video.</p>
   *
   * @see Record
   */
  private static final class RecordObj implements Record {
    Video video; // the video
    int numOwned;   // copies owned
    int numOut;     // copies currently rented
    int numRentals; // total times video has been rented
    
    RecordObj(Video video, int numOwned, int numOut, int numRentals) {
      this.video = video;
      this.numOwned = numOwned;
      this.numOut = numOut;
      this.numRentals = numRentals;
    }
    RecordObj copy() {
      return new RecordObj(video, numOwned, numOut, numRentals);
    }
    public Video video() {
      return video;
    }
    public int numOwned() {
      return numOwned;
    }
    public int numOut() {
      return numOut;
    }
    public int numRentals() {
      return numRentals;
    }
    public boolean equals(Object thatObject) {
      return video.equals(((Record)thatObject).video());
    }
    public int hashCode() {
      return video.hashCode();
    }
    public String toString() {
      StringBuffer buffer = new StringBuffer();
      buffer.append(video);
      buffer.append(" [total copies: ");
      buffer.append(numOwned);
      buffer.append(", currently checked out: ");
      buffer.append(numOut);
      buffer.append(", total rentals: ");
      buffer.append(numRentals);
      buffer.append("]");
      return buffer.toString();
    }
  }
}
