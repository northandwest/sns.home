/**
 * Project Name:ulewo-web
 * File Name:AttachementServiceImpl.java
 * Package Name:com.ulewo.service.impl
 * Date:2015年10月25日下午7:20:55
 * Copyright (c) 2015, bucuoa.com All Rights Reserved.
 *
*/

package com.ulewo.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bucuoa.ucenter.model.UlewoUser;
import com.ulewo.exception.BusinessException;
import com.ulewo.mapper.AttachmentDownloadMapper;
import com.ulewo.mapper.AttachmentMapper;
import com.ulewo.po.enums.ArticleType;
import com.ulewo.po.enums.DateTimePatternEnum;
import com.ulewo.po.model.Attachment;
import com.ulewo.po.model.AttachmentDownload;
import com.ulewo.po.model.Blog;
import com.ulewo.po.model.Knowledge;
import com.ulewo.po.model.SessionUser;
import com.ulewo.po.model.Topic;
import com.ulewo.po.query.AttachementQuery;
import com.ulewo.po.query.AttachmentDownloadQuery;
import com.ulewo.service.AttachementService;
import com.ulewo.service.BlogService;
import com.ulewo.service.KnowledgeService;
import com.ulewo.service.TopicService;
import com.ulewo.service.UserService;
import com.ulewo.utils.Constants;
import com.ulewo.utils.DateUtil;
import com.ulewo.utils.ServerUtils;
import com.ulewo.utils.StringTools;

/**
 * ClassName:AttachementServiceImpl <br/>
 * Date:     2015年10月25日 下午7:20:55 <br/>
 * @author   不错啊
 * Copyright (c) 2015, bucuoa.com All Rights Reserved. 
 */
@Service("attachementService")
public class AttachementServiceImpl implements AttachementService {

	@Resource
	private AttachmentMapper<Attachment, AttachementQuery> attacheentMapper;

	@Resource
	private TopicService topicService;

	@Resource
	private KnowledgeService knowledgeService;

	@Resource
	private BlogService blogService;

	@Resource
	private UserService userService;

	@Resource
	private AttachmentDownloadMapper<AttachmentDownload, AttachmentDownloadQuery> attachmentDownloadMapper;

