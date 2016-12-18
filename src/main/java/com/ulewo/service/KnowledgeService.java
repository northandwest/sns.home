package com.ulewo.service;

import java.util.List;

import com.ulewo.exception.BusinessException;
import com.ulewo.po.model.Attachment;
import com.ulewo.po.model.Knowledge;
import com.ulewo.po.query.KnowledgeQuery;
import com.ulewo.po.vo.PaginationResult;

public interface KnowledgeService {
	public List<Knowledge> findKnowledges4Index();

	/**
	 * 
	 * getKnowledgeById:(根据ID查询)
	 * @author luohaili
	 * @param topicId
	 * @return
	 * @since JDK 1.7
	 */
	public Knowledge getKnowledgeById(Integer knowledgeId);

	/**
	 * 
	 * findKnowledgesByPage:(分页查询)
	 * @author luohaili
	 * @param query
	 * @return
	 * @since JDK 1.7
	 */
	public PaginationResult<Knowledge> findKnowledgesByPage(KnowledgeQuery query);

	/**
	 * 
	 * addKnowledge:(新增)
	 * @author luohaili
	 * @param knowledge
	 * @param attachment TODO
	 * @since JDK 1.7
	 */
	public void addKnowledge(Knowledge knowledge, Attachment attachment) throws BusinessException;

	/**
	 * 
	 * showKnowledge:(展示详情)
	 * @author luohaili
	 * @param topicId
	 * @since JDK 1.7
	 */
	public Knowledge showKnowledge(Integer knowledgeId, Integer userId) throws BusinessException;

	/**
	 * 
	 * auditKnowledge:(审核). <br/>
	 *
	 * @author 不错啊
	 * @param ids
	 * @since JDK 1.7
	 */
	public void auditKnowledge(Integer[] ids);

	/**
	 * 
	 * deleteKnowledge:(删除知识库). <br/>
	 *
	 * @author 不错啊
	 * @param ids
	 * @since JDK 1.7
	 */
	public void deleteKnowledge(Integer[] ids);
}
