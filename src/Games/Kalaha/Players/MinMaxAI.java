package Games.Kalaha.Players;

import Games.Kalaha.Boards.Board;
import Games.Kalaha.Move;
import Games.Kalaha.Players.AI.Minimax;
import Games.Kalaha.Players.AI.Minimax2;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by mrt on 19/04/16.
 */

public class MinMaxAI extends Player{

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
               /*TODO Implement heuristic 1 here*/
                return 0;
            }
        }
        Heuristic minimizer = new Minimizer();
        Minimax minmax = new Minimax(s, players, 10 ,minimizer,leftTokensGrantee, emptyCapture);
        return new Move(minmax.bestMove(board));
    }

}
