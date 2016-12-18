package com.ulewo.po.convert;

import org.springframework.core.convert.converter.Converter;

import com.ulewo.po.enums.TopicType;
import com.ulewo.utils.StringTools;

public class TopicTypeConvertor implements Converter<String, TopicType> {
	@Override
	public TopicType convert(String enumValueStr) {
		String value = enumValueStr.trim();
		if (value.isEmpty() || !StringTools.isNumber(value)) {
			return null;
		}
		return TopicType.getTopicTypeByValue(Integer.parseInt(enumValueStr));
	}
}