package ru.shashki.server.jsf.util.shashki;


import io.snapfaces.faces.model.element.Rect;

import java.util.Arrays;

/**
 * Created with IntelliJ IDEA.
 * Profile: alekspo
 * Date: 07.12.13
 * Time: 21:07
 */
public class Square extends Rect {

    private final int row;
    private final int col;
    private final int rows;
    private final int cols;
    private final double deskSide;
    private boolean occupied;
    private Draught occupant;
    private double strokeLineWidth = 1.5;
    private String[] alph = new String[]{"a", "b", "c", "d", "e", "f", "g", "h"};
    private double offsetX;

    public Square(double deskSide, int rows, int cols, int row, int col, double offsetX, String fillColor) {
        this.deskSide = deskSide;
        this.row = row;
        this.col = col;
        this.rows = rows;
        this.cols = cols;
        this.offsetX = offsetX;

        occupied = false;
        occupant = null;

        updateShape();

        setDraggable(false);
        setFill(fillColor);
    }

    public static boolean isValid(int row, int col) {
        return row % 2 != col % 2;
    }

    /**
     * Get the piece that occupies this Square
     *
     * @return The piece that occupies this Square, if any
     */
    public Draught getOccupant() {
        if (this.isOccupied()) {
            return occupant;
        }

        return null;
    }

    /**
     * Set the occupant of this Square
     *
     * @param visitor The Piece that should now reside here
     */
    public void setOccupant(Draught visitor) {
        if (visitor != null) {
            this.occupant = visitor;
            this.occupied = true;
        } else {
            this.occupant = null;
            this.occupied = false;
        }
    }

    /**
     * Return whether or not this Square is occupied
     *
     * @return Whether or not this Square is selected
     */
    public boolean isOccupied() {
        return occupied;
    }

    @Override
    public String toString() {
        return "Square{" +
                "row=" + row +
                ", col=" + col +
                ", rows=" + rows +
                ", cols=" + cols +
                ", deskSide=" + deskSide +
                ", occupied=" + occupied +
                ", strokeLineWidth=" + strokeLineWidth +
                ", alph=" + Arrays.toString(alph) +
                ", offsetX=" + offsetX +
                '}';
    }

    public String toSend() {
        return String.valueOf(row) + "," + String.valueOf(col);
    }

    public String toNotation(boolean white, boolean second, boolean fromOpponent) {
        if (white) {
            return alph[col] + String.valueOf(rows - row);
        }
        return alph[cols - 1 - col] + String.valueOf(row + 1);
    }

    /**
     * Возвращает true если square на той же диагонале что и эта
     *
     * @param square
     * @return
     */
    public boolean isOnLine(Square square) {
        // равенство абсолютной велечины строки и колонки говорит что мы на одной диагонали
        if (Math.abs(this.getRow() - square.getRow()) == Math.abs(this.getCol() - square.getCol())) {
            // на главной диагонали
            if (this.getRow() - square.getRow() < 0 && this.getCol() - square.getCol() > 0
                    || this.getRow() - square.getRow() > 0 && this.getCol() - square.getCol() < 0) {
                return true;
            }
            // на побочной (математической главной)
            if (this.getRow() - square.getRow() < 0 && this.getCol() - square.getCol() < 0
                    || this.getRow() - square.getRow() > 0 && this.getCol() - square.getCol() > 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * Get the row of the game board that this square represents
     *
     * @return The row on the game board represented by this Square
     */
    public int getRow() {
        return row;
    }

    /**
     * Get the column of the game board that this square represents
     *
     * @return The column on the game board represented by this Square
     */
    public int getCol() {
        return col;
    }

    public boolean isBetween(Square from, Square to) {
        if (isBetween(this.getRow(), from.getRow(), to.getRow())
                && isBetween(this.getCol(), from.getCol(), to.getCol())) {
            return true;
        }
        if (isBetween(this.getRow(), from.getRow(), to.getRow())
                && isBetween(this.getCol(), to.getCol(), from.getCol())) {
            return true;
        }
        if (isBetween(this.getRow(), to.getRow(), from.getRow())
                && isBetween(this.getCol(), to.getCol(), from.getCol())) {
            return true;
        }
        if (isBetween(this.getRow(), to.getRow(), from.getRow())
                && isBetween(this.getCol(), from.getCol(), to.getCol())) {
            return true;
        }
        return false;
    }

    private static boolean isBetween(int value, int min, int max) {
        return ((value > min) && (value < max));
    }

    public void updateShape() {
        double x = ((double) col) * deskSide / ((double) rows) + offsetX;
        double y = ((double) row) * deskSide / ((double) cols);
        setX(x);
        setY(y);
        double squareSize = deskSide / rows;
        setWidth(squareSize);
        setHeight(squareSize);
    }

    public boolean contains(double x, double y) {
        return (Math.abs(x - getX()) < getWidth()) && (Math.abs(y - getY()) < getHeight());
    }

    public double getCenterX() {
        return getX() + getWidth() / 2;
    }

    public double getCenterY() {
        return getY() + getHeight() / 2;
    }
}