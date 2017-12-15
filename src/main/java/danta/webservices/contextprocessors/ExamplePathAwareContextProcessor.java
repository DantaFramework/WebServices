package danta.webservices.contextprocessors;

public class ExamplePathAwareContextProcessor {}

/**
import danta.api.ExecutionContext;
import danta.api.exceptions.ProcessException;
import danta.webservices.Constants;
import danta.webservices.WebServicesContentModel;
import danta.webservices.uri.URIPattern;
import danta.webservices.uri.URIResolveResult;
import danta.webservices.uri.URIResolver;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static danta.Constants.LOW_PRIORITY;
import static danta.webservices.Constants.MATCHED_URI_PATTERNS;
import static danta.webservices.Constants.URI_RESOLVER;

@Component
@Service
public class ExamplePathAwareContextProcessor
        extends AbstractResourcePathAwareContextProcessor {

    @Override
    protected List<String> pathPatterns() {
        return Arrays.asList(
                // For request of path: /api/docs/testname
                "/api/document/*", // No Match
                "/api/documents/*", // No Match
                "/api/docs/{name}", // Match
                "/api/docs/testname" // Best Match & will NOT have name variable
        );
    }

    @Override
    protected List<String> methods() {
        return Arrays.asList(Constants.HTTPMethod.POST);
    }

    @Override
    public int priority() {
        return LOW_PRIORITY;
    }

    @Override
    public void process(ExecutionContext executionContext, WebServicesContentModel contentModel)
            throws ProcessException {
        URIResolver resolver = (URIResolver) executionContext.get(URI_RESOLVER);
        ArrayList<URIPattern> patterns = (ArrayList<URIPattern>) executionContext.get(MATCHED_URI_PATTERNS);
        URIPattern bestMatch = resolver.find(patterns, URIResolver.MatchRule.BEST_MATCH);
        URIResolveResult result = resolver.resolve(bestMatch);
        contentModel.set("example.matchedPattern", bestMatch.toString());
        if (!result.names().isEmpty()) {
            for (String name : result.names()) {
                contentModel.set("example.vars." + name, result.get(name));
            }
        }
    }
}
*/