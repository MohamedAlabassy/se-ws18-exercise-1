/*
 * Assignment 01
 * Software Engineering WiSe2018/2019 
 * Names: Marat Khairtdinov(119434) & Mohamed Said Helmy Alabassy(119530)
 * Reference for the Source code modified for the Write Method in this Assignment: How to write to file in Java � BufferedWriter (2. JDK7 Example) 
 * https://www.mkyong.com/java/how-to-write-to-file-in-java-bufferedwriter-example/
 */

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Image {
	public byte[] data;
	private int h, w, rgbval;
	private int rgbmaxval = 0xFFFFFF;	//Max RGB value (White)
	
	//Image object Constructors
	public Image () {}
	
	public Image (int width, int height) {
		this();
		w = width;
		h = height;
		data = new byte[width*h*3];
	}	
	
	public void set (int x, int y, int val) {
		rgbval = rgbmaxval & val;	//To bit mask the lower 24 bits of val and ensure no value exceeds the white colour
		String s = "0x" + Integer.toHexString(rgbval);	//To convert the Hex rgbval to a string and dividing it into substrings according to its R,G,B values
		data[(y*w+x)*3] = (byte)Integer.parseInt(s.substring(2,4),16);
		data[(y*w+x)*3+1] = (byte)Integer.parseInt(s.substring(4,6),16);
		data[(y*w+x)*3+2] = (byte)Integer.parseInt(s.substring(6,8),16);
	}
	
	public void write(String filename) {
		int maxval=255;		
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) { 
			String filedata = "P6\t" + w + "\t" + h + "\t" + maxval;
			writer.write(filedata);
			for (int i=0; i<h;i++) {
				writer.newLine();
				for (int j=0;j<w;j++) {					
					writer.write(Integer.toHexString(Byte.toUnsignedInt((byte)(data[i*w+j])))+"\t");					
				}				
			}
			writer.close();
		}
		catch (IOException exc) {
			exc.printStackTrace();
		}
	}
}
