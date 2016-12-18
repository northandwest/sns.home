package com.ulewo.service.impl;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

//import org.apache.http.impl.cookie.DateUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ulewo.exception.BusinessException;
import com.ulewo.mapper.BlogMapper;
import com.ulewo.po.enums.ArticleType;
import com.ulewo.po.enums.BlogStatusEnum;
import com.ulewo.po.enums.DateTimePatternEnum;
import com.ulewo.po.enums.MarkEnum;
import com.ulewo.po.enums.MessageType;
import com.ulewo.po.enums.OrderByEnum;
import com.ulewo.po.enums.PageSize;
import com.ulewo.po.enums.TextLengthEnum;
import com.ulewo.po.model.Attachment;
import com.ulewo.po.model.Blog;
import com.ulewo.po.model.Like;
import com.ulewo.po.model.MessageParams;
import com.ulewo.po.query.BlogQuery;
import com.ulewo.po.query.LikeQuery;
import com.ulewo.po.query.UpdateQuyery4ArticleCount;
import com.ulewo.po.vo.PaginationResult;
import com.ulewo.po.vo.SimplePage;
import com.ulewo.service.AttachementService;
import com.ulewo.service.BlogService;
import com.ulewo.service.LikeService;
import com.ulewo.service.MessageService;
//import com.ulewo.service.SolrService;
import com.ulewo.service.UserService;
import com.ulewo.utils.ImageUtils;
import com.ulewo.utils.StringTools;

@Service("blogService")
public class BlogServiceImpl implements BlogService {

	@Resource
	private BlogMapper<Blog, BlogQuery> blogMapper;

	@Resource
	private AttachementService attachementService;

	@Resource
	private UserService userService;

	@Resource
	private FormatAtService formatAtService;

	@Resource
	private LikeService likeService;

//	@Resource
//	private SolrService solrService;

	@Resource
	private MessageService messageService;

	@Override
	public List<Blog> findBlogs4Index() {
		BlogQuery query = new BlogQuery();
		SimplePage page = new SimplePage(0, PageSize.SIZE20.getSize(), PageSize.SIZE20.getSize());
		query.setOrderBy(OrderByEnum.CREATE_TIME_DESC);
		query.setPage(page);
		query.setStatus(BlogStatusEnum.PASS);
		List<Blog> list = this.blogMapper.selectList(query);
		return list;
	}

	public Blog getBlogById(Integer blogId) {
		BlogQuery query = new BlogQuery();
		query.setBlogId(blogId);
		query.setShowContent(Boolean.TRUE);
		List<Blog> list = this.blogMapper.selectList(query);
		if (list.isEmpty()) {
			return null;
		}
		return list.get(0);
	}

	@Override
	public PaginationResult<Blog> findBlogByPage(BlogQuery query) {
		query.setOrderBy(OrderByEnum.CREATE_TIME_DESC);
		query.setShowContent(Boolean.FALSE);
		int count = this.blogMapper.selectCount(query);
		int pageSize = PageSize.SIZE20.getSize();
		int pageNo = 0;
		if (null != query.getPageNo()) {
			pageNo = query.getPageNo();
		}
		SimplePage page = new SimplePage(pageNo, count, pageSize);
		query.setPage(page);
		List<Blog> list = this.blogMapper.selectList(query);
		PaginationResult<Blog> result = new PaginationResult<Blog>(page, list);
		return result;
	}

	@Override
	public Blog showBlog(Integer blogId, Integer userId) throws BusinessException {
		Blog blog = getBlogById(blogId);
		if (blog == null || blog.getUserId().intValue() != userId.intValue()
				|| blog.getStatus() == BlogStatusEnum.DRAFTS) {
			throw new BusinessException("博客不存在或者已经删除");
		}
		blog.setFile(attachementService.getAttachmentByTopicIdAndFileType(blogId, ArticleType.BLOG));
		//阅读量加1
		UpdateQuyery4ArticleCount query = new UpdateQuyery4ArticleCount();
		query.setAddReadCount(Boolean.TRUE);
		query.setTopicId(blogId);
		blogMapper.updateInfoCount(query);

		LikeQuery likeQuery = new LikeQuery();
		likeQuery.setArticleId(blogId);
		likeQuery.setArticleType(ArticleType.BLOG);
		List<Like> likeUsers = likeService.findLikeList(likeQuery);
		blog.setLikeUsers(likeUsers);

		return blog;
	}

