package com.ulewo.po.handler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import com.ulewo.po.enums.BlogStatusEnum;

public class BlogStatusEnumHandler extends BaseTypeHandler<BlogStatusEnum> {

	@Override
	public void setNonNullParameter(PreparedStatement ps, int i, BlogStatusEnum parameter, JdbcType jdbcType)
			throws SQLException {
		ps.setInt(i, (parameter.ordinal()));
	}

	@Override
	public BlogStatusEnum getNullableResult(ResultSet rs, String columnName) throws SQLException {
		Integer s = rs.getInt(columnName);
		return s == null ? null : BlogStatusEnum.getStatusByValue(s);
	}

	@Override
	public BlogStatusEnum getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
		Integer s = rs.getInt(columnIndex);
		return s == null ? null : BlogStatusEnum.getStatusByValue(s);
	}

	@Override
	public BlogStatusEnum getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
		Integer s = cs.getInt(columnIndex);
		return s == null ? null : BlogStatusEnum.getStatusByValue(s);
	}

}