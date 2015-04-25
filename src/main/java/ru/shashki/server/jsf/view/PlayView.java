package ru.shashki.server.jsf.view;

import org.primefaces.model.diagram.DefaultDiagramModel;
import org.primefaces.model.diagram.Element;
import org.primefaces.model.diagram.endpoint.DotEndPoint;
import org.primefaces.model.diagram.endpoint.EndPoint;
import org.primefaces.model.diagram.endpoint.EndPointAnchor;
import org.primefaces.model.diagram.endpoint.RectangleEndPoint;
import ru.shashki.server.jsf.util.shashki.Draught;
import ru.shashki.server.jsf.util.shashki.Square;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: alekspo
 * Date: 25.04.15
 * Time: 7:05
 */
@Model
public class PlayView implements Serializable {

    private DefaultDiagramModel model;

    private double deskSide = 27;
    private double OFFSET_X = 2;

    private int rows = 8;
    private int cols = 8;

    private Square[][] gameBoard = new Square[rows][cols];

    @PostConstruct
    public void init() {
        model = new DefaultDiagramModel();
        model.setMaxConnections(-1);

        boolean lastColor = false;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (lastColor) {
                    Square square = new Square(deskSide, rows, cols, i, j, OFFSET_X);
                    gameBoard[i][j] = square;
                    model.addElement(square);
                }
                // Toggle lastcolor
                lastColor = !lastColor;
            }
            // Switch starting color for next row
            lastColor = !lastColor;
        }

        Draught draught = new Draught(deskSide, rows, cols, 0, 7, true, OFFSET_X);
        model.addElement(draught);
    }

    private EndPoint createEndPoint(EndPointAnchor anchor) {
        DotEndPoint endPoint = new DotEndPoint(anchor);
        endPoint.setStyle("{fillStyle:'#404a4e'}");
        endPoint.setHoverStyle("{fillStyle:'#20282b'}");

        return endPoint;
    }

    private void drawDesk() {
        Element mainRect = new Element();
        mainRect.setDraggable(false);
        mainRect.setStyleClass("main-rect");
        model.addElement(mainRect);

        boolean lastColor = false;
        for (int i = 0; i < rows; i++) {
            int j = 0;
//            for (int j = 0; j < cols; j++) {
            if (lastColor) {
                Square square = new Square(deskSide, rows, cols, i, j, OFFSET_X);
                gameBoard[i][j] = square;
                model.addElement(square);

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
//            lastColor = !lastColor;
//        }
    }

    public Square[][] getGameBoard() {
        return gameBoard;
    }

    public Square getSquare(int row, int col) {
        if (inBounds(row, col)) {
            return gameBoard[row][col];
        }
        return null;
    }

    public Square getSquare(double x, double y) {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                Square square = getSquare(i, j);
//                if (square != null && square.contains(x, y)) {
//                    return square;
//                }
            }
        }
        return null;
    }

    public boolean inBounds(int row, int col) {
        return row >= 0 && row < rows && col >= 0 && col < cols;
    }

    private EndPoint createRectangleEndPoint(EndPointAnchor anchor) {
        RectangleEndPoint endPoint = new RectangleEndPoint(anchor);
        endPoint.setScope("network");
        endPoint.setSource(true);
        endPoint.setStyle("{fillStyle:'#98AFC7'}");
        endPoint.setHoverStyle("{fillStyle:'#5C738B'}");

        return endPoint;
    }

    public DefaultDiagramModel getModel() {
        return model;
    }
}
