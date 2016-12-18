package com.ulewo.mapper;

import java.util.List;

public interface CategoryMapper<T, Q> extends BaseMapper<T, Q> {
	List<T> selectCategory4TopicCount(Q q);
}
