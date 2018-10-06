// Name: Andy Shen, ID: 1304441
// Name: Sampson Ward, ID: 1312744

import java.io.*;
import java.nio.*;
import java.util.*;

public class BitUnpacker {

    long inBits;
    static ByteBuffer byteStream;

    int resetNum = 256;
    int phraseSize = 256;
    int counter;
    int shifter;

    byte[] byteArray = new byte[8];	//Byte array which stores 64 bits(1 long)


    public static void main(String[] args)
            throws java.io.IOException
    {
        BitUnpacker bitUnpacker = new BitUnpacker();

        bitUnpacker.Unpack();

    }


    public void Unpack()
            throws java.io.IOException
    {

        //store read bytes into an array
        int readIn = System.in.read(byteArray);


        //wrap the bytes into a buffer
        byteStream = ByteBuffer.wrap(byteArray);
        //convert buffer into a long for easier bit manipulation
        inBits = byteStream.getLong();


        //size of long in bits
        int size = 64;
        //how many bits the current phrase number is represented in
        int bitLimit = 9;
        //offset is amount needed to jump in the long to retrieve next phrase
        int offSet = 0;
        //amount to shift new phrase to push out any bits from the next phrase
        shifter = 55;

        //while the file is not empty
        while(readIn != -1){

            //if the counter exceeds phrase limit
            if (counter >= (phraseSize * 2)) {
                //double phrase limit
                phraseSize = phraseSize * 2;
                //decrease amount needed to push unwanted bits
                shifter--;
                //increase amount needed to jump accross to next phrase
                bitLimit++;
            }

            //store the incoming bits in a variable for shifting
            long outBits = inBits;

            //shift bits to the left by the offset to remove unwated leading phrase numbers
            outBits <<= offSet;

            //if the next phrase number to retrieve is not at the begining of the long (because of leading zero's we pushing a crucial bit into the next apparent phrase)
            if(offSet >= bitLimit){
                //shift bits left by one to include trailing bit
                outBits <<=1;
                //move bits unsigned right by shift amount to remove trailing phrases
                outBits >>>= shifter;
                //increase counter because I have reduced to a single phrase number
                counter++;
            }
            //else the phrase number to retireve is at the begining and a shift which includes a trailing bit is required
            else{
                //shift right unsigned by shifter minus 1 to keep trailing bit
                outBits >>>= (shifter-1);
                //increase counter
                counter++;
            }

            //if the reset number is found
            if(outBits == resetNum){
                //reset all variable to fit minimum phrase sizes
                counter = 0;
                shifter = 55;
                bitLimit=9;
            }
            //increase offset by how many bits needed to reach next phrase
            offSet += bitLimit;


            //if the offset becomes greater than the amount bits in a long
            if(offSet >= 63){

                //reset the offset
                offSet = 0;
                //retrieve new bytes and convert into long to begin shifting from new point in file
                readIn = System.in.read(byteArray);
                byteStream = ByteBuffer.wrap(byteArray);
                inBits = byteStream.getLong();

            }

            //print the outbits
            System.out.println(outBits);

        }
    }
}