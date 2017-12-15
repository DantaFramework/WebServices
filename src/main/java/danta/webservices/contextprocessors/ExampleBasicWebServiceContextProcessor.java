package danta.webservices.contextprocessors;

public class ExampleBasicWebServiceContextProcessor {}

/**
import danta.api.ExecutionContext;
import danta.api.exceptions.ProcessException;
import danta.webservices.WebServicesContentModel;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Service;

import static danta.Constants.HTML_EXT;

@Component
@Service
public class ExampleBasicWebServiceContextProcessor
        extends AbstractWebServiceContextProcessor {

    @Override
    public int priority() {
        return 0;
    }

    private static final String PAGE_PATH_SELECTOR = "page.path";

    @Override
    public void process(ExecutionContext executionContext, WebServicesContentModel contentModel)
            throws ProcessException {
        contentModel.set("testCP", "It works!");
        contentModel.set("content.test", "testhere");
        String pagePath = contentModel.getAsString(PAGE_PATH_SELECTOR);
        if(!pagePath.endsWith(HTML_EXT)) {
            contentModel.set(PAGE_PATH_SELECTOR, pagePath + HTML_EXT);
        }
    }
}
 */