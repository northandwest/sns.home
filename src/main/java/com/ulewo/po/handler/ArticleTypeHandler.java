package com.ulewo.po.handler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import com.ulewo.po.enums.ArticleType;

public class ArticleTypeHandler extends BaseTypeHandler<ArticleType> {

	@Override
	public void setNonNullParameter(PreparedStatement ps, int i, ArticleType parameter, JdbcType jdbcType)
			throws SQLException {
		ps.setInt(i, (parameter.ordinal()));
	}

	@Override
	public ArticleType getNullableResult(ResultSet rs, String columnName) throws SQLException {
		String s = rs.getString(columnName);
		return s == null ? null : ArticleType.getArticleTypeByType(s);
	}

	@Override
	public ArticleType getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
		String s = rs.getString(columnIndex);
		return s == null ? null : ArticleType.getArticleTypeByType(s);
	}

	@Override
	public ArticleType getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
		String s = cs.getString(columnIndex);
		return s == null ? null : ArticleType.getArticleTypeByType(s);
	}

}