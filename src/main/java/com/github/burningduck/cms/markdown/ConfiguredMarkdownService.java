package com.github.burningduck.cms.markdown;

import info.magnolia.jcr.util.NodeUtil;
import info.magnolia.jcr.util.PropertyUtil;
import info.magnolia.jcr.wrapper.HTMLEscapingNodeWrapper;
import org.apache.commons.lang3.StringUtils;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;

import javax.jcr.Node;
import java.util.Optional;

/**
 * Defaul implementation of {@link MarkdownService}.
 */
public class ConfiguredMarkdownService implements MarkdownService {

    public ConfiguredMarkdownService() {
    }

    @Override
    public String renderToHtml(Node contentNode, String propertyName) {
        final Node unwrappedNode = removeHtmlEscapingWrapper(contentNode);
        if (unwrappedNode == null || StringUtils.isBlank(propertyName)) {
            return null;
        }

        return Optional.ofNullable(PropertyUtil.getString(unwrappedNode, propertyName))
                .map(this::renderToHtml)
                .orElse(null);
    }

    @Override
    public String renderToHtml(String markdownInput) {
        if (StringUtils.isBlank(markdownInput)) {
            return null;
        }
        final HtmlRenderer renderer = HtmlRenderer.builder().build();
        final Parser parser = Parser.builder().build();

        return renderer.render(parser.parse(markdownInput));

    }

    @Override
    public Node removeHtmlEscapingWrapper(Node node) {
        if (node == null) {
            return null;
        }

        return NodeUtil.deepUnwrapAll(node, HTMLEscapingNodeWrapper.class);
    }
}
