package com.comp2042.UI;

import com.comp2042.brick_functions.BrickRotator;
import com.comp2042.brick_functions.MatrixOperations;
import com.comp2042.logic.bricks.*;

import java.awt.*;

public class BrickManager {

    private final BrickGenerator brickGenerator;
    private final BrickRotator brickRotator;
    private final BoardGrid boardGrid;
    private Point currentOffset;
    private Brick heldBrick=null;
    private boolean holdUsedThisTurn= false;

    public BrickManager(BoardGrid boardGrid) {
        this.boardGrid = boardGrid;
        this.brickGenerator = new RandomBrickGenerator();
        this.brickRotator = new BrickRotator();
    }

    /** Spawn a new brick at the top of the board */
    public boolean spawnNewBrick() {
        Brick brick = brickGenerator.getBrick();
        brickRotator.setBrick(brick);
        holdUsedThisTurn = false; // allow hold again for new brick


        // Center horizontally

        currentOffset = new Point(0, 1);

        // Check collision at start
        return !MatrixOperations.intersect(boardGrid.getMatrixCopy(),
                brickRotator.getCurrentShape(), currentOffset.x, currentOffset.y);
    }

    /** Move brick by dx, dy */
    public boolean move(int dx, int dy) {
        if (currentOffset == null) return false;
        Point newPos = new Point(currentOffset);
        newPos.translate(dx, dy);

        if (MatrixOperations.intersect(boardGrid.getMatrixCopy(),
                brickRotator.getCurrentShape(), newPos.x, newPos.y))
            return false;

        currentOffset = newPos;
        return true;
    }

    public boolean moveDown() { return move(0, 1); }
    public boolean moveLeft() { return move(-1, 0); }
    public boolean moveRight() { return move(1, 0); }

    public boolean rotateLeft() {
        // Get the next shape matrix directly
        int[][] rotated = brickRotator.getNextShape();

        // Check collision
        if (MatrixOperations.intersect(boardGrid.getMatrixCopy(),
                rotated, currentOffset.x, currentOffset.y))
            return false;

        // Update the current shape index
        int nextIndex = (brickRotator.getCurrentShapeIndex() + 1) % brickRotator.getShapeCount();
        brickRotator.setCurrentShape(nextIndex);

        return true;
    }


    /** Merge current brick into board */
    public void mergeToBoard() {
        boardGrid.merge(brickRotator.getCurrentShape(), currentOffset.x, currentOffset.y);
    }

    /** Accessors */
    public Point getCurrentOffset() { return new Point(currentOffset); }
    public int[][] getCurrentShape() { return brickRotator.getCurrentShape(); }
    public int[][] getNextShapePreview() { return brickGenerator.getNextBrick().getShapeMatrix().get(0); }

    /** Prepare view data */
    public ViewData getViewData() {
        Point offset = getCurrentOffset();
        return new ViewData(getCurrentShape(), offset.x, offset.y, getNextShapePreview());
    }

    public ViewData holdPiece() {
        if (holdUsedThisTurn) return null; // cannot hold twice per drop

        Brick current = brickRotator.getBrick();
        Brick temp = heldBrick;

        heldBrick = current;      // store current piece
        holdUsedThisTurn = true;  // prevent double hold

        if (temp == null) {
            // No previous hold → spawn a new brick
            spawnNewBrick();
        } else {
            // Swap the bricks
            brickRotator.setBrick(temp);
            currentOffset = new Point(0, 1);
        }

        return getViewData();
    }

    public int[][] getHeldBrickMatrix() {
        if (heldBrick == null) return null;
        return heldBrick.getShapeMatrix().get(0);
    }


}
