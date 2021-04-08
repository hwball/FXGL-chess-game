package com.hwball.fxglgames.chess;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.entity.Entity;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.Arrays;
import java.util.List;

import static com.almasb.fxgl.dsl.FXGLForKtKt.*;

public class ChessApp extends GameApplication {

    public enum Type {
        BOARD_SQUARE, PIECE
    }

    @Override
    protected void initSettings(GameSettings gameSettings) {
        gameSettings.setTitle("Chess");
        gameSettings.setVersion("1.0");
        gameSettings.setWidth(1000);
        gameSettings.setHeight(1000);
    }

    @Override
    protected void initGame() {
        drawBoard();
        spawnPieces();
    }

    private void drawBoard() {
        for (int rowCounter = 0; rowCounter < 8; rowCounter++) {
            for (int columnCounter = 0; columnCounter < 8; columnCounter++) {
                var node = new Rectangle(100, 100);
                node.setStroke(Color.BLACK);
                if (columnCounter % 2 == rowCounter % 2) {
                    node.setFill(Color.WHITE);
                } else {
                    node.setFill(Color.DARKGRAY);
                }
                Entity square = entityBuilder()
                        .type(Type.BOARD_SQUARE)
                        .at((columnCounter + 1) * 100, (rowCounter + 1) * 100)
                        .viewWithBBox(node)
                        .buildAndAttach();
            }
        }
    }

    private void spawnPieces() {
        List<String> sides = Arrays.asList("White", "Black");
        for (String side:sides) {
            //Pawns
            for (int columnCounter = 0; columnCounter < 8; columnCounter++) {
                Entity pawn = entityBuilder()
                        .type(Type.PIECE)
                        .at((columnCounter+1) *100, side.equals("White") ? 700 : 200)
                        .viewWithBBox(side + "_pawn.png")
                        .buildAndAttach();
            }

            spawnMinorPiece(side, 1, 8, "_rook.png");
            spawnMinorPiece(side, 2, 7, "_knight.png");
            spawnMinorPiece(side, 3, 6, "_bishop.png");

            spawnMajorPiece(side, 5, "_king.png");
            spawnMajorPiece(side, 4, "_queen.png");

        }

    }

    private void spawnMinorPiece(String side, int leftCol, int rightCol, String assetName) {
        for (int column:Arrays.asList(leftCol*100, rightCol*100)) {
            Entity minorPiece = entityBuilder()
                    .type(Type.PIECE)
                    .at(column, side.equals("White") ? 800 : 100)
                    .viewWithBBox(side + assetName)
                    .buildAndAttach();
        }
    }

    private void spawnMajorPiece(String side, int col, String assetName) {
            Entity MajorPiece = entityBuilder()
                    .type(Type.PIECE)
                    .at(col *100, side.equals("White") ? 800 : 100)
                    .viewWithBBox(side + assetName)
                    .buildAndAttach();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
