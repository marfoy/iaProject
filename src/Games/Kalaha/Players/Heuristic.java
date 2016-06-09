package Games.Kalaha.Players;

import Games.Kalaha.Boards.Board;

/**
 * This interface is used to represent a heuristic. We added player to compute method in order to know which player
 * we are computing the heuristic for (usefull for MaxN)
 */
public interface Heuristic {
     double compute(Board board, String player);
}
