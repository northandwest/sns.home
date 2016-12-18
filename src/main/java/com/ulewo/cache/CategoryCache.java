/**
 * Project Name:ulewo-web
 * File Name:CategoryCache.java
 * Package Name:com.ulewo.cache
 * Date:2015年11月5日下午9:13:22
 * Copyright (c) 2015, bucuoa.com All Rights Reserved.
 *
*/

package com.ulewo.cache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ulewo.po.model.Category;
import com.ulewo.service.CategoryService;
import com.ulewo.utils.Constants;
import com.ulewo.utils.SpringContextUtil;

/**
 * ClassName:CategoryCache <br/>
 * Date:     2015年11月5日 下午9:13:22 <br/>
 * @author   不错啊
 * Copyright (c) 2015, bucuoa.com All Rights Reserved. 
 */
public class CategoryCache {

	private static Map<String, List<Category>> categoryCache = null;

	private static Map<String, Category> signleCategoryCache = null;

	private static CategoryService categoryService = (CategoryService) SpringContextUtil.getBean("categoryService");
	static {
		categoryCache = new HashMap<String, List<Category>>();
		signleCategoryCache = new HashMap<String, Category>();
		categoryCache.put(Constants.CACHE_KEY_BBS_CATEGORY, new ArrayList<Category>());
		categoryCache.put(Constants.CACHE_KEY_EXAM_CATEGORY, new ArrayList<Category>());
		categoryCache.put(Constants.CACHE_KEY_KNOWLEDGE_CATEGORY, new ArrayList<Category>());
		categoryCache.put(Constants.CACHE_KEY_ASK_CATEGORY, new ArrayList<Category>());
	}

	public static void refreshCategoryCache() {

		//清空原来的数据
		categoryCache.get(Constants.CACHE_KEY_BBS_CATEGORY).clear();
		categoryCache.get(Constants.CACHE_KEY_EXAM_CATEGORY).clear();
		categoryCache.get(Constants.CACHE_KEY_KNOWLEDGE_CATEGORY).clear();
		categoryCache.get(Constants.CACHE_KEY_ASK_CATEGORY).clear();
		List<Category> list = categoryService.findCategroyList(null);
		for (Category c : list) {
			Category category = (Category) c.clone();
			
			final String y = Constants.Y;
			if (y.equals(c.getShowInBBS())) {//bbs
				
				categoryCache.get(Constants.CACHE_KEY_BBS_CATEGORY).add(category);
				filterChild(category, Constants.CACHE_KEY_BBS_CATEGORY);
			}
			if (y.equals(c.getShowInExam())) {//exam
				categoryCache.get(Constants.CACHE_KEY_EXAM_CATEGORY).add(category);
				filterChild(category, Constants.CACHE_KEY_EXAM_CATEGORY);
			}
			if (y.equals(c.getShowInKnowledge())) {//knowledge
				categoryCache.get(Constants.CACHE_KEY_KNOWLEDGE_CATEGORY).add(category);
				filterChild(category, Constants.CACHE_KEY_KNOWLEDGE_CATEGORY);
			}
			if (y.equals(c.getShowInQuestion())) {//ask
				categoryCache.get(Constants.CACHE_KEY_ASK_CATEGORY).add(category);
				filterChild(category, Constants.CACHE_KEY_ASK_CATEGORY);
			}
			signleCategoryCache.put(Constants.CACHE_KEY_CATEOGRY + c.getCategoryId(), c);
			List<Category> children = c.getChildren();
			for (Category s : children) {
				signleCategoryCache.put(Constants.CACHE_KEY_CATEOGRY + s.getCategoryId(), s);
			}

		}
	}

	public static void filterChild(Category category, String show) {
		List<Category> filterChildren = new ArrayList<Category>();
		List<Category> childrens = category.getChildren();
		for (Category child : childrens) {
			String y = Constants.Y;
			if (show.equals(Constants.CACHE_KEY_BBS_CATEGORY) && y.equals(child.getShowInBBS())) {
				filterChildren.add(child);
			}
			if (show.equals(Constants.CACHE_KEY_EXAM_CATEGORY) && y.equals(child.getShowInExam())) {
				filterChildren.add(child);
			}
			if (show.equals(Constants.CACHE_KEY_KNOWLEDGE_CATEGORY) && y.equals(child.getShowInKnowledge())) {
				filterChildren.add(child);
			}
			if (show.equals(Constants.CACHE_KEY_ASK_CATEGORY) && y.equals(child.getShowInQuestion())) {
				filterChildren.add(child);
			}

		}
		category.setChildren(filterChildren);
	}

	/**
	 * 
	 * getBbsCategory:(获取论坛分类). <br/>
	 *
	 * @author 不错啊
	 * @return
	 * @since JDK 1.7
	 */
	public static List<Category> getBbsCategory() {
		return categoryCache.get(Constants.CACHE_KEY_BBS_CATEGORY);
	}

	public static List<Category> getKnowledgeCategory() {
		return categoryCache.get(Constants.CACHE_KEY_KNOWLEDGE_CATEGORY);
	}

	public static List<Category> getAskCategory() {
		return categoryCache.get(Constants.CACHE_KEY_ASK_CATEGORY);
	}

	public static List<Category> getExamCategory() {
		return categoryCache.get(Constants.CACHE_KEY_EXAM_CATEGORY);
	}

	public static Category getCategoryById(Integer categoryId) {
		return signleCategoryCache.get(Constants.CACHE_KEY_CATEOGRY + categoryId);
	}
}
