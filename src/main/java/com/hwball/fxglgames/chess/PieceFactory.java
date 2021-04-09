package com.hwball.fxglgames.chess;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import static com.almasb.fxgl.dsl.FXGLForKtKt.entityBuilder;

public class PieceFactory implements EntityFactory {

    @Spawns("pawn")
    public Entity newPawn(SpawnData data) {
        String side = data.get("side");
        var newPawn = entityBuilder(data)
                .type(ChessApp.Type.PIECE)
                .with(new PawnViewComponent(side))
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
                .with(new PawnViewComponent(side))
                .viewWithBBox(side + "_rook.png")
                .build();

        return newRook;
    }

    @Spawns("knight")
    public Entity newKnight(SpawnData data) {
        String side = data.get("side");
        var newKnight = entityBuilder(data)
                .type(ChessApp.Type.PIECE)
                .with(new PawnViewComponent(side))
                .viewWithBBox(side + "_knight.png")
                .build();

        return newKnight;
    }

    @Spawns("bishop")
    public Entity newBishop(SpawnData data) {
        String side = data.get("side");
        var newBishop = entityBuilder(data)
                .type(ChessApp.Type.PIECE)
                .with(new PawnViewComponent(side))
                .viewWithBBox(side + "_bishop.png")
                .build();

        return newBishop;
    }

    @Spawns("queen")
    public Entity newQueen(SpawnData data) {
        String side = data.get("side");
        var newQueen = entityBuilder(data)
                .type(ChessApp.Type.PIECE)
                .with(new PawnViewComponent(side))
                .viewWithBBox(side + "_queen.png")
                .build();

        return newQueen;
    }

    @Spawns("king")
    public Entity newKing(SpawnData data) {
        String side = data.get("side");
        var newKing = entityBuilder(data)
                .type(ChessApp.Type.PIECE)
                .with(new PawnViewComponent(side))
                .viewWithBBox(side + "_king.png")
                .build();

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
