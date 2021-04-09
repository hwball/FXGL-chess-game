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

    public void moveParent() {
        parentEntity.getComponent(PawnViewComponent.class).move(this.getEntity().getX(), this.getEntity().getY());
    }
}
