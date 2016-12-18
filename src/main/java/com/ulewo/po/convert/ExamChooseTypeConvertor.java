package com.ulewo.po.convert;

import org.springframework.core.convert.converter.Converter;

import com.ulewo.po.enums.ExamChooseType;
import com.ulewo.utils.StringTools;

public class ExamChooseTypeConvertor implements Converter<String, ExamChooseType> {
	@Override
	public ExamChooseType convert(String enumValueStr) {
		String value = enumValueStr.trim();
		if (value.isEmpty() || !StringTools.isNumber(value)) {
			return null;
		}
		return ExamChooseType.getExamChooseTypeByValue(Integer.parseInt(enumValueStr));
	}
}