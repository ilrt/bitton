package org.ilrt.wf.facets.web.freemarker;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.ilrt.wf.facets.web.spring.controllers.AbstractController;
import org.jmock.integration.junit4.JMock;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertTrue;

public class FreeMarkerTest {

    @Test
    public void test() throws IOException, TemplateException {

        Configuration configuration = new Configuration();
        configuration.setDirectoryForTemplateLoading(new File(getClass().getResource("/templates/")
                .getFile()));
        configuration.setObjectWrapper(new DefaultObjectWrapper());


        ModelAndView mav = new ModelAndView("mainView");
        mav.addObject(AbstractController.CONTEXT_PATH_KEY, "/resrev");
        //mav.addObject(FACET_VIEW_KEY, facetView);
        mav.addObject("message", "Hello World!");
        //return mav;

        //Map<String, String> mav = new HashMap<String, String>();
        //mav.put("message", "Hello!");

        Template template = configuration.getTemplate("mainView.ftl");

        Writer writer = new OutputStreamWriter(System.out);

        template.process(mav.getModel(), writer);
        writer.flush();

        assertTrue(true);
    }

}
