package com.ulewo.po.handler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

import com.ulewo.po.enums.Taskstatus;

@SuppressWarnings("rawtypes")
public class TaskStatusHandler extends BaseTypeHandler implements TypeHandler {

	@Override
	public Object getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
		Integer s = rs.getInt(columnIndex);
		return s == null ? null : Taskstatus.getStatusByStatus(s);
	}

	@Override
	public Object getNullableResult(ResultSet rs, String columnName) throws SQLException {
		Integer s = rs.getInt(columnName);
		return s == null ? null : Taskstatus.getStatusByStatus(s);
	}

	@Override
	public Object getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
		Integer s = cs.getInt(columnIndex);
		return s == null ? null : Taskstatus.getStatusByStatus(s);
	}

	@Override
	public void setNonNullParameter(PreparedStatement ps, int i, Object parameter, JdbcType jdbcType)
			throws SQLException {
		ps.setInt(i, ((Taskstatus) parameter).ordinal());
	}
}
