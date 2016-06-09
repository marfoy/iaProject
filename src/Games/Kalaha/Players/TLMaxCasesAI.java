package Games.Kalaha.Players;

import Games.Kalaha.Boards.Board;
import Games.Kalaha.Move;
import Games.Kalaha.Players.AI.TLMaxN;
import Games.Kalaha.Players.AI.TLMinimax;

/**
 * This AI maximises the tokens in cases belonging to the player
 */

public class TLMaxCasesAI extends Player{

    @Override
    public Move pickMove(String s) {

        //Creating class defining the heuristic
        class CaseMaximiser implements Heuristic {

            @Override
            public double compute(Board board, String player) {
		        int nbrTokens = 0;
		        for (int i = 0; i < board.getLength();i++){
		            if(!board.isKalaha(i) && board.getPlayer(i).equals(player)){
			            nbrTokens += board.getPieceAt(i);
		            }
                }
		        return  nbrTokens;
            }
        }

        Heuristic max = new CaseMaximiser();

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
