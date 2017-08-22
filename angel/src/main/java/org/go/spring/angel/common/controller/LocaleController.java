package org.go.spring.angel.common.controller;

import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.springframework.web.servlet.mvc.AbstractController;

public class LocaleController extends AbstractController {

	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		HttpSession session = request.getSession();

		String locale = request.getParameter("locale");
		Locale lo = null;
		if (locale.equals("ko"))
			lo = Locale.KOREAN;
		else if (locale.equals("en"))
			lo = Locale.ENGLISH;

		session.setAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME, lo);
		session.setAttribute("locale", lo);
		return null;
	}
}
