package com.github.burningduck.cms.markdown

import info.magnolia.jcr.util.ContentMap
import info.magnolia.jcr.wrapper.HTMLEscapingNodeWrapper
import java.util.*
import javax.inject.Inject
import javax.jcr.Node

/**
 * Templating functions supporting markodwn.
 *
 *
 * Bridges between Magnolia Freemarker and Markdown rendering.
 * As Null handling in freemarker is realy not funny,
 * we try to return alternative "empty" values where possible.
 *
 *
 * HtmlEscaping:
 * Magnolia wraps nodes to provide additional functionality.
 * One of those wrappers is [HTMLEscapingNodeWrapper].
 * This decorator escapes HTML which is generally a good thing(tm),
 * but breaks rendering of Markdown.
 */
interface MarkdownTemplatingFunctions {

    /**
     * Get a property from a [ContentMap] and render the value as HTML.

     * @param contentMap   the contentMap, maybe null.
     * *
     * @param propertyName the name of the property, maybe null.
     * *
     * @return the rendered HTML string, or [.NO_VALUE]
     */
    fun renderToHtml(contentMap: ContentMap?, propertyName: String?): String

    /**
     * Gget a property from a [Node] and render the value as HTML.

     * @param contentNode  the node, maybe null.
     * *
     * @param propertyName the name of the property, maybe null.
     * *
     * @return the rendered HTML string, or [.NO_VALUE]
     */
    fun renderToHtml(contentNode: Node?, propertyName: String?): String

    /**
     * Render given markdown to HTML - return @NO_VALUE if markdown is blank.

     * @param markdownInput the markdown to render, maybe null.
     * *
     * @return the rendered HTML string, or [.NO_VALUE]
     */
    fun renderToHtml(markdownInput: String?): String

    /**
     * Return a new [ContentMap] without HtmlEscaping.

     * @param contentMap the content map, maybe null.
     * *
     * @return a new content map without HtmlEscaping or null.
     */
    fun removeHtmlEscapingWrapper(contentMap: ContentMap?): ContentMap?

    /**
     * Removes HtmlEscaping from the given node.

     * @param node the node, maybe null.
     * *
     * @return the node without HtmlEscaping or null.
     */
    fun removeHtmlEscapingWrapper(node: Node?): Node?

    companion object {
        /**
         * Register functions using this name.
         */
        val TEMPLATING_FUNCTIONS_NAME = "markdownfn"
        /**
         * Default value to return.
         */
        val NO_VALUE = ""
    }
}

fun Node.asContentMap() = ContentMap(this)


/**
 * Delegates markdown rendering to [MarkdownService]
 */
class DefaultMarkdownTemplatingFunctions @Inject
constructor(private val markdownService: MarkdownService) : MarkdownTemplatingFunctions {

    override fun renderToHtml(contentMap: ContentMap?, propertyName: String?): String = renderToHtml(contentMap?.jcrNode, propertyName)

    override fun renderToHtml(contentNode: Node?, propertyName: String?): String {
        return Optional
                .ofNullable(markdownService.renderToHtml(contentNode, propertyName))
                .orElse(MarkdownTemplatingFunctions.Companion.NO_VALUE)
    }

    override fun renderToHtml(markdownInput: String?): String {
        return Optional
                .ofNullable(markdownService.renderToHtml(markdownInput))
                .orElse(MarkdownTemplatingFunctions.Companion.NO_VALUE)
    }

    override fun removeHtmlEscapingWrapper(contentMap: ContentMap?): ContentMap? = removeHtmlEscapingWrapper(contentMap?.jcrNode)?.asContentMap()

    override fun removeHtmlEscapingWrapper(node: Node?): Node? = markdownService.removeHtmlEscapingWrapper(node)

}
