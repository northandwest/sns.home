package com.ulewo.po.convert;

import org.springframework.core.convert.converter.Converter;

import com.ulewo.po.enums.StatusEnum;
import com.ulewo.utils.StringTools;

public class StatutsConvertor implements Converter<String, StatusEnum> {
	@Override
	public StatusEnum convert(String enumValueStr) {
		String value = enumValueStr.trim();
		if (value.isEmpty() || !StringTools.isNumber(value)) {
			return null;
		}
		return StatusEnum.getStatusByValue(Integer.valueOf(enumValueStr));
	}
}