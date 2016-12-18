package com.ulewo.utils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;

public class ImageUtils {
	/**
	 * 获取图片的后缀
	 */
	private static final String[] static_ext = { "jpg", "png", "gif", "bmp", "JPG", "PNG", "GIF", "BMP" };

	/**
	 * 头像，和背景图片不获取
	 */
	private static final String EMOTION = "emotion", GREY = "grey.gif";

	private static final int UPLOAD_STR_LENGTH = 7;

	@SuppressWarnings("unchecked")
	public static String getImages(String content) {

		StringBuilder sbf = new StringBuilder();
		HtmlCleaner htmlCleaner = new HtmlCleaner();
		TagNode allNode = htmlCleaner.clean(content);
		List<TagNode> nodeList = (List<TagNode>) allNode.getElementListByName("img", true);
		String image = "";
		if (nodeList != null) {
			for (TagNode node : nodeList) {
				image = String.valueOf(node.getAttributeByName("src")).trim();
				if (!image.contains(EMOTION) && !image.contains(GREY) && image.contains(".")
						&& ArrayUtils.contains(static_ext, image.substring(image.lastIndexOf(".") + 1))) {
					sbf.append(image + "|");
				}
			}
		}
		if (sbf.length() > 0) {
			sbf.substring(sbf.lastIndexOf("|"));
		}
		return sbf.toString();
	}

	/**
	 * 
	 * geThumbnail:(创建缩略图). <br/>
	 *
	 * @author 不错啊
	 * @param topicImage
	 * @param isFullPath
	 * @return
	 * @since JDK 1.7
	 */
	public static String createThumbnail(String topicImage, boolean isFullPath) {
		StringBuilder topicImageSmall = new StringBuilder();
		if (!StringUtils.isEmpty(topicImage)) {
			String realPath = ServerUtils.getRealPath();
			String[] topoicImages = topicImage.split("\\|");
			int smallCount = topoicImages.length;// 缩略图只需要生成三张即可
			if (smallCount > Constants.MAXTHUMBNAILCOUNT) {
				smallCount = Constants.MAXTHUMBNAILCOUNT;
			}
			for (int i = 0; i < smallCount; i++) {
				try {
					String img = topoicImages[i];
					if (isFullPath) {
						img = img.substring(img.indexOf("upload") + UPLOAD_STR_LENGTH);
					}
					String sourcePath = realPath + "/upload/" + img;
					String smallSavePath = sourcePath + "_s.jpg";
					String smallPath = img + "_s.jpg";
					BufferedImage src = ImageIO.read(new File(sourcePath));
					//图片宽度小于等于150不生成缩略语
					if (src.getWidth() <= Constants.THUMBNAILWIDTH) {
						continue;
					}
					BufferedImage dst = new ScaleFilter().getThumbnail(src, Constants.THUMBNAILWIDTH,
							Constants.THUMBNAILHEIGHT);
					ImageIO.write(dst, "JPEG", new File(smallSavePath));
					topicImageSmall.append(smallPath).append("|");
				} catch (Exception e) {
					e.printStackTrace();
					continue;
				}
			}
		}

		return topicImageSmall.toString();
	}

	public static void main(String[] args) {
		/*
		 * BufferedImage src = null; String sourcePath = "E:/01.jpg"; String
		 * smallPath = "E:/01_s.jpg"; try { // 生成的缩略图 最宽100，最高100 int cutHeight
		 * = 100; src = ImageIO.read(new File(sourcePath)); int width =
		 * src.getWidth(); int height = src.getHeight(); if (width>height) {
		 * cutHeight = height * 100 / width;; } BufferedImage dst = new
		 * ScaleFilter(cutHeight).filter(src, null); ImageIO.write(dst, "JPEG",
		 * new File(smallPath)); } catch (IOException e) { e.printStackTrace();
		 * }
		 */
	}
}
