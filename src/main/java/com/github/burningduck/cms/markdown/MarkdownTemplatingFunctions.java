package com.github.burningduck.cms.markdown;

import info.magnolia.jcr.util.ContentMap;
import info.magnolia.jcr.wrapper.HTMLEscapingNodeWrapper;

import javax.jcr.Node;

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
public interface MarkdownTemplatingFunctions {
    /**
     * Register functions using this name.
     */
    String TEMPLATING_FUNCTIONS_NAME = "markdownfn";
    /**
     * Default value to return.
     */
    String NO_VALUE = "";

    /**
     * Get a property from a {@link ContentMap} and render the value as HTML.
     *
     * @param contentMap   the contentMap, maybe null.
     * @param propertyName the name of the property, maybe null.
     * @return the rendered HTML string, or {@link #NO_VALUE}
     */
    String renderToHtml(ContentMap contentMap, String propertyName);

    /**
     * Gget a property from a {@link Node} and render the value as HTML.
     *
     * @param contentNode  the node, maybe null.
     * @param propertyName the name of the property, maybe null.
     * @return the rendered HTML string, or {@link #NO_VALUE}
     */
    String renderToHtml(Node contentNode, String propertyName);

    /**
     * Render given markdown to HTML - return @NO_VALUE if markdown is blank.
     *
     * @param markdownInput the markdown to render, maybe null.
     * @return the rendered HTML string, or {@link #NO_VALUE}
     */
    String renderToHtml(String markdownInput);

    /**
     * Return a new {@link ContentMap} without HtmlEscaping.
     *
     * @param contentMap the content map, maybe null.
     * @return a new content map without HtmlEscaping or null.
     */
    ContentMap removeHtmlEscapingWrapper(ContentMap contentMap);

    /**
     * Removes HtmlEscaping from the given node.
     *
     * @param node the node, maybe null.
     * @return the node without HtmlEscaping or null.
     */
    Node removeHtmlEscapingWrapper(Node node);
}