	@Override
	public void addAttachement(Attachment file) throws BusinessException {
		final int MAXKMARk = 10000;
		Integer mark = file.getDownloadMark();
		mark = mark == null ? 0 : mark;
		file.setDownloadMark(mark);
		if (StringTools.isEmpty(file.getFileName()) || StringTools.isEmpty(file.getFileUrl()) || mark > MAXKMARk) {
			throw new BusinessException("参数异常");
		}
		//复制文件到正式目录
		String realPath = ServerUtils.getRealPath();

		String saveDir = DateUtil.format(new Date(), DateTimePatternEnum.YYYYMM.getPattern());
		String fileDir = realPath + Constants.PATH_UPLOAD + saveDir;
		File dir = new File(fileDir);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		String sourcFilePath = realPath + Constants.PATH_TEMP_UPLOAD + file.getFileUrl();
		String targetFilePath = realPath + Constants.PATH_UPLOAD + file.getFileUrl();
		File s = new File(sourcFilePath);
		File t = new File(targetFilePath);
		try {
			if (s.exists()) {
				FileUtils.copyFile(s, t);
				s.delete();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		attacheentMapper.insert(file);
	}

	@Override
	public Attachment getAttachmentByTopicIdAndFileType(Integer topicId, ArticleType fileTypieType) {
		if (null == topicId || null == fileTypieType) {
			return null;
		}
		AttachementQuery query = new AttachementQuery();
		query.setTopicId(topicId);
		query.setFileTopicType(fileTypieType);
		List<Attachment> attachmentList = this.attacheentMapper.selectList(query);
		if (attachmentList.isEmpty()) {
			return null;
		}
		return attachmentList.get(0);
	}

	@Override
	public Attachment getAttachmentById(Integer attachmentId) {
		if (null == attachmentId) {
			return null;
		}
		AttachementQuery query = new AttachementQuery();
		query.setAttachmentId(attachmentId);
		List<Attachment> attachmentList = this.attacheentMapper.selectList(query);
		if (attachmentList.isEmpty()) {
			return null;
		}
		return attachmentList.get(0);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = BusinessException.class)
	public Attachment downloadAttachment(SessionUser sessionUser, Integer attachmentId) throws BusinessException {
		Attachment attachment = this.getAttachmentById(attachmentId);
		if (null == attachment) {
			throw new BusinessException("附件不存在");
		}
		Integer topicId = attachment.getTopicId();
		ArticleType fileTypieType = attachment.getFileTopicType();
		//判断附件文章类型
		Integer userId = null;
		if (fileTypieType == ArticleType.TOPIC) {
			Topic topic = topicService.getTopicById(topicId);
			if (null == topic) {
				throw new BusinessException("附件对应的文章不存在");
			}
			checkUserDownloadPermission(topic.getUserId(), sessionUser.getUserId(), attachment.getDownloadMark(),
					attachmentId);
			userId = topic.getUserId();
		} else if (fileTypieType == ArticleType.KNOWLEDGE) {
			Knowledge knowledge = knowledgeService.getKnowledgeById(topicId);
			if (null == knowledge) {
				throw new BusinessException("附件对应的文章不存在");
			}
			checkUserDownloadPermission(knowledge.getUserId(), sessionUser.getUserId(), attachment.getDownloadMark(),
					attachmentId);
			userId = knowledge.getUserId();
		} else if (fileTypieType == ArticleType.BLOG) {
			Blog blog = blogService.getBlogById(topicId);
			if (null == blog) {
				throw new BusinessException("附件对应的文章不存在");
			}
			checkUserDownloadPermission(blog.getUserId(), sessionUser.getUserId(), attachment.getDownloadMark(),
					attachmentId);
			userId = blog.getUserId();
		}
		//幂等插入
		AttachmentDownload download = new AttachmentDownload();
		download.setAttachmentId(attachmentId);
		download.setUserId(sessionUser.getUserId());
		download.setUserName(sessionUser.getUserName());
		download.setUserIcon(sessionUser.getUserIcon());
		attachmentDownloadMapper.insert(download);

		//下载数量+1
		attacheentMapper.updateDownloadCount(attachmentId);

		//给发布者加积分
		userService.changeMark(userId, attachment.getDownloadMark());

		return attachment;
	}

	/**
	 * 
	 * checkUserDownloadPermission:(校验用户下载权限)
	 * @author luohaili
	 * @param topicUserId
	 * @param userId
	 * @param downloadMark
	 * @param attachmentId
	 * @throws BusinessException
	 * @since JDK 1.7
	 */
	private void checkUserDownloadPermission(Integer topicUserId, Integer userId, Integer downloadMark,
			Integer attachmentId) throws BusinessException {
		//下载者不是 发布者校验积分并且下载积分大于0
		if (topicUserId.intValue() != userId.intValue() && downloadMark != null && downloadMark > 0) {
			//查询用户是否下载过
			AttachmentDownloadQuery query = new AttachmentDownloadQuery();
			query.setAttachmentId(attachmentId);
			query.setUserId(userId);
			int downloadCount = this.attachmentDownloadMapper.selectCount(query);
			//如果没有下载过，那么要减去相应的积分
			if (downloadCount == 0) {
				int count = userService.changeMark(userId, -downloadMark);
				if (count == 0) {
					throw new BusinessException("积分不够");
				}
			}
		}
	}

	public void checkDownload(Integer attachmentId, Integer topicId, SessionUser sessionUser) throws BusinessException {
		if (null == attachmentId || null == topicId) {
			throw new BusinessException("参数错误");
		}
		Attachment attachment = this.getAttachmentById(attachmentId);
		if (null == attachment) {
			throw new BusinessException("附件不存在");
		}

		ArticleType fileTypieType = attachment.getFileTopicType();
		//判断附件文章类型
		Integer topicUserId = null;
		if (fileTypieType == ArticleType.TOPIC) {
			Topic topic = topicService.getTopicById(topicId);
			if (null == topic) {
				throw new BusinessException("附件对应的文章不存在");
			}
			topicUserId = topic.getUserId();
		} else if (fileTypieType == ArticleType.KNOWLEDGE) {
			Knowledge knowledge = knowledgeService.getKnowledgeById(topicId);
			if (null == knowledge) {
				throw new BusinessException("附件对应的文章不存在");
			}
			topicUserId = knowledge.getUserId();
		} else if (fileTypieType == ArticleType.BLOG) {
			Blog blog = blogService.getBlogById(topicId);
			if (null == blog) {
				throw new BusinessException("附件对应的文章不存在");
			}
			topicUserId = blog.getUserId();
		}

		if (topicUserId.intValue() != sessionUser.getUserId().intValue() && attachment.getDownloadMark() != null
				&& attachment.getDownloadMark() > 0) {
			UlewoUser user = userService.findUserByUserId(sessionUser.getUserId().toString());
			if (user.getMark() < attachment.getDownloadMark()) {
				throw new BusinessException("你的积分只有&nbsp;&nbsp;" + user.getMark() + "&nbsp;&nbsp;分,积分不够");
			}
		}
	}
}
