package br.ufpr.c3sl.deepclone;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.zip.DataFormatException;

import javax.swing.JPanel;

import junit.framework.Assert;

import org.junit.Test;

import br.ufpr.c3sl.deepClone.Compressor;
import br.ufpr.c3sl.deepClone.ObjectByteArray;
import br.ufpr.c3sl.model.Mistake;
import br.ufpr.c3sl.model.User;
import br.ufpr.c3sl.session.Session;

public class CompressorTest {
	
	private static User user;
	
	@org.junit.BeforeClass
	public static void setup(){
		user = new User("dmarczal@gmail.com");
		user.setId(1l);
		Session.setCurrentUser(user);
	}
	
	@Test
	public void should_compress() throws IOException, DataFormatException{
		JPanel jp = new JPanel();
		jp.setName("Teste");
		
		byte[] obj = ObjectByteArray.getByteOfArray(jp);
		
		byte[] compress = Compressor.compress(obj);
		
		byte[] decompress = Compressor.decompress(compress);
		
		JPanel newpanel = (JPanel) ObjectByteArray.getObject(decompress);
		Assert.assertEquals(newpanel.getName(), "Teste");
	}
	
	@Test
	public void should_compress_and_save() throws IOException, DataFormatException{
		user = new User("dmarczal@gmail.com");
		user.setId(1l);
		Session.setCurrentUser(user);
		
		JPanel jp = new JPanel();
		jp.setName("Teste");
		
		
		Mistake mistake = new Mistake();

		byte[] obj;
		synchronized (jp) {
			obj = ObjectByteArray.getByteOfArray(jp);
		}

		try {
			mistake.setObject(Compressor.compress(obj));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
		mistake.setExercise(jp.getName());
		mistake.setOa("Fractal Simulator");
		mistake.setUser(Session.getCurrentUser());

		mistake.setAnswer("2");
		mistake.setCell("1 2");
		mistake.setCorrectAnswer("3");
		mistake.setDescription("Erro de teste");
		mistake.setTitle("Erro de teste");
		mistake.setCreatedAt(new Timestamp(new Date().getTime()));
		mistake.setUpdatedAt(new Timestamp(new Date().getTime()));
	
		Mistake msave = mistake.save();
		
		byte[] decompress = null;
		try {
			decompress = Compressor.decompress(msave.getObject());
			JPanel newpanel = (JPanel) ObjectByteArray.getObject(decompress);
			
			Assert.assertEquals(newpanel.getName(), "Teste");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
