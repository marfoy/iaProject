package Games.Kalaha.Players;

import Games.Kalaha.Boards.Board;
import Games.Kalaha.Move;
import Games.Kalaha.Players.AI.TLMaxN;
import Games.Kalaha.Players.AI.TLMinimax;

/**
 * Created by mrt on 19/04/16.
 */

public class TLMinSumSquare extends Player{

    public int i = 0;
    @Override
    public Move pickMove(String s) {
        //In this game, one avatar = one player so we ignore the string
        //We extend core player which knows the board
        i++;
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
