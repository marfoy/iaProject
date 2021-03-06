package Games.Kalaha.Players.AI;
import java.util.ArrayList;

/**
 * Created by Clement
 * This AI returns the move that needs to be played in order to continue an uninterrupted winning sequence of actions
 * Move can be -infinity if no such move exists. The main of this class contains the examples from the assignment as
 * well as some examples that will fail. We see that all tests pass.
 */
public class TLWinSequenceDetector {

    public TLWinSequenceDetector() {

    }
    /**
     * Returns true if all values in the arrayList are 0s (ie the game is finished)
     * @param pitsValues
     * @return
     */
    public static boolean won(ArrayList<Integer> pitsValues) {
        boolean won = true;
        for(int value: pitsValues){
            if(value != 0) {
                return false;
            }
        }
        return won;
    }

    /**
     * Returns the index of the move that allows us to play again and eventually win. The index is then used on the
     * list of pits the player possesses. Returns -infinity if such a move doesn't exist.
     * @param pitsValues
     * @return
     */
    public static double winSequence(ArrayList<Integer> pitsValues) {
        int kalahaIndex = pitsValues.size(); //Index of the kalaha

        //For each value in the pits
        for(int index = 0; index < kalahaIndex; index++) {

            //We save the old value (necessary if we need to change it back after)
            int previousPitValue = pitsValues.get(index);
            //If the move allows us to play again ie the last token contained in the pit lands on a kalaha
            if(previousPitValue+index == kalahaIndex) {

                //We try to play this move (current pit is emptied, its value added to the next pits)
                pitsValues.set(index,0);
                for(int i = index+1; i<kalahaIndex;i++) {
                    int temp = pitsValues.get(i);
                    pitsValues.set(i,(temp+1));
                }

                //After playing the move we test if we won. If we did, we return the index of the pit we played for the
                //winning move
                if(won(pitsValues)) {
                    return index;
                }

                //If we didn't win with this move, we might win further down the plays
                //We recursively call the method and check if we can win down the line
                double isWon = winSequence(pitsValues);

                //If -infinity is returned, it means we can't win down the line. Else we can somehow win further down.
                //The current move is a good move that will allow us to win later so we return it
                if(Double.isFinite(isWon)) {
                    return index;
                }

                //Undoing the move (adding tokens back to their initial state).
                //This is necessary for the recursive calls
                for(int i = kalahaIndex-1; i > index; i--) {
                    int temp = pitsValues.get(i);
                    pitsValues.set(i,temp-1);
                }
                pitsValues.set(index,previousPitValue);
            }
        }
        //If we get here, no winning move (at this depth or further down) has been found. The move played before didn't
        //Allow us to win
        return Double.NEGATIVE_INFINITY;
    }

    public static void main(String[] args) {
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

        System.out.println("Test 1 :");
        System.out.println(test1);
        System.out.println(winSequence(test1));
        System.out.println("Test 2 :");
        System.out.println(test2);
        System.out.println(winSequence(test2));
        System.out.println("Test 3 :");
        System.out.println(test3);
        System.out.println(winSequence(test3));
        System.out.println("Test 4 :");
        System.out.println(test4);
        System.out.println(winSequence(test4));
        System.out.println("Test 5 :");
        System.out.println(test5);
        System.out.println(winSequence(test5));
        System.out.println("Test 6 :");
        System.out.println(test6);
        System.out.println(winSequence(test6));
        System.out.println("Test 7 :");
        System.out.println(test7);
        System.out.println(winSequence(test7));
        System.out.println("Test 8 :");
        System.out.println(test8);
        System.out.println(winSequence(test8));
        System.out.println("Test 9 :");
        System.out.println(test9);
        System.out.println(winSequence(test9));

        //All tests are working as expected
    }
}
