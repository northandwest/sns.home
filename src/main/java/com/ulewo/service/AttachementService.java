/**
 * Project Name:ulewo-web
 * File Name:AttachementService.java
 * Package Name:com.ulewo.service
 * Date:2015年10月25日下午4:10:56
 * Copyright (c) 2015, bucuoa.com All Rights Reserved.
 *
*/

package com.ulewo.service;

import com.ulewo.exception.BusinessException;
import com.ulewo.po.enums.ArticleType;
import com.ulewo.po.model.Attachment;
import com.ulewo.po.model.SessionUser;

/**
 * ClassName:AttachementService <br/>
 * Date:     2015年10月25日 下午4:10:56 <br/>
 * @author   不错啊
 * Copyright (c) 2015, bucuoa.com All Rights Reserved. 
 */
public interface AttachementService {
	/**
	 * 
	 * addAttachement:(保存附件). <br/>
	 *
	 * @author 不错啊
	 * @param file
	 * @throws BusinessException
	 * @since JDK 1.7
	 */
	public void addAttachement(Attachment file) throws BusinessException;

	/**
	 * 
	 * getAttachmentByTopicIdAndFileType:(根据文章ID和类型查询附件). <br/>
	 *
	 * @author 不错啊
	 * @param topicType
	 * @param fileTypieType
	 * @return
	 * @since JDK 1.7
	 */
	public Attachment getAttachmentByTopicIdAndFileType(Integer topicId, ArticleType fileTypieType);

	/**
	 * 
	 * getAttachmentById:(通过Id获取附件)
	 * @author luohaili
	 * @param attachmentId
	 * @return
	 * @since JDK 1.7
	 */
	public Attachment getAttachmentById(Integer attachmentId);

	/**
	 * 
	 * downloadAttachment:(下载附件)
	 * @author luohaili
	 * @param attachmmentId
	 * @return
	 * @since JDK 1.7
	 */
	public Attachment downloadAttachment(SessionUser sessionUser, Integer attachmentId) throws BusinessException;

	/**
	 * 
	 * checkDownload:(校验附件下载). <br/>
	 *
	 * @author 不错啊
	 * @param attachmentId
	 * @param topicId
	 * @param sessionUser
	 * @throws BusinessException
	 * @since JDK 1.7
	 */
	public void checkDownload(Integer attachmentId, Integer topicId, SessionUser sessionUser) throws BusinessException;
}
