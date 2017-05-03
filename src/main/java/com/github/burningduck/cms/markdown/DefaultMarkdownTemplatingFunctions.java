package com.github.burningduck.cms.markdown;

import info.magnolia.jcr.util.ContentMap;
import org.apache.commons.lang3.StringUtils;

import javax.inject.Inject;
import javax.jcr.Node;
import java.util.Optional;

/**
 * Delegates markdown rendering to {@link MarkdownService}
 */
public class DefaultMarkdownTemplatingFunctions implements MarkdownTemplatingFunctions {

    private final MarkdownService markdownService;

    @Inject
    public DefaultMarkdownTemplatingFunctions(final MarkdownService markdownService) {
        this.markdownService = markdownService;
    }

    @Override
    public String renderToHtml(final ContentMap contentMap, final String propertyName) {
        final ContentMap unwrappedContentMap = removeHtmlEscapingWrapper(contentMap);
        if (unwrappedContentMap == null || StringUtils.isBlank(propertyName)) {
            return NO_VALUE;
        }

        return Optional.ofNullable(unwrappedContentMap.get(propertyName))
                .map(String::valueOf)
                .map(this::renderToHtml)
                .orElse(NO_VALUE);
    }

    @Override
    public String renderToHtml(final Node contentNode, final String propertyName) {
        return Optional
                .ofNullable(markdownService.renderToHtml(contentNode, propertyName))
                .orElse(NO_VALUE);
    }


    @Override
    public String renderToHtml(final String markdownInput) {
       return Optional
                .ofNullable(markdownService.renderToHtml(markdownInput))
                .orElse(NO_VALUE);
    }

    @Override
    public ContentMap removeHtmlEscapingWrapper(final ContentMap contentMap) {
        if (contentMap == null) {
            return null;
        }
        final Node node = removeHtmlEscapingWrapper(contentMap.getJCRNode());
        if (node == null) {
            return null;
        }

        return new ContentMap(node);
    }

    @Override
    public Node removeHtmlEscapingWrapper(final Node node) {
        return markdownService.removeHtmlEscapingWrapper(node);
    }

}
