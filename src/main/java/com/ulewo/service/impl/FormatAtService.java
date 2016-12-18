package com.ulewo.service.impl;

import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.bucuoa.ucenter.model.UlewoUser;
import com.ulewo.service.UserService;
import com.ulewo.utils.ServerUtils;

@Component
public class FormatAtService {

	@Resource
	private UserService userService;

	private static Pattern referer_pattern = Pattern.compile("@([^@\\s\\:\\;\\,\\\\.\\<\\?\\？\\{\\}\\&]{1,})");// @.+?[\\s:]

	private static String userUrl = "/user/";

	public String GenerateRefererLinks(String msg, Set<Integer> userIds) {

		StringBuilder html = new StringBuilder();
		int lastIdx = 0;
		Matcher matchr = referer_pattern.matcher(msg);
		while (matchr.find()) {
			String origion_str = matchr.group();
			String userName = origion_str.substring(1, origion_str.length()).trim();
			html.append(msg.substring(lastIdx, matchr.start()));
			UlewoUser user = userService.findUserByUserName(userName);
			if (null != user) {
				html.append("<a href=\"" + ServerUtils.getDomain() + userUrl + user.getUserId()
						+ "\" class=\"referer\" target=\"_blank\">@");
				html.append(userName.trim());
				html.append("</a> ");
				userIds.add(user.getUserId().intValue());
			} else {
				html.append(origion_str);
			}
			lastIdx = matchr.end();
		}
		html.append(msg.substring(lastIdx));
		return html.toString();
	}
}
