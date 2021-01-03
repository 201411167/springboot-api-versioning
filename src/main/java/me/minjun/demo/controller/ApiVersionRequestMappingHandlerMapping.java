package me.minjun.demo.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.servlet.mvc.condition.*;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.lang.reflect.Method;

@Slf4j
public class ApiVersionRequestMappingHandlerMapping extends RequestMappingHandlerMapping {
    private final String prefix;

    public ApiVersionRequestMappingHandlerMapping(String prefix) {
        this.prefix = prefix;
    }

    @Override
    protected RequestMappingInfo getMappingForMethod(Method method, Class<?> handlerType) {
        RequestMappingInfo info = super.getMappingForMethod(method, handlerType);

        ApiVersion methodAnnotation = AnnotationUtils.findAnnotation(method, ApiVersion.class);
        ApiVersion typeAnnotation = AnnotationUtils.findAnnotation(handlerType, ApiVersion.class);

        if(methodAnnotation != null){
            RequestCondition<?> condition = getCustomMethodCondition(method);
            info = createApiVersionInfo(methodAnnotation, condition).combine(info);
        }
        if(typeAnnotation != null){
            RequestCondition<?> condition = getCustomTypeCondition(handlerType);
            info = createApiVersionInfo(typeAnnotation, condition).combine(info);
        }

        return info;
    }

    private RequestMappingInfo createApiVersionInfo(ApiVersion annotation, RequestCondition<?> customCondition){
        double[] values = annotation.value();
        String[] patterns = new String[values.length];
        for(int i=0; i<values.length; i++){
            // build the URL prefix
            patterns[i] = prefix + values[i];
        }

        return new RequestMappingInfo(
                new PatternsRequestCondition(patterns, getUrlPathHelper(), getPathMatcher(), useSuffixPatternMatch(), useTrailingSlashMatch(), getFileExtensions()),
                new RequestMethodsRequestCondition(),
                new ParamsRequestCondition(),
                new HeadersRequestCondition(),
                new ConsumesRequestCondition(),
                new ProducesRequestCondition(),
                customCondition
        );
    }


}
