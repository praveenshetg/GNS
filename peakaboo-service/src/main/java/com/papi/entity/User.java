package com.papi.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

@Entity
@javax.persistence.Table(name = "PW_USER")
public class User implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	// @GeneratedValue(strategy = GenerationType.IDENTITY)
	@GeneratedValue(generator = "increment")
	@GenericGenerator(name = "increment", strategy = "increment")
	@Column(name = "user_id")
	private Long id;

	@Column(name = "FNAME")
	private String fName;

	@Column(name = "LNAME")
	private String lName;

	@Column(name = "OEMAIL", unique = true)
	private String oEmail;

	@Column(name = "PEMAIL")
	private String pEmail;

	@Column(name = "AGE")
	private Integer age;
	
	@Column(name = "dob")
	private Integer dob;

	@Column(name = "USERNAME")
	private String username;

	@Column(name = "PASSWORD")
	private String password;

	@Column(name = "GENDER")
	private String gender;

	@Column(name = "ABOUTYOU")
	@Type(type="text")
	private String aboutYou;

	@Column(name = "ORGANIZATION")
	private String organization;
	
	@Column(name = "designation")
	private String designation;
	
	private String phone;
	
	private Long group_id;
	
	private String role_name;
	
	private String token;
	
	@Lob
    @Column(name="image", columnDefinition="mediumblob")
    private byte[] image;
	
	

	// bi directional mapping to traverse from both the side

	/*@ManyToOne
	@JoinColumn(name = "GROUP_ID")
	private Group group;*/

	


	public byte[] getImage() {
		return image;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public Integer getDob() {
		return dob;
	}
	public void setDob(Integer dob) {
		this.dob = dob;
	}
	public String getDesignation() {
		return designation;
	}
	public void setDesignation(String designation) {
		this.designation = designation;
	}
	public void setImage(byte[] image) {
		this.image = image;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getRole_name() {
		return role_name;
	}
	public void setRole_name(String role_name) {
		this.role_name = role_name;
	}
	public Long getGroup_id() {
		return group_id;
	}
	public void setGroup_id(Long group_id) {
		this.group_id = group_id;
	}
	public User() {
	}
	public User(String username, String password, String oEmailId,Long groupId){
		this.username = username;
		this.password = password;
		this.oEmail = oEmailId;
		this.group_id = groupId;
	}
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getfName() {
		return fName;
	}

	public void setfName(String fName) {
		this.fName = fName;
	}

	public String getlName() {
		return lName;
	}

	public void setlName(String lName) {
		this.lName = lName;
	}

	public String getoEmail() {
		return oEmail;
	}

	public void setoEmail(String oEmail) {
		this.oEmail = oEmail;
	}

	public String getpEmail() {
		return pEmail;
	}

	public void setpEmail(String pEmail) {
		this.pEmail = pEmail;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getAboutYou() {
		return aboutYou;
	}

	public void setAboutYou(String aboutYou) {
		this.aboutYou = aboutYou;
	}

	public String getOrganization() {
		return organization;
	}

	public void setOrganization(String organization) {
		this.organization = organization;
	}

	@Override
	public String toString() {
		return "User: " + this.id + ", " + this.fName + ", " + this.age;
	}
	
	
	

}
