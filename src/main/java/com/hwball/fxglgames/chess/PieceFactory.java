package com.hwball.fxglgames.chess;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.Arrays;

import static com.almasb.fxgl.dsl.FXGLForKtKt.entityBuilder;

/***
 *       Move Map
 *
 *       t  f  y
 *    a  j     k  o
 *    l           r
 *    z  n     m  p
 *       c  b  v
 */

public class PieceFactory implements EntityFactory {

    @Spawns("pawn")
    public Entity newPawn(SpawnData data) {
        String side = data.get("side");
        var newPawn = entityBuilder(data)
                .type(ChessApp.Type.PIECE)
                .with(new PieceViewComponent(side, Arrays.asList("f"), Arrays.asList("j", "k"), Arrays.asList("ff", "f"), true))
                .viewWithBBox(side + "_pawn.png")
                .build();

        newPawn.getViewComponent().addEventHandler(MouseEvent.MOUSE_CLICKED,
                e -> FXGL.<ChessApp>getAppCast().showAvailableMoves(newPawn));
        return newPawn;
    }

    @Spawns("rook")
    public Entity newRook(SpawnData data) {
        String side = data.get("side");
        var newRook = entityBuilder(data)
                .type(ChessApp.Type.PIECE)
                .with(new PieceViewComponent(side, Util.buildFilesDirections(Arrays.asList("f", "l", "r", "b")), Util.buildFilesDirections(Arrays.asList("f", "l", "r", "b"))))
                .viewWithBBox(side + "_rook.png")
                .build();

        newRook.getViewComponent().addEventHandler(MouseEvent.MOUSE_CLICKED,
                e -> FXGL.<ChessApp>getAppCast().showAvailableMoves(newRook));
        return newRook;
    }

    @Spawns("knight")
    public Entity newKnight(SpawnData data) {
        String side = data.get("side");
        var newKnight = entityBuilder(data)
                .type(ChessApp.Type.PIECE)
                .with(new PieceViewComponent(side, Arrays.asList("t", "y", "o", "p", "v", "c", "z", "a"), Arrays.asList("t", "y", "o", "p", "v", "c", "z", "a")))
                .viewWithBBox(side + "_knight.png")
                .build();

        newKnight.getViewComponent().addEventHandler(MouseEvent.MOUSE_CLICKED,
                e -> FXGL.<ChessApp>getAppCast().showAvailableMoves(newKnight));
        return newKnight;
    }

    @Spawns("bishop")
    public Entity newBishop(SpawnData data) {
        String side = data.get("side");
        var newBishop = entityBuilder(data)
                .type(ChessApp.Type.PIECE)
                .with(new PieceViewComponent(side, Util.buildFilesDirections(Arrays.asList("j", "k", "n", "m")), Util.buildFilesDirections(Arrays.asList("j", "k", "m", "n"))))
                .viewWithBBox(side + "_bishop.png")
                .build();

        newBishop.getViewComponent().addEventHandler(MouseEvent.MOUSE_CLICKED,
                e -> FXGL.<ChessApp>getAppCast().showAvailableMoves(newBishop));
        return newBishop;
    }

    @Spawns("queen")
    public Entity newQueen(SpawnData data) {
        String side = data.get("side");
        var newQueen = entityBuilder(data)
                .type(ChessApp.Type.PIECE)
                .with(new PieceViewComponent(side, Util.buildFilesDirections(Arrays.asList("f", "l", "r", "b", "j", "k", "n", "m")), Util.buildFilesDirections(Arrays.asList("f", "l", "r", "b", "j", "k", "m", "n"))))
                .viewWithBBox(side + "_queen.png")
                .build();

        newQueen.getViewComponent().addEventHandler(MouseEvent.MOUSE_CLICKED,
                e -> FXGL.<ChessApp>getAppCast().showAvailableMoves(newQueen));
        return newQueen;
    }

    @Spawns("king")
    public Entity newKing(SpawnData data) {
        String side = data.get("side");
        var newKing = entityBuilder(data)
                .type(ChessApp.Type.PIECE)
                .with(new PieceViewComponent(side, Arrays.asList("f", "l", "r", "b", "j", "k", "n", "m"), Arrays.asList("f", "l", "r", "b", "j", "k", "n", "m")))
                .viewWithBBox(side + "_king.png")
                .build();

        newKing.getViewComponent().addEventHandler(MouseEvent.MOUSE_CLICKED,
                e -> FXGL.<ChessApp>getAppCast().showAvailableMoves(newKing));
        return newKing;
    }

    @Spawns("marker")
    public Entity newMarker(SpawnData data) {
        Rectangle marker = new Rectangle(100, 100);
        marker.setFill(Color.YELLOW);
        marker.setStroke(Color.BLACK);
        var newMarker = entityBuilder(data)
                .type(ChessApp.Type.PIECE)
                .with(new MarkerComponent(data.get("parent")))
                .viewWithBBox(marker)
                .build();
        newMarker.getViewComponent().addEventHandler(MouseEvent.MOUSE_CLICKED,
                e -> FXGL.<ChessApp>getAppCast().movePiece(newMarker));
        return newMarker;
    }
}
