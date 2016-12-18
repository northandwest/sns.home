-- --------------------------------------------------------
-- 主机:                           192.168.1.110
-- 服务器版本:                        5.1.73-log - Source distribution
-- 服务器操作系统:                      redhat-linux-gnu
-- HeidiSQL 版本:                  9.3.0.4984
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

-- 导出  表 bucuoa.foot 结构
CREATE TABLE IF NOT EXISTS `foot` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `tag_id` bigint(20) DEFAULT '0',
  `creater_id` bigint(20) DEFAULT '0',
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=3 DEFAULT CHARSET=ucs2 COMMENT='脚印';

-- 正在导出表  bucuoa.foot 的数据：2 rows
/*!40000 ALTER TABLE `foot` DISABLE KEYS */;
INSERT INTO `foot` (`id`, `tag_id`, `creater_id`, `create_time`) VALUES
	(1, 1, 10720, '2016-02-27 18:41:55'),
	(2, 2, 10720, '2016-02-27 18:42:03');
/*!40000 ALTER TABLE `foot` ENABLE KEYS */;


-- 导出  表 bucuoa.foot_tag 结构
CREATE TABLE IF NOT EXISTS `foot_tag` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `parent_id` bigint(20) NOT NULL DEFAULT '0',
  `use_count` bigint(20) NOT NULL DEFAULT '0',
  `name` varchar(50) DEFAULT '' COMMENT '名称',
  `content` varchar(500) DEFAULT '' COMMENT '描述',
  `statusx` int(11) DEFAULT '0' COMMENT '状态',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=3 DEFAULT CHARSET=ucs2;

-- 正在导出表  bucuoa.foot_tag 的数据：2 rows
/*!40000 ALTER TABLE `foot_tag` DISABLE KEYS */;
INSERT INTO `foot_tag` (`id`, `parent_id`, `use_count`, `name`, `content`, `statusx`) VALUES
	(1, 0, 1, '良乡', '', 0),
	(2, 0, 1, '大学城西', '', 0);
/*!40000 ALTER TABLE `foot_tag` ENABLE KEYS */;


