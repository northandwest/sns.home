/**
 * Project Name:ulewo-web
 * File Name:AttachmentDownloadServiceImpl.java
 * Package Name:com.ulewo.service.impl
 * Date:2015年10月30日下午9:23:55
 * Copyright (c) 2015, bucuoa.com All Rights Reserved.
 *
*/

package com.ulewo.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ulewo.mapper.AttachmentDownloadMapper;
import com.ulewo.po.model.AttachmentDownload;
import com.ulewo.po.query.AttachmentDownloadQuery;
import com.ulewo.service.AttachmentDownloadService;

/**
 * ClassName:AttachmentDownloadServiceImpl <br/>
 * Date:     2015年10月30日 下午9:23:55 <br/>
 * @author   不错啊
 * Copyright (c) 2015, bucuoa.com All Rights Reserved. 
 */
@Service("attachmentDownloadService")
public class AttachmentDownloadServiceImpl implements AttachmentDownloadService {

	@Resource
	private AttachmentDownloadMapper<AttachmentDownload, AttachmentDownloadQuery> attachmentDownloadMapper;

	@Override
	public List<AttachmentDownload> findAttachmentDonwload(Integer attachmentId) {
		AttachmentDownloadQuery query = new AttachmentDownloadQuery();
		query.setAttachmentId(attachmentId);
		return attachmentDownloadMapper.selectList(query);
	}
}
