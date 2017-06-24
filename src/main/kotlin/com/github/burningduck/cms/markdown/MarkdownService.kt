package com.github.burningduck.cms.markdown

import com.vladsch.flexmark.html.HtmlRenderer
import com.vladsch.flexmark.parser.Parser
import info.magnolia.jcr.util.NodeUtil
import info.magnolia.jcr.util.PropertyUtil
import info.magnolia.jcr.wrapper.DelegateNodeWrapper
import info.magnolia.jcr.wrapper.HTMLEscapingNodeWrapper
import javax.jcr.Node

/**
 * Render Markdown to HTML.
 */
interface MarkdownService {

    /**
     * Get a property from a [Node] and render the value as HTML.
     *
     * @param contentNode  the node.
     * @param propertyName the name of the property.
     * @return the rendered HTML string.
     */
    fun renderToHtml(contentNode: Node?, propertyName: String?): String?

    /**
     * Render given markdown to HTML .

     * @param markdownInput the markdown to render.
     * @return the rendered HTML string.
     */
    fun renderToHtml(markdownInput: String?): String?

    /**
     * Removes HtmlEscaping from the given node.

     * @param node the node.
     * @return the node without HtmlEscaping ..
     */
    fun removeHtmlEscapingWrapper(node: Node?): Node?


}

/**
 * Extension functions used in this Service.
 * Maybe move to separate Extension file if proven to be helpfull
 */

/**
 * Remove all Wrappers of the given type
 */
inline fun <reified T : DelegateNodeWrapper> Node.deepUnwrapAll(): Node = NodeUtil.deepUnwrapAll(this, T::class.java)

/**
 * Get the String value of a property
 */
fun Node.getPropertyString(propertyName : String) : String? = PropertyUtil.getString(this, propertyName)


/**
 * Defaul implementation of [MarkdownService].
 */
class ConfiguredMarkdownService : MarkdownService {

    override fun renderToHtml(contentNode: Node?, propertyName: String?): String? {
        val unwrappedNode = removeHtmlEscapingWrapper(contentNode)
        if (unwrappedNode != null && propertyName != null) {
            if (unwrappedNode.hasProperty(propertyName)) {
                return renderToHtml(unwrappedNode.getPropertyString(propertyName))
            }
        }
        return null
    }

    override fun renderToHtml(markdownInput: String?): String? {
        if (markdownInput != null) {
            val renderer = HtmlRenderer.builder().build()
            val parser = Parser.builder().build()

            return renderer.render(parser.parse(markdownInput!!))
        }
        return null
    }

    override fun removeHtmlEscapingWrapper(node: Node?): Node? = node?.deepUnwrapAll<HTMLEscapingNodeWrapper>()
}
