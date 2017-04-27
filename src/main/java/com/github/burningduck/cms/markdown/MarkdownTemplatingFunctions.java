package com.github.burningduck.cms.markdown;

import info.magnolia.jcr.util.ContentMap;
import info.magnolia.jcr.util.NodeUtil;
import info.magnolia.jcr.util.PropertyUtil;
import info.magnolia.jcr.wrapper.HTMLEscapingNodeWrapper;
import org.apache.commons.lang3.StringUtils;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;

import javax.jcr.Node;
import java.util.Optional;

/**
 * Templating functions supporting markodwn.
 * <p>
 * Bridges between Magnolia Freemarker and Markdown rendering.
 * As Null handling in freemarker is realy not funny,
 * we try to return alternative "empty" values where possible.
 * <p>
 * HtmlEscaping:
 * Magnolia wraps nodes to provide additional functionality.
 * One of those wrappers is {@link HTMLEscapingNodeWrapper}.
 * This decorator escapes HTML which is generally a good thing(tm),
 * but breaks rendering of Markdown.
 */
public class MarkdownTemplatingFunctions {

    /**
     * Register functions using this name.
     */
    public static final String TEMPLATING_FUNCTIONS_NAME = "markdownfn";

    /**
     * Default value to return.
     */
    public static final String NO_VALUE = "";

    /**
     * Get a property from a {@link ContentMap} and render the value as HTML.
     *
     * @param contentMap   the contentMap, maybe null.
     * @param propertyName the name of the property, maybe null.
     * @return the rendered HTML string, or {@link #NO_VALUE}
     */
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

    /**
     * Gget a property from a {@link Node} and render the value as HTML.
     *
     * @param contentNode  the node, maybe null.
     * @param propertyName the name of the property, maybe null.
     * @return the rendered HTML string, or {@link #NO_VALUE}
     */
    public String renderToHtml(final Node contentNode, final String propertyName) {
        final Node unwrappedNode = removeHtmlEscapingWrapper(contentNode);
        if (unwrappedNode == null || StringUtils.isBlank(propertyName)) {
            return NO_VALUE;
        }

        return Optional.ofNullable(PropertyUtil.getString(unwrappedNode, propertyName))
                .map(this::renderToHtml)
                .orElse(NO_VALUE);
    }


    /**
     * Render given markdown to HTML - return @NO_VALUE if markdown is blank.
     *
     * @param markdownInput the markdown to render, maybe null.
     * @return the rendered HTML string, or {@link #NO_VALUE}
     */
    public String renderToHtml(final String markdownInput) {
        if (StringUtils.isBlank(markdownInput)) {
            return NO_VALUE;
        }
        final HtmlRenderer renderer = HtmlRenderer.builder().build();
        final Parser parser = Parser.builder().build();

        return renderer.render(parser.parse(markdownInput));
    }

    /**
     * Return a new {@link ContentMap} without HtmlEscaping.
     *
     * @param contentMap the content map, maybe null.
     * @return a new content map without HtmlEscaping or null.
     */
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

    /**
     * Removes HtmlEscaping from the given node.
     *
     * @param node the node, maybe null.
     * @return the node without HtmlEscaping or null.
     */
    public Node removeHtmlEscapingWrapper(final Node node) {
        if (node == null) {
            return null;
        }

        return NodeUtil.deepUnwrapAll(node, HTMLEscapingNodeWrapper.class);
    }

}
