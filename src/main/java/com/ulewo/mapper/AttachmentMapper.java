/**
 * Project Name:ulewo-common
 * File Name:AttachementMapper.java
 * Package Name:com.ulewo.mapper
 * Date:2015年10月25日下午7:22:53
 * Copyright (c) 2015, bucuoa.com All Rights Reserved.
 *
*/

package com.ulewo.mapper;

import org.springframework.stereotype.Repository;

/**
 * ClassName:AttachementMapper <br/>
 * Date:     2015年10月25日 下午7:22:53 <br/>
 * @author   不错啊
 * Copyright (c) 2015, bucuoa.com All Rights Reserved. 
 */
@Repository
public interface AttachmentMapper<T, Q> extends BaseMapper<T, Q> {
	public void updateDownloadCount(Integer attachmentId);
}