	public Blog getBlogByBlogId(Integer blogId, Integer userId) throws BusinessException {
		Blog blog = getBlogById(blogId);
		if (blog == null || blog.getUserId().intValue() != userId.intValue()) {
			throw new BusinessException("博客不存在或者已经删除");
		}
		blog.setFile(attachementService.getAttachmentByTopicIdAndFileType(blogId, ArticleType.BLOG));
		return blog;
	}

	@Override
	public void saveDraftsBlog(Blog blog) throws BusinessException {
		blog.setStatus(BlogStatusEnum.DRAFTS);
		if (blog.getBlogId() != null) {
			this.blogMapper.update(blog);
		} else {
			blog.setCreateTime(new Date());
			this.blogMapper.insert(blog);
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = BusinessException.class)
	public void postBlog(Blog blog, Attachment attachment) throws BusinessException {
		if (blog.getTitle() == null || blog.getTitle().length() > TextLengthEnum.LENGTH_150.getLength()
				|| blog.getCategoryId() == null || StringTools.isEmpty(blog.getContent())
				|| blog.getContent().length() > TextLengthEnum.LONGTEXT.getLength()) {
			throw new BusinessException("参数错误");
		}

		String content = blog.getContent();
		String summary = StringTools.clearHtmlTag(content);
		if (summary.length() > TextLengthEnum.LENGTH_200.getLength()) {
			summary = summary.substring(0, TextLengthEnum.LENGTH_200.getLength().intValue()) + "......";
		}
		Set<Integer> receiveUserIds = new HashSet<Integer>();
		String formatContent = formatAtService.GenerateRefererLinks(content, receiveUserIds);
		blog.setSummary(summary);
		blog.setContent(formatContent);
		String blogImage = ImageUtils.getImages(content);
		blog.setBlogImage(blogImage);
		String blogImageSmall = ImageUtils.createThumbnail(blogImage, true);
		blog.setBlogImageThum(blogImageSmall);

		Date curDate = new Date();
		blog.setCreateTime(curDate);
		boolean add = false;
		blog.setStatus(BlogStatusEnum.PASS);
		if (blog.getBlogId() == null) {
			//插入
			add = true;
			this.blogMapper.insert(blog);
		} else {
			Blog temp = this.getBlogById(blog.getBlogId());
			if (temp.getStatus() == BlogStatusEnum.DRAFTS) {
				add = true;
			}
			this.blogMapper.update(blog);
		}
		//加分
		if (add) {
			this.userService.changeMark(blog.getUserId(), MarkEnum.MARK_BLOG.getMark());
		}
		//发布附件
		if (attachment != null && !StringTools.isEmpty(attachment.getFileName())
				&& !StringTools.isEmpty(attachment.getFileUrl())) {
			attachment.setCreateTime(curDate);
			attachment.setTopicId(blog.getBlogId());
			attachment.setFileTopicType(ArticleType.BLOG);
			attachementService.addAttachement(attachment);
		}

//		SolrBean solr = new SolrBean();
//		solr.setId(blog.getBlogId().toString() + "_" + ArticleType.BLOG.getType());
//		solr.setContent(StringTools.clearHtmlTag(blog.getContent()));
//		solr.setTitle(blog.getTitle());
//		solr.setSummary(StringTools.clearHtmlTag(blog.getSummary()));
//		solr.setUserId(blog.getUserId().toString());
//		solr.setUserName(blog.getUserName());
//		solr.setCreateTime(DateUtils.formatDate(blog.getCreateTime(),
//				DateTimePatternEnum.YYYY_MM_DD_HH_MM_SS.getPattern()));
//		solr.setArticleType(ArticleType.BLOG.getType());
//		solrService.addArticle(solr);

		//发送消息
		MessageParams messageParams = new MessageParams();
		messageParams.setArticleId(blog.getBlogId());
		messageParams.setArticleUserId(blog.getUserId());
		messageParams.setMessageType(MessageType.AT_ARTICLE_MESSAGE);
		messageParams.setArticleType(ArticleType.BLOG);
		messageParams.setReceiveUserIds(receiveUserIds);
		messageParams.setSendUserName(blog.getUserName());
		messageParams.setSendUserId(blog.getUserId());
		messageService.createMessage(messageParams);
	}

	@Override
	public void delBlog(Integer blogId, Integer userId) throws BusinessException {
		if (blogId == null) {
			throw new BusinessException("参数错误");
		}
		BlogQuery query = new BlogQuery();
		query.setBlogId(blogId);
		query.setUserId(userId);
		int count = blogMapper.delete(query);
		if (count == 0) {
			throw new BusinessException("博客不存在");
		}
	}

	@Override
	public void deleteBatch(Integer[] ids) {
		this.blogMapper.deleteBatch(ids);
	}
}
