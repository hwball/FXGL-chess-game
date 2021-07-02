package com.hwball.fxglgames.chess;

import com.almasb.fxgl.dsl.components.view.ChildViewComponent;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.almasb.fxgl.dsl.FXGLForKtKt.getAppHeight;
import static com.almasb.fxgl.dsl.FXGLForKtKt.getAppWidth;

public class PieceViewComponent extends ChildViewComponent {
    private List<Entity> moveMarkers= new ArrayList<>();
    private boolean firstMove = true;
    public boolean isVulnerable= false;
    private String side;
    private boolean canEnPassant = false;
    private List<String> firstMoveOptions;
    private List<String> moveOptions;
    private List<String> attackOptions;
    private List<String> criticalOptions;

    public PieceViewComponent(String side, List<String> moveOptions, List<String> attackOptions) {
        this.side = side;
        this.moveOptions = moveOptions;
        this.attackOptions = attackOptions;
    }

    public PieceViewComponent(String side, List<String> moveOptions, List<String> attackOptions, List<String> firstMoveOptions) {
        this.side = side;
        this.moveOptions = moveOptions;
        this.attackOptions = attackOptions;
        this.firstMoveOptions = firstMoveOptions;
    }

    public PieceViewComponent(String side, List<String> moveOptions, List<String> attackOptions, List<String> firstMoveOptions, boolean canEnPassant) {
        this.side = side;
        this.moveOptions = moveOptions;
        this.attackOptions = attackOptions;
        this.firstMoveOptions = firstMoveOptions;
        this.canEnPassant = canEnPassant;
    }

    private List<String> getMoveOptions() {
        if (firstMove && this.firstMoveOptions != null) return this.firstMoveOptions;
        return this.moveOptions;
    }

    private List<String> getAttackOptions() {
        return attackOptions;
    }


    protected String getSide() {
        return side;
    }

    public List<SpawnData> getAvailableMoves(Entity[][] board) {
        List<String> moves = getMoveOptions();
        List<SpawnData> dataL = new ArrayList<>();

        for (String move: moves) {
            double x = this.getEntity().getX() / getAppWidth() * 8;
            double y = this.getEntity().getY() / getAppHeight() * 8;

            Move parsedMove = Util.parseMove(move, x, y, side, board);

            if (!parsedMove.obstruction && Util.moveIsOnBoard(y, x)) {
                var data = new SpawnData(parsedMove.x * getAppWidth() / 8,
                        parsedMove.y * getAppHeight() / 8);
                data.put("parent", this.getEntity());
                dataL.add(data);
            }
        }

        //check for attack options
        dataL.addAll(getAvailableAttacks(board));
        if (criticalOptions != null) dataL.addAll(getCriticalMoves(board));
        return dataL;
    }

    private List<SpawnData> getAvailableAttacks(Entity[][] board) {
        List<String> attacks = getAttackOptions();
        List<SpawnData> dataL = new ArrayList<>();

        for (String attack: attacks) {
            double x = this.getEntity().getX() / getAppWidth() * 8;
            double y = this.getEntity().getY() / getAppHeight() * 8;

            Move parsedMove = Util.parseMove(attack, x, y, side, board);
            if (parsedMove.obstruction && !parsedMove.obstructionSide.equals(this.getSide())) {
                var data = new SpawnData(parsedMove.x * getAppWidth() / 8,
                        parsedMove.y * getAppHeight() / 8);
                data.put("parent", this.getEntity());
                dataL.add(data);
            }
        }
        return dataL;
    }

    private List<SpawnData> getCriticalMoves(Entity[][] board) {
        List<SpawnData> dataL = new ArrayList<>();

        for (String move: this.criticalOptions) {
            double x = this.getEntity().getX() / getAppWidth() * 8;
            double y = this.getEntity().getY() / getAppHeight() * 8;

            Move parsedMove = Util.parseMove(move, x, y, side, board);
            var data = new SpawnData(parsedMove.x * getAppWidth() / 8,
                    parsedMove.y * getAppHeight() / 8);
            data.put("parent", this.getEntity());
            dataL.add(data);
        }
        return dataL;
    }

    private void sendAlertOfEnPassantOption(Entity[][] board) {
        double y = this.getEntity().getY() / getAppHeight() * 8;
        double lx = (this.getEntity().getX() / getAppWidth() * 8) - 1;
        double rx = (this.getEntity().getX() / getAppWidth() * 8) + 1;
        Entity leftEntity = Util.moveIsOnBoard(y, lx) ? board[(int) y][(int) lx] : null;
        Entity rightEntity = Util.moveIsOnBoard(y, rx) ?  board[(int) y][(int) rx] : null;
        if (leftEntity != null && !leftEntity.getComponent(PieceViewComponent.class).getSide().equals(this.side)) {
            List<String> criticalMove = leftEntity.getComponent(PieceViewComponent.class).getSide().equals("White") ?
                    Arrays.asList("k") : Arrays.asList("j");
            leftEntity.getComponent(PieceViewComponent.class).getCriticalMoveAlert(criticalMove);
        }
        if (rightEntity != null && !rightEntity.getComponent(PieceViewComponent.class).getSide().equals(this.side)) {
            List<String> criticalMove = rightEntity.getComponent(PieceViewComponent.class).getSide().equals("White") ?
                    Arrays.asList("j") : Arrays.asList("k");
            rightEntity.getComponent(PieceViewComponent.class).getCriticalMoveAlert(criticalMove);
        }
        this.isVulnerable = true;
    }

    private Entity[][] checkForEnPassant(Entity[][] board) {
        double y = this.getEntity().getY() / getAppHeight() * 8;
        double x = this.getEntity().getX() / getAppWidth() * 8;
        y = side.equals("White") ? y + 1 : y - 1;

        if(board[(int) y][(int) x] != null && board[(int) y][(int) x].getComponent(PieceViewComponent.class).isVulnerable) {
            return board[(int) y][(int) x].getComponent(PieceViewComponent.class).die(board);
        }
        return board;
    }

    public void getCriticalMoveAlert(List<String> criticalOptions) {
        this.criticalOptions = criticalOptions;
    }

    public void assignMarker(Entity marker) {
        moveMarkers.add(marker);
    }

    public void newTurn() {
        this.criticalOptions = null;
    }

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
        if (this.firstMove && this.canEnPassant) sendAlertOfEnPassantOption(newBoard);
        if (this.canEnPassant) newBoard = checkForEnPassant(newBoard);
        this.firstMove = false;
        cleanUpMoveMarkers();
        return newBoard;
    }

    public Entity[][] die(Entity[][] board) {
        var newBoard = board.clone();
        newBoard[(int) (this.getEntity().getY() / getAppHeight() * 8)][(int) (this.getEntity().getX() / getAppWidth() * 8)] = null;
        this.entity.removeFromWorld();
        return newBoard;
    }
}
