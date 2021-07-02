package com.hwball.fxglgames.chess;

import com.almasb.fxgl.dsl.components.view.ChildViewComponent;
import com.almasb.fxgl.entity.Entity;

import static com.almasb.fxgl.dsl.FXGLForKtKt.getAppHeight;
import static com.almasb.fxgl.dsl.FXGLForKtKt.getAppWidth;

public class MarkerComponent extends ChildViewComponent {
    private Entity parentEntity;

    public MarkerComponent(Entity parentEntity) {
        this.parentEntity = parentEntity;
    }

    public Entity[][] moveParent(Entity[][] board ) {
        Entity[][] currentBoard = board;
        if (board[(int) (this.getEntity().getY() / getAppHeight() * 8)][(int) (this.getEntity().getX() / getAppWidth() * 8)] != null)
            currentBoard = board[(int) (this.getEntity().getY() / getAppHeight() * 8)][(int) (this.getEntity().getX() / getAppWidth() * 8)].getComponent(PieceViewComponent.class).die(board);
        return parentEntity.getComponent(PieceViewComponent.class).move(this.getEntity().getX(), this.getEntity().getY(), currentBoard);
    }
}
