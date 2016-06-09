package Games.Kalaha.Players;

import Games.Kalaha.Boards.Board;
import Games.Kalaha.Move;
import Games.Kalaha.Players.AI.TLMaxN;
import Games.Kalaha.Players.AI.TLMinimax;

/**
 * This AI minimises the sum of the squared differences of the pits belonging to the player
 */

public class TLMinSumSquareAI extends Player{

    @Override
    public Move pickMove(String s) {

        class Minimizer implements Heuristic {

            @Override
            public double compute(Board board, String player) {
		        int sumSquare = 0;
                for (int i = 0; i < board.getLength();++i){
			        for (int j = 0; j < board.getLength();++j){
			            if(!board.isKalaha(i) && !board.isKalaha(j) && board.getPlayer(j).equals(player) && board.getPlayer(i).equals(player) && j!=i){
				            sumSquare += ((board.getPieceAt(j) - board.getPieceAt(i)) * (board.getPieceAt(j) - board.getPieceAt(i)));
			            }
		            }
                }
		        return  -1*sumSquare;
            }
        }

        Heuristic minimizer = new Minimizer();

        if(players.size() > 2) {
            TLMaxN maxN = new TLMaxN(s, players, 6 ,minimizer,leftTokensGrantee, emptyCapture);
            return new Move(maxN.bestMove(board));
        }
        else {
            TLMinimax minmax = new TLMinimax(s, players, 10 ,minimizer,leftTokensGrantee, emptyCapture);
            return new Move(minmax.bestMove(board));
        }
    }

}
