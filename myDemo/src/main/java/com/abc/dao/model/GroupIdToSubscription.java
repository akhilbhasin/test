package com.abc.dao.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the GroupIdToSubscriptions database table.
 * 
 */
@Entity
@Table(name="GroupIdToSubscriptions")
public class GroupIdToSubscription implements Serializable {
	private static final long serialVersionUID = 1L;

	
	private int id;

	private String groupId;

	private String subName;

	public GroupIdToSubscription() {
	}

	@Id
	@Column(name = "id")
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Column(name = "groupId", length = 50)
	public String getGroupId() {
		return this.groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	@Column(name = "subName", length = 50)
	public String getSubName() {
		return this.subName;
	}

	public void setSubName(String subName) {
		this.subName = subName;
	}

}