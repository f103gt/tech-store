package com.store.config;

import com.store.config.communication.MailConfig;
import com.store.config.database.HibernateConfig;
import com.store.config.database.JpaConfig;
import com.store.config.web.resource.WebMvcConfig;
import jakarta.servlet.MultipartConfigElement;
import jakarta.servlet.ServletRegistration;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

@Configuration
public class MvcInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[] {MailConfig.class, HibernateConfig.class, JpaConfig.class};
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[] {WebMvcConfig.class};
    }

    @Override
    protected String[] getServletMappings() {
        return new String[] {"/"};
    }

    /*@Override
    protected void customizeRegistration(ServletRegistration.Dynamic registration) {
        registration.setMultipartConfig(
                // Maybe use more sophisticated configuration than this:
                new MultipartConfigElement(""));
    }*/

    @Override
    protected void customizeRegistration(ServletRegistration.Dynamic registration) {
        // Set the directory location where files will be stored temporarily on disk.
        String location = ""; // Replace this with your desired temporary directory.

        // Set the maximum file size that can be uploaded (10 MB in this case).
        long maxFileSize = 10 * 1024 * 1024;

        // Set the maximum request size (20 MB in this case).
        long maxRequestSize = 20 * 1024 * 1024;

        // Set the threshold size at which files will be written to disk (5 MB in this case).
        int fileSizeThreshold = 5 * 1024 * 1024;

        MultipartConfigElement multipartConfigElement = new MultipartConfigElement(
                location,
                maxFileSize,
                maxRequestSize,
                fileSizeThreshold
        );
        registration.setMultipartConfig(multipartConfigElement);
    }
}

