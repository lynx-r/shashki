package ru.shashki.server.jsf.view;

import io.snapfaces.faces.event.ClickEvent;
import io.snapfaces.faces.model.DefaultSnapModel;
import ru.shashki.server.jsf.util.shashki.Board;
import ru.shashki.server.jsf.util.shashki.BoardBackgroundLayer;
import ru.shashki.server.jsf.util.shashki.Draught;
import ru.shashki.server.jsf.util.shashki.Square;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 * Created with IntelliJ IDEA.
 * User: alekspo
 * Date: 25.04.15
 * Time: 7:05
 */
@ManagedBean
@SessionScoped
public class PlayView extends BaseView {

    private DefaultSnapModel model;

    private int deskSide = 600;
    private int OFFSET_X = 30;

    private int rows = 8;
    private int cols = 8;

    private Square[][] gameBoard = new Square[rows][cols];
    private Boolean white;

    private Draught prevClicked;
    private Board board;
    private double draughtRadius;
    private String draughtWhiteColor = "#f0ffff";
    private String draughtBlackColor = "#a9a9a9";
    private String draughtMineColor;
    private String deskSquareColor;
    private String fillDeskColor = "#8b4513";
    private String draughtClickedColor = "#ff0000";
    private String deskSquareHighlight = "#ff0000";
    private String deskSquareHighlightToBeat = "#aaffaa";

    @PostConstruct
    public void init() {
        white = Boolean.valueOf(getFacesContext().getExternalContext().getRequestParameterMap().get("white"));
        model = new DefaultSnapModel();

        BoardBackgroundLayer backgroundLayer = new BoardBackgroundLayer(deskSide, deskSide - OFFSET_X, OFFSET_X,
                rows, cols, fillDeskColor);
        model.addGroup(backgroundLayer);

        board = new Board(backgroundLayer, rows, cols, white, draughtWhiteColor, draughtBlackColor, deskSquareHighlight,
                deskSquareHighlightToBeat);
        model.addGroup(board);

        draughtRadius = board.getMineDraughts().get(0).getR();
        draughtMineColor = board.getMineDraughts().get(0).getFill();
        deskSquareColor = board.getSquare(0, 1).getFill();
    }

    public DefaultSnapModel getModel() {
        return model;
    }

    public void onClick(ClickEvent event) {
        if (event.getTarget() instanceof Draught) {
            Draught clicked = (Draught) event.getTarget();
            if (!draughtMineColor.equals(clicked.getFill())) {
                return;
            }
            if (prevClicked != null) {
                // TODO брать цвета от родителей
                prevClicked.updateShape();
            }
            clicked.setFill(draughtClickedColor);
            board.resetDeskDrawing();
            board.highlightAllowedMoves(clicked);
            prevClicked = clicked;
        } else if (event.getTarget() instanceof Square) {
            Square square = (Square) event.getTarget();
            board.moveDraught(prevClicked, square);
        }

        System.out.println(event.getTarget());
    }

    public double getDraughtRadius() {
        return draughtRadius;
    }

    public String getDraughtWhiteColor() {
        return draughtWhiteColor;
    }

    public String getDraughtBlackColor() {
        return draughtBlackColor;
    }

    public String getDraughtMineColor() {
        return draughtMineColor;
    }

    public String getDeskSquareColor() {
        return deskSquareColor;
    }

    public String getDraughtClickedColor() {
        return draughtClickedColor;
    }

    public String getDeskSquareHighlight() {
        return deskSquareHighlight;
    }

    public String getDeskSquareHighlightToBeat() {
        return deskSquareHighlightToBeat;
    }
}
