package com.ulewo.po.handler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import com.ulewo.po.enums.StatusEnum;

public class StatusEnumHandler extends BaseTypeHandler<StatusEnum> {

	@Override
	public void setNonNullParameter(PreparedStatement ps, int i, StatusEnum parameter, JdbcType jdbcType)
			throws SQLException {
		ps.setInt(i, (parameter.ordinal()));
	}

	@Override
	public StatusEnum getNullableResult(ResultSet rs, String columnName) throws SQLException {
		Integer s = rs.getInt(columnName);
		return s == null ? null : StatusEnum.getStatusByValue(s);
	}

	@Override
	public StatusEnum getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
		Integer s = rs.getInt(columnIndex);
		return s == null ? null : StatusEnum.getStatusByValue(s);
	}

	@Override
	public StatusEnum getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
		Integer s = cs.getInt(columnIndex);
		return s == null ? null : StatusEnum.getStatusByValue(s);
	}

}