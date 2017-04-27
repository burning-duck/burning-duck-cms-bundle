package com.github.burningduck.cms.setup;

import com.github.burningduck.cms.markdown.MarkdownTemplatingFunctions;
import info.magnolia.module.DefaultModuleVersionHandler;
import info.magnolia.module.InstallContext;
import info.magnolia.module.delta.Task;
import info.magnolia.rendering.module.setup.InstallRendererContextAttributeTask;

import java.util.ArrayList;
import java.util.List;

/**
 * Setup handler for module.
 */
public class BurningDuckCmsVersionHandler extends DefaultModuleVersionHandler {

    public BurningDuckCmsVersionHandler() {
    }

    @Override
    protected List<Task> getExtraInstallTasks(InstallContext installContext) {
        final List<Task> tasks = new ArrayList<>();

        tasks.addAll(registerMarkdownTemplatingFunctions());

        return tasks;
    }

    /**
     *  Register markdown functions in custom renderer.
     *
     *  https://documentation.magnolia-cms.com/display/DOCS/Templating+functions#Templatingfunctions-Configurethefunctionsinarenderer
     *
     * @return a list of tasks.
     */
    private List<Task> registerMarkdownTemplatingFunctions() {
        final List<Task> tasks = new ArrayList<>();

        tasks.add(new InstallRendererContextAttributeTask("rendering", "freemarker", MarkdownTemplatingFunctions.TEMPLATING_FUNCTIONS_NAME, MarkdownTemplatingFunctions.class.getName()));
        tasks.add(new InstallRendererContextAttributeTask("site", "site", MarkdownTemplatingFunctions.TEMPLATING_FUNCTIONS_NAME, MarkdownTemplatingFunctions.class.getName()));

        return tasks;
    }


}
