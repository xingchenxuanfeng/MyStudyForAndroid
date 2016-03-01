package com.test.test;

import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

public class Solution {
	Stack<String> stack = new Stack<>();

	public boolean wordBreak1(String s, Set<String> wordDict) {
		boolean result = false;
		for (int i = s.length(); i > 0; i--) {
			if (wordDict.contains(s.substring(0, i))) {
				result = wordBreak(s.substring(i), wordDict);
				if (i == s.length()) {
					return true;
				}
				if (result) {
					return result;
				}
			}
		}
		return result;
	}

	public static void main(String[] args) {
		Set<String> a = new HashSet<>();
		a.add("leet");
		a.add("code");
		String m = "leetcode";
		boolean wordBreak = new Solution().wordBreak(m, a);
		System.out.println(wordBreak);
	}

	public boolean wordBreak(String s, Set<String> wordDict) {
		boolean result = false;
		for (int i = s.length(); i > 0; i--) {
			String temp = s.substring(0, i);
			if (wordDict.contains(temp)) {
				if (stack.isEmpty()) {
					stack.push(temp);
				} else {
					stack.push(stack.peek() + temp);
					wordDict.add(stack.peek());
				}
				wordDict.add(temp);
				result = wordBreak(s.substring(i), wordDict);
				stack.pop();
				if (i == s.length()) {
					return true;
				}
				if (result) {
					return result;
				}
			}
		}
		if (!stack.isEmpty()) {
		}
		return result;
	}
}
