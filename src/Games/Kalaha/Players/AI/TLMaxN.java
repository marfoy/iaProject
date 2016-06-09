package Games.Kalaha.Players.AI;

import Games.Kalaha.Boards.Board;
import Games.Kalaha.Game;
import Games.Kalaha.Move;
import Games.Kalaha.Players.Heuristic;

import java.util.ArrayList;

/**
 * This is an adapation of the minmax algorithm for more than two players. We only have max nodes and results are
 * stored in a vector of values.
 */
public class TLMaxN extends TLMinimax {

    public TLMaxN(String player, ArrayList<String> players, int maxDepth, Heuristic heuristic, Game.LeftTokensGrantee leftTokenGrantee, boolean emptyCapture) {
        super(player, players, maxDepth, heuristic, leftTokenGrantee, emptyCapture);
    }

    /**
     * Returns the int corresponding to the best move according to maxN algorithm
     * @param board The board we are playing on
     * @return
     */
    public int bestMove(Board board) {

        Game game = new Game(board.clone(),leftTokenGrantee,emptyCapture,competitors);
        maxValueMaxN(game, player, 0);
        return bestPitChoice;
    }

    /**
     * Max node for maxN algorithm
     * @param game
     * @param currentPlayer
     * @param depth
     * @return
     */
    public ArrayList<Double> maxValueMaxN(Game game, String currentPlayer, int depth ) {

        if(terminalTest(game.getBoard()) || depth == maxDepth) {
            ArrayList<Double> valueVector = new ArrayList(competitors.size());
            for(String p : competitors) {
                valueVector.add(heuristic.compute(game.getBoard(),p));
            }
            return valueVector;
        }

        ArrayList<Integer> moves = possibleMoves(game.getBoard(),currentPlayer);
        ArrayList<Double> ve = new ArrayList<>(competitors.size());
        ArrayList<Double> tempV = new ArrayList<>(competitors.size());

        for(int i = 0; i<competitors.size(); i++) {
            ve.add(Double.NEGATIVE_INFINITY);
            tempV.add(Double.NEGATIVE_INFINITY);
        }

        int indexOfCurrent =  competitors.indexOf(currentPlayer);

        for(int m : moves ) {
            Move nextMove = new Move(m);
            nextMove.apply(game);
            String nextPlayer = game.getCurrentPlayer();

            if(nextPlayer.equals(currentPlayer)) {
                tempV = maxValueMaxN(game,currentPlayer,(depth+1));
            }
            else {
                tempV = maxValueMaxN(game, nextPlayer, (depth+1));
            }

            if(tempV.get(indexOfCurrent) > ve.get(indexOfCurrent)) {
                ve = tempV;
                if(depth == 0) {
                    this.bestPitChoice = m;
                }
            }

            nextMove.cancel(game);
        }

        return ve;
    }

}
