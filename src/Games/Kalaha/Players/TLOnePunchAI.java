package Games.Kalaha.Players;

import Games.Kalaha.Boards.Board;
import Games.Kalaha.Move;
import Games.Kalaha.Players.AI.TLAlphaBeta;
import Games.Kalaha.Players.AI.TLMaxN;
import Games.Kalaha.Players.AI.TLMinimax;
import Games.Kalaha.Players.AI.TLWinSequenceDetector;

import java.util.ArrayList;
import java.util.Collections;

/**
 * This is the the answer to the third question. This is the best AI we could make. It first tries to play a winning
 * sequence without giving the hand to another player. If it can't it will usee the heuristic that had the best results
 * in our tests and if it only has two players, it uses alpha-beta instead of minmax, thus allowing us to go deeper in
 * the exploration tree (because of the pruning that alpha-beta does)
 */
public class TLOnePunchAI extends Player {

    @Override
    public Move pickMove(String avatar) {
        //Setting up two arraylists : One with the indexes of all the pits and one with the values contained in them
        ArrayList<Integer> pits = new ArrayList<>();
        ArrayList<Integer> pitsValues = new ArrayList<>();

        for (int pitIndex = 0; pitIndex < board.getLength(); pitIndex++) {
            if (board.getPlayer(pitIndex).equals(avatar)) {
                pits.add(pitIndex);
                pitsValues.add(board.getPieceAt(pitIndex));
            }
        }

        //Removing the kalaha from the arraylist (this makes things easier)
        int kalahaIndex = pits.indexOf(Collections.max(pits));
        //kalaha is always after the pits so it is the max index in the arraylist of indexes
        pitsValues.remove(kalahaIndex);

        //Instanciating win sequence detector
        TLWinSequenceDetector detector = new TLWinSequenceDetector();
        //With all that info we can test if we can make a winning sequence
        double winMove = detector.winSequence(pitsValues);

        //If an index I is returned, we can make a winning move playing the pit index I in the arrayList of pits
        if(Double.isFinite(winMove)) {
            return new Move(pits.get((int) winMove));
        }
        //Else we can't make a winning move and we play the best heuristic with MaxN or alpha beta
        else {
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
                TLMaxN maxN = new TLMaxN(avatar, players, 6 ,max,leftTokensGrantee, emptyCapture);
                return new Move(maxN.bestMove(board));
            }
            else {
                TLAlphaBeta alphaBeta = new TLAlphaBeta(avatar, players, 14 ,max,leftTokensGrantee, emptyCapture);
                return new Move(alphaBeta.bestMove(board));
            }
        }
    }
}
