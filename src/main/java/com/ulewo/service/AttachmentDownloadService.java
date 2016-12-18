/**
 * Project Name:ulewo-web
 * File Name:AttachmentDownloadService.java
 * Package Name:com.ulewo.service
 * Date:2015年10月30日下午9:20:48
 * Copyright (c) 2015, bucuoa.com All Rights Reserved.
 *
*/

package com.ulewo.service;

import java.util.List;

import com.ulewo.po.model.AttachmentDownload;

/**
 * ClassName:AttachmentDownloadService <br/>
 * Date:     2015年10月30日 下午9:20:48 <br/>
 * @author   不错啊
 * Copyright (c) 2015, bucuoa.com All Rights Reserved. 
 */
public interface AttachmentDownloadService {
	/**
	 * 
	 * findAttachmentDonwload:(查看下载). <br/>
	 *
	 * @author 不错啊
	 * @param query
	 * @return
	 * @since JDK 1.7
	 */
	public List<AttachmentDownload> findAttachmentDonwload(Integer attachmentId);
}
