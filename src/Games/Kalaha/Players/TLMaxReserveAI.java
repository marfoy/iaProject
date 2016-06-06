package Games.Kalaha.Players;

import Games.Kalaha.Boards.Board;
import Games.Kalaha.Move;
import Games.Kalaha.Players.AI.TLMinimax;

/**
 * Created by mrt on 19/04/16.
 */

public class TLMaxReserveAI extends Player{

    public int i = 0;
    @Override
    public Move pickMove(String s) {
        //In this game, one avatar = one player so we ignore the string
        //We extend core player which knows the board
        System.out.println("Nom IA : "+s+" joue son "+i+ "  ème coup");
        i++;
        class Minimizer implements Heuristic {

            @Override
            public double compute(Board board) {
		int maxReserve = 0;
		for (int i = 0; i < board.getLength();++i){
		    if(board.isKalaha(i) && board.getPlayer(i).equals(s)){
			maxReserve = Math.max(maxReserve,board.getPieceAt(i));
		    }
		}
		return  maxReserve;
            }
        }
        Heuristic minimizer = new Minimizer();
        TLMinimax minmax = new TLMinimax(s, players, 10 ,minimizer,leftTokensGrantee, emptyCapture);
        return new Move(minmax.bestMove(board));
    }

}
