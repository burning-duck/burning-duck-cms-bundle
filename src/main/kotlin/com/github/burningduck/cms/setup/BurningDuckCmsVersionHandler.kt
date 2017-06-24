package com.github.burningduck.cms.setup

import com.github.burningduck.cms.markdown.MarkdownTemplatingFunctions
import info.magnolia.module.DefaultModuleVersionHandler
import info.magnolia.module.InstallContext
import info.magnolia.module.delta.Task
import info.magnolia.rendering.module.setup.InstallRendererContextAttributeTask
import java.util.*

/**
 * Setup handler for module.
 */
class BurningDuckCmsVersionHandler : DefaultModuleVersionHandler() {

    override fun getExtraInstallTasks(installContext: InstallContext?): List<Task> {
        val tasks = ArrayList<Task>()

        tasks.addAll(registerMarkdownTemplatingFunctions())

        return tasks
    }

    /**
     * Register markdown functions in custom renderer.

     * https://documentation.magnolia-cms.com/display/DOCS/Templating+functions#Templatingfunctions-Configurethefunctionsinarenderer

     * @return a list of tasks.
     */
    private fun registerMarkdownTemplatingFunctions(): List<Task> {
        val tasks = ArrayList<Task>()

        tasks.add(InstallRendererContextAttributeTask("rendering", "freemarker", MarkdownTemplatingFunctions.TEMPLATING_FUNCTIONS_NAME, MarkdownTemplatingFunctions::class.java.name))
        tasks.add(InstallRendererContextAttributeTask("site", "site", MarkdownTemplatingFunctions.TEMPLATING_FUNCTIONS_NAME, MarkdownTemplatingFunctions::class.java.name))

        return tasks
    }


}
