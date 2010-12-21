package br.ufpr.c3sl.util;

import java.awt.Canvas;
import java.awt.Component;
import java.awt.Container;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.ImageIcon;

import br.ufpr.c3sl.model.User;
import br.ufpr.c3sl.state.SaveState;

/**
 * CARRIE Framework
 * class Util of CARRIE framework
 * @author  Diego Marczal
 * @version Outubro 16, 2010
 */ 
public class Util {

	private static final String ICONS_PATH = "/br/ufpr/c3sl/icons/";
	private static User currentUser = null;
	
	/**
	 * CARRIE Framework
	 * class JpCarrie the main panel of CARRIE framework
	 * @param kclass Class to get URL path
	 * @param imgName name of the icon, the extension is png
	 */ 
	public static java.net.URL getIconURL(Class<?> kclass, String imgName) {
		java.net.URL imgURL = kclass.getResource(ICONS_PATH + imgName + ".png");
		if (imgURL != null) {
			return imgURL;
		} else {
			System.err.println("Couldn't find file: " + ICONS_PATH + imgName + ".png");
			return null;
		}
	}

	/**
	 * get Image Icon
	 * @param kclass Class to get image icon
	 * @param imgName name of the icon, the extension is png
	 */ 
	public static ImageIcon getImageIcon(Class<?> kclass, String iconName) {
		return new ImageIcon(getIconURL(kclass, iconName));
	}

	/**
	 * validate Email
	 * @param email the email address
	 * @return true if email is valid
	 * 		   false if email is not valid	
	 */ 
	public static boolean validateEmail(String email){
		Pattern p = Pattern.compile(".+@.+\\.[a-z]+");
		Matcher m = p.matcher(email);
		return m.matches();
	}

	/**
	 * update Static Fields of the one container
	 * @param container the container that need to be update 
	 */
	public static void updateStaticFields(Container container){
		for (Component c : container.getComponents()) {
			if (c instanceof SaveState)
				((SaveState) c).buildEventsAndTransientvariables();

			if (!(c instanceof Canvas))
				updateStaticFields((Container)c);
		}
	}
	
	/**
	 * set current user
	 * @param user the actual user
	 */ 
	public static void setCurrentUserl(User user){
		currentUser = user;
	}

	/**
	 * get current user
	 * @return currentUser the actual user
	 */ 
	public static User getCurrentUserl(){
		return currentUser;
	}
	
	/**
	 * Format Date and Time
	 * For example from 2010-12-18 21:51:00
	 * to Dom 18 Nov 2010 9:51:00 pm
	 * @parm time the time that will be format
	 * @return date String formt date
	 */ 
	public static String getDateTimeFormated(Timestamp time){
		String[] months = {"Jan", "Fev", "Mar",
						   "Abr", "Mai", "Jun",
						   "Jul", "Ago", "Set",
						   "Out", "Nov", "Dez"};
		
		String[] daysOfWeek = {"Dom", "Seg", "Ter", "Qua", "Qui", "Sex", "Sáb"};
		
		SimpleDateFormat hourFormat = new SimpleDateFormat("hh:mm:ss");
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(time);
		
		String formatDate = " " + daysOfWeek[calendar.get(Calendar.DAY_OF_WEEK)-1]  
		                              		  + " " + calendar.get(Calendar.DAY_OF_MONTH) + " "  
		                                      + months[calendar.get(Calendar.MONTH)] + " "   
		                                      + calendar.get(Calendar.YEAR) + " "   
		                                      + hourFormat.format(calendar.getTime()) + " " 
		                                      + ((calendar.get(Calendar.AM_PM) ==  0) ? "am" :"pm");  
		return formatDate; 
	}
}
