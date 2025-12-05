package com.comp2042.logic.core;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ClearRowTest {

    @Test
    void testClearRowProperties() {
        int[][] matrix = {{0,1},{1,0}};
        ClearRow clearRow = new ClearRow(2, matrix, 100);

        assertEquals(2, clearRow.getLinesRemoved());
        assertEquals(100, clearRow.getScoreBonus());

        int[][] copiedMatrix = clearRow.getNewMatrix();
        assertArrayEquals(matrix, copiedMatrix);

        // Ensure copy is independent
        copiedMatrix[0][0] = 9;
        assertNotEquals(copiedMatrix[0][0], matrix[0][0]);
    }
}
