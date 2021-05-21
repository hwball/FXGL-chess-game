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

/***
 *       Move Map
 *
 *          f
 *       j     k
 *    l           r
 *       n     m
 *          b
 */

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

    private List<String> getAttackOptions() {
        return Arrays.asList("j", "k");
    }

    private String getSide() {
        return side;
    }

    public List<SpawnData> getAvailableMoves(Entity[][] board) {
        List<String> moves = getMoveOptions();
        List<SpawnData> dataL = new ArrayList<>();

        for (String move: moves) {
            double x = this.getEntity().getX() / getAppWidth() * 8;
            double y = this.getEntity().getY() / getAppHeight() * 8;

            boolean obstruction = false;
            for (int i =0; i < move.length(); i++) {
                switch (move.charAt(i)) {
                    case 'f':
                        y = side.equals("White") ? y - 1 : y + 1;
                        obstruction = obstruction ? obstruction : (board[(int) y][(int) x] != null);
                        break;
                }
            }

            if (!obstruction) {
                var data = new SpawnData(x * getAppWidth() / 8,
                        y * getAppHeight() / 8);
                data.put("parent", this.getEntity());
                dataL.add(data);
            }
        }

        //check for attack options
        dataL.addAll(getAvailableAttacks(board));
        return dataL;
    }

    private List<SpawnData> getAvailableAttacks(Entity[][] board) {
        List<String> attacks = getAttackOptions();
        List<SpawnData> dataL = new ArrayList<>();

        for (String attack: attacks) {
            double x = this.getEntity().getX() / getAppWidth() * 8;
            double y = this.getEntity().getY() / getAppHeight() * 8;

            boolean enemyObstruction = false;
            attackLoop: for (int i =0; i < attack.length(); i++) {
                switch (attack.charAt(i)) {
                    case 'j':
                        y = side.equals("White") ? y - 1 : y + 1;
                        x = side.equals("White") ? x - 1 : x + 1;
                        if (board[(int) y][(int) x] != null && !board[(int) y][(int) x].getComponent(PawnViewComponent.class).getSide().equals(this.getSide())) {
                            enemyObstruction = true;
                            break attackLoop;
                        }
                        break;
                    case 'k':
                        y = side.equals("White") ? y - 1 : y + 1;
                        x = side.equals("White") ? x + 1 : x - 1;
                        if (board[(int) y][(int) x] != null && !board[(int) y][(int) x].getComponent(PawnViewComponent.class).getSide().equals(this.getSide())) {
                            enemyObstruction = true;
                            break attackLoop;
                        }
                        break;
                }
            }
            if (enemyObstruction) {
                var data = new SpawnData(x * getAppWidth() / 8,
                        y * getAppHeight() / 8);
                data.put("parent", this.getEntity());
                dataL.add(data);
            }
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

    public Entity[][] move(double column, double row, Entity[][] board){
        var newBoard = board.clone();
        newBoard[(int) (this.getEntity().getY() / getAppHeight() * 8)][(int) (this.getEntity().getX() / getAppWidth() * 8)] = null;
        this.getEntity().setX(column);
        this.getEntity().setY(row);
        newBoard[(int) (row / getAppHeight() * 8)][(int) (column / getAppWidth() * 8)] = this.getEntity();
        this.firstMove = false;
        cleanUpMoveMarkers();
        return newBoard;
    }
}
