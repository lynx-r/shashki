//package ru.shashki.server.jsf.util.shashki;
//
//import com.ait.lienzo.client.core.animation.*;
//import com.ait.lienzo.client.core.event.NodeMouseClickEvent;
//import com.ait.lienzo.client.core.event.NodeMouseClickHandler;
//import com.ait.lienzo.client.core.event.NodeTouchEndEvent;
//import com.ait.lienzo.client.core.event.NodeTouchEndHandler;
//import com.ait.lienzo.client.core.shape.Layer;
//import com.google.web.bindery.event.shared.EventBus;
//import net.rushashki.social.shashki64.client.component.widget.NotationPanel;
//import net.rushashki.social.shashki64.client.config.ShashkiGinjector;
//import net.rushashki.social.shashki64.client.event.*;
//import net.rushashki.social.shashki64.shashki.util.Operator;
//import net.rushashki.social.shashki64.shashki.util.PossibleOperators;
//
//import java.io.Serializable;
//import java.util.*;
//
///**
// * Created with IntelliJ IDEA.
// * Profile: alekspo
// * Date: 08.12.13
// * Time: 13:26
// */
//public class Board implements Serializable {
//  public static final String CANCEL_MOVE = "cancel_move";
//  public static final String NOT_REMOVED = "null";
//  private static final String NEXT_MOVE = "next_move";
//  private static final String STOP_BEAT_MOVE = "stop_move";
//  public static final String MOVE_STR_SEPARATOR = ",";
//  private static final String ANNOTATION_SIMPLE_MOVE = "-";
//  private static final String ANNOTATION_BEAT_MOVE = ":";
//
//  private final BoardBackgroundLayer backgroundLayer;
//  private Vector<Square> capturedSquares = new Vector<>();
//  private Vector<Draught> mineDraughtVector;
//  private Vector<Draught> opponentDraughtVector;
//
//  private boolean white;
//  private boolean turn;
//  private int rows;
//  private int cols;
//  private final double removeDraughtFade = 400;
//  private final double moveDraughtDuration = 800;
//  private boolean emulate = false; // эмулировать шашки
//  private HashMap<String, Integer> alphMap;
//  private Stack<Square> capturedStack = new Stack<>();
//  // стек ходов шашек, когда они становятся дамками
//  private Stack<Integer> queenStepStack = new Stack<>();
//
//  private List<Square> highlightedSquares = new ArrayList<>();
//  private String lastEndMove;
//  private String lastStartMove;
//  private String lastCaptured;
//
//  public Board(BoardBackgroundLayer backgroundLayer, int rows, int cols, boolean white) {
//
//    this.backgroundLayer = backgroundLayer;
//    this.white = white;
//    turn = white;
//
//    this.rows = rows;
//    this.cols = cols;
//
//    mineDraughtVector = new Vector<>(rows / 2 * 3);
//    opponentDraughtVector = new Vector<>(rows / 2 * 3);
//
//    placeDraughts();
//
//    alphMap = new HashMap<>();
//    alphMap.put("a", 0);
//    alphMap.put("b", 1);
//    alphMap.put("c", 2);
//    alphMap.put("d", 3);
//    alphMap.put("e", 4);
//    alphMap.put("f", 5);
//    alphMap.put("g", 6);
//    alphMap.put("h", 7);
//
////    addNodeMouseClickHandler(new NodeMouseClickHandler() {
////      @Override
////      public void onNodeMouseClick(NodeMouseClickEvent nodeMouseClickEvent) {
//        Board.this.moveDraught(nodeMouseClickEvent.getX(), nodeMouseClickEvent.getY());
////      }
////    });
//
////    addNodeTouchEndHandler(new NodeTouchEndHandler() {
////      @Override
////      public void onNodeTouchEnd(NodeTouchEndEvent nodeTouchEndEvent) {
//        Board.this.moveDraught(nodeTouchEndEvent.getX(), nodeTouchEndEvent.getY());
////      }
////    });
//
////    eventBus.addHandler(PlayMoveOpponentEvent.TYPE, new PlayMoveOpponentEventHandler() {
////      @Override
////      public void onPlayMoveOpponent(PlayMoveOpponentEvent event) {
////        Board.this.moveOpponent(event.getStartMove(), event.getEndMove(), event.getCaptured());
////      }
////    });
//  }
//
//  private void placeDraughts() {
//    for (int row = 0; row < 3; row++) {
////    for(int row = 3; row < 4; row++) {
//      for (int col = 0; col < 8; col++) {
////      for (int col = 2; col < 3; col++) {
//        if (Square.isValid(row, col)) {
//          opponentDraughtVector.add(addDraught(row, col, !white));
//        }
//      }
//    }
//
//    // Now establish the Black side
//    for (int row = 5; row < 8; row++) {
////    for(int row = 4; row < 5; row++) {
//      for (int col = 0; col < 8; col++) {
////      for (int col = 5; col < 6; col++) {
//        if (Square.isValid(row, col)) {
//          mineDraughtVector.add(addDraught(row, col, white));
//        }
//      }
//    }
//
///*
//    if (isWhite()) {
//      opponentDraughtVector.add(addDraught(3, 4, !white));
//    } else {
//      mineDraughtVector.add(addDraught(4, 3, white));
//    }
//
//    if (isWhite()) {
//      mineDraughtVector.add(addDraught(4, 3, white));
//    } else {
//      opponentDraughtVector.add(addDraught(3, 4, !white));
//    }
//*/
//  }
//
//  private Draught addDraught(int row, int col, boolean white) {
//    Square square = backgroundLayer.getSquare(row, col);
//    Draught draught;
//    if (square != null && Square.isValid(row, col)) {
//      draught = new Draught(backgroundLayer.getDeskSide(), rows, cols, row, col, white, backgroundLayer.getOffsetX());
//      add(draught);
//      square.setOccupant(draught);
//      return draught;
//    }
//    return null;
//  }
//
//  public List<Square> highlightAllowedMoves(Draught clickedPiece) {
//    for (int row = 0; row < rows; row++) {
//      for (int col = 0; col < cols; col++) {
//        Square square = backgroundLayer.getSquare(row, col);
//        if (null != square) {
//          Draught draught = square.getOccupant();
//          if (draught != null && draught.isWhite() == clickedPiece.isWhite()) {
//            highlightPossibleMoves(draught, clickedPiece);
//          }
//        }
//      }
//    }
//    // если нашли шашку, которая будет побита, удаляем прорисоку обычных ходов
//    if (!capturedSquares.isEmpty()) {
//      for (int row = 0; row < rows; row++) {
//        for (int col = 0; col < cols; col++) {
//          Square square = backgroundLayer.getSquare(row, col);
//          if (null != square && isHighlightedSquare(square)) {
//            fadeOutSquare(square);
//          }
//        }
//      }
//    }
//    backgroundLayer.draw();
//    return highlightedSquares;
//  }
//
//  /**
//   * Find all possible Squares to which this Draught can move
//   *
//   * @param p Draught for which moves should be found
//   * @return A Vector of Squares to which this Draught can move
//   */
//  private void highlightPossibleMoves(Draught p, Draught clickedDraught) {
//    /* Possible moves include up-left, up-right, down-left, down-right
//     * This corresponds to (row-- col--), (row-- col++),
//		 * 						(row++ col--), (row++ col++) respectively
//		 */
//    Vector<Square> possibleMoves = new Vector<>();
//    Vector<Square> jumpMoves = new Vector<>();
//    boolean white = p.isWhite();
//    int row = p.getRow();
//    int col = p.getCol();
//
//    //Begin checking which moves are possible, keeping in mind that only black shashki may move up
//    //and only red shashki may move downwards
//
//    boolean queen = p.isQueen();
//    if (this.white) {
//      possibleMovePair(Operator.SUBTRACTION, Operator.SUBTRACTION, row, col, white, white, queen,
//          possibleMoves, jumpMoves, true);
//      possibleMovePair(Operator.SUBTRACTION, Operator.ADDITION, row, col, white, white, queen,
//          possibleMoves, jumpMoves, true);
//      possibleMovePair(Operator.ADDITION, Operator.SUBTRACTION, row, col, !white, white, queen,
//          possibleMoves, jumpMoves, true);
//      possibleMovePair(Operator.ADDITION, Operator.ADDITION, row, col, !white, white, queen,
//          possibleMoves, jumpMoves, true);
//    } else {
//      // top-left
//      possibleMovePair(Operator.SUBTRACTION, Operator.SUBTRACTION, row, col, !white, white, queen,
//          possibleMoves, jumpMoves, true);
//      // top-right
//      possibleMovePair(Operator.SUBTRACTION, Operator.ADDITION, row, col, !white, white, queen,
//          possibleMoves, jumpMoves, true);
//      // bottom-left
//      possibleMovePair(Operator.ADDITION, Operator.SUBTRACTION, row, col, white, white, queen,
//          possibleMoves, jumpMoves, true);
//      // bottom-right
//      possibleMovePair(Operator.ADDITION, Operator.ADDITION, row, col, white, white, queen,
//          possibleMoves, jumpMoves, true);
//    }
//
//    if (p == clickedDraught) {
//      if (!jumpMoves.isEmpty()) {
//        for (Square currentSq : jumpMoves) {
//          Square initSq = backgroundLayer.getSquare(clickedDraught.getRow(), clickedDraught.getCol());
//          if (initSq.isOnLine(currentSq)) {
//            highlightSquareToBeat(currentSq);
//            highlightedSquares.add(currentSq);
//          }
//        }
//      } else if (!possibleMoves.isEmpty()) {
//        for (Square square : possibleMoves) {
//          fadeInSquare(square);
//          highlightedSquares.add(square);
//        }
//      }
//    }
//  }
//
//  private void fadeInSquare(Square square) {
//    square.setAlpha(.5);
//  }
//
//  private void fadeOutSquare(Square square) {
//    square.setAlpha(1.0);
//  }
//
//  private boolean isHighlightedSquare(Square square) {
//    return .5 == square.getAlpha();
//  }
//
//  private void highlightSquareToBeat(Square square) {
//    square.setAlpha(.4);
//  }
//
//  /**
//   * Possible move in next and back direction
//   *
//   * @param opRow
//   * @param opCol
//   * @param row
//   * @param col
//   * @param white
//   * @param sideWhite        - цвет текущей шашки
//   * @param outPossibleMoves
//   * @param outJumpMoves
//   */
//  private void possibleMovePair(Operator opRow, Operator opCol, int row, int col, boolean white, boolean sideWhite,
//                                boolean queen, Vector<Square> outPossibleMoves, Vector<Square> outJumpMoves,
//                                boolean straightQueen) {
//    if (inBounds(opRow.apply(row, 1), opCol.apply(col, 1)) && (white || queen)) {
//      // Check moves to the op1, op2 of this Draught
//      if (!backgroundLayer.getSquare(opRow.apply(row, 1), opCol.apply(col, 1)).isOccupied()) {
//        outPossibleMoves.add(backgroundLayer.getSquare(opRow.apply(row, 1), opCol.apply(col, 1)));
//        if (queen) {
//          possibleMovePair(opRow, opCol, opRow.apply(row, 1), opCol.apply(col, 1), white, sideWhite,
//              true, outPossibleMoves, outJumpMoves, straightQueen);
//        }
//      } else {
//        //if square is occupied, and the color of the Draught in square is
//        //not equal to the Draught whose moves we are checking, then
//        //check to see if we can make the jump by checking
//        //the next square in the same direction
//        if (queen) {
//          if (inBounds(opRow.apply(row, 2), opCol.apply(col, 2))) {
//            if (!backgroundLayer.getSquare(opRow.apply(row, 2), opCol.apply(col, 2)).isOccupied()
//                && backgroundLayer.getSquare(opRow.apply(row, 1), opCol.apply(col, 1)).getOccupant().isWhite() != sideWhite) {
//              int i = opRow.apply(row, 2);
//              int j = opCol.apply(col, 2);
//              Vector<Square> jumps = new Vector<>();
//              Vector<Square> oneJumps = new Vector<>();
//              while (inBounds(i, j) && !backgroundLayer.getSquare(i, j).isOccupied()) {
//                if (straightQueen) {
//                  if (opRow.equals(PossibleOperators.SUB) && opCol.equals(PossibleOperators.SUB)
//                      || opRow.equals(PossibleOperators.ADD) && opCol.equals(PossibleOperators.ADD)) { // top-left & bottom-right
//                    possibleMovePair(Operator.ADDITION, Operator.SUBTRACTION, i, j, white, sideWhite, true,
//                        outPossibleMoves, outJumpMoves, false);
//                    possibleMovePair(Operator.SUBTRACTION, Operator.ADDITION, i, j, white, sideWhite, true,
//                        outPossibleMoves, outJumpMoves, false);
//                  } else if (opRow.equals(PossibleOperators.SUB) && opCol.equals(PossibleOperators.ADD)
//                      || opRow.equals(PossibleOperators.ADD) && opCol.equals(PossibleOperators.SUB)) { // bottom-left & top-right
//                    possibleMovePair(Operator.SUBTRACTION, Operator.SUBTRACTION, i, j, white, sideWhite, true,
//                        outPossibleMoves, outJumpMoves, false);
//                    possibleMovePair(Operator.ADDITION, Operator.ADDITION, i, j, white, sideWhite, true,
//                        outPossibleMoves, outJumpMoves, false);
//                  }
//                  if (!outJumpMoves.isEmpty()) {
//                    jumps.add(backgroundLayer.getSquare(i, j));
//                    outJumpMoves.clear();
//                  } else if (jumps.isEmpty()) {
//                    oneJumps.add(backgroundLayer.getSquare(i, j));
//                  }
//                } else {
//                  outJumpMoves.add(backgroundLayer.getSquare(i, j));
//                }
//                i = opRow.apply(i, 1);
//                j = opCol.apply(j, 1);
//              }
//              if (!jumps.isEmpty()) {
//                outJumpMoves.addAll(jumps);
//              } else if (!oneJumps.isEmpty()) {
//                outJumpMoves.addAll(oneJumps);
//              }
//              Square captured = backgroundLayer.getSquare(opRow.apply(row, 1), opCol.apply(col, 1));
//              if (!capturedSquares.contains(captured)) {
//                capturedSquares.add(captured);
//              }
//            }
//          }
//        } else {
//          if (inBounds(opRow.apply(row, 2), opCol.apply(col, 2))) {
//            if (!backgroundLayer.getSquare(opRow.apply(row, 2), opCol.apply(col, 2)).isOccupied()
//                && backgroundLayer.getSquare(opRow.apply(row, 1), opCol.apply(col, 1))
//                .getOccupant().isWhite() != sideWhite) {
//              outJumpMoves.add(backgroundLayer.getSquare(opRow.apply(row, 2), opCol.apply(col, 2)));
//              Square captured = backgroundLayer.getSquare(opRow.apply(row, 1), opCol.apply(col, 1));
//              if (!capturedSquares.contains(captured)) {
//                capturedSquares.add(captured);
//              }
//            }
//          }
//        }
//      }
//    }
//
//    if (!queen) {
//      if (inBounds(opRow.apply(row, 2), opCol.apply(col, 2))) {
//        if (!backgroundLayer.getSquare(opRow.apply(row, 2), opCol.apply(col, 2)).isOccupied()
//            && backgroundLayer.getSquare(opRow.apply(row, 1), opCol.apply(col, 1)).isOccupied()
//            && backgroundLayer.getSquare(opRow.apply(row, 1), opCol.apply(col, 1))
//            .getOccupant().isWhite() != sideWhite) {
//          outJumpMoves.add(backgroundLayer.getSquare(opRow.apply(row, 2), opCol.apply(col, 2)));
//          Square captured = backgroundLayer.getSquare(opRow.apply(row, 1), opCol.apply(col, 1));
//          if (!capturedSquares.contains(captured)) {
//            capturedSquares.add(captured);
//          }
//        }
//      }
//    }
//  }
//
//  public BoardBackgroundLayer getBackgroundLayer() {
//    return backgroundLayer;
//  }
//
//  public void resetDeskDrawing() {
//    backgroundLayer.resetDeskDrawing();
//  }
//
//  public Square getSquare(double row, double col) {
//    return backgroundLayer.getSquare(row, col);
//  }
//
//  public Square getSquare(int row, int col) {
//    return backgroundLayer.getSquare(row, col);
//  }
//
//  private void possibleNextMovePair(Square takenSquare, Operator opRow, Operator opCol, int row, int col,
//                                    boolean queen, Vector<Square> outJumpMoves) {
//    if (inBounds(opRow.apply(row, 1), opCol.apply(col, 1))) {
//      // Check moves to the op1, op2 of this Draught
//      Square jumpSquare = backgroundLayer.getSquare(opRow.apply(row, 2), opCol.apply(col, 2));
//      Square capturedSquare = backgroundLayer.getSquare(opRow.apply(row, 1), opCol.apply(col, 1));
//      //if square is occupied, and the color of the Draught in square is
//      //not equal to the Draught whose moves we are checking, then
//      //check to see if we can make the jump by checking
//      //the next square in the same direction
//      if (queen) {
//        if (inBounds(opRow.apply(row, 2), opCol.apply(col, 2))) {
//          if (capturedSquare.isBetween(backgroundLayer.getSquare(row, col), takenSquare)) {
//            return;
//          }
//          int r = row;
//          int c = col;
//          while (inBounds(opRow.apply(r, 2), opCol.apply(c, 2))
//              && !capturedSquare.isOccupied()) {
//            r = opRow.apply(r, 1);
//            c = opCol.apply(c, 1);
//            capturedSquare = backgroundLayer.getSquare(opRow.apply(r, 1), opCol.apply(c, 1));
//            jumpSquare = backgroundLayer.getSquare(opRow.apply(r, 2), opCol.apply(c, 2));
//          }
//          if (jumpSquare != null && !jumpSquare.isOccupied()
//              && capturedSquare.getOccupant().isWhite() != white) {
//            int i = jumpSquare.getRow();
//            int j = jumpSquare.getCol();
//            while (inBounds(i, j) && !backgroundLayer.getSquare(i, j).isOccupied()) {
//              outJumpMoves.add(backgroundLayer.getSquare(i, j));
//              i = opRow.apply(i, 1);
//              j = opCol.apply(j, 1);
//            }
//            if (!capturedSquares.contains(capturedSquare)) {
//              capturedSquares.add(capturedSquare);
//            }
//          }
//        }
//      } else if (backgroundLayer.getSquare(opRow.apply(row, 1), opCol.apply(col, 1)).isOccupied()) {
//        // нельзя возвращяться назад
//        if (takenSquare == jumpSquare) {
//          return;
//        }
//        if (inBounds(opRow.apply(row, 2), opCol.apply(col, 2))) {
//          if (!jumpSquare.isOccupied()
//              && capturedSquare.getOccupant().isWhite() != white) {
//            outJumpMoves.add(jumpSquare);
//            if (!capturedSquares.contains(capturedSquare)) {
//              capturedSquares.add(capturedSquare);
//            }
//          }
//        }
//      }
//    }
//  }
//
//  /**
//   * Perform a move on the board. This function does not perform input checking, as it is only called
//   * once a move has been validated by highlightPossibleMoves
//   * <p>
//   * <p>
//   * //   * @param from 				The square from which we are moving
//   * //   * @param to				The square to which we are moving
//   *
//   * @return True if a jump has been performed, false if it's just a normal move
//   */
//  public String move(Square from, Square to) {
//    String removedCoords = NOT_REMOVED;
//
//    Draught beingMoved = from.getOccupant();
//
//    from.setOccupant(null);
//    beingMoved.setPosition(to.getRow(), to.getCol());
//    to.setOccupant(beingMoved);
//
//    if (!capturedSquares.isEmpty()) {
//      //A jump has been performed, so get the Square that lies between from and to
//      Square takenSquare = null;
//      for (Square s : capturedSquares) {
//        if (to.isOnLine(s) && s.isBetween(from, to)) {
//          takenSquare = s;
//          break;
//        }
//      }
//
//      if (takenSquare == null) {
//        return NOT_REMOVED;
//      }
//
//      int row = to.getRow();
//      int col = to.getCol();
//      boolean queen = beingMoved.isQueen();
//      if (!queen && row == 0) {
//        queen = true;
//      }
//      Vector<Square> jumpMoves = new Vector<>();
//      if (this.white) {
//        possibleNextMovePair(from, Operator.SUBTRACTION, Operator.SUBTRACTION, row, col, queen,
//            jumpMoves);
//        possibleNextMovePair(from, Operator.SUBTRACTION, Operator.ADDITION, row, col, queen,
//            jumpMoves);
//        possibleNextMovePair(from, Operator.ADDITION, Operator.SUBTRACTION, row, col, queen,
//            jumpMoves);
//        possibleNextMovePair(from, Operator.ADDITION, Operator.ADDITION, row, col, queen,
//            jumpMoves);
//      } else {
//        // top-left
//        possibleNextMovePair(from, Operator.SUBTRACTION, Operator.SUBTRACTION, row, col, queen,
//            jumpMoves);
//        // top-right
//        possibleNextMovePair(from, Operator.SUBTRACTION, Operator.ADDITION, row, col, queen,
//            jumpMoves);
//        // bottom-left
//        possibleNextMovePair(from, Operator.ADDITION, Operator.SUBTRACTION, row, col, queen,
//            jumpMoves);
//        // bottom-right
//        possibleNextMovePair(from, Operator.ADDITION, Operator.ADDITION, row, col, queen,
//            jumpMoves);
//      }
//
//      removedCoords = takenSquare.toSend() + (!jumpMoves.isEmpty() ? MOVE_STR_SEPARATOR + NEXT_MOVE : MOVE_STR_SEPARATOR + STOP_BEAT_MOVE);
//
//      opponentDraughtVector.remove(takenSquare.getOccupant());
//      removeDraughtFrom(takenSquare);
//
//      capturedSquares = new Vector<>();
//    }
//
//    return removedCoords;
//  }
//
//  public void removeDraughtFrom(Square takenSquare) {
//    removeDraughtFrom(takenSquare, false);
//  }
//
//  private void removeDraughtFrom(Square takenSquare, boolean clearDesk) {
//    final Draught takenDraught = takenSquare.getOccupant();
//    if (takenDraught == null) {
//      return;
//    }
//    takenSquare.setOccupant(null);
//    AnimationProperties props = new AnimationProperties();
//    props.push(AnimationProperty.Properties.ALPHA(0));
//    takenDraught.animate(AnimationTweener.LINEAR, props,
//        removeDraughtFade, new IAnimationCallback() {
//          @Override
//          public void onStart(IAnimation iAnimation, IAnimationHandle iAnimationHandle) {
//          }
//
//          @Override
//          public void onFrame(IAnimation iAnimation, IAnimationHandle iAnimationHandle) {
//          }
//
//          @Override
//          public void onClose(IAnimation iAnimation, IAnimationHandle iAnimationHandle) {
//            remove(takenDraught);
//            if (!isEmulate() && !clearDesk) {
//              eventBus.fireEvent(new CheckWinnerEvent());
//            }
//          }
//        });
//  }
//
//  public boolean inBounds(int row, int col) {
//    return row >= 0 && row < rows && col >= 0 && col < cols;
//  }
//
//  private Square parseStep(String move) {
//    int sRow = 8 - Integer.parseInt(move.substring(1, 2));
//    int sCol = alphMap.get(move.substring(0, 1));
//
//    return backgroundLayer.getSquare(sRow, sCol);
//  }
//
//  private Square findCaptured(Square firstStep, Square secondStep) {
//    Square captured = null;
//    OUTERLOOP:
//    for (int n = 0; n < rows; n++) {
//      for (int m = 0; m < cols; m++) {
//        Square current = backgroundLayer.getSquare(n, m);
//        if (null != current && null != current.getOccupant() && current.isBetween(firstStep, secondStep)
//            && current.isOnLine(firstStep)) {
//          captured = current;
//          break OUTERLOOP;
//        }
//      }
//    }
//    return captured;
//  }
//
//  public void moveEmulatedNextWhite(String move, int stepCursor) {
//    if (move.contains(ANNOTATION_SIMPLE_MOVE)) {
//      String[] steps = move.split(ANNOTATION_SIMPLE_MOVE);
//      Square startSquare = parseStep(steps[0]);
//      Square endSquare = parseStep(steps[1]);
//      move(startSquare, endSquare, null, false, stepCursor);
//    } else if (move.contains(ANNOTATION_BEAT_MOVE)) {
//      String[] steps = move.split(ANNOTATION_BEAT_MOVE);
//      for (int i = 0; i < steps.length - 1; i++) {
//        Square firstStep = parseStep(steps[i]);
//        Square secondStep = parseStep(steps[i + 1]);
//        Square captured = findCaptured(firstStep, secondStep);
//        if (null == captured) {
//          return;
//        }
//        capturedStack.push(captured);
//        move(firstStep, secondStep, captured, false, stepCursor);
//      }
//    }
//  }
//
//  public void moveEmulatedNextBlack(String move, int stepCursor) {
//    if (move.contains(ANNOTATION_SIMPLE_MOVE)) {
//      String[] steps = move.split(ANNOTATION_SIMPLE_MOVE);
//      Square startSquare = parseStep(steps[0]);
//      Square endSquare = parseStep(steps[1]);
//      move(startSquare, endSquare, null, false, stepCursor);
//    } else if (move.contains(ANNOTATION_BEAT_MOVE)) {
//      String[] steps = move.split(ANNOTATION_BEAT_MOVE);
//      for (int i = 0; i < steps.length - 1; i++) {
//        Square firstStep = parseStep(steps[i]);
//        Square secondStep = parseStep(steps[i + 1]);
//        Square captured = findCaptured(firstStep, secondStep);
//        if (null == captured) {
//          return;
//        }
//        capturedStack.push(captured);
//        move(firstStep, secondStep, captured, false, stepCursor);
//      }
//    }
//  }
//
//  public void moveEmulatedPrevWhite(String move, int stepCursor) {
//    if (move.contains(ANNOTATION_SIMPLE_MOVE)) {
//      String[] steps = move.split(ANNOTATION_SIMPLE_MOVE);
//      Square startSquare = parseStep(steps[1]);
//      Square endSquare = parseStep(steps[0]);
//      move(startSquare, endSquare, null, false, stepCursor);
//    } else if (move.contains(ANNOTATION_BEAT_MOVE)) {
//      String[] steps = move.split(ANNOTATION_BEAT_MOVE);
//      for (int i = steps.length - 1; i > 0; i--) {
//        Square firstStep = parseStep(steps[i]);
//        Square secondStep = parseStep(steps[i - 1]);
////        Square captured = findCaptured(firstStep, secondStep);
//        Square captured = capturedStack.pop();
//        if (null == captured) {
//          return;
//        } else {
//          mineDraughtVector.add(addDraught(captured.getRow(), captured.getCol(), !white));
//        }
//        move(firstStep, secondStep, null, false, stepCursor);
//      }
//    }
//  }
//
//  public void moveEmulatedPrevBlack(String move, int stepCursor) {
//    if (move.contains(ANNOTATION_SIMPLE_MOVE)) {
//      String[] steps = move.split(ANNOTATION_SIMPLE_MOVE);
//      Square startSquare = parseStep(steps[1]);
//      Square endSquare = parseStep(steps[0]);
//      move(startSquare, endSquare, null, false, stepCursor);
//    } else if (move.contains(ANNOTATION_BEAT_MOVE)) {
//      String[] steps = move.split(ANNOTATION_BEAT_MOVE);
//      for (int i = steps.length - 1; i > 0; i--) {
//        Square firstStep = parseStep(steps[i]);
//        Square secondStep = parseStep(steps[i - 1]);
////        Square captured = findCaptured(firstStep, secondStep);
//        Square captured = capturedStack.pop();
//        if (null == captured) {
//          return;
//        } else {
//          mineDraughtVector.add(addDraught(captured.getRow(), captured.getCol(), white));
//        }
//        move(firstStep, secondStep, null, false, stepCursor);
//      }
//    }
//  }
//
//  public void moveOpponent(String startMove, String endMove, String captured) {
//    moveOpponent(startMove, endMove, captured, -1);
//  }
//
//  public void moveOpponent(String start, String end, String capture, int stepCursor) {
//    int sRow = Integer.valueOf(start.split(MOVE_STR_SEPARATOR)[0]);
//    int sCol = Integer.valueOf(start.split(MOVE_STR_SEPARATOR)[1]);
//
//    int eRow = Integer.valueOf(end.split(MOVE_STR_SEPARATOR)[0]);
//    int eCol = Integer.valueOf(end.split(MOVE_STR_SEPARATOR)[1]);
//
//    Square sSquare = backgroundLayer.getSquare(sRow, sCol);
//    Square eSquare = backgroundLayer.getSquare(eRow, eCol);
//
//    int startRow = rows - 1 - Integer.valueOf(start.split(MOVE_STR_SEPARATOR)[0]);
//    int startCol = cols - 1 - Integer.valueOf(start.split(MOVE_STR_SEPARATOR)[1]);
//
//    int endRow = rows - 1 - Integer.valueOf(end.split(MOVE_STR_SEPARATOR)[0]);
//    int endCol = cols - 1 - Integer.valueOf(end.split(MOVE_STR_SEPARATOR)[1]);
//
//    Square startSquare = backgroundLayer.getSquare(startRow, startCol);
//    Square endSquare = backgroundLayer.getSquare(endRow, endCol);
//
//    boolean simpleMove = capture.contains(NOT_REMOVED);
//    if (!capture.contains(CANCEL_MOVE)) {
//      String op = simpleMove ? ANNOTATION_SIMPLE_MOVE : ANNOTATION_BEAT_MOVE;
//      String move = sSquare.toNotation(!isWhite(), false, false)
//          + op
//          + eSquare.toNotation(!isWhite(), true, false)
//          + (isWhite() ? NotationPanel.NOTATION_SEPARATOR : "");
//      eventBus.fireEvent(new NotationMoveEvent(move));
//    }
//
//    Square captured = null;
//    boolean nextCapture = false;
//    if (!simpleMove) {
//      int beatenRow = rows - 1 - Integer.valueOf(capture.split(MOVE_STR_SEPARATOR)[0]);
//      int beatenCol = cols - 1 - Integer.valueOf(capture.split(MOVE_STR_SEPARATOR)[1]);
//      nextCapture = NEXT_MOVE.equals(capture.split(MOVE_STR_SEPARATOR)[2]);
//      captured = backgroundLayer.getSquare(beatenRow, beatenCol);
//    }
//
//    boolean cancelMove = capture.contains(CANCEL_MOVE);
//
//    move(startSquare, endSquare, captured, nextCapture, cancelMove, stepCursor);
//  }
//
//  private void move(Square startSquare, Square endSquare, Square captured, boolean nextCapture, int stepCursor) {
//    move(startSquare, endSquare, captured, nextCapture, false, stepCursor);
//  }
//
//  private void move(Square startSquare, Square endSquare, Square captured, boolean nextCapture, boolean cancelMove) {
//    move(startSquare, endSquare, captured, nextCapture, cancelMove, -1);
//  }
//
//  private void move(Square startSquare, Square endSquare, Square captured, boolean nextCapture, boolean cancelMove,
//                    int stepCursor) {
//    final Draught occupant = startSquare.getOccupant();
//
//    // вычисляем координаты для перемещения шашки относительно её центра
//    occupant.updateShape();
//    final double mouseMovedX = occupant.getX() + endSquare.getCenterX() - startSquare.getCenterX(); // + occupant.getMouseMovedX();
//    final double mouseMovedY = occupant.getY() + endSquare.getCenterY() - startSquare.getCenterY(); // + occupant.getMouseMovedY();
//
//    occupant.animate(AnimationTweener.LINEAR, new AnimationProperties(
//        AnimationProperty.Properties.X(mouseMovedX),
//        AnimationProperty.Properties.Y(mouseMovedY)
//    ), moveDraughtDuration, new AnimationCallback() {
//      @Override
//      public void onClose(IAnimation animation, IAnimationHandle handle) {
//        super.onClose(animation, handle);
//        // if draught achieved start of desk mark it as Queen
//        if (!occupant.isQueen()) {
//          if (isEmulate()) {
//            if (null != endSquare.getOccupant() && endSquare.getOccupant().isWhite() && 0 == endSquare.getRow()
//                || null != endSquare.getOccupant() && !endSquare.getOccupant().isWhite()
//                && (rows - 1) == endSquare.getRow()) {
//              occupant.setPosition(endSquare.getRow(), endSquare.getCol());
//              occupant.setQueen(true);
//              queenStepStack.push(stepCursor);
//            }
//          } else if ((rows - 1) == endSquare.getRow()) {
//            occupant.setPosition(endSquare.getRow(), endSquare.getCol());
//            occupant.setQueen(true);
//          }
//        }
//      }
//    });
//
//    endSquare.setOccupant(occupant);
//    occupant.setPosition(endSquare.getRow(), endSquare.getCol());
//
//    if (captured != null && !cancelMove) {
//      mineDraughtVector.remove(captured.getOccupant());
//      removeDraughtFrom(captured);
//    } else if (captured != null) {
//      if (isMyTurn()) {
//        opponentDraughtVector.add(addDraught(captured.getRow(), captured.getCol(), isWhite()));
//      } else {
//        mineDraughtVector.add(addDraught(captured.getRow(), captured.getCol(), !isWhite()));
//      }
//    }
//
//    if (!nextCapture && !isEmulate()) {
//      toggleTurn();
//    }
//
//    // переносим этот код из конца анимации сюда, потому что в ускоренной промотки он не выполняется.
//    if (isEmulate() && occupant.isQueen()) {
//      if ((null != endSquare.getOccupant() && endSquare.getOccupant().isWhite()
//          && 0 == startSquare.getRow()
//          || null != endSquare.getOccupant() && !endSquare.getOccupant().isWhite()
//          && (rows - 1) == startSquare.getRow())
//          && queenStepStack.peek() == stepCursor + 1) {
//        occupant.setPosition(endSquare.getRow(), endSquare.getCol());
//        occupant.setQueen(false);
//        queenStepStack.pop();
//      }
//    }
//    startSquare.setOccupant(null);
//    // ---
//  }
//
//  public boolean isWhite() {
//    return white;
//  }
//
//  public boolean isMyTurn() {
//    return turn;
//  }
//
//  public boolean toggleTurn() {
//    turn = !turn;
//    eventBus.fireEvent(new TurnChangeEvent(turn));
//    return turn;
//  }
//
//  public Vector<Draught> getMineDraughts() {
//    return mineDraughtVector;
//  }
//
//  public Vector<Draught> getOpponentDraughts() {
//    return opponentDraughtVector;
//  }
//
//  public boolean isEmulate() {
//    return emulate;
//  }
//
//  public void setEmulate(boolean emulate) {
//    this.emulate = emulate;
//  }
//
//  public void moveDraught(double clickX, double clickY) {
//    Draught selectedDraught = Draught.getSelectedDraught();
//    if (selectedDraught != null && !highlightedSquares.isEmpty()) {
//      Square endSquare = getSquare(clickX, clickY);
//      lastEndMove = endSquare.toSend();
//
//      Square startSquare = getSquare(selectedDraught.getRow(), selectedDraught.getCol());
//      lastStartMove = startSquare.toSend();
//
//      if (highlightedSquares.contains(endSquare) && startSquare.isOnLine(endSquare)) {
//        String captured = lastCaptured = move(startSquare, endSquare);
//
//        boolean isSimpleMove = NOT_REMOVED.equals(captured);
//        if (NOT_REMOVED.equals(captured) || STOP_BEAT_MOVE.equals(captured.split(MOVE_STR_SEPARATOR)[2])) {
//          toggleTurn();
//        }
//        if (!selectedDraught.isQueen()) {
//          if (selectedDraught.getRow() == 0) {
//            selectedDraught.setQueen(true);
//          }
//        }
//
//        String op = isSimpleMove ? ANNOTATION_SIMPLE_MOVE : ANNOTATION_BEAT_MOVE;
//        String move = startSquare.toNotation(isWhite(), false, false)
//            + op
//            + endSquare.toNotation(isWhite(), true, false)
//            + (isWhite() ? "" : NotationPanel.NOTATION_SEPARATOR);
//        eventBus.fireEvent(new NotationMoveEvent(move));
//        eventBus.fireEvent(new PlayMoveEvent(startSquare.toSend(), endSquare.toSend(), captured));
//
//        AnimationProperties props = new AnimationProperties();
//        props.push(AnimationProperty.Properties.X(endSquare.getCenterX()));
//        props.push(AnimationProperty.Properties.Y(endSquare.getCenterY()));
//
//        selectedDraught.animate(AnimationTweener.LINEAR, props, 100);
//
//        props = new AnimationProperties();
//        props.push(AnimationProperty.Properties.SCALE(1.0));
//
//        selectedDraught.animate(AnimationTweener.LINEAR, props, 100);
//
//        backgroundLayer.resetDeskDrawing();
//      }
//    }
//  }
//
//  public void clearDesk() {
//    for (int i = 0; i < cols; i++) {
//      for (int j = 0; j < rows; j++) {
//        Square square = getSquare(i, j);
//        if (square != null) {
//          removeDraughtFrom(square, true);
//        }
//      }
//    }
//  }
//
//  public String getLastEndMove() {
//    return lastEndMove;
//  }
//
//  public String getLastStartMove() {
//    return lastStartMove;
//  }
//
//  public String getLastCaptured() {
//    return lastCaptured;
//  }
//
//  public void moveCanceled(String startMove, String endMove, String capture) {
//    int startRow = Integer.valueOf(startMove.split(MOVE_STR_SEPARATOR)[0]);
//    int startCol = Integer.valueOf(startMove.split(MOVE_STR_SEPARATOR)[1]);
//
//    int endRow = Integer.valueOf(endMove.split(MOVE_STR_SEPARATOR)[0]);
//    int endCol = Integer.valueOf(endMove.split(MOVE_STR_SEPARATOR)[1]);
//
//    Square startSquare = backgroundLayer.getSquare(startRow, startCol);
//    Square endSquare = backgroundLayer.getSquare(endRow, endCol);
//
//    boolean simpleMove = capture.contains(NOT_REMOVED);
//    boolean cancelMove = capture.contains(CANCEL_MOVE);
//
//    Square captured = null;
//    boolean nextCapture = false;
//    if (!simpleMove) {
//      int beatenRow = Integer.valueOf(capture.split(MOVE_STR_SEPARATOR)[0]);
//      int beatenCol = Integer.valueOf(capture.split(MOVE_STR_SEPARATOR)[1]);
//      nextCapture = NEXT_MOVE.equals(capture.split(MOVE_STR_SEPARATOR)[2]);
//      captured = backgroundLayer.getSquare(beatenRow, beatenCol);
//    }
//
//    move(startSquare, endSquare, captured, nextCapture, cancelMove);
//  }
//
//}
