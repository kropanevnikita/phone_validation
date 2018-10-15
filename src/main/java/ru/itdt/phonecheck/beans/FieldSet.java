package ru.itdt.phonecheck.beans;

import java.util.HashMap;
import java.util.Map;

public class FieldSet {

	private Map<String, Object> fields;

	public FieldSet() {
		fields = new HashMap<>();
	}

	public void addField(String name, Object value) {
		this.fields.put(name, value);
	}

	public Object getFieldValue(String name) {
		return this.fields.get(name);
	}

}