-- 导出  表 bucuoa.ulewo_ask 结构
CREATE TABLE IF NOT EXISTS `ulewo_ask` (
  `ask_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '问答ID',
  `title` varchar(200) DEFAULT NULL COMMENT '标题',
  `p_category_id` int(11) DEFAULT NULL COMMENT '父级分类ID',
  `category_id` int(11) DEFAULT NULL COMMENT '分类ID',
  `content` longtext COMMENT '内容',
  `summary` text COMMENT '摘要',
  `user_id` int(11) DEFAULT NULL COMMENT '用户ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `read_count` int(11) DEFAULT '0' COMMENT '阅读数',
  `like_count` int(11) DEFAULT '0' COMMENT '喜欢数',
  `comment_count` int(11) DEFAULT '0' COMMENT '评论数',
  `collection_count` int(11) DEFAULT '0' COMMENT '收藏数',
  `ask_image` varchar(1000) DEFAULT NULL COMMENT '图片',
  `ask_image_thum` varchar(1000) DEFAULT NULL COMMENT '缩略图',
  `mark` int(11) DEFAULT '0' COMMENT '赏分',
  `best_answer_id` int(11) DEFAULT NULL COMMENT '最佳回复id',
  `user_name` varchar(50) DEFAULT NULL COMMENT '用户名',
  `user_icon` varchar(50) DEFAULT NULL COMMENT '用户小图像',
  `slove_type` int(1) DEFAULT '0' COMMENT '是否已经解决 0未解决 1已经解决',
  `best_answer_user_id` int(11) DEFAULT NULL COMMENT '最佳答案作者',
  `best_answer_user_name` varchar(50) DEFAULT NULL COMMENT '最佳答案作者名称',
  PRIMARY KEY (`ask_id`),
  KEY `idx_topic_id` (`ask_id`),
  KEY `idx_user_id` (`user_id`),
  KEY `idx_category_id` (`p_category_id`,`category_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3387 DEFAULT CHARSET=utf8 COMMENT='问答';

-- 正在导出表  bucuoa.ulewo_ask 的数据：~4 rows (大约)
/*!40000 ALTER TABLE `ulewo_ask` DISABLE KEYS */;
INSERT INTO `ulewo_ask` (`ask_id`, `title`, `p_category_id`, `category_id`, `content`, `summary`, `user_id`, `create_time`, `read_count`, `like_count`, `comment_count`, `collection_count`, `ask_image`, `ask_image_thum`, `mark`, `best_answer_id`, `user_name`, `user_icon`, `slove_type`, `best_answer_user_id`, `best_answer_user_name`) VALUES
	(3383, '第一个问答', NULL, NULL, '<p>第一个问答</p>,', '第一个问答,', 10001, '2016-01-02 15:03:21', 2, 0, 0, 0, '', '', 0, NULL, 'ulewo', 'avatars/10719.jpg', 0, NULL, NULL),
	(3384, '唐僧取经后来怎么了', NULL, NULL, '<p>唐僧取经后来怎么了</p>,', '唐僧取经后来怎么了,', 10720, '2016-02-20 00:09:09', 8, 1, 2, 0, '', '', 0, 5973, 'ddrisme', 'avatars/10720.jpg', 1, 10720, 'ddrisme'),
	(3385, 'hah&nbsp;ni打喷嚏', NULL, NULL, '<p>hah ni打喷嚏</p>,', 'hah ni打喷嚏,', 10000, '2016-04-03 04:31:11', 1, 0, 0, 0, '', '', 0, NULL, '多多洛', 'default.gif', 0, NULL, NULL),
	(3386, '天不下雨，天不刮风，天上有太阳', NULL, NULL, '<p>天不下雨，天不刮风，天上有太阳</p>,', '天不下雨，天不刮风，天上有太阳,', 10576, '2016-04-03 08:42:25', 3, 0, 1, 0, '', '', 0, NULL, 'west', 'defaultsmall.gif', 0, NULL, NULL);
/*!40000 ALTER TABLE `ulewo_ask` ENABLE KEYS */;


-- 导出  表 bucuoa.ulewo_attachment 结构
CREATE TABLE IF NOT EXISTS `ulewo_attachment` (
  `attachment_id` int(11) NOT NULL AUTO_INCREMENT,
  `topic_id` int(11) DEFAULT NULL COMMENT '文章ID',
  `file_name` varchar(200) DEFAULT NULL COMMENT '文件名',
  `file_url` varchar(255) DEFAULT NULL COMMENT '文件地址',
  `download_mark` int(11) DEFAULT '0' COMMENT '下载所需积分',
  `download_count` int(11) DEFAULT '0' COMMENT '下载次数',
  `topic_type` char(1) DEFAULT 'T' COMMENT 'T:论坛 B:博客',
  PRIMARY KEY (`attachment_id`),
  KEY `attachment_id` (`attachment_id`),
  KEY `attachment_topicid` (`topic_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 正在导出表  bucuoa.ulewo_attachment 的数据：~0 rows (大约)
/*!40000 ALTER TABLE `ulewo_attachment` DISABLE KEYS */;
/*!40000 ALTER TABLE `ulewo_attachment` ENABLE KEYS */;


-- 导出  表 bucuoa.ulewo_attachment_download 结构
CREATE TABLE IF NOT EXISTS `ulewo_attachment_download` (
  `attachment_id` int(11) NOT NULL DEFAULT '0' COMMENT '附件ID',
  `user_id` int(11) NOT NULL DEFAULT '0' COMMENT '用户ID',
  `user_name` varchar(50) DEFAULT NULL COMMENT '用户名',
  `user_icon` varchar(50) DEFAULT NULL COMMENT '用户小图像',
  PRIMARY KEY (`attachment_id`,`user_id`),
  KEY `attachement_download_id` (`attachment_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 正在导出表  bucuoa.ulewo_attachment_download 的数据：~0 rows (大约)
/*!40000 ALTER TABLE `ulewo_attachment_download` DISABLE KEYS */;
/*!40000 ALTER TABLE `ulewo_attachment_download` ENABLE KEYS */;


-- 导出  表 bucuoa.ulewo_blog 结构
CREATE TABLE IF NOT EXISTS `ulewo_blog` (
  `blog_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL COMMENT '用户ID',
  `category_id` int(11) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `summary` text,
  `content` longtext,
  `read_count` int(11) DEFAULT '0',
  `create_time` datetime DEFAULT NULL,
  `key_word` varchar(255) DEFAULT NULL,
  `allow_comment` int(2) DEFAULT NULL,
  `blog_image_thum` varchar(3000) DEFAULT NULL COMMENT '缩略图',
  `blog_image` varchar(3000) DEFAULT NULL COMMENT '图片',
  `comment_count` int(11) DEFAULT '0' COMMENT '评论数',
  `like_count` int(11) DEFAULT '0' COMMENT '喜欢数',
  `collection_count` int(11) DEFAULT '0' COMMENT '收藏数',
  `user_name` varchar(50) DEFAULT NULL COMMENT '用户名',
  `user_icon` varchar(50) DEFAULT NULL COMMENT '用户小图像',
  `status` int(1) DEFAULT '0' COMMENT '状态 0：草稿 1：已经发布',
  PRIMARY KEY (`blog_id`),
  KEY `blog_index_id` (`blog_id`),
  KEY `blog_index_userid` (`user_id`),
  KEY `blog_index_categoryid` (`category_id`),
  KEY `blog_index_userid_categoryid` (`user_id`,`category_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- 正在导出表  bucuoa.ulewo_blog 的数据：~3 rows (大约)
/*!40000 ALTER TABLE `ulewo_blog` DISABLE KEYS */;
INSERT INTO `ulewo_blog` (`blog_id`, `user_id`, `category_id`, `title`, `summary`, `content`, `read_count`, `create_time`, `key_word`, `allow_comment`, `blog_image_thum`, `blog_image`, `comment_count`, `like_count`, `collection_count`, `user_name`, `user_icon`, `status`) VALUES
	(1, 1000000, 0, 'test博客', 'test博客', '<p>test博客</p>', 10, '2016-08-11 06:38:02', NULL, NULL, '', '', 0, 1, 1, 'wujiang', 'avatars/1000000.jpg', 1),
	(2, 1000000, 0, '博客。我是测试虫', '大家好。我是测试虫', '<p>大家好。我是测试虫</p>', 4, '2016-08-16 05:50:07', NULL, NULL, '', '', 5, 0, 0, 'wujiang', 'avatars/1000000.jpg', 1),
	(3, 1000000, 0, '212add hello', '啊啊啊啊', '<p>啊啊啊啊</p>', 13, '2016-08-16 06:25:06', NULL, NULL, '', '', 2, 1, 1, 'wujiang', 'avatars/1000000.jpg', 1);
/*!40000 ALTER TABLE `ulewo_blog` ENABLE KEYS */;


-- 导出  表 bucuoa.ulewo_blog_category 结构
CREATE TABLE IF NOT EXISTS `ulewo_blog_category` (
  `category_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL COMMENT '用户ID',
  `name` varchar(50) DEFAULT NULL,
  `rang` int(2) DEFAULT NULL,
  PRIMARY KEY (`category_id`),
  KEY `blog_categroy_id` (`category_id`),
  KEY `blog_categroy_userid` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=92 DEFAULT CHARSET=utf8;

-- 正在导出表  bucuoa.ulewo_blog_category 的数据：~4 rows (大约)
/*!40000 ALTER TABLE `ulewo_blog_category` DISABLE KEYS */;
INSERT INTO `ulewo_blog_category` (`category_id`, `user_id`, `name`, `rang`) VALUES
	(88, 1, '私人', 1),
	(89, 1, '工作', 2),
	(90, 1, '人生', 1),
	(91, 1, '技术', 2);
/*!40000 ALTER TABLE `ulewo_blog_category` ENABLE KEYS */;


-- 导出  表 bucuoa.ulewo_category 结构
CREATE TABLE IF NOT EXISTS `ulewo_category` (
  `category_id` int(11) NOT NULL AUTO_INCREMENT,
  `pid` int(11) DEFAULT '0' COMMENT '父Id',
  `name` varchar(30) DEFAULT NULL COMMENT '分类名称',
  `description` varchar(500) DEFAULT '分类描述',
  `rang` int(11) DEFAULT NULL COMMENT '分类编号',
  `allow_post` varchar(1) DEFAULT 'Y' COMMENT '允许发帖',
  `show_in_bbs` char(1) DEFAULT 'Y' COMMENT '是否是论坛分类：Y 是 N 不是',
  `show_in_question` char(1) DEFAULT 'Y' COMMENT '是否是问答分类：Y 是 N 不是',
  `show_in_knowledge` char(1) DEFAULT 'Y' COMMENT '是否是知识库分类：Y 是 N 不是',
  `show_in_exam` char(1) DEFAULT 'Y',
  PRIMARY KEY (`category_id`),
  KEY `topic_category_index_id` (`category_id`),
  KEY `idx_show_in_bbs` (`show_in_bbs`),
  KEY `idx_show_in_question` (`show_in_question`),
  KEY `idx_show_in_knowledge` (`show_in_knowledge`)
) ENGINE=InnoDB AUTO_INCREMENT=10048 DEFAULT CHARSET=utf8 COMMENT='系统分类';

-- 正在导出表  bucuoa.ulewo_category 的数据：~41 rows (大约)
/*!40000 ALTER TABLE `ulewo_category` DISABLE KEYS */;
INSERT INTO `ulewo_category` (`category_id`, `pid`, `name`, `description`, `rang`, `allow_post`, `show_in_bbs`, `show_in_question`, `show_in_knowledge`, `show_in_exam`) VALUES
	(10000, 0, 'Java企业应用', 'Java企业级应用，各种JavaEE框架', 2, 'Y', 'Y', NULL, 'Y', 'Y'),
	(10001, 10000, 'Spring', NULL, 1, 'Y', 'Y', NULL, 'Y', 'Y'),
	(10002, 10000, 'SpringMVC', NULL, 2, 'Y', 'Y', NULL, 'Y', 'Y'),
	(10003, 10000, 'Struts', NULL, 3, 'Y', 'Y', NULL, 'Y', 'Y'),
	(10004, 10000, 'MyBatis', NULL, 4, 'Y', 'Y', NULL, 'Y', 'Y'),
	(10010, 0, 'Web前端技术', 'Web前端，做一个会前端的程序猿', 4, 'Y', 'Y', NULL, 'Y', 'Y'),
	(10011, 10010, 'JavaScript', NULL, 1, 'Y', 'Y', NULL, 'Y', 'Y'),
	(10012, 10010, 'HTML5', NULL, 2, 'Y', 'Y', NULL, 'Y', 'Y'),
	(10013, 10010, 'jQuery', NULL, 3, 'Y', 'Y', NULL, 'Y', 'Y'),
	(10014, 10010, 'CSS', NULL, 4, 'Y', 'Y', NULL, 'Y', 'Y'),
	(10015, 0, '编程语言', '各类编程语言，谁是世界上最好的语言，Javaer，PHPer又要撕逼了', 5, 'Y', 'Y', NULL, 'Y', 'Y'),
	(10016, 10015, 'Java', NULL, 1, 'Y', 'Y', NULL, 'Y', 'Y'),
	(10017, 10015, 'PHP', NULL, 2, 'Y', 'Y', NULL, 'Y', 'Y'),
	(10018, 10015, 'C', NULL, 3, 'Y', 'Y', NULL, 'Y', 'Y'),
	(10019, 10015, 'C++', NULL, 4, 'Y', 'Y', NULL, 'Y', 'Y'),
	(10020, 10015, 'Python', NULL, 5, 'Y', 'Y', NULL, 'Y', NULL),
	(10021, 10015, '.net', NULL, 6, 'Y', 'Y', NULL, 'Y', NULL),
	(10022, 10015, 'Ruby', NULL, 7, 'Y', 'Y', NULL, 'Y', NULL),
	(10023, 0, '综合技术', '这里是综合区', 6, 'Y', 'Y', NULL, 'Y', 'Y'),
	(10024, 10023, 'Liunx', NULL, 2, 'Y', 'Y', NULL, 'Y', NULL),
	(10025, 10023, '数据库', NULL, 3, 'Y', 'Y', NULL, 'Y', NULL),
	(10026, 10023, '敏捷开发', NULL, 4, 'Y', 'Y', NULL, 'Y', NULL),
	(10027, 10023, '数据结构', NULL, 5, 'Y', 'Y', NULL, 'Y', NULL),
	(10028, 10023, '软件测试', NULL, 6, 'Y', 'Y', NULL, 'Y', NULL),
	(10029, 10023, '项目管理', NULL, 7, 'Y', 'Y', NULL, 'Y', NULL),
	(10030, 0, '招聘求职', '招聘求职，招聘人才，面试技巧，面试经验', 8, 'Y', 'Y', NULL, 'Y', NULL),
	(10031, 10030, '招聘', NULL, 1, 'Y', 'Y', NULL, 'Y', 'Y'),
	(10032, 10030, '面试分享', NULL, 2, 'Y', 'Y', NULL, 'Y', 'Y'),
	(10033, 0, '海阔天空', '海阔天空，程序猿的那些事儿', 7, 'Y', 'Y', NULL, NULL, NULL),
	(10034, 10033, '八卦', NULL, 1, 'Y', 'Y', NULL, 'Y', 'Y'),
	(10035, 10033, '实力吹牛', NULL, 2, 'Y', 'Y', NULL, 'Y', 'Y'),
	(10036, 10033, '闲聊', NULL, 3, 'Y', 'Y', NULL, 'Y', 'Y'),
	(10037, 10033, '锻炼身体', NULL, 4, 'Y', 'Y', NULL, 'Y', 'Y'),
	(10038, 0, '有乐窝官方', '有乐窝官方', 9, 'Y', 'Y', NULL, NULL, NULL),
	(10039, 10038, '活动', NULL, 1, 'Y', 'Y', NULL, 'Y', 'Y'),
	(10040, 10038, '意见\\建议', NULL, 2, 'Y', 'Y', NULL, 'Y', 'Y'),
	(10043, 0, '实力自学', '自学能力很重要，一起来自学吧', 1, 'Y', 'Y', NULL, 'Y', NULL),
	(10044, 10043, '自学心得', NULL, 1, 'Y', 'Y', NULL, 'Y', 'Y'),
	(10045, 10043, '学习资料', NULL, 2, 'Y', 'Y', NULL, 'Y', 'Y'),
	(10046, 10043, '面试题', NULL, 3, 'Y', 'Y', NULL, 'Y', 'Y'),
	(10047, 10023, 'IT资讯', NULL, 1, NULL, 'Y', NULL, NULL, NULL);
/*!40000 ALTER TABLE `ulewo_category` ENABLE KEYS */;


-- 导出  表 bucuoa.ulewo_collection 结构
CREATE TABLE IF NOT EXISTS `ulewo_collection` (
  `user_id` int(11) NOT NULL DEFAULT '0' COMMENT '用户ID',
  `article_id` int(11) NOT NULL DEFAULT '0',
  `title` varchar(250) DEFAULT NULL,
  `article_type` char(1) NOT NULL DEFAULT '',
  `create_time` datetime DEFAULT NULL,
  `article_user_id` int(11) DEFAULT NULL COMMENT '文章作者ID',
  PRIMARY KEY (`user_id`,`article_id`,`article_type`),
  KEY `collection_index_userid` (`user_id`),
  KEY `collection_index_articleid` (`article_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 正在导出表  bucuoa.ulewo_collection 的数据：~5 rows (大约)
/*!40000 ALTER TABLE `ulewo_collection` DISABLE KEYS */;
INSERT INTO `ulewo_collection` (`user_id`, `article_id`, `title`, `article_type`, `create_time`, `article_user_id`) VALUES
	(1, 2382, 'test1', 'K', '2016-08-06 08:04:52', NULL),
	(1000000, 1, 'test博客', 'B', '2016-08-11 06:50:30', 1000000),
	(1000000, 3, '啊啊啊', 'B', '2016-08-17 06:04:54', 1000000),
	(1000000, 2380, '成为&amp;nbsp;OpenStack&amp;nbsp;基金会黄金会员，对&amp;nbsp;UnitedStack、EasyStack&amp;nbsp;意味着什么？', 'K', '2016-08-19 04:40:00', NULL),
	(1000000, 2384, '大家好。我是测试虫', 'K', '2016-08-16 06:22:17', NULL);
/*!40000 ALTER TABLE `ulewo_collection` ENABLE KEYS */;


-- 导出  表 bucuoa.ulewo_comment 结构
CREATE TABLE IF NOT EXISTS `ulewo_comment` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `pid` int(11) DEFAULT NULL,
  `article_id` int(11) DEFAULT NULL COMMENT '文章ID',
  `content` longtext COMMENT '内容',
  `user_id` int(11) DEFAULT NULL COMMENT '用户ID',
  `create_time` datetime DEFAULT NULL,
  `source_from` char(1) DEFAULT 'P' COMMENT 'P:pc端发布  A:安卓客户端发出',
  `article_type` char(1) DEFAULT NULL COMMENT '文章类型 T:讨论区 B:博客 S:吐槽 K:知识库 A:问答',
  `user_name` varchar(50) DEFAULT NULL COMMENT '用户名',
  `user_icon` varchar(50) DEFAULT NULL COMMENT '用户小图像',
  PRIMARY KEY (`id`),
  KEY `topic_comment_index_id` (`id`),
  KEY `topic_comment_index_pid` (`pid`),
  KEY `topic_comment_index_topicid` (`article_id`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8;

-- 正在导出表  bucuoa.ulewo_comment 的数据：~24 rows (大约)
/*!40000 ALTER TABLE `ulewo_comment` DISABLE KEYS */;
INSERT INTO `ulewo_comment` (`id`, `pid`, `article_id`, `content`, `user_id`, `create_time`, `source_from`, `article_type`, `user_name`, `user_icon`) VALUES
	(1, 0, 2383, '<p>36氪</p><p>36氪</p><p>36氪</p><p>36氪</p><p>36氪</p><p>36氪</p><p>36氪</p><p>36氪</p><p>36氪</p>', 1, '2016-08-06 08:05:39', 'P', 'K', '50986486', 'avatars/1.jpg'),
	(2, 0, 2384, '顶顶顶', 1000000, '2016-08-16 06:19:56', 'P', 'K', 'wujiang', 'avatars/1000000.jpg'),
	(3, 2, 2384, '啊啊啊', 1000000, '2016-08-16 06:21:53', 'P', 'K', 'wujiang', 'avatars/1000000.jpg'),
	(4, 2, 2384, '啊啊啊', 1000000, '2016-08-16 06:21:56', 'P', 'K', 'wujiang', 'avatars/1000000.jpg'),
	(5, 2, 2384, '爸爸[爱你]', 1000000, '2016-08-16 06:22:02', 'P', 'K', 'wujiang', 'avatars/1000000.jpg'),
	(6, 0, 2384, '啊啊啊', 1000000, '2016-08-16 06:22:12', 'P', 'K', 'wujiang', 'avatars/1000000.jpg'),
	(7, 0, 2384, '房山好。。', 1000000, '2016-08-16 06:22:26', 'P', 'K', 'wujiang', 'avatars/1000000.jpg'),
	(8, 2, 2384, '<a href="http://www.bucuoa.com/user/1000000" class="referer" target="_blank">@wujiang</a> &nbsp;啊啊啊', 1000000, '2016-08-16 06:23:15', 'P', 'K', 'wujiang', 'avatars/1000000.jpg'),
	(9, 0, 2, '啊啊啊', 1000000, '2016-08-16 06:24:14', 'P', 'B', 'wujiang', 'avatars/1000000.jpg'),
	(10, 9, 2, '啊啊啊', 1000000, '2016-08-16 06:24:25', 'P', 'B', 'wujiang', 'avatars/1000000.jpg'),
	(11, 9, 2, '啊啊啊啊', 1000000, '2016-08-16 06:24:28', 'P', 'B', 'wujiang', 'avatars/1000000.jpg'),
	(12, 0, 2, '啊斯顿发啊', 1000000, '2016-08-16 06:24:31', 'P', 'B', 'wujiang', 'avatars/1000000.jpg'),
	(13, 0, 2, '阿斯顿发生点啊', 1000000, '2016-08-16 06:24:37', 'P', 'B', 'wujiang', 'avatars/1000000.jpg'),
	(14, 0, 3, '啊啊啊', 1000000, '2016-08-16 06:25:10', 'P', 'B', 'wujiang', 'avatars/1000000.jpg'),
	(15, 0, 3, '啊啊啊', 1000000, '2016-08-16 06:25:12', 'P', 'B', 'wujiang', 'avatars/1000000.jpg'),
	(16, 0, 2384, '阿卜杜', 1000000, '2016-08-16 07:04:37', 'P', 'K', 'wujiang', 'avatars/1000000.jpg'),
	(17, 0, 2384, 'abcdefg', 1000000, '2016-08-23 06:23:50', 'P', 'K', 'wujiang', 'avatars/1000000.jpg'),
	(18, 0, 2381, 'adddd', 1000000, '2016-09-24 20:05:04', 'P', 'K', 'wujiang', 'avatars/1000000.jpg'),
	(19, 0, 2381, 'dddd', 1000000, '2016-09-24 20:05:06', 'P', 'K', 'wujiang', 'avatars/1000000.jpg'),
	(20, 0, 2381, 'ddddd', 1000000, '2016-09-24 20:05:08', 'P', 'K', 'wujiang', 'avatars/1000000.jpg'),
	(21, 0, 2381, 'dddd', 1000000, '2016-09-24 20:05:10', 'P', 'K', 'wujiang', 'avatars/1000000.jpg'),
	(22, 0, 2381, 'asdfasdf', 1000000, '2016-09-24 20:05:11', 'P', 'K', 'wujiang', 'avatars/1000000.jpg'),
	(23, 0, 2381, 'asdfasdf', 1000000, '2016-09-24 20:05:12', 'P', 'K', 'wujiang', 'avatars/1000000.jpg'),
	(24, 0, 2381, 'asdfasdfasdf', 1000000, '2016-09-24 20:05:15', 'P', 'K', 'wujiang', 'avatars/1000000.jpg');
/*!40000 ALTER TABLE `ulewo_comment` ENABLE KEYS */;


-- 导出  表 bucuoa.ulewo_exam 结构
CREATE TABLE IF NOT EXISTS `ulewo_exam` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `exam_title` text,
  `p_category_id` int(11) DEFAULT NULL,
  `category_id` int(11) DEFAULT NULL,
  `choose_type` int(2) DEFAULT NULL COMMENT '1:单选  2:多选',
  `user_id` int(11) DEFAULT NULL COMMENT '用户ID',
  `create_time` datetime DEFAULT NULL,
  `status` int(11) DEFAULT '0' COMMENT '状态 0 未审核  1已审核',
  `analyse` text,
  `user_name` varchar(50) DEFAULT NULL COMMENT '用户名',
  `user_icon` varchar(50) DEFAULT NULL COMMENT '用户小图像',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- 正在导出表  bucuoa.ulewo_exam 的数据：~3 rows (大约)
/*!40000 ALTER TABLE `ulewo_exam` DISABLE KEYS */;
INSERT INTO `ulewo_exam` (`id`, `exam_title`, `p_category_id`, `category_id`, `choose_type`, `user_id`, `create_time`, `status`, `analyse`, `user_name`, `user_icon`) VALUES
	(4, '下面关于数组的说法中，错误的是（）\r', NULL, 10000, 1, 1000000, '2016-09-24 12:29:08', 1, '数组不是对象，是内存结构！', 'wujiang', 'avatars/1000000.jpg'),
	(5, '下列属于关系型数据库的是（）', NULL, 10000, 2, 1000000, '2016-09-24 12:35:36', 1, '', 'wujiang', 'avatars/1000000.jpg'),
	(6, '关于类的描叙正确的是（&nbsp;）\r', NULL, 10000, 1, 1000000, '2016-09-24 13:26:54', 1, '', 'wujiang', 'avatars/1000000.jpg');
/*!40000 ALTER TABLE `ulewo_exam` ENABLE KEYS */;


-- 导出  表 bucuoa.ulewo_exam_detail 结构
CREATE TABLE IF NOT EXISTS `ulewo_exam_detail` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `exam_id` int(11) DEFAULT NULL,
  `answer` varchar(500) DEFAULT NULL COMMENT '答案',
  `is_right_answer` int(1) DEFAULT '0' COMMENT '0:非正确答案 1:正确答案',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8;

-- 正在导出表  bucuoa.ulewo_exam_detail 的数据：~14 rows (大约)
/*!40000 ALTER TABLE `ulewo_exam_detail` DISABLE KEYS */;
INSERT INTO `ulewo_exam_detail` (`id`, `exam_id`, `answer`, `is_right_answer`) VALUES
	(1, 1, 'yes', 1),
	(2, 1, 'no', 0),
	(7, 4, '.在类中声明一个整数数组作为成员变量，如果没有给它赋值，数值元素值为空', 0),
	(8, 4, '数组可以在内存空间连续存储任意一组数据', 0),
	(9, 4, '数组必须先声明，然后才能使用', 0),
	(10, 4, '数组本身是一个对象', 1),
	(11, 5, 'Oracle', 1),
	(12, 5, 'MySql', 1),
	(13, 5, 'IMS', 0),
	(14, 5, 'MongoDB', 0),
	(15, 6, '在类中定义的变量称为类的成员变量，在别的类中可以直接使用', 1),
	(16, 6, '局部变量的作用范围仅仅在定义它的方法内，或者是在定义它的控制流块中', 0),
	(17, 6, '使用别的类的方法仅仅需要引用方法的名字即可', 0),
	(18, 6, '一个类的方法使用该类的另一个方法时可以直接引用方法名', 0);
/*!40000 ALTER TABLE `ulewo_exam_detail` ENABLE KEYS */;


-- 导出  表 bucuoa.ulewo_group 结构
CREATE TABLE IF NOT EXISTS `ulewo_group` (
  `gid` int(11) NOT NULL AUTO_INCREMENT COMMENT '组id',
  `group_name` varchar(100) NOT NULL DEFAULT '' COMMENT '组名称',
  `group_desc` longtext NOT NULL COMMENT '组描述',
  `group_icon` varchar(100) NOT NULL DEFAULT '' COMMENT '群图标',
  `group_banner` varchar(50) DEFAULT NULL COMMENT '群个性图片',
  `join_perm` varchar(2) NOT NULL DEFAULT '' COMMENT '加入权限 Y无需审核 N需要审核',
  `post_perm` varchar(2) NOT NULL DEFAULT '' COMMENT '发帖权限 A任何人可以发帖  M成员可以发帖',
  `group_userid` int(50) DEFAULT NULL,
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `commend_grade` int(11) DEFAULT '0',
  `visitcount` int(11) DEFAULT '0' COMMENT '访问数量',
  `isvalid` varchar(2) NOT NULL DEFAULT 'Y' COMMENT '是否有效 Y有效 N无效',
  `group_notice` mediumtext,
  `p_categroy_id` int(11) DEFAULT NULL,
  `categroy_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`gid`),
  KEY `group_index_gid` (`gid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 正在导出表  bucuoa.ulewo_group 的数据：~0 rows (大约)
/*!40000 ALTER TABLE `ulewo_group` DISABLE KEYS */;
/*!40000 ALTER TABLE `ulewo_group` ENABLE KEYS */;


-- 导出  表 bucuoa.ulewo_group_category 结构
CREATE TABLE IF NOT EXISTS `ulewo_group_category` (
  `group_category_id` int(11) NOT NULL AUTO_INCREMENT,
  `pid` int(11) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `rang` int(11) DEFAULT NULL,
  PRIMARY KEY (`group_category_id`)
) ENGINE=MyISAM AUTO_INCREMENT=34 DEFAULT CHARSET=utf8;

-- 正在导出表  bucuoa.ulewo_group_category 的数据：0 rows
/*!40000 ALTER TABLE `ulewo_group_category` DISABLE KEYS */;
/*!40000 ALTER TABLE `ulewo_group_category` ENABLE KEYS */;


-- 导出  表 bucuoa.ulewo_group_member 结构
CREATE TABLE IF NOT EXISTS `ulewo_group_member` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `gid` int(50) DEFAULT NULL COMMENT '组id',
  `userid` int(50) DEFAULT NULL COMMENT '用户id',
  `grade` int(2) DEFAULT '0' COMMENT '等级',
  `member_type` varchar(2) DEFAULT NULL COMMENT '是否是成员  Y是成员 N不是成员',
  `join_time` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `group_member_index_gid` (`gid`),
  KEY `group_member_index_userid` (`userid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 正在导出表  bucuoa.ulewo_group_member 的数据：~0 rows (大约)
/*!40000 ALTER TABLE `ulewo_group_member` DISABLE KEYS */;
/*!40000 ALTER TABLE `ulewo_group_member` ENABLE KEYS */;


-- 导出  表 bucuoa.ulewo_knowledge 结构
CREATE TABLE IF NOT EXISTS `ulewo_knowledge` (
  `topic_id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(200) DEFAULT NULL,
  `p_category_id` int(11) DEFAULT NULL,
  `category_id` int(11) DEFAULT NULL,
  `content` longtext,
  `summary` text,
  `user_id` int(11) DEFAULT NULL COMMENT '用户ID',
  `create_time` datetime DEFAULT NULL,
  `read_count` int(11) DEFAULT '0' COMMENT '0',
  `topic_image` text,
  `topic_image_thum` text,
  `status` int(1) DEFAULT '1' COMMENT '状态 1：未审核  2 ：已经审核',
  `comment_count` int(11) DEFAULT '0' COMMENT '评论数',
  `like_count` int(11) DEFAULT '0' COMMENT '喜欢数',
  `collection_count` int(11) DEFAULT '0' COMMENT '收藏数',
  `user_name` varchar(50) DEFAULT NULL COMMENT '用户名',
  `user_icon` varchar(50) DEFAULT NULL COMMENT '用户小图像',
  PRIMARY KEY (`topic_id`),
  KEY `idx_topic_id` (`topic_id`),
  KEY `idx_category_id` (`p_category_id`,`category_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2385 DEFAULT CHARSET=utf8;

-- 正在导出表  bucuoa.ulewo_knowledge 的数据：~5 rows (大约)
/*!40000 ALTER TABLE `ulewo_knowledge` DISABLE KEYS */;
INSERT INTO `ulewo_knowledge` (`topic_id`, `title`, `p_category_id`, `category_id`, `content`, `summary`, `user_id`, `create_time`, `read_count`, `topic_image`, `topic_image_thum`, `status`, `comment_count`, `like_count`, `collection_count`, `user_name`, `user_icon`) VALUES
	(2377, '第一个知识库', 10043, 10044, '<div id="OSChina_News_72896" class="Body NewsContent TextContent">\r\n			<p style="margin:0 0 10px 0;"><a style="color:#A00;font-weight:bold;" href="http://www.capitalonline.net.cn/zh-cn/zt/2016/041801/?sn=osc" target="_blank">4月19日，首都在线推荐分享送好礼活动开启啦，赶紧参与吧！&gt;&gt;&gt; »</a>&nbsp;&nbsp;<img src="http://my.oschina.net/img/hot3.png" align="absmiddle" data-bd-imgshare-binded="1"></p>						<p>原生手游市场已是红海，腾讯、网易等寡头独霸天下，H5游戏市场或将成为下一个风口。据笔者所知，很多H5游戏开发团队由于选择引擎不慎导致项目甚至团队夭折。如何选择适合团队和项目的引擎，笔者通过学习和项目实践，总结微薄经验，供大家参考，非技术人员也可以将本篇内容作为引擎选择的重要关注点。</p><p>选择H5游戏引擎的思考维度<br>1、开发语言的支持<br>2、2D、3D、VR的支持<br>3、性能<br>4、引擎的应用广度<br>5、设计理念<br>6、工作流支持力度<br>7、商业化成熟案例<br>8、学习资源与技术支持能力</p><p>首先，我们要知道，当前主流的游戏引擎有哪些。由于H5引擎有很多，笔者在这里进行了精心的筛选，过滤掉不支持webGL的引擎，以及封装了第三方渲染内核的JS框架，和不能直接在浏览器中运行的JS引擎。</p><p>为什么要过滤掉这几种呢，首先，没有自己的渲染内核，仅仅是基于第三方的内核作的API封装，笔者很担心可持续的性能优化和维护能力。另外，不能在浏览器中直接运行的JS引擎，将限制H5游戏跨平台的交互能力。还有，&nbsp;笔者非常看好webGL模式，认为webGL模式才是H5引擎的未来。原因有几点：</p><blockquote><p>第一、性能，webGL模式远超Canvas数倍。DOM模式就不适合用于真正的游戏开发，更不用提。<br>第二、3D方向，webGL模式理论上可以制作2D和3D游戏，Canvas和DOM模式下只能制作2D游戏。<br>第三、普及率，webGL的普及率已经非常高了，尤其是支持webGL的腾讯TBS-Blink内核已在4月19日发布，并逐步在微信、QQ空间、QQ浏览器、手机QQ等APP中采用静默安装方式全面升级。这个普及率在国内带来的影响，;你懂的……</p></blockquote><p><strong>1、选择H5游戏开发语言</strong></p><p>拥有广泛开发者的H5游戏开发语言共有三种，分别为Flash&nbsp;AS3、TypeScript、JavaScript。其中Flash&nbsp;AS3、TypeScript均属于面向对象的高级脚本语言，通过编译器将原项目代码编译成JavaScript代码文件运行于浏览器之中，面向对象的高级语言无论是项目开发管理，还是项目开发的工具环境的成熟度都明显优于JavaScript脚本语言，尤其是中大型项目方面，AS3等高级语言的效率会更高。</p><p><img src="http://www.gamelook.com.cn/wp-content/uploads/2016/04/s115.jpg" alt="s1" width="550" data-bd-imgshare-binded="1"></p><p>从上图看出，支持JavaScript语言的引擎更多，由于AS3语言的编译器为Layabox引擎推出的，因此采用AS3作为开发语言的仅有Layabox引擎。笔者建议在开发中大型游戏项目的时候，采用TypeScript或者是Flash&nbsp;AS3语言进行开发。如果是小型游戏，任选其一即可。</p><p><strong>2、引擎的未来延续能力</strong></p><p>选择一个引擎，并不是简单的认为，满足眼前够用就可以了，引擎的未来延续能力也是很重要的，这个项目是2D，下个项目想开发3D，如果引擎不支持怎么办？去换个引擎？如果VR的机会来了，再想发布VR版本，这个引擎不支持，需要重新开发吗？等等问题，作为开发者尽可能要提前想好。</p><p><img src="http://www.gamelook.com.cn/wp-content/uploads/2016/04/s29.jpg" alt="s2" width="550" data-bd-imgshare-binded="1"></p><p>通过上图，可以看出，即便是在支持webGL的H5引擎里，有只面向2D游戏的，也有只面向3D游戏的，同时支持2D、3D、VR的H5引擎，从目前看只有Layabox与Egret引擎。</p><p><strong>3、性能是核心需求</strong></p><p>性能是H5游戏面临的核心门槛，也是很多H5游戏不被专业玩家认可的重要原因之一。游戏卡顿，不流畅，这样的产品体验很难在激烈竞争中生存下来。</p><p>H5产业早期的普及阶段即将过去，游戏品质在迅速提高，品质中包括精细的美术和炫酷的动画等。在复杂的游戏项目面前，上述种种元素，其流畅体验度对游戏引擎是极大的考验。所以选择性能优秀的引擎是保证品质的最重要基础，一定要谨慎。</p><p>在游戏项目研发开始时，一定要先对复杂的模块做DEMO测试，特别是带背景滚动的游戏。比如横屏卷轴游戏，对帧数稳定性要求极高，如果满足不了性能上的需求，可能会带来眩晕、眼花、疲倦等不良体验。</p><p><img src="http://www.gamelook.com.cn/wp-content/uploads/2016/04/s38.jpg" alt="s3" width="550" data-bd-imgshare-binded="1"></p><p>在webGL的2D渲染性能方面，pixi.js的性能处于当前的顶级。在webGL的3D渲染性能方面，Three.js非常优秀。在runtime方面Cocos2d-js也有着原生级的表现，经过对比，笔者认为Layabox性能的综合实力最强，在各个渲染领域都保持在HTML5引擎的顶级水平。当然，上图仅作为参考，对于任何号称某个引擎性能最牛的论调，一定要亲自进行性能DEMO的测试对比，而不要轻易采信。</p><p>由于性能是游戏最核心的需求，笔者这里再多说一句，大型项目在系统复杂度、UI复杂度、动画显示数量和种类等方面与小型游戏项目完全不在一个量级。会涉及到比小游戏更复杂的性能优化、内存管理、资源管理等需求，如果选择了小马拉大车的低性能引擎，项目夭折可能性非常大，除非最后项目开发者花大量时间自己优化引擎。所以性能差一点，就会导致结果差很多，不可主观想象。</p><p><strong>4、与引擎的应用广度</strong></p><p>随着H5游戏品质提升，在其他领域也具备一定的竞争力和价值，一次开发可发行各个领域版本，已成为日渐明确的需求，这里面包括发行原生APP手游和PC的flash页游需求，大统一的引擎时代即将来领。目前最火爆的H5游戏《传奇世界H5》据说有40%的收入来自PC网页。</p><p>发布PC页游时，由于PC浏览器目前对HTML5兼容性不足70%，用户损耗很大，页游联运平台可能会拒绝或放量很少，只有采用能同时发布Flash版本的引擎，才能解决这个问题。</p><p><img src="http://www.gamelook.com.cn/wp-content/uploads/2016/04/s43.jpg" alt="s4" width="550" data-bd-imgshare-binded="1"></p><p><strong>5、设计理念与定位</strong></p><p>设计理念是个比较大的话题，也是个很重要的引擎选择因素，比如引擎是要专注移动端，还是要面向全平台多端游戏市场。是注重性能，还是注重工具链等等。深入了解不同引擎的理念与定位，才能更好的与游戏产品进行结合。</p><p><img src="http://www.gamelook.com.cn/wp-content/uploads/2016/04/s52.jpg" alt="s5" width="550" data-bd-imgshare-binded="1"></p><p>上图内容仅作参考，详情建议去各引擎官网深入了解。</p><p><strong>6、工作流支持力度</strong></p><p>作为商业级开源引擎，工具链的提供与支持也是一种选择考量要素，比如UI编辑器、粒子编辑器、骨骼编辑器、场景编辑器等等，如果引擎方直接提供或支持，那么将会较大的提升研发效率。<br>本文中提到的7个引擎，只有Egret、Layabox、Cocos2d-JS这三个引擎，在工具链方面提供足够全面的支撑。</p><p><strong>7、是否有成熟的商业案例</strong></p><p>怎么证明引擎是成熟的？一定要有成熟的商业案例，一般引擎的官网上都会有游戏案例介绍，我们在选择引擎之前要进行深入体验，包括：商业案例的数量、商业案例的种类、稳定性、流畅度（要在低端机里体验）、项目复杂度、项目相似度等。如果有一些大型成功案例背书会相对安全可靠些。<br>从目前的行业案例来看，Layabox引擎的MMORPG《醉西游》、重度动作游戏《猎刃2》、大型模拟经营游戏《梦幻家园》等无疑是H5引擎技术的最高水准代表作。但是从卡牌、挂机等类型的付费游戏总体数量来看，Egret引擎明显占优，充分说明该引擎的市场宣传力度更胜一筹。</p><p><strong>8、学习资源与技术支持能力</strong></p><p>能提供什么样的学习资源，以及技术支持，对于开发者也是重要因素，如果你是技术大牛，只想使用轻量的第三方渲染内核。那么2D游戏，pixi.js无疑是首选。3D游戏，笔者推荐Three.js。但是这两种引擎的学习资料都比较稀少。笔者认为学习资料的完善，以及在学习过程中的技术支持力度，将会很大的帮助你解决引擎使用中的问题。所以，API完善，DEMO完善，文档完善，社区的响应速度，交流氛围，以及QQ技术支持等，都可以作为你选择引擎的因素考量之一。</p><p><strong>9、页游移植产品的引擎选择</strong></p><p>目前像《醉西游》等优秀H5产品是Flash页游或手游移植而成，移植类的产品在选用引擎时要注意，代码是否可以直接移植？如果可以，那将节省大量的开发成本。比如Flash&nbsp;AS3开发的2D或3D页游或手游，可以把逻辑与算法代码直接拷贝移植到Layabox引擎项目中，开发速度提高数倍。</p><p>写在最后：最后提醒一下，千万不要相信某些引擎的单方宣传，一定要花一点时间去研究实践，亲自制作DEMO去作一作对比，动手体验到的才是真理。</p><p><strong>针对DEMO测试笔者有几点建议：</strong></p><p>&nbsp; &nbsp; &nbsp; &nbsp;&nbsp;1、采用一个复杂的UI，特别是复杂列表，比如说没有分页的背包列表，背包里放上不同的道具图片，测试滑动时的流畅度，这块比较考验性能，元素越复杂，数据越多，尤其能对比出来性能上的差异。</p><p>&nbsp; &nbsp; &nbsp; &nbsp;&nbsp;2、包含最复杂战斗部分，不要写战斗逻辑代码，不然会花的时间太长，只需要把战斗相关的动画和复杂的元素放在场景中模拟即可，因为H5游戏性能瓶颈通常在于画面的显示。</p><p>&nbsp; &nbsp; &nbsp; &nbsp;&nbsp;3、 测试主要目的是看项目在引擎中性能，这是最至关重要的，所以，硬件上，我们要选择低端安卓手机（比如红米）进行测试。软件环境建议使用微信环境测试，首先，因为微信公众号是H5的主要渠道之一，其次，微信当前的H5性能低于chrome浏览器，在恶劣的环境下更能测试引擎的优劣。</p><p>文章来源：<a target="_blank" href="http://www.gamelook.com.cn/2016/04/251421">游戏大观</a><br></p>\r\n		</div>', '第一个知识库', 10001, '2016-01-02 15:03:38', 15, '', '', 1, 0, 0, 0, 'ulewo', 'avatars/10719.jpg'),
	(2378, 'dierge&nbsp;知识库', 10043, 10044, '<p>多大的</p>', '多大的', 10720, '2016-02-20 00:39:42', 8, '', '', 1, 6, 0, 0, 'ddrisme', 'avatars/10720.jpg'),
	(2379, 'SimpleBlog&nbsp;1.2.0&nbsp;发布，基于最新&nbsp;Django&nbsp;开发的博客', 10043, 10044, '<p>SimpleBlog 1.2.0 发布了，基于Django1.9.5开发的简易博客系统1.2.0发布了。</p><p>更新说明：</p><p>升级到最新版本的Django1.9.5</p><p>部署平台迁移至PythonAnywhere</p><p>全文搜索升级</p><p>各种配置、模板、url、views等升级</p><p>主分支基于python3.4.3，对于python2的支持暂时只在xadmin用到时切换</p><p>相应的教程博客也更新和升级了</p><p>详情：使用Django1.9开发博客教程 - 目录汇总贴</p><p><br/></p>', 'SimpleBlog 1.2.0 发布了，基于Django1.9.5开发的简易博客系统1.2.0发布了。更新说明：升级到最新版本的Django1.9.5部署平台迁移至PythonAnywhere全文搜索升级各种配置、模板、url、views等升级主分支基于python3.4.3，对于python2的支持暂时只在xadmin用到时切换相应的教程博客也更新和升级了详情：使用Django1.9开发博客教程 - 目录汇总贴', 10573, '2016-04-26 06:21:08', 7, '', '', 1, 0, 0, 0, 'wujiang', 'defaultsmall.gif'),
	(2380, '成为&nbsp;OpenStack&nbsp;基金会黄金会员，对&nbsp;UnitedStack、EasyStack&nbsp;意味着什么？', 10043, 10045, '<p><br/></p><p>在奥斯汀举办的 OpenStack 峰会上，UnitedStack、EasyStack成为 OpenStack 新晋黄金会员。其他的黄金会员包括，思科、戴尔、DreamHost、EMC、爱立信、富士通、日立、华为、inwinStack 等国内外知名 IT 厂商。</p><p>OpenStack 基金会是一个开放的组织，由各司赞助会费，管理 OpenStack 项目，帮助推广 OpenStack 的开发、发行和应用。OpenStack 基金会只允许最多 8 家白金会员资格和 24 家黄金会员资格。在创立初期，也有数家初创公司曾经是黄金会员，例如 CloudScaling, Piston Cloud Computing，Nebula 等。但近年来，随着这些初创公司被收购和倒闭，基金会黄金会员逐渐被大公司把持，业界渐渐开始出现了一些不满的声音，认为 OpenStack 已经是大厂商的游戏。Mirantis 虽然是初创公司，但从影响力和融资规模看，他无疑已经属于 OpenStack 大厂商。</p><p>而ZstackCEO 张鑫认为，这个时候基金会接纳两家来自中国的初创公司成为黄金会员，一方面是对这两家公司贡献的肯定，另一方面也是对业界质疑的一种回应。</p><p>OpenStack 诞生 6年 以来，已经成为云计算的事实标准，但其发展并非一帆风顺，例如创始成员 Nebula 在融资 4000 万美元后倒闭……都对让业界对 OpenStack 的市场规模是否支撑起大玩家和初创公司产生了一些质疑。</p><p>张鑫告诉 36 氪，这个时候有两家来自中国的初创企业成为黄金会员，无疑向世界证明了 OpenStack 在中国这样巨大的、新兴的市场是充满活力的，为从业者和用户都打了一剂强心针。</p><p>当 然在加入 OpenStack 基金会后，对于这两家初创公司公司，在资源有限的的情况下，如何平衡社区和业务确实是一个非常难以决断的两难的问题。但从国内 OpenStack 创业公司对社区的投入来看拥抱社区、贡献社区、紧跟社区渐渐成为主流。虽然每年上百万的会费对于初创公司来说并不算小，但黄金会员的头衔无疑是最好的背 书，会在未来的商务中取得更加有利的位置。</p><p>一位不愿透露姓名的某公司大牛则认为，目前还不能够评价能够带来什么价值，可能暂时还是要看最终 能不能够得到投资方和市场的认可。意义当然是积极的，认可开源的价值，但我们也应看到 Nebule 曾经的 OpenStack 发起者最后是如何黯然退出 openstack 基金会的。</p><p>从某投资机构的态度来看，对于两家成为 OpenStack 基金会黄金会员，不会再资本层面得到更大的利好。<br/></p><p>而对于中国开源来说，此举无疑是对中国公司于开源贡献的肯定，在 M 版本的贡献中可以看到优铭云（UMCloud）、象云、休伦科技这些初创公司的名字。</p><p>长 久以来中国公司在世界开源运动中始终以索取者的姿态出现。目前 OpenStack 黄金会员的中国公司，除了老牌公司华为和这两家新近创业公司外，无一家中国互联网公司。而中国的互联网公司使用 OpenStack 搭建公有云以及内部使用的不在少数，其中不乏一些大家耳熟能详的巨头。</p><p>反观美国的企业，Netflix 的微服务开源框架在世界范围内取得了巨大认可，Twitter、Facebook、Google、Ebay、Linkedin 等互联网公司的开源项目更早以成为众多互联网公司基础架构的基石。这些公司不遗余力的推动开源并没有损害自身的业务或让竞争对手强大，反而在世界范围内建 立了自己的技术领导地位。在开源方面，印度、俄罗斯、日本都走在了中国前面前面。</p><p><br/></p><p>文章来源：36氪</p><p><br/></p>', '在奥斯汀举办的 OpenStack 峰会上，UnitedStack、EasyStack成为 OpenStack 新晋黄金会员。其他的黄金会员包括，思科、戴尔、DreamHost、EMC、爱立信、富士通、日立、华为、inwinStack 等国内外知名 IT 厂商。OpenStack 基金会是一个开放的组织，由各司赞助会费，管理 OpenStack 项目，帮助推广 OpenStack 的开发、发行和应用。OpenStack 基金会只允许最多 8 家白金会员资格和 24 家黄金会员资格。在创立初期，也有数家初创公司曾经是黄金会员，例如 CloudScaling, Piston Cloud Compu......', 10573, '2016-04-28 02:23:17', 5, '', '', 1, 1, 2, 2, 'wujiang', 'defaultsmall.gif'),
	(2381, '安全科普：URL&nbsp;Hacking&nbsp;&ndash;&nbsp;前端URL猥琐流思路', 10000, 10002, '<p><br/></p><p style="margin: 0px 0px 10px; padding: 0px; font-size: 18px; text-align: center; color: rgb(255, 0, 0); font-family: &#39;Century Gothic&#39;, &#39;Microsoft YaHei&#39;, Verdana, Tahoma, &#39;Lucida Grande&#39;, Arial, sans-serif; line-height: 27.9841px; orphans: 2; white-space: normal; widows: 2; background-color: rgb(255, 255, 255);">0x00 目录<br/></p><p>0x01 链接的构成<br/>0x02 浏览器算如何对url进行解析的<br/>0x03 链接真的只能是这样固定的格式么？<br/>0x04 链接真的是你看到的那样么？<br/></p><p></p><p>0x01 链接的构成<br/></p><p>链接真的只能固定成我们常用的格式么？</p><p>不知道有多少人思考过这个问题！我们经常输入的格式一般都是www.xxxx.com！</p><p>或者再加上协议名 http https 端口以及路径什么的 或者再加上账号密码！如下图： ￼&nbsp;</p><p>第一部分：协议名(以单个冒号结束)<br/>第二部分：用户信息 也就是账号密码！(登陆ftp时常用)<br/>第三部分：主机名(也就是域名)<br/>第四部分：端口<br/>第五部分：查询，这里有个bug。。。&nbsp;&nbsp;应该是?号后的内容才是查询！ <br/>第六部分：片段ID(是不会发送到服务器的！)<br/></p><p>0x02 浏览器是如何对url进行解析的<br/></p><p>我们都知道我们访问一个网站是带有协议的比如http ftp https 等等！</p><p>首先浏览器会提取我们链接中的协议名，它是如何提取的呢？</p><p>（以下为copy web之困上的内容 他写的比较详细！）</p><p>1.提取协议名：<br/></p><p>他会查找第一个:号在哪，如何找到了 那么:号左边的便是协议名！如果获得的协议名中出现了不该有的字符，那么认为这可能就是个相对的url 获得的并不是协议名！</p><p>2.去除层级url标记符：<br/></p><p>字符串//应该算跟在协议名后面的 如果发现有该字符 则会跳过该字符 如果没有找到便不管了！所以 http:baidu.com<br/>也是可以访问的！ 浏览器中还可以用反斜杠来代替正斜杆\\\\代替//firefox除外！</p><p>3.获取授权信息部分：<br/></p><p>依次扫描url，如果这三个符号中 哪个先出现便以哪个为准来截取</p><p>/(正斜杠)<br/>?(问号)<br/>#(井号)<br/></p><p>从url里提取出来的信息，就算授权部分信息！</p><p>除了IE跟safari其他浏览器还接受 ;(分号)也算授权信息部分中可接受的分隔符！</p><p>(1)定位登陆信息，如果有的话：</p><p>授权部分信息提取出来后，在截取出来的信息里再来查找 @ 如果找到了 那么他前面的部分便是登陆信息！登陆信息再查找 : (冒号) 冒号前面的便是账号<br/>后面便是密码！</p><p>(2)提取目标地址</p><p>授权信息部分剩下的便是目标地址了 第一个冒号分开的就算主机名跟端口！用方括号括起来的就是ipv6地址，这也是个特例！</p><p>结合以上信息 我们分析下以下链接：</p><p>ftp://admin:admin@192.168.1.100:21<br/></p><p>这样的链接我经常用来登陆ftp！这样便会以admin的身份 密码为:admin</p><p>ftp协议去登陆主机192.168.1.100，端口号是21端口!</p><p>4.确定路径(如果的确存在)<br/></p><p>如果授权部分的结尾跟着一个正斜杆,某些场景里，跟着一个反斜杠或者分号，就像之前提到的，依次扫描下一个? #<br/>或字符串结尾符，那个先出现便以哪个为准！截取出来的部分就是路径信息！最后根据unix路径语义进行规范化整理！</p><p>5.提取查询字符串(如果的确存在)<br/></p><p>如果在上一条解析里，后面跟着的是一个问号，便继续扫描下一个 # 或到字符串结尾，哪个先出现便以哪个为准！中间的部分便是查询字符串。</p><p>6.提取片段ID<br/></p><p>如果成功解析完上一条信息，它最后还跟着#号 那么从这个符号到字符串的结尾便算片段ID了，片段ID是不会发送到服务器的！一般用来跳到A标签的锚链接<br/>或者用来js的 location.hash 取值 等等!</p><p>如果大家去年跟着wooyun的基友们一块玩烂了基础认证钓鱼的话那么应该能回想起来！当时很多网站在插入图片的地方都判断了后缀名是不是图片的后缀名jpg<br/>gif等等！但是hook不是gif 什么结尾的！当时的方法便是在hook后面加上#.jpg！这样便可以成功的来钓鱼了！原理也是一样的！</p><p>下面我们拿几个例子来解析一下：</p><p>例子1：<br/></p><p>http://xss1.com&amp;action=test@www.baidu.com<br/></p><p>这样一个链接在普通用户看来 是会认为访问xss1.com的！</p><p>但是实际上是去往www.baidu.com 的！为什么呢？结合以上的知识我们分析一下！</p><p>首先 协议名提取出来了 然后获得授权部分信息，? / # 都未出现 浏览器便无法获得一个字符串来获得主机地址！我们再往后看@符 @符前面的便认为是登陆信息<br/>并不会当做主机名来解析！所以现在xss1.com&amp;action=test 已经被当做登陆信息了<br/>现在唯一的主机名便只有www.baidu.com了！</p><p>而xss1.com&amp;action=test在我们访问网站的时候 被当做了登陆了信息去访问www.baidu.com了！</p><p>例子2：<br/></p><p>http://xss1.com\\@www.baidu.com<br/></p><p>首先看下这个链接在chrome中的样子：</p><p></p><p>很明显的看到 这样一个链接在chrome中是会去访问xss1.com的！</p><p>现在我们来看下在firefox下的样子：</p><p></p><p>会提示我们是否要用账号为：xss1.com\\的信息去访问www.baidu.com!</p><p>这是为什么呢？浏览器差异 我们在之前也说了！</p><p>因为在除了firefox外，其他的浏览器都会把(反斜杠当做正斜杠来解析！)</p><p>而正斜杠的出现就代表授权信息部分结束了！因为提取授权部分信息是用\\ ? #</p><p>所以授权信息部分结束 那么前面的便当成了主机名！</p><p>而firefox是不会把\\当成正斜杠的 而@符号前面的 便算登陆信息 后面的就是主机名！所以当用firefox去访问这个链接时 才出现了<br/>上图中的提示！</p><p>例子3：<br/></p><p>http://xss1.com;.baidu.com/<br/></p><p>由于机器没有IE 就不上图了吧！</p><p>微软浏览器允许主机名称中出现 ; (分号)并成功的解析到了这个地址！当然还需要baidu.com提前做了这样的域名解析设置！</p><p>大多数其他浏览器会自动的把url纠正成http://xss1.com/;.baidu.com/</p><p>然后用户就访问到了xss1.com(safari除外，它会认为这个语法错误)</p><p>0x03 链接真的只能是这样固定的格式么？<br/></p><p>不知道有多少人想过这个问题，链接真的只能是这样么！</p><p>通过上面的介绍后，相信大家应该会说No了！</p><p>我记得之前有篇文章讲，xss加载钩子的时候http://做黑名单内！于是那位兄弟便拆分了http://</p><p>var i=&#39;http&#39;;<br/>var b=&#39;://&#39;;<br/></p><p>这样也是一种办法 但是我们有没有更好的办法呢？ 答案肯定是有的 //www.baidu.com 也是可以被加载的！</p><p>(当前网页的协议是什么 加载这个钩子便用什么协议来加载！ 如在https协议的网页中 这样加载钩子 那么默认就是https去加载钩子了！)</p><p>到了这里，我们不得不思考 这样能正常的打开一个网页 我们还有什么方法来加载网页？这时候我们可以fuzz一下！</p><p>如下图：</p><p></p><p>可以看到//后面我们还能输入tab，换行，/ @ \\等等！那我们来测试一下！构造如下链接去访问一下！</p><p>\\\\/www.baidu.com<br/>\\\\@www.baidu.com<br/>\\\\/@www.baidu.com<br/>\\\\\\\\\\\\\\www.baidu.com<br/>///////www.baidu.com<br/></p><p>等等全部能正常的访问到百度!大家可以自己试一下！最好的话写在a 标签 或者 img script里把！这样更贴近我们平常所遇到的环境！</p><p>￼</p><p>既然我们在文章的标题提到了猥琐 这样够猥琐？No 还不够！这样我们的连接始终还是带着一定的特征！</p><p>www .com .net 什么的特征还在，既然说到猥琐 我们就要更加猥琐！比如下面这样的一串字符串！</p><p>ⅅʳºℙˢ&nbsp;&nbsp;--&gt;&nbsp;&nbsp;drops <br/>ʷººʸⓊⁿ&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; —&gt;&nbsp;&nbsp;wooyun<br/>Ⓞʳℊ&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; —&gt;&nbsp;&nbsp;org<br/></p><p>最后拼凑 ：</p><p>ⅅʳºℙˢ.ʷººʸⓊⁿ.ºʳℊ<br/></p><p>变成这样也是能够访问的 大家可以试试！</p><p>那么这样一段字符串是如何得来的呢？</p><p>我们可以通过http:/xsser.me/hf.html来fuzz!</p><p>在fuzz之前先给科普一下：</p><p>针对域名的编码：Punycode</p><p>经过Punycode编码后的域名是会被DNS服务器所识别的！</p><p>就拿中文域名来说，因为操作系统的核心都是英文组成，DNS服务器的解析也是由英文代码交换，所以DNS服务器上并不支持直接的中文域名解析。<br/>所有中文域名的解析都需要转成punycode码，然后由DNS解析punycode码。最后我们成功的访问到了我们要去网站！只不过今天我们这里punycode编码的解析过程并不是由dns服务器来解析的<br/>而是在浏览器访问时就给解码回来！</p><p>在drops中瞌睡龙的文章也提到过！</p><p>http://drops.wooyun.org/papers/146</p><p>说了这么多，开始把！(也顺便讲一下这个玩意应该怎么用)</p><p>首先我们算要测试url 所以要先把 Callback 中的 x.protocol 改成hostname！</p><p>然后再把hostname等于的值也改掉，改成我们要测试的主机名！(别带上协议名)</p><p>比如drops.wooyun.org</p><p>然后再在exp里把A标签的链接改成带有协议名的主机名！(不带的话不能访问)</p><p>都设置好 如下图：</p><p></p><p>下面的小参数可以使用默认的！参数都设置好了，现在我们要标识 我们要测试哪个字符，用:{chr} 代替该字符即可！</p><p></p><p>好，现在设置好后点击Fuzzing 枪打出头鸟 我们就先测d吧！</p><p>可以看到右边的框里出现了一段数字，这段数字是ASCii码每个字符以逗号分割！</p><p>我们可以使用工具把ASCii码给转换回来，不过我比较喜欢chrome 方便！</p><p>现在我们复制他们！然后丢chrome里把他们给还原回来！打开控制台(F12)</p><p></p><p>输入String.fromCharCode(ASCII码) 回车便出来了！</p><p>好经过测试我们得出第一个字符 d 可以使用</p><p>DdⅅⅆⅮⅾⒹⓓＤｄ<br/></p><p>来代替！</p><p>这里我就不一一的fuzz给大家看了！我们贴出最后经过fuzz后的字符串吧！</p><p>http://ⅅʳºℙˢ.ʷººʸⓊⁿ.ºʳℊ<br/></p><p>大家可以复制 然后访问一下！依然是能够访问的到的！</p><p>但是这里也局限于需要一个可以解析的中间件才能访问！</p><p>如果curl的话就不行了！</p><p></p><p>为什么呢？很简单因为没解析 curl他不会去解析这个字符串！</p><p>而浏览器为什么能够正常访问 算因为他会对我们编码后的值进行解析再访问！</p><p>所以这点也算需要知道的！</p><p>可是这种情况我们在哪能用到呢？我们往下看！</p><p>如果在插入钩子的时候或其他什么的时候，对方算基于黑名单过滤的www .com .org什么的,那么便可以用这种方式去绕过！</p><p>这里的思路大家就去扩散下 有什么更猥琐的思路求交流！</p><p>再来个例子吧！</p><p>首先拿一个被腾讯认为是危险网站的红X站</p><p></p><p>可以看到这个链接发出来是会被当做危险网站的！</p><p>现在我们对其中的一个字符fuzz!为什么是一个字符？</p><p>(因为你fuzz的字符多了 会被当成符号 让腾讯认为这不是一个链接！然后就不 能一点就会打开网页了 比如这样。。。)</p><p></p><p>可以看到这样带的符号多了 让腾讯这不是一个链接 就不会生成个超链接了！</p><p>所以我们一般只fuzz几个字符便好了！</p><p>说干就干，我们来开始测试吧！</p><p>原链接：http://laohujijiqiao8.com</p><p>还是用 http://xsser.me/hf.html 来fuzz</p><p></p><p>经过fuzz 测试出来 http://laohujijiqiao8.com 的o 可以用以下的字符来代替！ ￼</p><p>O o º ℴ Ⓞ ⓞ Ｏ ｏ<br/></p><p>现在我们来测试一下！</p><p>http://laohujijiqiaº8.com<br/></p><p>发出去 看还带没带危险网站的标识！上图： ￼</p><p></p><p>现在已经没有标识这是个危险网站的 并且还能够正常打开！是不是已经达到我们的目的了呢？</p><p>之前用这种方式把一个蓝色标示的网站弄成显示为腾讯官网！链接如下：</p><p>http://www.qq.com@xss1.com#<br/></p><p>(ps:以前没加#号时 还是蓝色链接 但是加了#号就显示为腾讯的官网了！)</p><p>因为前面的链接：www.qq.com 发送出去是会显示为腾讯官方网站的！但是现在好像不行了！</p><p>0x04 链接真的是你看到的那样么？<br/></p><p>有人在社区里发了这么个帖子：百度URL跳转 绕过腾讯红XX</p><p>可是我们真的需要要有url跳转漏洞才能跳转么？</p><p>No 任何网站都可以！如下：</p><p>http://www.baidu.com@qq.com<br/></p><p>把这段地址填入浏览器中 访问会发现去了 www.qq.com了 而并不是平常大家所认为的www.baidu.com<br/>这是为什么，我们可以看看此篇文章的开头！</p><p>http:// 后面可以算userinfo 也就算用户信息 账号密码什么的！</p><p>结束是以单个@号结束！ 所以我们这段链接为什么去qq.com 而不是去baidu.com 算因为一个@符 让浏览器认为www.baidu.com<br/>算一段用户信息 而后面的才算主机名 他要去访问的地址！</p><p>所以我们有时候伪装找不到跳转漏洞也可以如此实现！</p><p>然而在chrome 跟firefox下 还可以这么写:</p><p>http:www.baidu.com@qq.com<br/></p><p>协议名没有// 也会被认为是http://</p><p>没看过web之困或者之前没接触过data uri的基友们！可能看了上面这个小例子就会很惊叹了 原来还可以这样！</p><p>在web之困中还讲了其实url地址是可以用进制来代替的！只不过算把ip地址给转换成进制来访问！</p><p>十进制 —||||||&gt; 十六进制 —||||||&gt; 八进制 然后在访问时 指定协议然后加个0</p><p>http://0[八进制] 比如 115.239.210.26 首先用.分割数字 115 239 210 26 然后选择10进制转换16进制！</p><p>(要用0来表示前缀，可以是一个0也可以是多个0 跟XSS中多加几个0来绕过过滤一样！)</p><p>首先把这四段数字给 转成 16 进制！结果：73 ef d2 1a&nbsp;&nbsp;然后把 73efd21a 这十六进制一起转换成8进制！<br/></p><p>￼</p><p>结果：16373751032</p><p>然后指定协议 http:// 用0表示前缀 加上结果 链接：</p><p>http://0016373751032<br/></p><p></p><p>成功解析成我们原来的ip了！</p><p>结合最开始的一个例子：</p><p>http://xss1.com&amp;action=test@www.baidu.com<br/></p><p>后面还带着www.baidu.com 太打眼了,现在把我们上面转换后的地址加在后面 记得带上0前缀!</p><p>http://xss1.com&amp;action=test@016373751032<br/></p><p>这样就不打眼了 看上去舒服多了 有木有？</p><p>既然解析回来了 那我们看看能不能用这个地址来加载一些资源比如图片 js什么的！</p><p>￼</p><p>可以看到成功加载了图片！那应该也是加载js等等的！</p><p>相信有扩散性的基友们都有想法了，平时用来绕过一些限制等等！</p><p>具体的大家去实验吧！web的世界 无穷大啊！</p><p><br/></p><p>作者：0x_Jin</p><p>Tags：</p><p>安全科普,</p><p>如果您喜欢我的博客，欢迎点击图片定订阅到邮箱&nbsp;也可以点击链接【订阅到鲜果】</p><p><br/></p>', '0x00 目录0x01 链接的构成0x02 浏览器算如何对url进行解析的0x03 链接真的只能是这样固定的格式么？0x04 链接真的是你看到的那样么？0x01 链接的构成链接真的只能固定成我们常用的格式么？不知道有多少人思考过这个问题！我们经常输入的格式一般都是www.xxxx.com！或者再加上协议名 http https 端口以及路径什么的 或者再加上账号密码！如下图： ￼第一部分：协议名(以单个冒号结束)第二部分：用户信息 也就是账号密码！(登陆ftp时常用)第三部分：主机名(也就是域名)第四部分：端口第五部分：查询，这里有个bug。。。应该是?号后的内容才是查询！ 第六部分：片段ID......', 10573, '2016-04-28 02:27:06', 7, '', '', 1, 7, 0, 0, 'wujiang', 'defaultsmall.gif'),
	(2382, 'test1', 10000, 10003, '<p>test</p>', 'test', 1, '2016-08-04 05:53:47', 4, '', '', 1, 0, 1, 1, '50986486', 'defaultsmall.gif'),
	(2383, 'test投稿111', 10000, 10003, '<p>啊北侧打啊<br/></p>', '啊北侧打啊', 1000000, '2016-08-11 06:39:03', 82, '', '', 1, 0, 0, 0, 'wujiang', 'avatars/1000000.jpg'),
	(2384, '大家好。我是测试虫', 10043, 10045, '<p>大家好。我是测试虫</p>', '大家好。我是测试虫', 1000000, '2016-08-16 05:49:18', 18, '', '', 1, 9, 1, 1, 'wujiang', 'avatars/1000000.jpg');
/*!40000 ALTER TABLE `ulewo_knowledge` ENABLE KEYS */;


-- 导出  表 bucuoa.ulewo_like 结构
CREATE TABLE IF NOT EXISTS `ulewo_like` (
  `article_id` int(11) NOT NULL DEFAULT '0' COMMENT '文章ID',
  `article_type` char(1) NOT NULL DEFAULT '' COMMENT '文章类型 T:讨论区 B:博客 S:吐槽 K:知识库 A:问答',
  `user_id` int(11) NOT NULL DEFAULT '0' COMMENT '用户ID',
  `create_time` datetime DEFAULT NULL,
  `user_name` varchar(50) DEFAULT NULL COMMENT '用户名',
  `user_icon` varchar(50) DEFAULT NULL COMMENT '用户小图像',
  PRIMARY KEY (`article_id`,`user_id`,`article_type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 正在导出表  bucuoa.ulewo_like 的数据：~8 rows (大约)
/*!40000 ALTER TABLE `ulewo_like` DISABLE KEYS */;
INSERT INTO `ulewo_like` (`article_id`, `article_type`, `user_id`, `create_time`, `user_name`, `user_icon`) VALUES
	(1, 'B', 1000000, '2016-08-11 06:50:32', 'wujiang', 'avatars/1000000.jpg'),
	(3, 'B', 1000000, '2016-08-17 06:05:00', 'wujiang', 'avatars/1000000.jpg'),
	(6, 'S', 1000000, '2016-08-16 05:45:58', 'wujiang', 'avatars/1000000.jpg'),
	(7, 'S', 1000000, '2016-08-16 05:45:55', 'wujiang', 'avatars/1000000.jpg'),
	(9, 'S', 1000000, '2016-08-11 06:38:12', 'wujiang', 'avatars/1000000.jpg'),
	(2380, 'K', 1000000, '2016-08-19 04:40:04', 'wujiang', 'avatars/1000000.jpg'),
	(2382, 'K', 1, '2016-08-06 08:04:54', '50986486', 'avatars/1.jpg'),
	(2384, 'K', 1000000, '2016-08-16 06:22:18', 'wujiang', 'avatars/1000000.jpg');
/*!40000 ALTER TABLE `ulewo_like` ENABLE KEYS */;


-- 导出  表 bucuoa.ulewo_message 结构
CREATE TABLE IF NOT EXISTS `ulewo_message` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `received_user_id` int(11) DEFAULT NULL COMMENT '接受人ID',
  `create_time` datetime DEFAULT NULL,
  `status` int(1) DEFAULT '0',
  `description` varchar(500) DEFAULT NULL,
  `url` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_id` (`id`),
  KEY `idx_receive_userid` (`received_user_id`),
  KEY `idx_status` (`status`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8;

-- 正在导出表  bucuoa.ulewo_message 的数据：~18 rows (大约)
/*!40000 ALTER TABLE `ulewo_message` DISABLE KEYS */;
INSERT INTO `ulewo_message` (`id`, `received_user_id`, `create_time`, `status`, `description`, `url`) VALUES
	(1, 10719, '2016-02-24 20:43:06', 0, '<span class=\'message-user-name\'>ddrisme</span>在【吐槽】<span class=\'message-title\'>第一条吐槽</span>中回复了你', 'http://192.168.1.190:8080/user/10719//spitslot/2564#null_null'),
	(2, 10719, '2016-02-27 18:45:58', 0, '<span class=\'message-user-name\'>ddrisme</span>在【吐槽】<span class=\'message-title\'>测试</span>中回复了你', 'http://192.168.1.190:8080/user/10720//spitslot/2722#null_null'),
	(3, 10720, '2016-04-24 07:10:58', 0, '<span class=\'message-user-name\'>wujiang</span>在【知识库】<span class=\'message-title\'>dierge&nbsp;知识库</span>中回复了你', 'http://www.bucuoa.com/knowledge/2378#1_5975'),
	(4, 10720, '2016-04-24 07:11:01', 0, '<span class=\'message-user-name\'>wujiang</span>在【知识库】<span class=\'message-title\'>dierge&nbsp;知识库</span>中回复了你', 'http://www.bucuoa.com/knowledge/2378#1_5976'),
	(5, 10720, '2016-04-24 07:11:03', 0, '<span class=\'message-user-name\'>wujiang</span>在【知识库】<span class=\'message-title\'>dierge&nbsp;知识库</span>中回复了你', 'http://www.bucuoa.com/knowledge/2378#1_5977'),
	(6, 10720, '2016-04-24 07:11:05', 0, '<span class=\'message-user-name\'>wujiang</span>在【知识库】<span class=\'message-title\'>dierge&nbsp;知识库</span>中回复了你', 'http://www.bucuoa.com/knowledge/2378#1_5978'),
	(7, 10720, '2016-04-24 07:11:06', 0, '<span class=\'message-user-name\'>wujiang</span>在【知识库】<span class=\'message-title\'>dierge&nbsp;知识库</span>中回复了你', 'http://www.bucuoa.com/knowledge/2378#1_5979'),
	(8, 10720, '2016-04-24 07:11:08', 0, '<span class=\'message-user-name\'>wujiang</span>在【知识库】<span class=\'message-title\'>dierge&nbsp;知识库</span>中回复了你', 'http://www.bucuoa.com/knowledge/2378#1_5980'),
	(9, 10573, '2016-08-06 08:05:40', 0, '<span class=\'message-user-name\'>50986486</span>在【知识库】<span class=\'message-title\'>成为&nbsp;OpenStack&nbsp;基金会黄金会员，对&nbsp;UnitedStack、EasyStack&nbsp;意味着什么？</span>中回复了你', 'http://www.bucuoa.com/knowledge/2380#1_1'),
	(10, 2, '2016-08-16 05:46:17', 0, '<span class=\'message-user-name\'>wujiang</span>在【吐槽】<span class=\'message-title\'>[哈哈]</span>中回复了你', 'http://www.bucuoa.com/user/2//spitslot/7#null_null'),
	(11, 2, '2016-08-16 05:46:28', 0, '<span class=\'message-user-name\'>wujiang</span>在【吐槽】<span class=\'message-title\'>[哈哈]</span>中回复了你', 'http://www.bucuoa.com/user/2//spitslot/7#null_null'),
	(12, 10573, '2016-09-24 20:05:04', 0, '<span class=\'message-user-name\'>wujiang</span>在【知识库】<span class=\'message-title\'>安全科普：URL&nbsp;Hacking&nbsp;&ndash;&nbsp;前端URL猥琐流思路</span>中回复了你', 'http://www.bucuoa.com/knowledge/2381#1_18'),
	(13, 10573, '2016-09-24 20:05:06', 0, '<span class=\'message-user-name\'>wujiang</span>在【知识库】<span class=\'message-title\'>安全科普：URL&nbsp;Hacking&nbsp;&ndash;&nbsp;前端URL猥琐流思路</span>中回复了你', 'http://www.bucuoa.com/knowledge/2381#1_19'),
	(14, 10573, '2016-09-24 20:05:08', 0, '<span class=\'message-user-name\'>wujiang</span>在【知识库】<span class=\'message-title\'>安全科普：URL&nbsp;Hacking&nbsp;&ndash;&nbsp;前端URL猥琐流思路</span>中回复了你', 'http://www.bucuoa.com/knowledge/2381#1_20'),
	(15, 10573, '2016-09-24 20:05:10', 0, '<span class=\'message-user-name\'>wujiang</span>在【知识库】<span class=\'message-title\'>安全科普：URL&nbsp;Hacking&nbsp;&ndash;&nbsp;前端URL猥琐流思路</span>中回复了你', 'http://www.bucuoa.com/knowledge/2381#1_21'),
	(16, 10573, '2016-09-24 20:05:11', 0, '<span class=\'message-user-name\'>wujiang</span>在【知识库】<span class=\'message-title\'>安全科普：URL&nbsp;Hacking&nbsp;&ndash;&nbsp;前端URL猥琐流思路</span>中回复了你', 'http://www.bucuoa.com/knowledge/2381#1_22'),
	(17, 10573, '2016-09-24 20:05:12', 0, '<span class=\'message-user-name\'>wujiang</span>在【知识库】<span class=\'message-title\'>安全科普：URL&nbsp;Hacking&nbsp;&ndash;&nbsp;前端URL猥琐流思路</span>中回复了你', 'http://www.bucuoa.com/knowledge/2381#1_23'),
	(18, 10573, '2016-09-24 20:05:15', 0, '<span class=\'message-user-name\'>wujiang</span>在【知识库】<span class=\'message-title\'>安全科普：URL&nbsp;Hacking&nbsp;&ndash;&nbsp;前端URL猥琐流思路</span>中回复了你', 'http://www.bucuoa.com/knowledge/2381#1_24');
/*!40000 ALTER TABLE `ulewo_message` ENABLE KEYS */;


-- 导出  表 bucuoa.ulewo_sign_in 结构
CREATE TABLE IF NOT EXISTS `ulewo_sign_in` (
  `user_id` int(11) NOT NULL DEFAULT '0' COMMENT '用户ID',
  `sign_date` date NOT NULL DEFAULT '0000-00-00',
  `sign_time` datetime DEFAULT NULL,
  `source_from` varchar(1) DEFAULT 'P' COMMENT '来源 A:安卓 P:PC',
  `user_name` varchar(50) DEFAULT NULL COMMENT '用户名',
  `user_icon` varchar(50) DEFAULT NULL COMMENT '用户小图像',
  PRIMARY KEY (`user_id`,`sign_date`),
  KEY `signin_index_userid` (`user_id`),
  KEY `signin_index_signdate` (`sign_date`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 正在导出表  bucuoa.ulewo_sign_in 的数据：~19 rows (大约)
/*!40000 ALTER TABLE `ulewo_sign_in` DISABLE KEYS */;
INSERT INTO `ulewo_sign_in` (`user_id`, `sign_date`, `sign_time`, `source_from`, `user_name`, `user_icon`) VALUES
	(1, '2016-08-03', '2016-08-03 04:38:20', 'P', '50986486', 'defaultsmall.gif'),
	(1, '2016-08-04', '2016-08-04 05:00:45', 'P', '50986486', 'defaultsmall.gif'),
	(1, '2016-08-06', '2016-08-06 07:25:52', 'P', '50986486', 'avatars/1.jpg'),
	(1, '2016-08-07', '2016-08-07 01:17:28', 'P', '50986486', 'avatars/1.jpg'),
	(1, '2016-08-10', '2016-08-10 22:10:33', 'P', '50986486', 'avatars/1.jpg'),
	(1, '2016-08-11', '2016-08-11 04:44:35', 'P', '50986486', 'avatars/1.jpg'),
	(1, '2016-08-13', '2016-08-13 04:52:19', 'P', '50986486', 'avatars/1.jpg'),
	(2, '2016-08-10', '2016-08-10 22:16:06', 'P', '9760976', 'avatars/2.jpg'),
	(2, '2016-08-11', '2016-08-11 05:00:50', 'P', '9760976', 'avatars/2.jpg'),
	(1000000, '2016-08-10', '2016-08-10 22:26:46', 'P', 'wujiang', 'avatars/1000000.jpg'),
	(1000000, '2016-08-11', '2016-08-11 04:51:19', 'P', 'wujiang', 'avatars/1000000.jpg'),
	(1000000, '2016-08-16', '2016-08-16 05:46:32', 'P', 'wujiang', 'avatars/1000000.jpg'),
	(1000000, '2016-08-17', '2016-08-17 06:01:16', 'P', 'wujiang', 'avatars/1000000.jpg'),
	(1000000, '2016-08-19', '2016-08-19 04:39:35', 'P', 'wujiang', 'avatars/1000000.jpg'),
	(1000000, '2016-08-23', '2016-08-23 06:13:34', 'P', 'wujiang', 'avatars/1000000.jpg'),
	(1000000, '2016-08-25', '2016-08-25 05:05:02', 'P', 'wujiang', 'avatars/1000000.jpg'),
	(1000000, '2016-08-31', '2016-08-31 06:35:53', 'P', 'wujiang', 'avatars/1000000.jpg'),
	(1000000, '2016-09-01', '2016-09-01 05:34:25', 'P', 'wujiang', 'avatars/1000000.jpg'),
	(1000000, '2016-09-24', '2016-09-24 20:03:40', 'P', 'wujiang', 'avatars/1000000.jpg');
/*!40000 ALTER TABLE `ulewo_sign_in` ENABLE KEYS */;


-- 导出  表 bucuoa.ulewo_spit_slot 结构
CREATE TABLE IF NOT EXISTS `ulewo_spit_slot` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `user_id` int(11) DEFAULT NULL COMMENT '用户ID',
  `image_url` varchar(500) DEFAULT NULL COMMENT '图片路径',
  `image_url_small` varchar(600) DEFAULT NULL,
  `music_url` varchar(500) DEFAULT NULL,
  `content` text COMMENT '内容',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `source_from` varchar(1) DEFAULT 'P' COMMENT '来源 A:安卓 P:PC',
  `user_name` varchar(50) DEFAULT NULL COMMENT '用户名',
  `user_icon` varchar(50) DEFAULT NULL COMMENT '用户小图像',
  `comment_count` int(11) DEFAULT '0' COMMENT '评论数',
  `like_count` int(11) DEFAULT '0' COMMENT '喜欢数',
  PRIMARY KEY (`id`),
  KEY `blast_index_id` (`id`),
  KEY `blast_index_userid` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;

-- 正在导出表  bucuoa.ulewo_spit_slot 的数据：~10 rows (大约)
/*!40000 ALTER TABLE `ulewo_spit_slot` DISABLE KEYS */;
INSERT INTO `ulewo_spit_slot` (`id`, `user_id`, `image_url`, `image_url_small`, `music_url`, `content`, `create_time`, `source_from`, `user_name`, `user_icon`, `comment_count`, `like_count`) VALUES
	(1, 1, '', '', NULL, '[给力]', '2016-08-03 05:12:12', 'P', '50986486', 'avatars/1.jpg', 0, 0),
	(2, 1, '', '', NULL, '[浮云]', '2016-08-03 05:12:33', 'P', '50986486', 'avatars/1.jpg', 0, 0),
	(3, 1, '', '', NULL, '啊啊', '2016-08-05 06:37:40', 'P', '50986486', 'avatars/1.jpg', 0, 0),
	(4, 1, '', '', NULL, 'aaaaa', '2016-08-05 06:46:12', 'P', '50986486', 'avatars/1.jpg', 0, 0),
	(5, 1, '', '', NULL, 'abcd', '2016-08-05 06:51:57', 'P', '50986486', 'avatars/1.jpg', 0, 0),
	(6, 2, '', '', NULL, '大家好。。我是版主！', '2016-08-11 05:03:00', 'P', '9760976', 'avatars/2.jpg', 0, 1),
	(7, 2, '', '', NULL, '[哈哈]', '2016-08-11 05:04:42', 'P', '9760976', 'avatars/2.jpg', 2, 1),
	(8, 1, '', '', NULL, 'aaaa', '2016-08-10 22:12:49', 'P', '50986486', 'avatars/1.jpg', 0, 0),
	(9, 1000000, '', '', NULL, 'aaaaa', '2016-08-10 22:29:53', 'P', 'wujiang', 'avatars/1000000.jpg', 0, 1),
	(10, 1000000, '', '', NULL, 'adasasdfasd', '2016-09-01 05:35:16', 'P', 'wujiang', 'avatars/1000000.jpg', 0, 0);
/*!40000 ALTER TABLE `ulewo_spit_slot` ENABLE KEYS */;


-- 导出  表 bucuoa.ulewo_spit_slot_comment 结构
CREATE TABLE IF NOT EXISTS `ulewo_spit_slot_comment` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '吐槽评论ID',
  `spit_slot_id` int(11) DEFAULT NULL COMMENT '吐槽id',
  `content` varchar(1000) DEFAULT NULL COMMENT '评论内容',
  `create_time` datetime DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL COMMENT '用户ID',
  `source_from` char(1) DEFAULT 'P',
  `user_name` varchar(50) DEFAULT NULL COMMENT '用户名',
  `user_icon` varchar(50) DEFAULT NULL COMMENT '用户小图像',
  PRIMARY KEY (`id`),
  KEY `blastcomment_index_blastid` (`spit_slot_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- 正在导出表  bucuoa.ulewo_spit_slot_comment 的数据：~2 rows (大约)
/*!40000 ALTER TABLE `ulewo_spit_slot_comment` DISABLE KEYS */;
INSERT INTO `ulewo_spit_slot_comment` (`id`, `spit_slot_id`, `content`, `create_time`, `user_id`, `source_from`, `user_name`, `user_icon`) VALUES
	(1, 7, 'hah&nbsp;.mei&nbsp;wenti', '2016-08-16 05:46:17', 1000000, 'P', 'wujiang', 'avatars/1000000.jpg'),
	(2, 7, '[给力]', '2016-08-16 05:46:28', 1000000, 'P', 'wujiang', 'avatars/1000000.jpg');
/*!40000 ALTER TABLE `ulewo_spit_slot_comment` ENABLE KEYS */;


-- 导出  表 bucuoa.ulewo_statistics 结构
CREATE TABLE IF NOT EXISTS `ulewo_statistics` (
  `statistics_date` date NOT NULL COMMENT '统计日期',
  `signin_count` int(11) DEFAULT '0' COMMENT '签到数',
  `spit_slot_count` int(11) DEFAULT '0' COMMENT '吐槽数',
  `spit_slot_comment_count` int(11) DEFAULT '0' COMMENT '吐槽评论数',
  `topic_count` int(11) DEFAULT '0' COMMENT '帖子数',
  `topic_comment_count` int(11) DEFAULT '0' COMMENT '帖子评论数',
  `knowledge_count` int(11) DEFAULT '0' COMMENT '知识库数',
  `knowledge_comment_count` int(11) DEFAULT '0' COMMENT '知识库评论数',
  `ask_count` int(11) DEFAULT '0' COMMENT '问答数',
  `ask_comment_count` int(11) DEFAULT '0' COMMENT '问答评论数',
  `blog_count` int(11) DEFAULT '0' COMMENT '博客数',
  `blog_comment_count` int(11) DEFAULT '0' COMMENT '博客评论数',
  `exam_count` int(11) DEFAULT '0' COMMENT '考题数',
  `user_count` int(11) DEFAULT '0' COMMENT '新增用户数',
  `active_user_count` int(11) DEFAULT '0' COMMENT '活跃用户数',
  PRIMARY KEY (`statistics_date`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='统计信息';

-- 正在导出表  bucuoa.ulewo_statistics 的数据：~74 rows (大约)
/*!40000 ALTER TABLE `ulewo_statistics` DISABLE KEYS */;
INSERT INTO `ulewo_statistics` (`statistics_date`, `signin_count`, `spit_slot_count`, `spit_slot_comment_count`, `topic_count`, `topic_comment_count`, `knowledge_count`, `knowledge_comment_count`, `ask_count`, `ask_comment_count`, `blog_count`, `blog_comment_count`, `exam_count`, `user_count`, `active_user_count`) VALUES
	('2016-01-02', 0, 1, 0, 0, 0, 1, 0, 1, 0, 1, 0, 0, 1, 1),
	('2016-02-19', 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
	('2016-02-20', 1, 3, 2, 2, 1, 1, 0, 1, 2, 1, 0, 0, 1, 1),
	('2016-02-21', 1, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1),
	('2016-02-22', 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1),
	('2016-02-24', 1, 2, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1),
	('2016-02-25', 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1),
	('2016-02-26', 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1),
	('2016-02-27', 1, 1, 1, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 1),
	('2016-02-28', 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1),
	('2016-02-29', 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
	('2016-03-28', 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
	('2016-03-29', 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
	('2016-03-30', 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1),
	('2016-03-31', 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
	('2016-04-01', 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
	('2016-04-02', 1, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
	('2016-04-03', 2, 4, 0, 0, 0, 0, 0, 2, 1, 0, 0, 0, 0, 0),
	('2016-04-04', 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
	('2016-04-05', 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
	('2016-04-06', 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
	('2016-04-07', 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
	('2016-04-08', 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
	('2016-04-09', 1, 1, 1, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0),
	('2016-04-10', 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
	('2016-04-11', 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
	('2016-04-17', 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
	('2016-04-18', 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
	('2016-04-24', 1, 0, 0, 0, 0, 0, 6, 0, 0, 0, 0, 0, 0, 0),
	('2016-04-25', 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
	('2016-04-26', 1, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0),
	('2016-04-27', 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
	('2016-04-28', 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0),
	('2016-04-30', 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
	('2016-05-01', 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
	('2016-05-02', 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
	('2016-06-25', 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
	('2016-06-26', 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
	('2016-07-23', 1, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
	('2016-07-24', 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
	('2016-07-30', 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
	('2016-07-31', 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
	('2016-08-01', 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
	('2016-08-02', 1, 0, 2, 0, 0, 16, 2, 0, 0, 0, 0, 0, 0, 0),
	('2016-08-03', 1, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
	('2016-08-04', 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0),
	('2016-08-05', 0, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
	('2016-08-06', 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0),
	('2016-08-07', 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
	('2016-08-08', 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
	('2016-08-09', 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
	('2016-08-10', 3, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
	('2016-08-11', 3, 2, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0),
	('2016-08-13', 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
	('2016-08-16', 1, 0, 2, 0, 0, 1, 8, 0, 0, 2, 7, 0, 0, 0),
	('2016-08-17', 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
	('2016-08-19', 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
	('2016-08-20', 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
	('2016-08-23', 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0),
	('2016-08-25', 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
	('2016-08-31', 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
	('2016-09-01', 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
	('2016-09-10', 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
	('2016-09-11', 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
	('2016-09-12', 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
	('2016-09-13', 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
	('2016-09-15', 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
	('2016-09-16', 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
	('2016-09-17', 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
	('2016-09-18', 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
	('2016-09-19', 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
	('2016-09-23', 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0),
	('2016-09-24', 1, 0, 0, 0, 0, 0, 7, 0, 0, 0, 0, 3, 0, 0),
	('2016-09-25', 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
/*!40000 ALTER TABLE `ulewo_statistics` ENABLE KEYS */;


-- 导出  表 bucuoa.ulewo_task 结构
CREATE TABLE IF NOT EXISTS `ulewo_task` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '任务ID',
  `task_classz` varchar(100) DEFAULT NULL COMMENT '任务执行的类',
  `task_method` varchar(100) DEFAULT NULL COMMENT '任务执行的方法',
  `task_time` varchar(50) DEFAULT NULL COMMENT '任务调度时间',
  `last_update_time` datetime DEFAULT NULL COMMENT '任务最后更新时间',
  `status` int(2) DEFAULT '0' COMMENT '任务状态 0：初始状态 1:暂停',
  `description` varchar(200) DEFAULT NULL COMMENT '描述',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COMMENT='任务调度';

-- 正在导出表  bucuoa.ulewo_task 的数据：~1 rows (大约)
/*!40000 ALTER TABLE `ulewo_task` DISABLE KEYS */;
INSERT INTO `ulewo_task` (`id`, `task_classz`, `task_method`, `task_time`, `last_update_time`, `status`, `description`) VALUES
	(6, 'com.ulewo.task.StatisticsTask', 'statisticsInfo', '0/10 * * * * ?', '2015-12-19 15:33:25', 0, '统计数量');
/*!40000 ALTER TABLE `ulewo_task` ENABLE KEYS */;


-- 导出  表 bucuoa.ulewo_topic 结构
CREATE TABLE IF NOT EXISTS `ulewo_topic` (
  `topic_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `topic_type` int(1) DEFAULT '0' COMMENT '帖子类型 0普通帖 1投票贴',
  `p_category_id` int(11) DEFAULT NULL COMMENT '组id',
  `category_id` int(11) DEFAULT NULL COMMENT '分组ID',
  `title` varchar(100) DEFAULT '' COMMENT '标题',
  `keyword` varchar(100) DEFAULT NULL COMMENT '关键字',
  `content` longtext COMMENT '内容',
  `summary` text COMMENT '���',
  `user_id` int(11) DEFAULT NULL COMMENT '用户ID',
  `create_time` datetime DEFAULT NULL,
  `last_comment_time` datetime DEFAULT NULL,
  `read_count` int(11) DEFAULT '0' COMMENT '阅读数',
  `grade` int(2) DEFAULT '0' COMMENT '帖子等级 0普通帖 置顶1',
  `essence` int(1) DEFAULT '0' COMMENT '精华',
  `valid` varchar(2) DEFAULT 'Y' COMMENT '是否有效 Y有效 N无效',
  `topic_image` mediumtext,
  `topic_image_thum` mediumtext COMMENT '文章图标',
  `comment_count` int(11) DEFAULT '0' COMMENT '评论数',
  `like_count` int(11) DEFAULT '0' COMMENT '喜欢数',
  `collection_count` int(11) DEFAULT '0' COMMENT '收藏数',
  `user_name` varchar(50) DEFAULT NULL COMMENT '用户名',
  `user_icon` varchar(50) DEFAULT NULL COMMENT '用户小图像',
  PRIMARY KEY (`topic_id`),
  KEY `topic_index_id` (`topic_id`),
  KEY `topic_index_gid` (`p_category_id`),
  KEY `topic_index_id_gid` (`topic_id`,`p_category_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 正在导出表  bucuoa.ulewo_topic 的数据：~0 rows (大约)
/*!40000 ALTER TABLE `ulewo_topic` DISABLE KEYS */;
/*!40000 ALTER TABLE `ulewo_topic` ENABLE KEYS */;


-- 导出  表 bucuoa.ulewo_topic_vote 结构
CREATE TABLE IF NOT EXISTS `ulewo_topic_vote` (
  `vote_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '投票ID',
  `topic_id` int(11) NOT NULL,
  `vote_type` int(11) DEFAULT NULL COMMENT '1 单选 2多选',
  `end_date` date NOT NULL,
  PRIMARY KEY (`vote_id`),
  KEY `idx_topic_id` (`topic_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 正在导出表  bucuoa.ulewo_topic_vote 的数据：~0 rows (大约)
/*!40000 ALTER TABLE `ulewo_topic_vote` DISABLE KEYS */;
/*!40000 ALTER TABLE `ulewo_topic_vote` ENABLE KEYS */;


-- 导出  表 bucuoa.ulewo_topic_vote_dtl 结构
CREATE TABLE IF NOT EXISTS `ulewo_topic_vote_dtl` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(300) DEFAULT NULL,
  `count` int(11) DEFAULT NULL COMMENT '投票数',
  `vote_id` int(11) DEFAULT NULL COMMENT '投票ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='ϸ';

-- 正在导出表  bucuoa.ulewo_topic_vote_dtl 的数据：~0 rows (大约)
/*!40000 ALTER TABLE `ulewo_topic_vote_dtl` DISABLE KEYS */;
/*!40000 ALTER TABLE `ulewo_topic_vote_dtl` ENABLE KEYS */;


-- 导出  表 bucuoa.ulewo_topic_vote_user 结构
CREATE TABLE IF NOT EXISTS `ulewo_topic_vote_user` (
  `vote_dtl_id` int(11) DEFAULT NULL COMMENT '投票详情ID',
  `user_id` int(11) DEFAULT NULL COMMENT '用户ID',
  `vote_date` datetime NOT NULL COMMENT '投票时间',
  KEY `idx_user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='ûͶƱ';

-- 正在导出表  bucuoa.ulewo_topic_vote_user 的数据：~0 rows (大约)
/*!40000 ALTER TABLE `ulewo_topic_vote_user` DISABLE KEYS */;
/*!40000 ALTER TABLE `ulewo_topic_vote_user` ENABLE KEYS */;


-- 导出  表 bucuoa.ulewo_user 结构
CREATE TABLE IF NOT EXISTS `ulewo_user` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `email` varchar(100) NOT NULL DEFAULT '' COMMENT '邮箱',
  `user_name` varchar(50) DEFAULT NULL COMMENT '用户名',
  `password` varchar(50) NOT NULL DEFAULT '' COMMENT '密码',
  `user_icon` varchar(50) DEFAULT NULL COMMENT '用户小图像',
  `user_bg` varchar(100) DEFAULT '0' COMMENT '背景图',
  `birthday` varchar(50) DEFAULT NULL COMMENT '生日',
  `sex` varchar(1) DEFAULT 'M' COMMENT '性别 M男 F女',
  `characters` varchar(200) DEFAULT NULL COMMENT '个性签名',
  `mark` int(11) DEFAULT '0' COMMENT '积分',
  `address` varchar(50) DEFAULT NULL COMMENT '籍贯',
  `work` varchar(50) DEFAULT NULL COMMENT '职业',
  `register_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `last_login_time` datetime DEFAULT NULL COMMENT '最后登录时间',
  `isvalid` varchar(1) DEFAULT 'Y' COMMENT '是否有效 Y有效 N无效',
  `activation_code` varchar(25) DEFAULT NULL COMMENT '激活码',
  PRIMARY KEY (`user_id`,`email`(1),`password`),
  KEY `user_index_userid` (`user_id`),
  KEY `user_index_username` (`user_name`),
  KEY `user_index_email` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 正在导出表  bucuoa.ulewo_user 的数据：~0 rows (大约)
/*!40000 ALTER TABLE `ulewo_user` DISABLE KEYS */;
/*!40000 ALTER TABLE `ulewo_user` ENABLE KEYS */;


-- 导出  表 bucuoa.ulewo_user_friend 结构
CREATE TABLE IF NOT EXISTS `ulewo_user_friend` (
  `user_id` int(11) NOT NULL DEFAULT '0' COMMENT '用户ID',
  `friend_user_id` int(11) NOT NULL DEFAULT '0' COMMENT 'friend用户ID',
  `create_time` datetime DEFAULT NULL,
  `user_name` varchar(50) DEFAULT NULL COMMENT '用户名',
  `user_icon` varchar(50) DEFAULT NULL COMMENT '用户小图像',
  `friend_user_name` varchar(50) DEFAULT NULL COMMENT '用户名',
  `friend_user_icon` varchar(50) DEFAULT NULL COMMENT '用户小图像',
  PRIMARY KEY (`user_id`,`friend_user_id`),
  KEY `friend_index_userid` (`user_id`),
  KEY `friend_index_friendid` (`friend_user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 正在导出表  bucuoa.ulewo_user_friend 的数据：~10 rows (大约)
/*!40000 ALTER TABLE `ulewo_user_friend` DISABLE KEYS */;
INSERT INTO `ulewo_user_friend` (`user_id`, `friend_user_id`, `create_time`, `user_name`, `user_icon`, `friend_user_name`, `friend_user_icon`) VALUES
	(1, 2, '2016-08-10 22:24:42', '50986486', 'avatars/1.jpg', '9760976', 'avatars/2.jpg'),
	(1, 1000000, '2016-08-10 22:25:24', '50986486', 'avatars/1.jpg', 'wujiang', 'avatars/1000000.jpg'),
	(2, 1, '2016-08-08 23:03:54', '9760976', 'defaultsmall.gif', '50986486', 'avatars/1.jpg'),
	(10573, 10576, '2016-04-26 07:19:39', 'wujiang', 'defaultsmall.gif', 'west', 'defaultsmall.gif'),
	(10576, 10001, '2016-04-07 06:31:54', 'west', 'defaultsmall.gif', '多多洛', 'avatars/10001.jpg'),
	(10576, 10573, '2016-04-17 05:57:13', 'west', 'defaultsmall.gif', 'wujiang', 'defaultsmall.gif'),
	(10576, 10719, '2016-04-07 06:28:56', 'west', 'defaultsmall.gif', 'az61663', 'defaultsmall.gif'),
	(10720, 10719, '2016-02-24 20:43:42', 'ddrisme', 'avatars/10720.jpg', 'ulewo', 'avatars/10719.jpg'),
	(1000000, 1, '2016-08-31 06:37:18', 'wujiang', 'avatars/1000000.jpg', '50986486', 'avatars/1.jpg'),
	(1000000, 2, '2016-08-16 06:23:45', 'wujiang', 'avatars/1000000.jpg', '9760976', 'avatars/2.jpg');
/*!40000 ALTER TABLE `ulewo_user_friend` ENABLE KEYS */;


-- 导出  表 bucuoa.user_exam_result 结构
CREATE TABLE IF NOT EXISTS `user_exam_result` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户ID',
  `question_id` bigint(20) DEFAULT NULL COMMENT '题目',
  `create_time` datetime DEFAULT NULL COMMENT '时间',
  `result` varchar(500) DEFAULT NULL COMMENT '结果',
  `user_name` varchar(50) DEFAULT NULL COMMENT '用户名',
  `user_icon` varchar(50) DEFAULT NULL COMMENT '用户小图像',
  `statusx` tinyint(4) DEFAULT NULL COMMENT '状态',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- 正在导出表  bucuoa.user_exam_result 的数据：~2 rows (大约)
/*!40000 ALTER TABLE `user_exam_result` DISABLE KEYS */;
INSERT INTO `user_exam_result` (`id`, `user_id`, `question_id`, `create_time`, `result`, `user_name`, `user_icon`, `statusx`) VALUES
	(1, 10000, 5, '2016-09-24 14:28:44', NULL, NULL, 'avatars/1000000.jpg', 0),
	(2, 10000, 5, '2016-09-24 19:53:14', NULL, NULL, 'avatars/1000000.jpg', 0),
	(3, 10000, 6, '2016-09-24 20:05:06', NULL, NULL, 'avatars/1000000.jpg', 0),
	(4, 10000, 6, '2016-09-24 20:05:28', NULL, NULL, 'avatars/1000000.jpg', 0),
	(5, 10000, 5, '2016-09-24 20:05:36', NULL, NULL, 'avatars/1000000.jpg', 0),
	(6, 10000, 6, '2016-09-24 20:05:38', NULL, NULL, 'avatars/1000000.jpg', 0),
	(7, 10000, 4, '2016-09-24 20:05:41', NULL, NULL, 'avatars/1000000.jpg', 0),
	(8, 10000, 4, '2016-09-24 20:06:38', NULL, NULL, 'avatars/1000000.jpg', 1),
	(9, 10000, 5, '2016-09-24 20:06:57', NULL, NULL, 'avatars/1000000.jpg', 1),
	(10, 10000, 5, '2016-09-24 20:07:06', NULL, NULL, 'avatars/1000000.jpg', 1);
/*!40000 ALTER TABLE `user_exam_result` ENABLE KEYS */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
