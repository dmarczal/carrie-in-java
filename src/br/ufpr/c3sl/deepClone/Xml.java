package br.ufpr.c3sl.deepClone;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedInputStream;
import java.io.IOException;

public class Xml {

	public static byte[] writeObjet(Object obj){
		FastByteArrayOutputStream fbos =
			new FastByteArrayOutputStream();
		
		XMLEncoder out = new XMLEncoder(fbos);
		
		try {
			out.writeObject(obj);
			out.flush();
			out.close();
			fbos.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return fbos.getByteArray();
	}
	
	public static Object getObjet(byte[] array){
		FastByteArrayInputStream fbis;
		XMLDecoder in;
		Object o;

		try {

			fbis = new FastByteArrayInputStream(array, array.length);
			in = new XMLDecoder(new BufferedInputStream(fbis));

			o = in.readObject();

			in.close();
			return o;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
