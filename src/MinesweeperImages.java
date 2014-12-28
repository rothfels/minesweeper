import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class MinesweeperImages implements MinesweeperConstants {

  // Squares
  private Image flag;
  private Image mine;
  private Image one;
  private Image two;
  private Image three;
  private Image four;
  private Image five;
  private Image six;
  private Image seven;
  private Image eight;

  // Other
  private Image dead;
  private Image alive;
  private Image rollover;
  private Image win;

  private static MinesweeperImages instance = new MinesweeperImages();

  public static MinesweeperImages getInstance() {
    return instance;
  }

  public static void init() throws IOException {
    instance.flag = ImageIO.read(new File("images/flag.png"));
    instance.mine = ImageIO.read(new File("images/mine.png"));
    instance.one = ImageIO.read(new File("images/1.png"));
    instance.two = ImageIO.read(new File("images/2.png"));
    instance.three = ImageIO.read(new File("images/3.png"));
    instance.four = ImageIO.read(new File("images/4.png"));
    instance.five = ImageIO.read(new File("images/5.png"));
    instance.six = ImageIO.read(new File("images/6.png"));
    instance.seven = ImageIO.read(new File("images/7.png"));
    instance.eight = ImageIO.read(new File("images/8.png"));

    instance.dead = ImageIO.read(new File("images/skeleton.png"));
    instance.alive = ImageIO.read(new File("images/smile.png"));
    instance.rollover = ImageIO.read(new File("images/shock.png"));
    instance.win = ImageIO.read(new File("images/glasses-cool.png"));
  }

  public Image getFlag() {
    return flag;
  }

  public Image getMine() {
    return mine;
  }

  public Image getOne() {
    return one;
  }

  public Image getTwo() {
    return two;
  }

  public Image getThree() {
    return three;
  }

  public Image getFour() {
    return four;
  }

  public Image getFive() {
    return five;
  }

  public Image getSix() {
    return six;
  }

  public Image getSeven() {
    return seven;
  }

  public Image getEight() {
    return eight;
  }

  public Image getDead() {
    return dead;
  }

  public Image getAlive() {
    return alive;
  }

  public Image getRollover() {
    return rollover;
  }

  public Image getWin() {
    return win;
  }
}
