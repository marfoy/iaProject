package Games.Kalaha.Players.AI;

import Games.Kalaha.Boards.Board;
import Games.Kalaha.Game;
import Games.Kalaha.Move;
import Games.Kalaha.Players.Heuristic;

import java.util.ArrayList;
import java.util.DoubleSummaryStatistics;

/**
 * Created by Clement on 06-06-16.
 */
public class TLMaxN extends TLMinimax {


    public TLMaxN(String player, ArrayList<String> players, int maxDepth, Heuristic heuristic, Game.LeftTokensGrantee leftTokenGrantee, boolean emptyCapture) {
        super(player, players, maxDepth, heuristic, leftTokenGrantee, emptyCapture);
    }


    public int bestMove(Board board) {

        Game game = new Game(board.clone(),leftTokenGrantee,emptyCapture,competitors);
        maxValue(game, player, 0);
        System.out.println("AI pit choice "+bestPitChoice);
        System.out.println(" ");
        return bestPitChoice;
    }

    public ArrayList<Double> maxValueMaxN(Game game, String currentPlayer, int depth ) {
        if(terminalTest(game.getBoard()) || depth == maxDepth) {
            ArrayList<Double> valueVector = new ArrayList<>(competitors.size());
            for(String p : competitors) {
                valueVector.add(heuristic.compute(game.getBoard(),p));
            }
            return valueVector;
        }

        ArrayList<Double> v = new ArrayList(competitors.size());
        ArrayList<Double> tempV = new ArrayList(competitors.size());
        ArrayList<Integer> moves = possibleMoves(game.getBoard(),currentPlayer);
        int indexOfCurrent =  competitors.indexOf(currentPlayer);

        for(int m : moves ) {
            Move nextMove = new Move(m);
            nextMove.apply(game);
            String nextPlayer = game.getCurrentPlayer();
            //Two cases, player is the same as before, we max (he plays two times) or its the other player and we min
            if(nextPlayer.equals(currentPlayer)) {
                tempV = maxValueMaxN(game,currentPlayer,(depth+1));
            }
            else {
                tempV = maxValueMaxN(game, nextPlayer, (depth + 1));
            }

            if(tempV.get(indexOfCurrent) > v.get(indexOfCurrent)) {
                v = tempV;
                if(depth == 0) {
                    this.bestPitChoice = m;
                }
            }

            nextMove.cancel(game);
        }

        return v;
    }

}
