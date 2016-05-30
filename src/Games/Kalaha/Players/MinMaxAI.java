package Games.Kalaha.Players;

import Games.Kalaha.Boards.Board;
import Games.Kalaha.Move;
import Games.Kalaha.Players.AI.Minimax;
import Games.Kalaha.Players.AI.Minimax2;

import java.util.ArrayList;

/**
 * Created by mrt on 19/04/16.
 */

public class MinMaxAI extends Player{
    /*
    private Heuristic heuristic;
    public MinMaxAI(Heuristic heur){
        this.heuristic = heur;
    }

    @Override
    public Move pickMove(String s) {
        return minMaxDecision(s);
    }
    public Move minMaxDecision(String avatar){
        int maxUtility = 0;
        int tempUtility;
        int action = 0;
        for (int i = 0; i < board.getLength(); ++i) {
            tempUtility = minValue(result(board,new Move(i)),avatar);
            if (board.getPlayer(i).equals(avatar) && !board.isKalaha(i) && board.getPieceAt(i) > 0 && tempUtility >= maxUtility) {
                maxUtility = tempUtility;
                action = i;
            }
        }
        return new Move(action);
    }

    private int maxValue(Board result,String avatar) {
        if(terminalTest(result,avatar))
            return heuristic.compute(result);
        else{
            int v = Integer.MIN_VALUE;
            ArrayList<Integer> actions = actionPossible(result,avatar);
            for(Integer action : actions){
                v = Math.max(v,minValue(result(board,new Move(action)),avatar));
            }
            return v;
        }
    }
    private int minValue(Board result,String avatar) {
        if(terminalTest(result,avatar))
            return heuristic.compute(result);
        else{
            int v = Integer.MAX_VALUE;
            ArrayList<Integer> actions = actionPossible(result,avatar);
            for(Integer action : actions){
                v = Math.min(v,maxValue(result(board,new Move(action)),avatar));
            }
            return v;
        }
    }

    private ArrayList<Integer> actionPossible(Board result,String avatar) {
        ArrayList<Integer> possibleMoves = new ArrayList<>();
        for (int i = 0; i < result.getLength(); ++i) {
            if (result.getPlayer(i).equals(avatar) && !result.isKalaha(i) && result.getPieceAt(i) > 0 ) {
                possibleMoves.add(i);
            }
        }
        return possibleMoves;
    }

    private boolean terminalTest(Board state,String avatar){
        for (int i = 0; i < state.getLength(); ++i) {
            if (!board.getPlayer(i).equals(avatar) && !board.isKalaha(i) && board.getPieceAt(i) > 0) {
                return false;
            }
        }
        return true;
    }

    public Board result(Board board, Move action){
        ///TODO Need to implement apply(move) method who already exist but this method take game parameter,that we don't need here
        //Game copyGame = new Game(game.getBoardClone(),game.getLeftTokensGrantee(),game.getEmptyCapture(),game.getPlayers());
        //action.apply(copyGame);
        return board;
    }
*/
    public int i = 0;
    @Override
    public Move pickMove(String s) {
        //In this game, one avatar = one player so we ignore the string
        //We extend core player which knows the board
        System.out.println("Nom IA : "+s+" joue son "+i+ "  ème coup");
        i++;
        Heuristic heuristic = new Minimax(s, players, 6, leftTokensGrantee, emptyCapture);
        return new Move(heuristic.compute(board));
    }

}
