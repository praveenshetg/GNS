//package com.papi.entity;
//
//import java.io.Serializable;
//import java.util.HashSet;
//import java.util.List;
//import java.util.Set;
//
//import javax.persistence.CascadeType;
//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.FetchType;
//import javax.persistence.GeneratedValue;
//import javax.persistence.Id;
//import javax.persistence.OneToMany;
//
//import org.hibernate.annotations.GenericGenerator;
//
//@Entity
//@javax.persistence.Table(name = "PW_GROUP")
//
//public class GroupOld implements Serializable {
//	private static final long serialVersionUID = 1L;
//	@Id
//	// @GeneratedValue(strategy = GenerationType.IDENTITY)
//	@GeneratedValue(generator = "increment")
//	@GenericGenerator(name = "increment", strategy = "increment")
//	@Column(name = "ID")
//	private long group_id;
//
//	@Column(name = "NAME")
//	private String name;
//
//	@Column(name = "CATEGORY")
//	private String category;
//
//	@Column(name = "STATUS")
//	private String status;
//
//	public GroupOld() {
//		this.category = "General";
//		this.status = "Active";
//	}
//	// @OneToMany
//	// @JoinColumn(name ="GROUP_ID")
//	// private List<User> users;
//
//	/*public List<User> getUsers() {
//		return users;
//	}
//
//	public void setUsers(List<User> users) {
//		this.users = users;
//	}*/
//
//	public long getId() {
//		return group_id;
//	}
//
//	public void setId(long id) {
//		this.group_id = id;
//	}
//
//	public String getName() {
//		return name;
//	}
//
//	public void setName(String name) {
//		this.name = name;
//	}
//
//	public String getCategory() {
//		return category;
//	}
//
//	public void setCategory(String category) {
//		this.category = category;
//	}
//
//	public String getStatus() {
//		return status;
//	}
//
//	public void setStatus(String status) {
//		this.status = status;
//	}
//
//	// bi directional mapping to traverse from both the side
//	//@OneToMany(fetch = FetchType.EAGER, mappedBy = "group")
//	//private List<User> users;
//	 @OneToMany(cascade = CascadeType.ALL,
//	            fetch = FetchType.LAZY,
//	            mappedBy = "group")
//	    private Set<User> users = new HashSet<User>();
//
//
//}
