
public interface MinesweeperConstants {

  public static enum SquareType {
    BLANK,
    ONE,
    TWO,
    THREE,
    FOUR,
    FIVE,
    SIX,
    SEVEN,
    EIGHT,
    MINE;

    public static SquareType parseType(int val) {
      switch (val) {
        case 0 : return BLANK;
        case 1 : return ONE;
        case 2 : return TWO;
        case 3 : return THREE;
        case 4 : return FOUR;
        case 5 : return FIVE;
        case 6 : return SIX;
        case 7 : return SEVEN;
        case 8 : return EIGHT;
        case 9 : return MINE;
        default : return BLANK;
      }
    }
  }

}
