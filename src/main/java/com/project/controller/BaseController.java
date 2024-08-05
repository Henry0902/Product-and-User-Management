package com.project.controller;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.common.Paginate;
import com.project.exception.ErrorException;

@Controller
public class BaseController {
	@Value("${STATIC_FOLDER}")
	String STATIC_FOLDER;

	public String getStringParams(Map<String, String> allParams, String nameParam) {
		if (StringUtils.isEmpty(allParams.get(nameParam))) {
			return null;
		}
		return allParams.get(nameParam);
	}

	public Long getLongParams(Map<String, String> allParams, String nameParam) {
		if (StringUtils.isEmpty(allParams.get(nameParam))) {
			return null;
		}
		return Long.valueOf(allParams.get(nameParam));
	}

	public Integer getIntParams(Map<String, String> allParams, String nameParam) {
		if (StringUtils.isEmpty(allParams.get(nameParam))) {
			return null;
		}
		return Integer.valueOf(allParams.get(nameParam));
	}


	private static final List<String> EXTENSIONS = Arrays.asList(".doc", ".docx", ".xls", ".xlsx");

	public void forwartParams(Map<String, String> allParams, Model model) {
		for (Entry<String, String> entry : allParams.entrySet()) {
			model.addAttribute(entry.getKey(), entry.getValue());
		}
		model.addAttribute("STATIC_FOLDER", STATIC_FOLDER);
	}
	
	public void forwartParams(Map<String, String> allParams, Model model, HttpServletRequest req) {
		for (Entry<String, String> entry : allParams.entrySet()) {
			model.addAttribute(entry.getKey(), entry.getValue());
		}
		model.addAttribute("uri", "/"+req.getRequestURI().split("/")[1]);
		model.addAttribute("STATIC_FOLDER", STATIC_FOLDER);
	}

	public <T> String checkErrorMessage(T obj) throws ErrorException {
		String errorStr = "";
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();
		Set<ConstraintViolation<T>> violations = validator.validate(obj);
		if (violations.size() > 0) {
			for (ConstraintViolation<T> constraintViolation : violations) {
				errorStr += constraintViolation.getMessage() + "<br/>";
			}
			throw new ErrorException(errorStr);
		}

		return errorStr;
	}

	public long getUserId(HttpServletRequest req) {
		return (Long) req.getSession().getAttribute("userid");
	}

	public String getUserName(HttpServletRequest req) {
		return (String) req.getSession().getAttribute("username");
	}
	public long getProductId(HttpServletRequest req) {
		return (Long) req.getSession().getAttribute("productid");
	}
	public String getProductName(HttpServletRequest req) {
		return (String) req.getSession().getAttribute("productname");
	}
	
	public String queryStringBuilder(Map<String, String> allParams) {
		StringBuilder builder = new StringBuilder();
		for (Entry<String, String> entry : allParams.entrySet()) {
			String key = entry.getKey();
			if (key.equals("page") || key.equals("limit") || key.equals("order") || key.equals("sort")
					|| key.equals("filterShow") || key.startsWith("s") || key.equals("id")) {
				builder.append("&").append(entry.getKey()).append("=").append(entry.getValue());
			}
		}

		return builder.toString();
	}

	public String queryStringBuilderAll(Map<String, String> allParams) {
		StringBuilder builder = new StringBuilder();
		for (Entry<String, String> entry : allParams.entrySet()) {
			builder.append("&").append(entry.getKey()).append("=").append(entry.getValue());
		}

		return builder.toString();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public <T> T mapToClass(Map data, Class cls) {
		try {
			Object obj = cls.newInstance();
			for (Field f : cls.getDeclaredFields()) {
				f.setAccessible(true);
				Object value = data.get(f.getName());

				if (!StringUtils.isEmpty(value)) {
					Type type = f.getGenericType();
					if (String.class.equals(type)) {
						f.set(obj, value);
					}

					try {

					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
			return (T) cls.cast(obj);
		} catch (Exception e) {
		}

		return null;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	<T> void updateMapToObject(Map<String, String> params, T source, Class cls) throws JsonMappingException {
		ObjectMapper mapper = new ObjectMapper();
		mapper.setDateFormat(new SimpleDateFormat("dd/MM/yyyy"));
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		Object overrideObj = mapper.convertValue(params, cls);
		mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
		mapper.updateValue(source, overrideObj);
	}

	<T> void updateObjectToObject(T source, T objectEdit) throws JsonMappingException {
		ObjectMapper mapper = new ObjectMapper();
//		mapper.setDateFormat(new SimpleDateFormat("dd/MM/yyyy"));
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
		mapper.updateValue(source, objectEdit);
	}

	public Date convertStringToDate(String format, String strDate) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		try {
			return dateFormat.parse(strDate);
		} catch (ParseException e) {
		}
		return null;
	}

	public Long convertStringToLong(String format, String strDate) {
		if (strDate == null)
			return null;
		SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		try {
			return dateFormat.parse(strDate).getTime();
		} catch (ParseException e) {
		}
		return null;
	}

	public static boolean isValidFormat(String format, String value, Locale locale) {
		LocalDateTime ldt = null;
		DateTimeFormatter fomatter = DateTimeFormatter.ofPattern(format, locale);

		try {
			ldt = LocalDateTime.parse(value, fomatter);
			String result = ldt.format(fomatter);
			return result.equals(value);
		} catch (DateTimeParseException e) {
			try {
				LocalDate ld = LocalDate.parse(value, fomatter);
				String result = ld.format(fomatter);
				return result.equals(value);
			} catch (DateTimeParseException exp) {
				try {
					LocalTime lt = LocalTime.parse(value, fomatter);
					String result = lt.format(fomatter);
					return result.equals(value);
				} catch (DateTimeParseException e2) {
					// Debugging purposes
					// e2.printStackTrace();
				}
			}
		}

		return false;
	}

	public String getSort(Map<String, String> allParams) {
		if (allParams.get("sort") == null) {
			return "desc";
		}
		return allParams.get("sort");
	}

	public Pageable getPageable(Map<String, String> allParams, Paginate paginate) {

		Pageable pageable;
		String order = "id";
		if (!StringUtils.isEmpty(allParams.get("order"))) {
			order = allParams.get("order");
		}
		Sort sort;
		if (getSort(allParams).equals("desc")) {
			sort = Sort.by(order).descending();
		} else {
			sort = Sort.by(order).ascending();
		}
		pageable = PageRequest.of(paginate.getPage() - 1, paginate.getLimit(), sort);
		return pageable;
	}

	boolean allowExtension(String fileName) {
		for (String ext : EXTENSIONS) {
			if (fileName.endsWith(ext)) {
				return true;
			}
		}
		return false;
	}
}
