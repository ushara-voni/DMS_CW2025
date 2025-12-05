package com.comp2042.logic.bricks;

import com.comp2042.logic.core.BoardGrid;
import com.comp2042.logic.core.ViewData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

class BrickManagerTest {

    private BrickManager manager;
    private BoardGrid board;

    @BeforeEach
    void setUp() {
        // Dummy 10x20 board with all empty cells
        board = new BoardGrid(10, 20);
        manager = new BrickManager(board);
    }

    @Test
    void testSpawnNewBrick() {
        boolean spawned = manager.spawnNewBrick();
        assertTrue(spawned);
        assertNotNull(manager.getViewData());
    }

    @Test
    void testHoldPiece() {
        ViewData initialData = manager.getViewData();
        int[][] initialShape = initialData.getBrickData();

        // First hold should store current and spawn new
        ViewData afterHold = manager.holdPiece();
        assertNotNull(manager.getHeldBrickMatrix());
        assertFalse(manager.holdPiece() == afterHold); // holdUsedThisTurn prevents double hold

        // Reset and test swapping held piece
        manager.resetHold();
        manager.holdPiece(); // hold current
        ViewData swapData = manager.holdPiece(); // swap with held
        assertNotNull(swapData);
    }

    @Test
    void testGetNextShapePreview() {
        int[][] nextShape = manager.getNextShapePreview();
        assertNotNull(nextShape);
    }

    @Test
    void testMove() {
        Point offsetBefore = manager.getCurrentOffset();

        // Move right
        boolean movedRight = manager.moveRight();
        assertTrue(movedRight);
        Point offsetAfterRight = manager.getCurrentOffset();
        assertEquals(offsetBefore.x + 1, offsetAfterRight.x);

        // Move left
        boolean movedLeft = manager.moveLeft();
        assertTrue(movedLeft);
        Point offsetAfterLeft = manager.getCurrentOffset();
        assertEquals(offsetBefore.x, offsetAfterLeft.x);

        // Move down
        boolean movedDown = manager.moveDown();
        assertTrue(movedDown);
        Point offsetAfterDown = manager.getCurrentOffset();
        assertEquals(offsetBefore.y + 1, offsetAfterDown.y);
    }

    @Test
    void testRotate() {
        boolean rotated = manager.rotate();
        assertTrue(rotated);

        // If we place a collision manually, rotation should fail
        // Simulate collision: fill board cells at current brick position
        int[][] currentShape = manager.getViewData().getBrickData();
        board.merge(currentShape, manager.getCurrentOffset().x, manager.getCurrentOffset().y);

        boolean rotationBlocked = manager.rotate();
        assertFalse(rotationBlocked);
    }

    @Test
    void testMergeToBoard() {
        int[][] shape = manager.getViewData().getBrickData();
        manager.mergeToBoard();

        int[][] boardMatrix = board.getMatrixCopy();
        // Check if the top-left cell of brick is now non-zero in board
        assertEquals(shape[0][0], boardMatrix[manager.getCurrentOffset().x][manager.getCurrentOffset().y]);
    }
}
