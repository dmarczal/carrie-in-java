package br.ufpr.c3sl.deepClone;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class ObjectByteArray {

	/**
	 * transform a Object in a getByteOfArray
	 * @param object 
	 */
	public static byte[] getByteOfArray(Object object){
		FastByteArrayOutputStream fbos =
			new FastByteArrayOutputStream();
		ObjectOutputStream out;
		
		try {
			out = new ObjectOutputStream(fbos);

			out.writeObject(object);
			out.flush();
			out.close();
			fbos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return fbos.getByteArray();
	} 

	/**
	 * transform a byte of array in Object 
	 * @param array byte of array
	 */
	public static Object getObject(byte[] array){
		FastByteArrayInputStream fbis;
		ObjectInputStream in;
		Object o;

		try {

			fbis = new FastByteArrayInputStream(array, array.length);
			in = new ObjectInputStream(fbis);

			o = in.readObject();

			in.close();
			return o;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	} 
}
