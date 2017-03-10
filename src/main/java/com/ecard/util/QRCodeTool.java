package com.ecard.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Hashtable;

import org.apache.commons.codec.binary.Base64;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;

public class QRCodeTool {
	
	public static String getQRCode(String value) throws WriterException, IOException{
		String text = value;   
        int width = 600;   
        int height = 600;   
        String format = "png";   
        Hashtable hints= new Hashtable();   
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");   
        BitMatrix bitMatrix;
		try {
			bitMatrix = new MultiFormatWriter().encode(text, BarcodeFormat.QR_CODE, width, height,hints);
			   File outputFile = new File("new.png");   
		         ByteArrayOutputStream outstream = new ByteArrayOutputStream(); 

		         MatrixToImageWriter.writeToStream(bitMatrix, format, outstream);
				 InputStream in = new ByteArrayInputStream(outstream.toByteArray()) ;
		         
		         byte[] data = null;
		         data = new byte[in.available()];
		         in.read(data);
		         in.close();
		         return new String(Base64.encodeBase64(data));
		        
		} catch (WriterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		}   
       
	}
}
