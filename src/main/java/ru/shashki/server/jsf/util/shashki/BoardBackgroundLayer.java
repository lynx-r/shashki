package ru.shashki.server.jsf.util.shashki;

import io.snapfaces.faces.model.element.Group;
import io.snapfaces.faces.model.element.Rect;

/**
 * Created with IntelliJ IDEA.
 * Profile: alekspo
 * Date: 07.12.13
 * Time: 20:39
 */
public class BoardBackgroundLayer extends Group {
    private final int rows;
    private final int cols;
    private final int contourSide;
    private final int deskSide;
    private final int offsetX;
    private final String fillColor;
    private Square[][] gameBoard;
    private Rect boardContourRect;
    //  private Vector<Text> coordsTextVector = new Vector<>();

    //
    public BoardBackgroundLayer(int contourSide, int deskSide, int offsetX, int rows, int cols, String fillColor) {
        this.contourSide = contourSide;
        this.deskSide = deskSide;
        this.offsetX = offsetX;

        this.rows = rows;
        this.cols = cols;

        this.fillColor = fillColor;

        gameBoard = new Square[rows][cols];

        drawDesk();

        setFill("lightgray");
    }

    public String getFillColor() {
        return fillColor;
    }

    private void drawDesk() {
        Rect background = new Rect(offsetX, 0, deskSide, deskSide);
        background.setFill("lightgray");
        addShape(background);

        if (boardContourRect == null) {
            boardContourRect = new Rect(offsetX, 0, deskSide, deskSide);
            addShape(boardContourRect);
        } else {
            boardContourRect.setWidth(contourSide);
            boardContourRect.setHeight(contourSide);
        }

        boolean lastColor = false;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (lastColor) {
                        Square square = new Square(deskSide, rows, cols, i, j, offsetX, fillColor);
                        gameBoard[i][j] = square;
                        addShape(square);

                        // текст для отладки
//            Text t = new Text(i + " " + j, "sanse", 12);
//            t.setX(square.getX() + 5);
//            t.setY(square.getY() + 20);
//            add(t);
                }
                // Toggle lastcolor
                lastColor = !lastColor;
            }
            // Switch starting color for next row
            lastColor = !lastColor;
        }
    }

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
//          double x = offsetX - 20;
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
//          double x = deskSide * ((double) j / ((double) rows) + 1 / ((double) cols * 2)) + offsetX - 25;
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
  public void resetDeskDrawing() {
      for (Square[] squares : gameBoard) {
          for (Square square : squares) {
              if (square != null) {
                  square.setFill(fillColor);
              }
          }
      }
  }
//
////  @Override
////  public void draw() {
////  }
//
  public Square[][] getGameBoard() {
    return gameBoard;
  }

  public Square getSquare(int row, int col) {
    if (inBounds(row, col)) {
      return gameBoard[row][col];
    }
    return null;
  }

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

  public boolean inBounds(int row, int col) {
    return row >= 0 && row < rows && col >= 0 && col < cols;
  }

  public int getDeskSide() {
    return deskSide;
  }

  public double getOffsetX() {
    return offsetX;
  }
}