package com.comp2042.logic.bricks;

import com.comp2042.UI.BoardGrid;
import com.comp2042.UI.ViewData;

import java.awt.*;
/**
 * Manages the lifecycle, movement, rotation, holding, and view data of
 * tetromino bricks within the game.
 *
 * This class coordinates the following responsibilities:
 * <ul>
 *     <li>Fetching new bricks from a {@link BrickGenerator}</li>
 *     <li>Handling movement and rotation through {@link BrickRotator}</li>
 *     <li>Checking collisions using {@link MatrixOperations}</li>
 *     <li>Merging bricks into the main board through {@link BoardGrid}</li>
 *     <li>Providing preview and hold piece information to the UI</li>
 * </ul>
 */
public class BrickManager {
    /** Generates the current and next bricks */
    private final BrickGenerator brickGenerator;

    /** Handles shape rotation logic */
    private final BrickRotator brickRotator;


    /** Reference to the board used to check collisions and merge bricks */
    private final BoardGrid boardGrid;

    /** Current X/Y board position of the falling brick */
    private Point currentOffset;


    /** Stored held brick (if any) */
    private Brick heldBrick=null;

    /** Ensures hold can only be used once per brick drop */
    private boolean holdUsedThisTurn= false;

    /**
     * Creates a new {@code BrickManager} attached to the specified board grid.
     *
     * @param boardGrid the board logic handler
     */
    public BrickManager(BoardGrid boardGrid) {
        this.boardGrid = boardGrid;
        this.brickGenerator = new FactoryBrickGenerator();
        this.brickRotator = new BrickRotator();
    }

    /**
     * Spawns a new brick at the top of the board.
     *
     * @return {@code true} if the spawn position is valid, {@code false} if a collision occurs (game over state)
     */
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


    /**
     * Attempts to move the current brick by the given X/Y delta.
     *
     * @param dx change in horizontal movement (-1 left, +1 right)
     * @param dy change in vertical movement (+1 down)
     * @return {@code true} if the movement succeeds, {@code false} if blocked by board or other blocks
     */
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

    /** @return true if moved downward successfully */
    public boolean moveDown() { return move(0, 1); }

    /** @return true if moved left successfully */
    public boolean moveLeft() { return move(-1, 0); }
    /** @return true if moved right successfully */
    public boolean moveRight() { return move(1, 0); }


    /**
     * Attempts to rotate the brick to its next rotation state.
     *
     * @return {@code true} if the rotation succeeds, {@code false} if blocked by collision
     */
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


    /**
     * Merges the current falling brick permanently into the board matrix.
     */
    public void mergeToBoard() {
        boardGrid.merge(brickRotator.getCurrentShape(), currentOffset.x, currentOffset.y);
    }

    /** @return a defensive copy of the brick’s current offset on the board */
    public Point getCurrentOffset() { return new Point(currentOffset); }
    /** @return the current shape matrix of the falling brick */
    public int[][] getCurrentShape() { return brickRotator.getCurrentShape(); }
    /**
     * @return the shape matrix of the next brick (rotation 0)
     */
    public int[][] getNextShapePreview() { return brickGenerator.getNextBrick().getShapeMatrix().get(0); }

    /**
     * Packages current brick, next brick, and position data for the UI.
     *
     * @return a {@link ViewData} instance used for rendering
     */
    public ViewData getViewData() {
        Point offset = getCurrentOffset();
        return new ViewData(getCurrentShape(), offset.x, offset.y, getNextShapePreview());
    }
    /**
     * Activates the hold piece system.
     *
     * Rules:
     * <ul>
     *     <li>Can only hold once per falling brick</li>
     *     <li>First time holding stores the brick and spawns a new one</li>
     *     <li>Subsequent holds swap the current and held bricks</li>
     * </ul>
     *
     * @return updated {@link ViewData}, or {@code null} if hold was already used this turn
     */
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

    /**
     * Returns the matrix for the held brick (rotation index 0).
     *
     * @return 2D int matrix of the held piece, or {@code null} if no piece is held
     */
    public int[][] getHeldBrickMatrix() {
        if (heldBrick == null) return null;
        return heldBrick.getShapeMatrix().get(0);
    }


}
