package ru.itdt.phonecheck.beans;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserDoc {

	private Map<String, List<FieldSet>> tables;

	public UserDoc() {
		tables = new HashMap<>();
	}

	public void addTable(String name, List<FieldSet> table) {
		this.tables.put(name, table);
	}

	public List<FieldSet> getTable(String name) {
		return this.tables.get(name);
	}

}
