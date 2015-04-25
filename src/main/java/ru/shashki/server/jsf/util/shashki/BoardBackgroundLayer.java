//package ru.shashki.server.jsf.util.shashki;
//
//
//import com.ait.lienzo.client.core.shape.Layer;
//import com.ait.lienzo.client.core.shape.Rectangle;
//import com.ait.lienzo.client.core.shape.Text;
//import com.ait.lienzo.shared.core.types.ColorName;
//
//import java.util.Vector;
//
///**
// * Created with IntelliJ IDEA.
// * Profile: alekspo
// * Date: 07.12.13
// * Time: 20:39
// */
//public class BoardBackgroundLayer extends Layer {
//  private final int rows;
//  private final int cols;
//  private final int side;
//  private final int deskSide;
//  private Square[][] gameBoard;
//  private Rectangle boardConturRect;
//  private Vector<Text> coordsTextVector = new Vector<>();
//  private int OFFSET_X = 30;
//
//  public BoardBackgroundLayer(int side, int deskSide, int rows, int cols) {
//    setListening(false);
//
//    this.side = side;
//    this.deskSide = deskSide;
//
//    this.rows = rows;
//    this.cols = cols;
//
//    gameBoard = new Square[rows][cols];
//
//    drawDesk();
//  }
//
//  private void drawDesk() {
//    Rectangle background = new Rectangle(deskSide, deskSide).setX(OFFSET_X).setY(0);
//    background.setFillColor(ColorName.LIGHTGRAY);
//    add(background);
//
//    if (boardConturRect == null) {
//      boardConturRect = new Rectangle(deskSide, deskSide).setX(OFFSET_X).setY(0);
//      add(boardConturRect);
//    } else {
//      boardConturRect.setWidth(side).setHeight(side);
//    }
//
//    Square square;
//    boolean lastColor = false;
//    for (int i = 0; i < rows; i++) {
//      for (int j = 0; j < cols; j++) {
//        if (lastColor) {
//          square = getSquare(i, j);
//          if (square == null) {
//            square = new Square(deskSide, rows, cols, i, j, OFFSET_X);
//            gameBoard[i][j] = square;
//            add(square);
//
//            // текст для отладки
////            Text t = new Text(i + " " + j, "sanse", 12);
////            t.setX(square.getX() + 5);
////            t.setY(square.getY() + 20);
////            add(t);
//          }
//        }
//        // Toggle lastcolor
//        lastColor = !lastColor;
//      }
//      // Switch starting color for next row
//      lastColor = !lastColor;
//    }
//  }
//
//  public void drawCoordinates(boolean white) {
//    if (!coordsTextVector.isEmpty()) {
//      for (Text t : coordsTextVector) {
//        remove(t);
//      }
//      coordsTextVector.clear();
//    }
//    int numCoords = white ? rows : 1;
//    int alphIdCoords = white ? 0 : cols - 1;
//    String alphCoords[] = new String[]{"A", "B", "C", "D", "E", "F", "G", "H"};
//    for (int i = 0; i < rows; i++) {
//      for (int j = 0; j < cols; j++) {
//        if (0 == j) {
//          double x = OFFSET_X - 20;
//          double y = deskSide * (((double) i) / ((double) cols) + 1 / ((double) rows * 2));
//
//          Text num = new Text(String.valueOf(numCoords), "Times New Roman", 12);
//          num.setFillColor(ColorName.BLACK);
//          numCoords = white ? numCoords - 1 : numCoords + 1;
//          num.setX(x);
//          num.setY(y);
//          add(num);
//          coordsTextVector.add(num);
//        }
//        if (rows == (i + 1)) {
//          double x = deskSide * ((double) j / ((double) rows) + 1 / ((double) cols * 2)) + OFFSET_X - 25;
//          double y = deskSide + 20;
//
//          Text alph = new Text(alphCoords[alphIdCoords], "Times New Roman", 12);
//          alph.setFillColor(ColorName.BLACK);
//          alphIdCoords = white ? alphIdCoords + 1 : alphIdCoords - 1;
//          alph.setX(x);
//          alph.setY(y);
//          add(alph);
//          coordsTextVector.add(alph);
//        }
//      }
//    }
//  }
//
//  public void resetDeskDrawing() {
//    for (int i = 0; i < rows; i++) {
//      for (int j = 0; j < cols; j++) {
//        Square square = getSquare(i, j);
//        if (square != null) {
//          square.setAlpha(1.0);
//          square.setStrokeWidth(1.5);
//          square.setStrokeColor(ColorName.BLACK);
//        }
//      }
//    }
//    draw();
//  }
//
////  @Override
////  public void draw() {
////  }
//
//  public Square[][] getGameBoard() {
//    return gameBoard;
//  }
//
//  public Square getSquare(int row, int col) {
//    if (inBounds(row, col)) {
//      return gameBoard[row][col];
//    }
//    return null;
//  }
//
//  public Square getSquare(double x, double y) {
//    for (int i = 0; i < rows; i++) {
//      for (int j = 0; j < cols; j++) {
//        Square square = getSquare(i, j);
//        if (square != null && square.contains(x, y)) {
//          return square;
//        }
//      }
//    }
//    return null;
//  }
//
//  public boolean inBounds(int row, int col) {
//    return row >= 0 && row < rows && col >= 0 && col < cols;
//  }
//
//  public int getDeskSide() {
//    return deskSide;
//  }
//
//  public double getOffsetX() {
//    return OFFSET_X;
//  }
//
//}