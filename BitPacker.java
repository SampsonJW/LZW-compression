// Name: Andy Shen, ID: 1304441
// Name: Sampson Ward, ID: 1312744

import java.io.*;
import java.util.*;
import java.nio.*;

public class BitPacker {
	long outBits;
	byte[] buffer;
	int shifter;
	int counter;
	int resetNum = 256;
	int phraseSize = 256;
	static Scanner in;

	public static void main(String[] args)
			throws java.io.IOException
	{
		BitPacker bitPacker = new BitPacker();

		bitPacker.Packer();


	}


	public void Packer()
			throws java.io.IOException
	{

		int cap = 64;

		outBits = 0;
		shifter = 9;

		in = new Scanner(System.in);




		while(in.hasNextLine()){


		    //If counter get larger than the phrase size
			if (counter >= (phraseSize * 2)) {
			    //double phrase size
				phraseSize = phraseSize * 2;
				//increase amount of bits required to represent the new phrase
				shifter++;
			}

			//shift the outbits long by the required phrase size
			outBits <<= shifter;
			//Decrement the capacity of our long
			cap = cap - shifter;

			//put the current phrase number into a variable
            int currPhrase = Integer.parseInt(in.nextLine());

            //If the phrase number is equal to the reset num then reset counter and shift amount
            if(currPhrase == resetNum){
                counter = 0;
                shifter = 9;
            }
            //Insert phrase and increment counter
			outBits |= currPhrase;
			counter++;

			//when the capacity is less than the shift amount (cant fit any more phrases)
            if(cap < shifter){



                //if the outbits are negative (has a leading bit of 1)
                //converts outbit into 2's compliment
                if (outBits < 0) {
                    //unsigned shift right converts negative back to positive with no data loss
                    outBits >>>= 1;

                }
                //store longs into a bytebuffer array
                buffer = ByteBuffer.allocate(8).putLong(outBits).array();
                for(int i = 0; i < buffer.length; i++){
                        //prints each byte one at a time
                        System.out.write(buffer[i]);
                }


                //change capacity back to maximum
                cap = 64;
                //wipe the previous long and get ready to read in new value
                outBits = 0;
            }


		}

        buffer = ByteBuffer.allocate(8).putLong(outBits).array();
        for(int i = 0; i < buffer.length; i++){
            //prints each byte one at a time
            System.out.write(buffer[i]);
        }
	}
}
