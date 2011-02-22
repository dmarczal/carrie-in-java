package br.ufpr.c3sl.model;

import java.sql.Timestamp;
import java.util.List;

import br.ufpr.c3sl.dao.MistakeDAO;
import br.ufpr.c3sl.daoFactory.DAOFactory;
import br.ufpr.c3sl.exception.UserException;

public class Mistake {
	
	private byte[] Object;
	
	private String exercise;
	private String learningObject;
	
	private MistakeInfo mistakeInfo;
	
	private Long createdAt;
	
	private User user;
	
	private Long id;
	
	public Mistake(){
		mistakeInfo = null;
		user = null;
	}
	
	public Timestamp getCreatedAtTime() {
		return new Timestamp(createdAt);
	}
	
	public void setCreatedAt(Long time) {
		this.createdAt = time;
	}
	
	public Long getCreatedAt(){
		return this.createdAt;
	}
	
	public byte[] getObject() {
		return Object;
	}
	
	public void setObject(byte[] Object) {
		this.Object = Object;
	}
	
	public void setMistakeInfo(MistakeInfo mistakeInfo) {
		this.mistakeInfo = mistakeInfo;
	}
	
	public MistakeInfo getMistakeInfo(){
		return this.mistakeInfo;
	}
	
	public String getExercise() {
		return exercise;
	}
	
	public void setExercise(String exercise) {
		this.exercise = exercise;
	}
	
	public String getLearningObject() {
		return learningObject;
	}
	
	public void setLearningObject(String learningObject) {
		this.learningObject = learningObject;
	}
	
	public User getUser() {
		return user;
	}
	
	public void setUser(User user) {
		this.user = user;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getId() {
		return id;
	}
	
	public Mistake save() throws UserException{
		DAOFactory dao = DAOFactory.getDAOFactory(DAOFactory.DATABASE_CHOOSE);
		MistakeDAO mistakedao = dao.getMistakeDAO();
		
		return mistakedao.insert(this);
	}
	
	/**
	 * Get All mistakes from a user and from a learningObjetct
	 * @param user The user
	 * @param learningObject The name of learning object 
	 */
	public static List<Mistake> all(User user, String learningObjetct){
		DAOFactory dao = DAOFactory.getDAOFactory(DAOFactory.DATABASE_CHOOSE);
		MistakeDAO mistakedao = dao.getMistakeDAO();
		
		return mistakedao.getAll(user, learningObjetct);
	}
}
