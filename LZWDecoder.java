//Sampson Ward
//1312744
//Andy Shen
//1304441
import java.io.*;
import java.util.*;


public class LZWDecoder {

    ArrayList<Phrase> phraseList;
    ArrayList<Integer> path = new ArrayList<Integer>();
    Scanner in;
    int phraseCount = 256;

    public static void main(String[] args)
            throws java.io.IOException {

        LZWDecoder decoder = new LZWDecoder();
        decoder.Decoder();

    }

    /*--------------------------------------------*/
    /*                                            */
    /*Method to Decrypt phrase number from Encoder*/
    /*                                            */
    /*--------------------------------------------*/
    public void Decoder()
            throws java.io.IOException {
        Phrase curr;
        String match;
        int last;
        int prev;
        int prevIndex;


        Initialize();

        in = new Scanner(System.in);

        int charac = Integer.parseInt(in.next());
        prev = charac;
        prevIndex = charac;
        try {
            while (in.hasNextLine()) {
                //resets decoder
                if (charac == 256) {
                    Initialize();
                    prev = charac;
                    prevIndex = charac;
                    charac = Integer.parseInt(in.next());

                }
                //if the phrase is less than 256 then we can just out put character
                if (charac < 256) {
                    System.out.write(charac);
                    //if it is the first iteration of this loop we get the character and put it into our empty array position
                    if (prev != charac) {
                        last = lastchara(charac);
                        curr = new Phrase(prevIndex, last);
                        phraseList.set(prev, curr);
                    }
                    //We add empty node as we do not know what character it is encoded with
                    curr = new Phrase(charac, -1);
                    //add empty node to array
                    phraseList.add(curr);
                    prevIndex = charac;
                    //So we know at what position we can add the next read in character
                    prev = phraseList.size() - 1;

                    charac = Integer.parseInt(in.next());


                } else if (charac > 256) {


                    //gets the encoded character so we can add it to our empty node
                    last = lastchara(charac);
                    //makes new phrase node that points to the previous index
                    curr = new Phrase(prevIndex, last);

                    phraseList.set(prev, curr);
                    //add empty phrase node as there is a mis match at this point
                    curr = new Phrase(charac, -1);
                    phraseList.add(curr);
                    prevIndex = charac;
                    prev = phraseList.size() - 1;

                    //For loop through traversed path and return reversed path as an integer
                    //the System.out.write prints byte
                    MatchString(charac);
                    for(int i = 0; i < path.size(); i++) {

                        curr = phraseList.get(path.get(i));

                        System.out.write(curr.cha);

                    }
                    path.clear();
                    charac = Integer.parseInt(in.next());


                }

            }
        } catch (Exception E) {
        }
    }


    /*-----------------------*/
    /*                       */
    /*Method to match strings*/
    /*                       */
    /*-----------------------*/
    public ArrayList MatchString(int p) {

        Phrase curr;
        curr = phraseList.get(p);
        path.add(p);
        int match;

        while (true) {

            if (curr.index == -1) {
                Collections.reverse(path);

                return path;
            }

            match = curr.index;
            path.add(match);
            curr = phraseList.get(curr.index);

        }
    }

    /*---------------------------------------------*/
    /*                                             */
    /*Method to find the last character in a phrase*/
    /*                                             */
    /*---------------------------------------------*/
    public int lastchara(int p) {

        Phrase curr;
        curr = phraseList.get(p);
        int match = curr.cha;

        while (curr.index != -1) {
            curr = phraseList.get(curr.index);
            match = curr.cha;

        }
        return match;
    }

    /*----------------------------------------------*/
    /*                                              */
    /*Method to make the root line of the Dictionary*/
    /*                                              */
    /*----------------------------------------------*/
    public void Initialize() {
        phraseList = new ArrayList<Phrase>();

        for (int i = 0; i < 257; i++) {
            //Phrase phrases = new Phrase(i, i);
            Phrase temp = new Phrase(-1, i);
            phraseList.add(temp);

        }

    }

}
