package com.ulewo.po.handler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import com.ulewo.po.enums.ExamChooseType;

public class ChooseTypeHandler extends BaseTypeHandler<ExamChooseType> {

	@Override
	public void setNonNullParameter(PreparedStatement ps, int i, ExamChooseType parameter, JdbcType jdbcType)
			throws SQLException {
		ps.setInt(i, (parameter.ordinal()));
	}

	@Override
	public ExamChooseType getNullableResult(ResultSet rs, String columnName) throws SQLException {
		Integer s = rs.getInt(columnName);
		return s == null ? null : ExamChooseType.getExamChooseTypeByValue(s);
	}

	@Override
	public ExamChooseType getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
		Integer s = rs.getInt(columnIndex);
		return s == null ? null : ExamChooseType.getExamChooseTypeByValue(s);
	}

	@Override
	public ExamChooseType getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
		Integer s = cs.getInt(columnIndex);
		return s == null ? null : ExamChooseType.getExamChooseTypeByValue(s);
	}

}