package Games.Kalaha.Players.AI;

import Games.Kalaha.Boards.Board;
import Games.Kalaha.Game;
import Games.Kalaha.Move;
import Games.Kalaha.Players.Heuristic;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * This is the alpha beta algorithm for two players
 */
public class TLAlphaBeta {

    //The player for which we are picking the move
    protected final String player;
    //The list containing all the players
    protected ArrayList<String> competitors;
    //Maximum depth for tree exploration
    protected final int maxDepth;
    //The best move to play
    protected int bestPitChoice;
    //Grant tokens according to game rule
    protected Game.LeftTokensGrantee leftTokenGrantee;
    //Is capturing 0 tokens a capture
    protected boolean emptyCapture;
    //Heuristic
    protected Heuristic heuristic;

    public TLAlphaBeta(String player, ArrayList<String> players, int maxDepth, Heuristic heuristic, Game.LeftTokensGrantee leftTokenGrantee,
                       boolean emptyCapture) {
        this.player = player;
        this.maxDepth = maxDepth;
        this.leftTokenGrantee = leftTokenGrantee;
        this.emptyCapture = emptyCapture;
        this.heuristic = heuristic;

        this.competitors = players;
        while(!competitors.get(0).equals(player)) {
            competitors.add(competitors.remove(0));
        }

    }

    public int bestMove(Board board) {
        Game game = new Game(board.clone(),leftTokenGrantee,emptyCapture,competitors);
        maxValue(game, player, 0,Double.NEGATIVE_INFINITY,Double.POSITIVE_INFINITY);
        return bestPitChoice;
    }

    /**
     * Yields true if the game is finished, ie one of the players has 0 tokens
     * @param board
     * @return
     */
    public boolean terminalTest(Board board) {
        //Hash map String-Int containing number of tokens for each player
        HashMap sums =  board.getSums(false, true);
        //If one of the players has 0 tokens, game is finished
        return sums.containsValue(0);
    }


    public double maxValue(Game game, String currentPlayer, int depth ,double alpha,double beta) {
        if(terminalTest(game.getBoard()) || depth == maxDepth) {
            return heuristic.compute(game.getBoard(), player);
        }

        double v = Double.NEGATIVE_INFINITY;
        double tempV;
        ArrayList<Integer> moves = possibleMoves(game.getBoard(),currentPlayer);

        for(int m : moves ) {
            Move nextMove = new Move(m);
            nextMove.apply(game);
            String nextPlayer = game.getCurrentPlayer();
            //Two cases, player is the same as before, we max (he plays two times) or its the other player and we min
            if(nextPlayer.equals(currentPlayer)) {
                tempV = Math.max(v, maxValue(game,currentPlayer,(depth+1),alpha,beta));
            }
            else {
                tempV = Math.max(v,minValue(game,nextPlayer,(depth+1),alpha,beta));
            }

            if(tempV > v && depth == 0) {
                this.bestPitChoice = m;
            }
            if(tempV >= beta) {
                nextMove.cancel(game);
                return tempV;
            }
            alpha = Math.max(tempV,alpha);
            v = tempV;
            nextMove.cancel(game);
        }

        return v;
    }


    public double minValue(Game game, String currentPlayer, int depth ,double alpha,double beta) {
        if(terminalTest(game.getBoard()) || depth == maxDepth ) {
            return heuristic.compute(game.getBoard(), player);
        }

        double v = Double.POSITIVE_INFINITY;

        ArrayList<Integer> moves = possibleMoves(game.getBoard(),currentPlayer);

        for(int m : moves ) {
            Move nextMove = new Move(m);
            nextMove.apply(game);
            String nextPlayer = game.getCurrentPlayer();
            //Two cases, player is the same as before, we min (he plays two times) or its the other player and we max
            if(nextPlayer.equals(currentPlayer)) {
                v = Math.min(v, minValue(game,currentPlayer,(depth+1),alpha,beta));
            }
            else {
                v = Math.min(v,maxValue(game,nextPlayer,(depth+1),alpha,beta));
            }
            if(v <= alpha) {
                nextMove.cancel(game);
                return v;
            }
            beta = Math.min(v,beta);
            nextMove.cancel(game);
        }
        return v;
    }
    /**
     * Returns all possible moves for a given player and a given board in an arrayList of integers
     * @param board
     * @param player
     * @return
     */
    public ArrayList<Integer> possibleMoves(Board board, String player) {
        ArrayList<Integer> moves = new ArrayList<>();
        //iterate on all pits and kalahas of the board, adding it as a possible move if it belongs to the player and is
        //not empty
        for(int move = 0; move < board.getLength();move++) {
            if(board.getPlayer(move).equals(player)) {
                if(!board.isKalaha(move) && board.getPieceAt(move) > 0) {
                    moves.add(move);
                }
            }
        }
        return moves;
    }

}
