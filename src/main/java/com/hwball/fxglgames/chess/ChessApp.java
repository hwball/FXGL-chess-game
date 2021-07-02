package com.hwball.fxglgames.chess;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.Arrays;
import java.util.List;

import static com.almasb.fxgl.dsl.FXGLForKtKt.*;

/**
 * TODO
 *
 * - handle promoting
 * - add win / loss / king rules
 */

public class ChessApp extends GameApplication {

    private Entity[][] board = new Entity[8][8];
    private boolean whiteTurn = true;

    public enum Type {
        BOARD_SQUARE, PIECE, MOVE_MARKER
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
        getGameWorld().addEntityFactory(new PieceFactory());
//        drawBoard();
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
            int y = side.equals("White") ? 6 : 1;
            for (int columnCounter = 0; columnCounter < 8; columnCounter++) {
                SpawnData data = new SpawnData(columnCounter * getAppWidth() / 8,
                        y * getAppHeight() / 8);
                data.put("side", side);
                board[y][columnCounter] = spawn("pawn",data);
            }

            spawnMinorPiece(side, 0, 7, "rook");
            spawnMinorPiece(side, 1,6, "knight");
            spawnMinorPiece(side, 2, 5, "bishop");

            spawnMajorPiece(side, 4, "king");
            spawnMajorPiece(side, 3, "queen");

        }
    }

    private void spawnMinorPiece(String side, int leftCol, int rightCol, String entityName) {
        int y = side.equals("White") ? 7 : 0;
        for (int column:Arrays.asList(leftCol, rightCol)) {
            SpawnData data = new SpawnData(column * getAppWidth() / 8,
                    y * getAppHeight() / 8);
            data.put("side", side);
            board[y][column] = spawn(entityName, data);
        }
    }

    private void spawnMajorPiece(String side, int column, String entityName) {
        int y = side.equals("White") ? 7 : 0;
        SpawnData data = new SpawnData(column * getAppWidth() / 8,
                y * getAppHeight() / 8);
        data.put("side", side);
        board[y][column] = spawn(entityName, data);
    }

    public void showAvailableMoves(Entity piece) {
        for (Entity[] row:board) {
            for (Entity boardPiece: row) {
                if (boardPiece != null){
                    boardPiece.getComponent(PieceViewComponent.class).cleanUpMoveMarkers();
                }
            }
        }
        PieceViewComponent component = piece.getComponent(PieceViewComponent.class);
        if ((component.getSide().equals("White") && this.whiteTurn) || (component.getSide().equals("Black") && !this.whiteTurn)) {
            List<SpawnData> dataL = component.getAvailableMoves(board);
            for (SpawnData data: dataL) {
                Entity newMarker = spawn("marker", data);
                piece.getComponent(PieceViewComponent.class).assignMarker(newMarker);
            }
        }
    }

    public void movePiece(Entity marker){
        for (Entity[] row:board) {
            for (Entity boardPiece: row) {
                if (boardPiece != null){
                    boardPiece.getComponent(PieceViewComponent.class).newTurn();
                }
            }
        }

        board = marker.getComponent(MarkerComponent.class).moveParent(board);
        this.whiteTurn = !this.whiteTurn;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
