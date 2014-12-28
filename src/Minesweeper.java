import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;


public class Minesweeper extends JFrame implements ActionListener, ModelListener {

  private static final long serialVersionUID = -4988521449829593839L;
  
  private int width;
  private int height;
  private int numMines;

  private Set<MinesweeperSquare> flaggedMines;
  private Set<MinesweeperSquare> flaggedOther;

  private MinesweeperBoard board;
  private MinesweeperImages images;

  private JButton button;
  private JPanel menu;
  private JPanel grid;

  public Minesweeper(int width, int height, int numMines) {
    super("Minesweeper");

    this.width = width;
    this.height = height;
    this.numMines = numMines;

    images = MinesweeperImages.getInstance();
    flaggedMines = new HashSet<MinesweeperSquare>();
    flaggedOther = new HashSet<MinesweeperSquare>();

    menu = createMenu();
    grid = createGrid();

    add(menu, BorderLayout.PAGE_START);
    add(grid, BorderLayout.CENTER);
  }

  private JPanel createMenu() {
    JPanel menu = new JPanel();
    menu.setLayout(new FlowLayout());
    button = createButton();
    menu.add(button);
    return menu;
  }

  private JButton createButton() {
    JButton button = new JButton(new ImageIcon(images.getAlive()));
    button.setRolloverIcon(new ImageIcon(images.getRollover()));
    button.setRolloverEnabled(true);
    button.addActionListener(this);
    return button;
  }

  private JPanel createGrid() {
    JPanel grid = new JPanel();
    grid.setLayout(new GridLayout(height, width));

    board = new MinesweeperBoard(width, height, numMines);

    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        MinesweeperSquare square = board.get(x, y);
        square.addListener(this);
        grid.add(new MinesweeperGridComponent(square, board));
      }
    }

    return grid;
  }

  @Override
  public void actionPerformed(ActionEvent arg0) {
    this.remove(grid);
    grid = createGrid();
    add(grid, BorderLayout.CENTER);
    button.setIcon(new ImageIcon(images.getAlive()));
    button.setRolloverEnabled(true);
    flaggedMines.clear();
    flaggedOther.clear();
    validate();
  }

  public void modelChanged(MinesweeperSquare square) {
    if (square.isFlagged()) {
      if (square.isMine()) flaggedMines.add(square);
      else flaggedOther.add(square);
    } else {
      if (square.isMine()) flaggedMines.remove(square);
      else flaggedOther.remove(square);
    }

    if (square.isMine() && square.isRevealed()) {
      // Game over.
      for (int x = 0; x < width; x++) {
        for (int y = 0; y < height; y++) {
          square = board.get(x, y);
          if (square.isMine()) {
            square.removeListener(this);
            square.setRevealed(true);
          }
        }
      }

      button.setIcon(new ImageIcon(images.getDead()));
      button.setRolloverEnabled(false);
      disableGrid();
    } else if (flaggedMines.size() == numMines && flaggedOther.size() == 0) {
      // Win.
      for (int x = 0; x < width; x++) {
        for (int y = 0; y < height; y++) {
          square = board.get(x, y);
          if (!square.isRevealed()) {
            square.removeListener(this);
            square.setRevealed(true);
          }
        }
      }

      button.setIcon(new ImageIcon(images.getWin()));
      button.setRolloverEnabled(false);
      disableGrid();
    }
  }

  private void disableGrid() {
    for (Component component : grid.getComponents()) {
      component.removeMouseListener((MinesweeperGridComponent) component);
    }
  }

  public static void main(String[] args) {
    try {
      MinesweeperImages.init();
    } catch (IOException e) {
      e.printStackTrace();
    }

    Minesweeper frame = new Minesweeper(40, 20, 100);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setResizable(false);
    frame.pack();
    frame.setVisible(true);
  }
}
