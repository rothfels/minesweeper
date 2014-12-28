import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JComponent;

public class MinesweeperGridComponent extends JComponent
    implements MouseListener, ModelListener, MinesweeperConstants {

  private static final long serialVersionUID = -863124844636027998L;
  
  private MinesweeperSquare square;
  private MinesweeperBoard board;

  private static final MinesweeperImages images =
      MinesweeperImages.getInstance();

  private static final int DEFAULT_WIDTH = 20;
  private static final int DEFAULT_HEIGHT = 20;

  public MinesweeperGridComponent(MinesweeperSquare square,
      MinesweeperBoard board) {
    super();
    this.square = square;
    this.board = board;
    setPreferredSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT));
    addMouseListener(this);
    square.addListener(this);
  }

  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);

    int width = getWidth();
    int height = getHeight();

    // Draw outlining box.
    g.setColor(Color.BLACK);
    g.drawRect(0, 0, width, height);

    boolean setBorder = square.isFlagged() || !square.isRevealed();
    if (setBorder) {
      this.setBorder(BorderFactory.createRaisedBevelBorder());
    } else {
      this.setBorder(null);
    }

    if (square.isFlagged()) {
      g.drawImage(images.getFlag(), 0, 0, width, height, null);
      return;
    }

    if (square.isRevealed()) {
      switch (square.getType()) {
        case MINE : {
          g.drawImage(images.getMine(),  0, 0, width, height, null);
          break;
        }
        case ONE : {
          g.drawImage(images.getOne(),   0, 0, width, height, null);
          break;
        }
        case TWO : {
          g.drawImage(images.getTwo(),   0, 0, width, height, null);
          break;
        }
        case THREE : {
          g.drawImage(images.getThree(), 0, 0, width, height, null);
          break;
        }
        case FOUR : {
          g.drawImage(images.getFour(),  0, 0, width, height, null);
          break;
        }
        case FIVE : {
          g.drawImage(images.getFive(),  0, 0, width, height, null);
          break;
        }
        case SIX : {
          g.drawImage(images.getSix(),   0, 0, width, height, null);
          break;
        }
        case SEVEN : {
          g.drawImage(images.getSeven(), 0, 0, width, height, null);
          break;
        }
        case EIGHT : {
          g.drawImage(images.getEight(), 0, 0, width, height, null);
          break;
        }
        default : break;
      }
    }

  }

  public void modelChanged(MinesweeperSquare square) {
    repaint();
  }

  @Override
  public void mouseClicked(MouseEvent e) {
    int modifier = e.getModifiers();
    switch (modifier) {
      case 16 : {
        if (square.isRevealed()) {
          board.revealNeighbors(square);
        } else if (!square.isFlagged()) {
          square.setRevealed(true);
          if (square.getType() == SquareType.BLANK) {
            board.revealNeighbors(square);
          }
        }
        break;
      }
      case 4 : {
        if (!square.isRevealed()) {
          square.setFlagged(!square.isFlagged());
        }
        break;
      }
      default: break;
    }
  }

  @Override
  public void mouseEntered(MouseEvent e) {}

  @Override
  public void mouseExited(MouseEvent e) {}

  @Override
  public void mousePressed(MouseEvent e) {}

  @Override
  public void mouseReleased(MouseEvent e) {}
}
