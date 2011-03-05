package br.ufpr.c3sl.model;

import java.sql.Timestamp;
import java.util.List;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import br.ufpr.c3sl.client.webservices.XMLMistakeDAO;

@Root
public class Mistake {

	@Element
	private Timestamp created_at;
	
	@Element
	private Timestamp updated_at;

	private User user;
	
	@Element(required=false)
	private Long id;

	@Element(required=false)
	private String title;
	
	@Element(required=false)
	private String exercise;
	
	@Element(required=false)
	private String oa;
	
	@Element(required=false)
	private String description;
	
	@Element
	private String answer;
	
	@Element(required=false)
	private String correct_answer;
	
	@Element(required=false)
	private String cell;
	
	@Element(required=false)
	private byte[] object;
	
	public byte[] getObject() {
//		try {
//			return Base64.decode(object);
//		} catch (Base64DecodingException e) {
//			e.printStackTrace();
//		}
		return object;
	}

	public void setObject(byte[] object) {
		this.object = object;//Base64.encode(object);;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public String getCorrectAnswer() {
		return correct_answer;
	}

	public void setCorrectAnswer(String correct_answer) {
		this.correct_answer = correct_answer;
	}

	public String getCell() {
		return cell;
	}

	public void setCell(String cell) {
		this.cell = cell;
	}

	public String getExercise() {
		return exercise;
	}

	public void setExercise(String exercise) {
		this.exercise = exercise;
	}

	public String getOa() {
		return oa;
	}

	public void setOa(String oa) {
		this.oa = oa;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Timestamp getCreatedAt(){
		return created_at;
	}
	
	public void setCreatedAt(Timestamp time){
		created_at = time;
	}
	
	public Timestamp getUpdatedAt() {
		return updated_at;
	}

	public void setUpdatedAt(Timestamp updated_at) {
		this.updated_at = updated_at;
	}

	/**
	 * Save the mistake
	 * @return Mistake saved
	 */
	public Mistake save(){
		return XMLMistakeDAO.save(this);
	}
	
	/**
	 * Get all mistakes
	 * @return List<Mistake> list off all mistakes
	 */
	public static List<Mistake> all(String oa){
		return XMLMistakeDAO.getAll(oa);
	}
	
}
