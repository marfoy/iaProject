package Games.Kalaha.Players;

import Games.Kalaha.Boards.Board;
import Games.Kalaha.Move;

/**
 * Created by mrt on 19/04/16.
 */
public interface Heuristic {
     double compute(Board board);
}
