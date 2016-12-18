package com.ulewo.po.convert;

import org.springframework.core.convert.converter.Converter;

import com.ulewo.po.enums.ArticleType;

public class ArticleTypeConvertor implements Converter<String, ArticleType> {
	@Override
	public ArticleType convert(String enumValueStr) {
		String value = enumValueStr.trim();
		if (value.isEmpty()) {
			return null;
		}
		return ArticleType.getArticleTypeByType(enumValueStr);
	}
}