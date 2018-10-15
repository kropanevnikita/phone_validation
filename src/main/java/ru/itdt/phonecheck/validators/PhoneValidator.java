package ru.itdt.phonecheck.validators;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Logger;
import java.util.regex.Pattern;

import ru.itdt.phonecheck.beans.FieldSet;

public class PhoneValidator {

	private static final String TABLE_NULL_ERROR_TEXT = "Таблица null.";

	private static final int ERROR_KEY = -1;

	private static final String PHONE_FIELD_NAME = "PhoneNumber";
	
	private static final String STRING_PATTERN = "^\\+7([ ]?)[(]([0-9]{3})[)]([ ]?)([0-9]{2})([ ]?)-([ ]?)([0-9]{2})([ ]?)-([ ]?)([0-9]{3})$";
	
	private static Logger log = Logger.getLogger(PhoneValidator.class.getName());
	
	public boolean validatePhone(String phone) {
		return Pattern.compile(STRING_PATTERN).matcher(phone).matches();
	}

	public Map<Integer, String> validateTable(List<FieldSet> table) {

		final Map<Integer, String> errors = new HashMap<>();
		log.info("Старт валидации.");
		if (table == null) {
			log.info("Таблица null. Конец валидации.");
			errors.put(ERROR_KEY, TABLE_NULL_ERROR_TEXT);
			return errors;
		}
		for (int i = 0; i < table.size(); i++) {
			if ((String) table.get(i).getFieldValue(PHONE_FIELD_NAME) != null) {
				if (!validatePhone((String) table.get(i).getFieldValue(PHONE_FIELD_NAME))) {
					errors.put(i, (String) table.get(i).getFieldValue(PHONE_FIELD_NAME));
					log.info("Ошибка в строке " + (i + 1) + ". Значение: " + (String) table.get(i).getFieldValue(PHONE_FIELD_NAME));
				} else {
					log.info("Строка " + (i + 1) + " проверена. Значение: " + (String) table.get(i).getFieldValue(PHONE_FIELD_NAME));
				}
			}
			else {
				errors.put(i, null);
				log.info("Ошибка в строке " + (i + 1) + ". Значение: " + null);
			}
			
		}
		log.info("Конец валидации.");
		return errors;

	}

	public String generateReport(String tableName, Map<Integer, String> errors) {

		if (errors.containsKey(ERROR_KEY)) {
			return TABLE_NULL_ERROR_TEXT;
		}
		
		if (!errors.isEmpty()) {
			StringBuilder nums = new StringBuilder();
			StringBuilder values = new StringBuilder();
			for (Entry<Integer, String> entry : errors.entrySet()) {
				nums.append(String.valueOf(entry.getKey() + 1) + ", ");
				values.append("'" + entry.getValue() + "'" + ", ");
			}
			return "Внимание, в строках " + nums.substring(0, nums.length() - 2) + " таблицы \"" + tableName
					+ "\"  данные не прошли проверку: " + values.substring(0, values.length() - 2) + ".";
		}

		return "Все данные прошли проверку.";

	}

}
