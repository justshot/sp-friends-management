package com.sp.utils;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Texts {
	public static Set<String> extractEmails(String text) {
		Set<String> set = new HashSet<String>();
		Pattern pattern = Pattern.compile("[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+");
		Matcher matcher = pattern.matcher(text);
		while (matcher.find()) {
			set.add(matcher.group());
		}
		return set;
	}
}
