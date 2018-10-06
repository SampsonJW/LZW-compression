//Sampson Ward
//1312744
//Andy Shen
//1304441
import java.io.*;
import java.util.*;

public class LZWEncoder {


    int phraseCount;
    TrieNode[] root;
    TrieNode curr;
    public static double resetNum;


    public static void main(String[] args)

            throws java.io.IOException
    {
        try {

            if (args.length != 1) {
                System.err.println("Enter in maximum phrase number please");
            }

        resetNum = Math.pow(2, (Double.parseDouble(args[0])))-1;
        LZWEncoder LZW = new LZWEncoder();
        LZW.Initialize();
        LZW.Encode();

        }catch(Exception e){}

    }
    /*-----------------------------------------------------------------*/
    /*                                                                 */
    /*Public void which makes the multiway Trie and encodes LZW phrases*/
    /*                                                                 */
    /*-----------------------------------------------------------------*/
    public void Encode() {

        int charac;
        int nextChar;
        int newLine;
        TrieNode pointer;
        TrieNode parent;

        try {

            charac = ReadIn();
            nextChar = ReadIn();

            parent = root[charac];

            while(charac != -1) {
                //We see is the a character is in our trie
                pointer = FindNode(nextChar, parent);
                //if it is not in our trie
                if (pointer == null) {

                    System.out.println(parent.phraseNum);
                    //add the character to trie
                    AddNode(nextChar, parent);
                    charac = nextChar;
                    nextChar = ReadIn();
                    parent = root[charac];
                } else {
                    //if it is in our trie look through its children to find a mismatch
                    parent = pointer;
                    charac = nextChar;
                    nextChar = ReadIn();

                }
            }

        } catch (Exception e) {
        }
    }

    /*------------------------------------------------------------------*/
    /*                                                                  */
    /*Public Trienode which searches the Trie for a match or empty space*/
    /*                                                                  */
    /*------------------------------------------------------------------*/
    public TrieNode FindNode(int c, TrieNode parent) {
        //if the parent has no child
        if (parent.child == null) {
            return null;
        }
            curr = parent.child;
        //trys to find if the character is in parents children
        while (curr != null) {
            if (curr.c == c) {
                return curr;
            }
            else{
            curr = curr.neighbour;
            }
        }
        //if theres no matching characters
        return null;
    }

    /*---------------------------------------------------*/
    /*                                                   */
    /*Public void to add a new node to LZW Trie structure*/
    /*                                                   */
    /*---------------------------------------------------*/
    public void AddNode(int c, TrieNode parent) {
        //if the child if null add child
        if (parent.child == null) {
            phraseCount++;
            //if the phrase count will be bigger the maximum phrase number
            if(phraseCount >= resetNum){
                System.out.println(256);
                //makes in trie nodes at root level
                Initialize();
            }

            parent.child = new TrieNode(c, phraseCount);

            return;
        }
        //if there is a child then we must add to its neighbours
        else{
            curr = parent.child;
            while (curr.neighbour != null) {

                curr = curr.neighbour;

            }
            phraseCount++;
            if(phraseCount >= resetNum){
                System.out.println(256);
                Initialize();
            }

            curr.neighbour = new TrieNode(c, phraseCount);
        }

    }

    /*------------------------------------------------------------*/
    /*                                                            */
    /*Method to make and reset the root line of the muiltiway Trie*/
    /*                                                            */
    /*------------------------------------------------------------*/
    public void Initialize() {


        root = new TrieNode[257];
        for (int i = 0; i < 257; i++) {

            root[i] = new TrieNode(i, i);
            phraseCount = i;

        }

    }

    /*----------------------------------------*/
    /*                                        */
    /*Small method to read from Standard input*/
    /*                                        */
    /*----------------------------------------*/
    public int ReadIn()
            throws java.io.IOException
    {
        return System.in.read();

    }

}
