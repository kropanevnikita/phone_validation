package ru.itdt.phonecheck.validators;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import ru.itdt.phonecheck.beans.FieldSet;
import ru.itdt.phonecheck.beans.UserDoc;
import ru.itdt.phonecheck.validators.PhoneValidator;

public class PhoneValidatorTests {

	private static final String PHONE_FIELD_NAME = "PhoneNumber";
	private static final String NAME_FIELD_NAME = "Name";
	private static final String TABLE_NAME = "table";

	UserDoc userDoc;
	UserDoc tableNull;
	PhoneValidator phoneValidator;
	Map<String, Boolean> result;

	@Before
	public void setUp() {

		result = new HashMap<>();

		userDoc = new UserDoc();
		tableNull = new UserDoc();

		List<FieldSet> table = new ArrayList<>();
		table.add(new FieldSet());
		table.get(0).addField(PHONE_FIELD_NAME, "45325342345");
		table.get(0).addField(NAME_FIELD_NAME, "Петр");
		table.add(new FieldSet());
		table.get(1).addField(PHONE_FIELD_NAME, "+7(999)97-32-322");
		table.get(1).addField(NAME_FIELD_NAME, "Ольга");
		table.add(new FieldSet());
		table.get(2).addField(PHONE_FIELD_NAME, "+7 (999) 99-99-999");
		table.get(2).addField(NAME_FIELD_NAME, "Александр");
		table.add(new FieldSet());
		table.get(3).addField(PHONE_FIELD_NAME, "+73242342");
		table.get(3).addField(NAME_FIELD_NAME, "Дмитрий");
		table.add(new FieldSet());
		table.get(4).addField(PHONE_FIELD_NAME, "frkdngd");
		table.get(4).addField(NAME_FIELD_NAME, "Евгения");
		userDoc.addTable(TABLE_NAME, table);

		userDoc.addTable(TABLE_NAME + 1, null);

		List<FieldSet> tableWithNull = new ArrayList<>();
		tableWithNull.add(new FieldSet());
		userDoc.addTable(TABLE_NAME + 2, tableWithNull);

		phoneValidator = new PhoneValidator();

	}

	@Test
	public void testTableNull() {
		assertEquals(1, phoneValidator.validateTable(tableNull.getTable(TABLE_NAME + 1)).entrySet().size());
		String report = phoneValidator.generateReport(TABLE_NAME + 1,
				phoneValidator.validateTable(userDoc.getTable(TABLE_NAME + 1)));
		assertEquals("Таблица null.", report);
	}

	@Test
	public void testEmptyField() {
		String report = phoneValidator.generateReport(TABLE_NAME + 2,
				phoneValidator.validateTable(userDoc.getTable(TABLE_NAME + 2)));
		assertEquals("Внимание, в строках 1 таблицы \"table2\"  данные не прошли проверку: 'null'.", report);
	}

	@Test
	public void testValidator() {
		String report = phoneValidator.generateReport(TABLE_NAME,
				phoneValidator.validateTable(userDoc.getTable(TABLE_NAME)));
		System.out.println(report);

		assertEquals(
				"Внимание, в строках 1, 4, 5 таблицы \"table\"  данные не прошли проверку: '45325342345', '+73242342', 'frkdngd'.",
				report);
	}

}
