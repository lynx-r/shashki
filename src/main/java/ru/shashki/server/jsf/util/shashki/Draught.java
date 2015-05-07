package ru.shashki.server.jsf.util.shashki;


import io.snapfaces.faces.model.element.Circle;

/**
 * Created with IntelliJ IDEA.
 * Profile: alekspo
 * Date: 07.12.13
 * Time: 21:08
 */
public class Draught extends Circle {
    private final double deskSide;
    private static Draught selectedDraught;
//  private Board board;

    private int row;
    private int col;
    private boolean white;

//  private Circle mainCircle = new Circle(0);
//  private Circle innerCircle1 = new Circle(0);
//  private Circle innerCircle2 = new Circle(0);
//  private Circle innerCircle3 = new Circle(0);
//  private Star queenStar = new Star(5, 0, 0);

    private int rows;
    private int cols;

    private boolean queen;

    private Draught currentDraught;
    private double offsetX;
    private Square location;
    private String draughtWhiteColor;
    private String draughtBlackColor;
    private Board parent;

    public Draught(Board parent, double deskSide, int rows, int cols, int row, int col, boolean white, double offsetX) {
        this.deskSide = deskSide;
        this.row = row;
        this.col = col;
        this.rows = rows;
        this.cols = cols;
        this.white = white;
        this.offsetX = offsetX;
        this.parent = parent;
        this.draughtWhiteColor = parent.getDraughtWhiteColor();
        this.draughtBlackColor = parent.getDraughtBlackColor();

//        setDraggable(true);
//    queenStar.setFillColor(white ? ColorName.BLUE : ColorName.RED);

//    Circle[] circles = {mainCircle, innerCircle1, innerCircle2, innerCircle3};

        int i = 0;
//    for (Circle circle : circles) {
//      if (i % 2 == 0) {
//        circle.setShadow(new Shadow(Color.fromColorString("#000"), 6, 0, 4));
//      } else {
//        circle.setShadow(new Shadow(Color.fromColorString("#000"), 6, 0, -4));
//      }
//      i++;
//      circle.setFillColor(white ? ColorName.WHITE : Color.fromColorString("#555"));
//    }

        updateShape();

        // TODO: Not Compile
//    addNodeMouseDownHandler(new NodeMouseDownHandler() {
//      @Override
//      public void onNodeMouseDown(NodeMouseDownEvent nodeMouseDownEvent) {
//        onNodeTouch((Draught) nodeMouseDownEvent.getSource());
//      }
//    });

//    addNodeTouchEndHandler(new NodeTouchEndHandler() {
//      @Override
//      public void onNodeTouchEnd(NodeTouchEndEvent nodeTouchEndEvent) {
//        onNodeTouch((Draught) nodeTouchEndEvent.getSource());
//      }
//    });

    }

//  private void onNodeTouch(Draught draught) {
//    if (!isValidStroke()) {
//      return;
//    }
//
//    board = (Board) getParent();
//    currentDraught = draught;
//
//    if (selectedDraught != null) {
//      AnimationProperties props = new AnimationProperties();
//      props.push(AnimationProperty.Properties.SCALE(1));
//
//      selectedDraught.animate(AnimationTweener.LINEAR, props, 100);
//    }
//
//    AnimationProperties props = new AnimationProperties();
//    props.push(AnimationProperty.Properties.SCALE(1.3));
//
//    currentDraught.animate(AnimationTweener.LINEAR, props, 100);
//
//    selectedDraught = currentDraught;
//
//    board.resetDeskDrawing();
//    board.highlightAllowedMoves(selectedDraught);
//  }

    public boolean isWhite() {
        return white;
    }

    public void setWhite(boolean white) {
        this.white = white;
    }

    public void updateShape() {
        if (white) {
            setFill(draughtWhiteColor);
        } else {
            setFill(draughtBlackColor);
        }

        double x = col * deskSide / rows;
        double y = row * deskSide / cols;
        double squareSize = deskSide / rows / 2 - 4;
        setCx(x + offsetX + squareSize + 4);
        setCy(y + squareSize + 4);
        setR(squareSize);
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public void setQueen(boolean queen) {
        this.queen = queen;
        updateShape();
    }

    public boolean isQueen() {
        return queen;
    }

//  private boolean isValidStroke() {
//    Board board = (Board) getParent();
//    return board.isMyTurn() && isWhite() == board.isWhite() && !board.isEmulate();
//  }

//  private boolean isAllowed(Square newSquare) {
//    return newSquare.getAlpha() < 1.0;
//  }

    public void setPosition(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public static Draught getSelectedDraught() {
        return selectedDraught;
    }

    public void setLocation(Square location) {
        this.location = location;
    }

    public Square getLocation() {
        return location;
    }

    public void setParent(Board parent) {
        this.parent = parent;
    }

    public Board getParent() {
        return parent;
    }

    @Override
    public String toString() {
        return "Draught{" +
                "deskSide=" + deskSide +
                ", row=" + row +
                ", col=" + col +
                ", white=" + white +
                ", rows=" + rows +
                ", cols=" + cols +
                ", queen=" + queen +
                ", currentDraught=" + currentDraught +
                ", offsetX=" + offsetX +
                ", location=" + location +
                ", draughtWhiteColor='" + draughtWhiteColor + '\'' +
                ", draughtBlackColor='" + draughtBlackColor + '\'' +
                '}';
    }
}
