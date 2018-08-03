package com.samba.tradereport;

import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.xpath;

import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;

public class PageResultMatchers {

	private static final String HEADER_ID = "result-heading";

	public static ResultMatcher hasElementById(String id) {
		return new ResultMatcher() {
			@Override
			public void match(MvcResult result) throws Exception {
				xpath("//*[@id='" + id + "']").exists().match(result);

			}
		};
	}

	public static ResultMatcher hasElementById(String id, String text) {
		return new ResultMatcher() {
			@Override
			public void match(MvcResult result) throws Exception {
				xpath("//*[@id='" + id + "']").string(containsString(text)).match(result);

			}
		};
	}

	public static ResultMatcher hasHeadingText(String txt) {
		return hasElementById(HEADER_ID, txt);
	}

	public static ResultMatcher hasNotElementById(String id) {
		return new ResultMatcher() {
			@Override
			public void match(MvcResult result) throws Exception {
				xpath("//*[@id='" + id + "']").doesNotExist().match(result);

			}
		};
	}

	public static ResultMatcher hasNoValidationErrors() {
		return new ResultMatcher() {
			@Override
			public void match(MvcResult result) throws Exception {
				xpath("//*[@id='error-summary']").doesNotExist().match(result);
				xpath("//*[@class='form-group error']").doesNotExist().match(result);
			}
		};
	}

	public static ResultMatcher hasTextInH1Source(String text) {
		return hasElementById("question-heading", text);
	}

	public static ResultMatcher hasTitle(String text) {
		return hasElementById("title", text);

	}

	public static ResultMatcher hasValidationErrors(String... errorText) {
		return new ResultMatcher() {
			@Override
			public void match(MvcResult result) throws Exception {
				xpath("//*[@id='error-summary']").exists().match(result);
				xpath("//*[@class='form-group error' or @class='form-item-wrapper error']").exists().match(result);
				for (String error : errorText) {
					xpath("//*[@class='error-message']").string(containsString(error)).match(result);
				}
			}
		};
	}

	public static ResultMatcher redirectsTo(String path) {
		return new ResultMatcher() {
			@Override
			public void match(MvcResult result) throws Exception {
				status().is3xxRedirection().match(result);
				view().name("redirect:" + path).match(result);
			}
		};
	}

}
