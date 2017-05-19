package pl.bTech.carsappII;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import pl.bTech.carsappII.utilities.GeneralConfig;

/**
 * @author Kamil Radek<kamil.radek@btech.pl>
 */
public class Initializer extends AbstractAnnotationConfigDispatcherServletInitializer {
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[]{GeneralConfig.class};
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return null;
    }

    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }
}
