var DRAUGHT_MOVE_DURATION = 400;
var MOVING_DRAUGHT = false;

$(document).ready(function () {
    updatePlay();
});

updatePlay = function () {
    snap = Snap(canvasId);

    var draughts = snap.selectAll("circle[fill~='" + draughtMineColor + "']");
    console.log(draughts);
    draughts.forEach(function (draught) {
        draught.unhover();
        draught.hover(function () {
            if (!MOVING_DRAUGHT) {
                draught.animate({fill: "yellow"}, 100, snap.easeIn);
                setTimeout(function () {
                    draught.animate({fill: draughtMineColor}, 100, snap.easeIn);
                }, 100);
            }
        }, draught);
    });

    var deskSquareColors = [deskSquareColor, deskSquareHighlight, deskSquareHighlightToBeat];

    deskSquareColors.forEach(function (color) {
        var squares = snap.selectAll("rect[fill~='" + color + "']");
        squares.forEach(function (square) {
            squareClickHandler(square);
        });
    });

    function squareClickHandler(square) {
        square.click(function () {
            var selectedDraught = snap.select("circle[fill~='" + draughtClickedColor + "']");
            if (selectedDraught !== undefined) {
                moveDraught(selectedDraught, square)
            }
        });
    }

    function moveDraught(selectedDraught, moveToSquare) {
        if (selectedDraught !== undefined && moveToSquare !== undefined) {
            var attr = moveToSquare.attr();
            var newX = parseFloat(attr.x) + parseFloat(attr.width) / 2;
            var newY = parseFloat(attr.y) + parseFloat(attr.height) / 2;
            selectedDraught.animate({
                    cx: newX,
                    cy: newY
                },
                DRAUGHT_MOVE_DURATION,
                snap.easeIn
            );
            selectedDraught.attr({fill: resetDraughtColor()});
            MOVING_DRAUGHT = true;
        }

        function resetDraughtColor() {
            return draughtMineColor === draughtWhiteColor ? draughtWhiteColor : draughtBlackColor;
        }
    }
};

doPlay = function () {
    if (!MOVING_DRAUGHT) {
        updateCanvasCommand();
    }
    updatePlayCommand();
    if (MOVING_DRAUGHT) {
        setTimeout(function () {
            MOVING_DRAUGHT = false;
            updateCanvasCommand();
            updatePlayCommand()
        }, DRAUGHT_MOVE_DURATION);
    }
};