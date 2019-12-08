/*
 * Copyright: Copyright (c) 2019 Arne Rantzen <arne@rantzen.net>
 * License: GPL-3
 * Last Edited: 08.12.19, 00:03
 */

package de.tyxar.clean_architecture_plugin.action

import com.intellij.openapi.actionSystem.*
import com.intellij.openapi.command.WriteCommandAction
import de.tyxar.clean_architecture_plugin.generator.Generator
import de.tyxar.clean_architecture_plugin.ui.FeatureDialog

/**
 * Flutter action in the context menu
 *
 * This class will call the dialog and generate the Flutter Clean-Architecture structure
 */
class ActionGenerateFlutter : AnAction() {
    /**
     * Is called by the context action menu entry with an [actionEvent]
     */
    override fun actionPerformed(actionEvent: AnActionEvent) {
        val dialog = FeatureDialog(actionEvent.project)
        if (dialog.showAndGet()) {
            generate(actionEvent.dataContext, dialog.getName(), dialog.splitSource())
        }
    }

    /**
     * Generates the Flutter Clean-Architecture structure in a [dataContext].
     * If a [root] String is provided, it will create the structure in a new folder.
     */
    private fun generate(dataContext: DataContext, root: String?, splitSource: Boolean?) {
        val project = CommonDataKeys.PROJECT.getData(dataContext) ?: return
        val selected = PlatformDataKeys.VIRTUAL_FILE.getData(dataContext) ?: return

        var folder = if (selected.isDirectory) selected else selected.parent
        WriteCommandAction.runWriteCommandAction(project) {
            if (root != null && root.isNotBlank()) {
                val result = Generator.createFolder(
                    project, folder, root
                )
                if (result.isLeft) return@runWriteCommandAction
                folder = result.right[root]
            }
            if (splitSource != null && splitSource) {
                var mapOrFalse = Generator.createFolder(
                    project, folder,
                    "data",
                    "repositories"
                )
                if (mapOrFalse.isLeft) return@runWriteCommandAction
                mapOrFalse.right["data"]?.let {
                    Generator.createFolder(
                        project, it,
                        "local",
                        "models", "data_sources"
                    )
                    Generator.createFolder(
                        project, it,
                        "remote",
                        "models", "data_sources"
                    )
                }
            } else {
                Generator.createFolder(
                    project, folder,
                    "data",
                    "repositories", "data_sources", "models"
                )
            }
            Generator.createFolder(
                project, folder,
                "domain",
                "repositories", "use_cases", "entities"
            )
            Generator.createFolder(
                project, folder,
                "presentation",
                "manager", "pages", "widgets"
            )
        }
    }
}