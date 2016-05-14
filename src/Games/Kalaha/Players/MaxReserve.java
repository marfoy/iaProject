package Games.Kalaha.Players;

import Games.Kalaha.Boards.Board;
import Games.Kalaha.Move;

/**
 * Created by mrt on 19/04/16.
 */
public class MaxReserve implements Heuristic {
    @Override
    public int compute(Board board) {
        int maxReserve = 0;
        for (int i = 0; i < board.getLength();++i){
            //TODO Maybe need information about players
            if(board.isKalaha(i) /*&& board.getPlayer(i).equals(players)*/)
                maxReserve = Math.max(maxReserve,board.getPieceAt(i));
        }
        return  maxReserve;
    }
}
