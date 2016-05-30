package Games.Kalaha.Players.AI;

import Games.Kalaha.Boards.Board;
import Games.Kalaha.Game;
import Games.Kalaha.Move;
import Games.Kalaha.Players.AI.util.State;
import Games.Kalaha.Players.Heuristic;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Clement on 30-05-16.
 */
public class Minimax implements Heuristic{

    //The player for which we are picking the move
    private final String player;
    //The list containing all the players
    private final ArrayList<String> competitors;
    //Maximum depth for tree exploration
    private final int maxDepth;
    //The best move to play
    private int bestPitChoice;
    //Grant tokens according to game rule
    private Game.LeftTokensGrantee leftTokenGrantee;
    //Is capturing 0 tokens a capture
    private boolean emptyCapture;

    public Minimax(String player, ArrayList<String> players, int maxDepth, Game.LeftTokensGrantee leftTokenGrantee,
                   boolean emptyCapture) {
        this.player = player;
        this.competitors = players;
        this.maxDepth = maxDepth;
        this.leftTokenGrantee = leftTokenGrantee;
        this.emptyCapture = emptyCapture;
    }

    @Override
    public int compute(Board board) {
        Game game = new Game(board.clone(),leftTokenGrantee,emptyCapture,competitors);
        maxValue(game, player, 0);
        System.out.println("AI pit choice "+bestPitChoice);
        System.out.println(" ");
        return bestPitChoice;
    }

    //Returns true if game is finished ie all pits are empty for one player
    public boolean terminalTest(Board board) {
        //Hash map String-Int containing number of tokens for each player
        HashMap sums =  board.getSums(false, true);
        //If one of the players has 0 tokens, game is finished
        return sums.containsValue(0);
    }

    //Utility is supposed to be several heuristics, here we just maximise nbr of tokens in player's reserve
    public int utility(Board board, String player) {
        // sums of tokens in reserves
        HashMap sums = board.getSums(true,false);
        return (int) sums.get(player);
    }


    public double maxValue(Game game, String currentPlayer, int depth ) {
        if(terminalTest(game.getBoard()) || depth == maxDepth) {
            return utility(game.getBoard(),this.player);
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
                tempV = Math.max(v, maxValue(game,currentPlayer,(depth+1)));
            }
            else {
                tempV = Math.max(v,minValue(game,nextPlayer,(depth+1)));
            }

            if(tempV > v && depth == 0) {
                this.bestPitChoice = m;
            }

            v = tempV;

            nextMove.cancel(game);
        }

        return v;
    }


    public double minValue(Game game, String currentPlayer, int depth ) {
        if(terminalTest(game.getBoard()) || depth == maxDepth ) {
            return utility(game.getBoard(),this.player);
        }

        double v = Double.POSITIVE_INFINITY;

        ArrayList<Integer> moves = possibleMoves(game.getBoard(),currentPlayer);

        for(int m : moves ) {
            Move nextMove = new Move(m);
            nextMove.apply(game);
            String nextPlayer = game.getCurrentPlayer();
            //Two cases, player is the same as before, we min (he plays two times) or its the other player and we max
            if(nextPlayer.equals(currentPlayer)) {
                v = Math.min(v, minValue(game,currentPlayer,(depth+1)));
            }
            else {
                v = Math.min(v,maxValue(game,nextPlayer,(depth+1)));
            }
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
