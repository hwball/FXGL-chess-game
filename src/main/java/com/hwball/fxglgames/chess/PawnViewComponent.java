package com.hwball.fxglgames.chess;

import com.almasb.fxgl.dsl.components.view.ChildViewComponent;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.almasb.fxgl.dsl.FXGLForKtKt.*;

public class PawnViewComponent extends ChildViewComponent implements PieceViewComponent {

    private boolean firstMove = true;
    private String side;
    private List<Entity> moveMarkers= new ArrayList<>();


    public PawnViewComponent(String side) {
        this.side = side;
    }

    private List<String> getMoveOptions() {
        if (firstMove) return Arrays.asList("ff", "f");
        return Arrays.asList("f");
    }

    private String getSide() {
        return side;
    }

    public List<SpawnData> getAvailableMoves() {
        List<String> moves = getMoveOptions();
        List<SpawnData> dataL = new ArrayList<>();

        for (String move: moves) {
            double x = this.getEntity().getX() / getAppWidth() * 8;
            double y = this.getEntity().getY() / getAppHeight() * 8;

            for (int i =0; i < move.length(); i++) {
                switch (move.charAt(i)) {
                    case 'f':
                        y = side.equals("White") ? y - 1 : y + 1;
                        break;
                }
            }

            var data = new SpawnData(x * getAppWidth() / 8,
                    y * getAppHeight() / 8);
            data.put("parent", this.getEntity());
            dataL.add(data);
        }
        return dataL;
    }

    public void assignMarker(Entity marker) {
        moveMarkers.add(marker);
    }

    @Override
    public void cleanUpMoveMarkers() {
        for (Entity moveMarker:moveMarkers) {
            moveMarker.removeFromWorld();
        }
        moveMarkers.clear();
    }

    public void move(double column, double row){
        this.getEntity().setX(column);
        this.getEntity().setY(row);
        this.firstMove = false;
        cleanUpMoveMarkers();
    }
}
