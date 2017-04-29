package com.github.burningduck.cms.markdown;

import javax.jcr.Node;

/**
 * Render Markdown to HTML.
 */
public interface MarkdownService {

    /**
     * Get a property from a {@link Node} and render the value as HTML.
     *
     * @param contentNode  the node, maybe null.
     * @param propertyName the name of the property, maybe null.
     * @return the rendered HTML string, or null
     */
    String renderToHtml(final Node contentNode, final String propertyName);

    /**
     * Render given markdown to HTML .
     *
     * @param markdownInput the markdown to render, maybe null.
     * @return the rendered HTML string, or null.
     */
    String renderToHtml(final String markdownInput);

    /**
     * Removes HtmlEscaping from the given node.
     *
     * @param node the node, maybe null.
     * @return the node without HtmlEscaping or null.
     */
    Node removeHtmlEscapingWrapper(final Node node);


}
