$(document).ready(function () {
    var selectedDraught;
    var moveToSquare;
    var s = Snap("#canvas");

    var draughts = s.selectAll("circle[fill~='" + draughtMineColor + "']");
    draughts.forEach(function (draught) {
        draught.hover(function () {
            draught.animate({r: draughtRadius + 2}, 100, s.easeIn);
        }, function () {
            draught.animate({r: draughtRadius}, 100, s.easeIn);
        });
    });

    var whiteDraughts = s.selectAll("circle[fill~='" + draughtMineColor + "']");

    function resetDraughtColor() {
        return draughtMineColor === draughtWhiteColor ? draughtWhiteColor : draughtBlackColor;
    }

    //whiteDraughts.forEach(function (draught) {
    //    draught.click(function () {
    //        if (selectedDraught !== undefined) {
    //            selectedDraught.attr({fill: resetDraughtColor()});
    //        }
    //
    //        var selectColor = draughtMineColor === draughtWhiteColor ? "red" : "green";
    //        draught.attr({fill: selectColor});
    //        selectedDraught = draught;
    //    });
    //});
    //
    //var squares = s.selectAll("rect[fill~='" + deskSquareColor + "']");
    //squares.forEach(function (square) {
    //    square.click(function () {
    //        if (selectedDraught !== undefined) {
    //            moveToSquare = square;
    //        }
    //    });
    //});

    PrimeFaces.widget.moveDraught = function () {
        if (selectedDraught !== undefined && moveToSquare !== undefined) {
            var attr = moveToSquare.attr();
            var newX = parseFloat(attr.x) + parseFloat(attr.width) / 2;
            var newY = parseFloat(attr.y) + parseFloat(attr.height) / 2;
            selectedDraught.animate({
                cx: newX,
                cy: newY
            }, 100, s.easeIn);
            selectedDraught.attr({fill: resetDraughtColor()});
            selectedDraught = moveToSquare = undefined;
        }
    }
});


