import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MinesweeperSquare implements MinesweeperConstants {

  private SquareType type;
  private int x;
  private int y;
  private boolean revealed;
  private boolean flagged;

  private List<MinesweeperSquare> neighbors;

  private List<ModelListener> listeners;

  public MinesweeperSquare(SquareType type, int x, int y) {
    super();
    this.type = type;
    this.x = x;
    this.y = y;
    this.revealed = false;
    this.flagged = false;
    listeners = new ArrayList<ModelListener>();
  }

  public List<MinesweeperSquare> getNeighbors() {
    return neighbors;
  }

  public void setNeighbors(List<MinesweeperSquare> neighbors) {
    this.neighbors = neighbors;
  }

  public int getX() {
    return x;
  }

  public int getY() {
    return y;
  }

  public boolean isRevealed() {
    return revealed;
  }

  public void setRevealed(boolean revealed) {
    this.revealed = revealed;
    notifyListeners();
  }

  public boolean isFlagged() {
    return flagged;
  }

  public void setFlagged(boolean flagged) {
    this.flagged = flagged;
    notifyListeners();
  }

  public SquareType getType() {
    return type;
  }

  public void setType(SquareType type) {
    this.type = type;
  }

  public void addListener(ModelListener listener) {
    listeners.add(listener);
  }

  public void removeListener(ModelListener listener) {
    Iterator<ModelListener> itr = listeners.iterator();
    while (itr.hasNext()) {
      ModelListener elem = itr.next();
      if (elem == listener) itr.remove();
    }
  }

  @Override
  public String toString() {
    String a = "(" + x + ", " + y + "): " + type + "\n";
    String b = "\tisRevealed: " + revealed + "\n";
    String c = "\tisFlagged: " + flagged;
    return a + b + c;
  }

  public boolean isMine() {
    return type == SquareType.MINE;
  }

  public boolean isBlank() {
    return type == SquareType.BLANK;
  }

  private void notifyListeners() {
    for (ModelListener listener : listeners) {
      listener.modelChanged(this);
    }
  }

}
