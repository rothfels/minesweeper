import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MinesweeperBoard implements MinesweeperConstants {

  private MinesweeperSquare[][] squares;
  private int width;
  private int height;
  @SuppressWarnings("unused") private int numMines;

  public MinesweeperBoard(int width, int height, int numMines) {
    this.width = width;
    this.height = height;
    this.numMines = numMines;

    squares = new MinesweeperSquare[width][height];

    // Initialize board.
    for (int x = 0; x < width; x++) {
      for (int y = 0; y < height; y++) {
        squares[x][y] = new MinesweeperSquare(SquareType.BLANK, x, y);
      }
    }

    Random random = new Random();

    // Place bombs on the board.
    int count = 0;
    while (count < numMines) {
      int x = random.nextInt(width);
      int y = random.nextInt(height);
      if (!squares[x][y].isMine()) {
        squares[x][y].setType(SquareType.MINE);
        count++;
      }
    }

    // Then fill in the rest of the board.
    for (int x = 0; x < width; x++) {
      for (int y = 0; y < height; y++) {
        MinesweeperSquare square = squares[x][y];
        List<MinesweeperSquare> neighbors = getNeighbors(square);
        square.setNeighbors(neighbors);

        int mineCount = 0;
        for (MinesweeperSquare neighbor : neighbors) {
          if (neighbor.isMine()) {
            mineCount++;
          }
        }

        if (!square.isMine()) {
          count = 0;
          square.setType(SquareType.parseType(mineCount));
        }
      }
    }
  }

  public MinesweeperSquare get(int x, int y) {
    return squares[x][y];
  }

  private List<MinesweeperSquare> getNeighbors(MinesweeperSquare source) {
    int x = source.getX();
    int y = source.getY();
    List<MinesweeperSquare> neighbors = new ArrayList<MinesweeperSquare>();
    for (int i = x - 1; i <= x + 1; i++) {
      for (int j = y - 1; j <= y + 1; j++) {
        boolean isValid = i >= 0 && j >= 0 && i < width && j < height;
        if (isValid) {
          neighbors.add(squares[i][j]);
        }
      }
    }
    return neighbors;
  }

  public void revealNeighbors(MinesweeperSquare source) {
    if (!neighborsRevealable(source)) {
      return;
    }

    for (MinesweeperSquare neighbor : source.getNeighbors()) {
      if (!neighbor.isRevealed() && neighbor.isBlank()) {
        neighbor.setRevealed(true);
        revealNeighbors(neighbor);
      }
      if (!neighbor.isFlagged()) {
        neighbor.setRevealed(true);
      }
    }
  }

  private boolean neighborsRevealable(MinesweeperSquare source) {
    if (!source.isRevealed()) {
      return false;
    }

    if (source.isBlank()) return true;

    int countFlagged = 0;
    for (MinesweeperSquare neighbor : source.getNeighbors()) {
      if (neighbor.isRevealed()) continue;
      if (neighbor.isBlank()) continue;

      if (neighbor.isFlagged()) countFlagged++;
    }

    int value = source.getType().ordinal();
    return countFlagged >= value;
  }

}
