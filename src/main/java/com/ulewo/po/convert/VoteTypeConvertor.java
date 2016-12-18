package com.ulewo.po.convert;

import org.springframework.core.convert.converter.Converter;

import com.ulewo.po.enums.VoteType;
import com.ulewo.utils.StringTools;

public class VoteTypeConvertor implements Converter<String, VoteType> {
	@Override
	public VoteType convert(String enumValueStr) {
		String value = enumValueStr.trim();
		if (value.isEmpty() || !StringTools.isNumber(value)) {
			return null;
		}
		return VoteType.getTypeByValue(Integer.valueOf(enumValueStr));
	}
}