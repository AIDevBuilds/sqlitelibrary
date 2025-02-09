package com.example.test;

import com.sandeepsingh91.db.Column;
import com.sandeepsingh91.db.TableName;

@TableName("Test")
public class Test {

	@Column("name")
	private String userName;

	@Column()
	private int id;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
