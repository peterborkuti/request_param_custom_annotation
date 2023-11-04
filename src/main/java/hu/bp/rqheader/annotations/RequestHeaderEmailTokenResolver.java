package hu.bp.rqheader.annotations;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

public class RequestHeaderEmailTokenResolver implements HandlerMethodArgumentResolver {
    public static final String HEADER_NAME = "X-EMAIL-TOKEN";

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return String.class.equals(parameter.getParameter().getType())
                && parameter.hasParameterAnnotation(RequestHeaderEmailToken.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        String token = webRequest.getHeader(HEADER_NAME);
        if (token == null || token.isEmpty()) throw new ServletRequestBindingException(HEADER_NAME + " header should not be null or empty");
        return token;
    }
}
