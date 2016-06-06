package Games.Kalaha.Players;

import Games.Kalaha.Move;
import Games.Kalaha.Players.AI.TLWinSequenceDetector;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Clement on 06-06-16.
 */
public class TLOnePunchAI extends TLMaxReserveAI {
    @Override
    public Move pickMove(String s) {
        //Setting up two arraylists : One with the indexes of all the pits and one with the values contained in them
        ArrayList<Integer> pits = new ArrayList<>();
        ArrayList<Integer> pitsValues = new ArrayList<>();

        for (int pitIndex = 0; pitIndex < board.getLength(); pitIndex++) {
            if (board.getPlayer(pitIndex).equals(s)) {
                pits.add(pitIndex);
                pitsValues.add(board.getPieceAt(pitIndex));
            }
        }

        //Removing the kalaha from the arraylist (this makes things easier)
        int kalahaIndex = Collections.max(pits);
        //kalah is always after the pits so it is the max index in the arraylist of indexes
        pitsValues.remove(kalahaIndex);

        //Instanciating win sequence detector
        TLWinSequenceDetector detector = new TLWinSequenceDetector();
        //With all that info we can test if we can make a winning sequence
        double winMove = detector.winSequence(pitsValues);

        //If an index I is returned, we can make a winning move playing the pit index I in the arrayList of pits
        if(Double.isFinite(winMove)) {
            return new Move(pits.get((int) winMove));
        }
        //Else we can't make a winning move and we play randomly
        else {
            return super.pickMove(s);
        }
    }
}
