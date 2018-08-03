package com.samba.tradereport;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyVararg;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.validation.BindingResult;

import com.samba.tradereport.history.History;
import com.samba.tradereport.model.UserData;
import com.samba.tradereport.router.IRoute;
import com.samba.tradereport.validation.FormValidationService;
import com.samba.tradereport.validation.IFormValidationService;

public class PageTestHarness {

	/**
	 * Builder for a PageTestHarness.
	 */
	public static class PageTestHarnessBuilder {

		public static PageTestHarnessBuilder create(MockMvc mockMvc) {
			return new PageTestHarnessBuilder(mockMvc);
		}

		PageTestHarness result;

		private PageTestHarnessBuilder(MockMvc mockMvc) {
			super();
			this.result = new PageTestHarness(mockMvc);
		}

		public PageTestHarness build() {
			return this.result;
		}

		public PageTestHarnessBuilder withFormValidationService(IFormValidationService formValidationService) {
			this.result.setFormValidationService(formValidationService);
			return this;
		}

		public PageTestHarnessBuilder withPath(String path) {
			this.result.path = path;
			return this;
		}

		public PageTestHarnessBuilder withRouter(IRoute<UserData> router) {
			this.result.setRouter(router);
			return this;
		}

		public PageTestHarnessBuilder withStartHistoryPage(String path) {
			this.result.history = new History(path);
			return this;
		}

		public PageTestHarnessBuilder withUserData(UserData userData) {
			this.result.userData = userData;
			return this;
		}
	}

	private static final String HISTORY_OBJECT_NAME = "history";
	private static final String NEXT_PAGE_PATH = "next-page";
	private static final String USER_DATA_OBJECT_NAME = "user";

	private static String location(MockHttpServletResponse response) {
		return response.getHeader("Location");
	}

	private static void propagateFlashAttributes(MvcResult mvcResult, MockHttpServletRequestBuilder getRequest) {
		Map<String, Object> flashAttributes = mvcResult.getFlashMap();
		if (!flashAttributes.isEmpty()) {
			getRequest.flashAttrs(flashAttributes);
		}
	}

	private IFormValidationService formValidationService;
	private History history;
	private ResultActions lastResultActions;
	private MockMvc mockMvc;
	private String path;
	private IRoute<UserData> router;
	private UserData userData = new UserData();

	private PageTestHarness(MockMvc mockMvc) {
		super();
		this.mockMvc = mockMvc;

		HttpServletRequest mockRequest = mock(HttpServletRequest.class);
		// setup the behaviour here (or do it in setup method or something)
		when(mockRequest.getRequestURI()).thenReturn("currentPage");
	}

	/**
	 * Perform a GET request and return ResultActions to test.
	 *
	 * @return
	 */
	public ResultActions doGet() {
		try {
			this.lastResultActions = this.mockMvc
					.perform(get(this.path).sessionAttr(USER_DATA_OBJECT_NAME, this.userData));
			return this.lastResultActions;

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Perform a POST request and return ResultActions to test.
	 */
	public ResultActions doPost() {
		try {
			this.lastResultActions = this.mockMvc
					.perform(post(this.path).sessionAttr(USER_DATA_OBJECT_NAME, this.userData)
							.sessionAttr(HISTORY_OBJECT_NAME, this.history).with(csrf()));
			return this.lastResultActions;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Perform a GET request followed by a redirected GET and return ResultActions
	 * to test.
	 */
	public ResultActions doPostRedirectGet() {
		try {
			MvcResult mvcResult = doPost().andReturn();
			String location = location(mvcResult.getResponse());
			MockHttpServletRequestBuilder getRequest = get(location).sessionAttr(USER_DATA_OBJECT_NAME, this.userData)
					.sessionAttr(HISTORY_OBJECT_NAME, this.history);
			propagateFlashAttributes(mvcResult, getRequest);
			this.lastResultActions = this.mockMvc.perform(getRequest);
			return this.lastResultActions;

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public UserData getUserData() {
		return this.userData;
	}

	public void teardown() {
		Mockito.verifyNoMoreInteractions(this.formValidationService, this.router);
	}

	private void setFormValidationService(IFormValidationService formValidationService) {
		this.formValidationService = formValidationService;
		final FormValidationService delegate = new FormValidationService(true);
		when(formValidationService.hasErrors(any(BindingResult.class), anyVararg())).thenAnswer(new Answer<Boolean>() {
			@Override
			public Boolean answer(InvocationOnMock invocation) throws Throwable {
				BindingResult errors = invocation.getArgumentAt(0, BindingResult.class);
				String[] fields;
				Object fieldsArg = invocation.getArgumentAt(1, Object.class);
				if (String.class.isAssignableFrom(fieldsArg.getClass())) {
					fields = new String[] { (String) fieldsArg };
				} else {
					fields = (String[]) fieldsArg;
				}
				return delegate.hasErrors(errors, fields);
			}
		});
	}

	private void setRouter(IRoute<UserData> router) {
		this.router = router;
		when(router.evaluate(this.userData)).thenReturn(NEXT_PAGE_PATH);
	}

}
