package Games.Kalaha.Players;

import Games.Kalaha.Boards.Board;
import Games.Kalaha.Move;
import Games.Kalaha.Players.AI.TLMaxN;
import Games.Kalaha.Players.AI.TLMinimax;

/**
 * This AI maximises the number of tokens in the player's reserves
 */

public class TLMaxReserveAI extends Player{

    @Override
    public Move pickMove(String s) {

        class ReserveMaximizer implements Heuristic {

            @Override
            public double compute(Board board, String player) {
		        int nbrTokens = 0;
		        for (int i = 0; i < board.getLength();i++){
		            if(board.isKalaha(i) && board.getPlayer(i).equals(player)){
			            nbrTokens += board.getPieceAt(i);
		            }
		        }
		        return  nbrTokens;
            }
        }

        Heuristic max = new ReserveMaximizer();
        if(players.size() > 2) {
            TLMaxN maxN = new TLMaxN(s, players, 6 ,max,leftTokensGrantee, emptyCapture);
            return new Move(maxN.bestMove(board));
        }
        else {
            TLMinimax minmax = new TLMinimax(s, players, 10 ,max,leftTokensGrantee, emptyCapture);
            return new Move(minmax.bestMove(board));
        }
    }

}
