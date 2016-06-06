package Games.Kalaha.Players;

import Games.Kalaha.Move;
import Games.Kalaha.Players.AI.TLWinSequenceDetector;

import java.util.*;

/**
 * Created by Clement on 31-05-16.
 */
public class TLWinSequenceAI extends RandomAI {

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
            return super.pickMove(avatar);
        }
    }

    public static void main(String[] args) {
        TLWinSequenceDetector detector = new TLWinSequenceDetector();
        //Tests from the example of the assignment
        ArrayList<Integer> test1 = new ArrayList<>();
        test1.add(0);
        test1.add(0);
        test1.add(1);
        ArrayList<Integer> test2 = new ArrayList<>();
        test2.add(0);
        test2.add(2);
        test2.add(0);
        ArrayList<Integer> test3 = new ArrayList<>();
        test3.add(0);
        test3.add(2);
        test3.add(1);
        ArrayList<Integer> test4 = new ArrayList<>();
        test4.add(3);
        test4.add(1);
        test4.add(0);
        ArrayList<Integer> test5 = new ArrayList<>();
        test5.add(3);
        test5.add(1);
        test5.add(1);

        //Tests that will fail
        ArrayList<Integer> test6 = new ArrayList<>();
        test6.add(4);
        test6.add(0);
        test6.add(0);

        ArrayList<Integer> test7 = new ArrayList<>();
        test7.add(0);
        test7.add(3);
        test7.add(0);

        ArrayList<Integer> test8 = new ArrayList<>();
        test8.add(0);
        test8.add(0);
        test8.add(2);

        ArrayList<Integer> test9 = new ArrayList<>();
        test9.add(4);
        test9.add(2);
        test9.add(1);

        System.out.println(test1);
        System.out.println(detector.winSequence(test1));
        System.out.println(test2);
        System.out.println(detector.winSequence(test2));
        System.out.println(test3);
        System.out.println(detector.winSequence(test3));
        System.out.println(test4);
        System.out.println(detector.winSequence(test4));
        System.out.println(test5);
        System.out.println(detector.winSequence(test5));

        System.out.println(test6);
        System.out.println(detector.winSequence(test6));
        System.out.println(test7);
        System.out.println(detector.winSequence(test7));
        System.out.println(test8);
        System.out.println(detector.winSequence(test8));
        System.out.println(test9);
        System.out.println(detector.winSequence(test9));

        //All tests are working as expected
    }
}
