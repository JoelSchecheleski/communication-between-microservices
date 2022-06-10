package br.com.upper.product.api.config.interceptor;

import java.util.Objects;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import br.com.upper.product.api.config.ValidationException;
import feign.RequestInterceptor;
import feign.RequestTemplate;

public class FeignClientAuthInterceptor implements RequestInterceptor {

    private  static final String AUTHORIZATION = "Authorization";

    @Override
    public void apply(RequestTemplate template) {
        var currentRequest = getCurrentRequest();
        template.header("Authorization", currentRequest.getHeader(AUTHORIZATION));
    }

    private HttpServletRequest getCurrentRequest(){
        try{
            return ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        }catch (Exception ex) {
            ex.printStackTrace();
            throw new ValidationException("The current request could not be proccessed.");
        }


    }

}
