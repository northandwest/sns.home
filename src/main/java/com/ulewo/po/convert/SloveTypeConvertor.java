package com.ulewo.po.convert;

import org.springframework.core.convert.converter.Converter;

import com.ulewo.po.enums.SloveTypeEnum;
import com.ulewo.utils.StringTools;

public class SloveTypeConvertor implements Converter<String, SloveTypeEnum> {
	@Override
	public SloveTypeEnum convert(String enumValueStr) {
		String value = enumValueStr.trim();
		if (value.isEmpty() || !StringTools.isNumber(value)) {
			return null;
		}
		return SloveTypeEnum.getTypeByValue(Integer.parseInt(value));
	}
}