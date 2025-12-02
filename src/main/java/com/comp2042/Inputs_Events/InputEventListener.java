package com.comp2042.Inputs_Events;


import com.comp2042.UI.ClearRow;
import com.comp2042.UI.ViewData;

public interface InputEventListener {

    ViewData onDownEvent(MoveEvent event);
    ViewData onHardDropEvent(MoveEvent event);

    ViewData onLeftEvent(MoveEvent event);

    ViewData onRightEvent(MoveEvent event);

    ViewData onRotateEvent(MoveEvent event);

    int[][] getBoardMatrix();

    void createNewGame();
    ClearRow pollLastClearRow();
    ViewData onHoldEvent(MoveEvent event);
    int[][] getHeldBrickMatrix();
}
