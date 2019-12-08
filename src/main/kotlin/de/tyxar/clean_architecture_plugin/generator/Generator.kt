/*
 * Copyright: Copyright (c) 2019 Arne Rantzen <arne@rantzen.net>
 * License: GPL-3
 * Last Edited: 07.12.19, 23:26
 */

package de.tyxar.clean_architecture_plugin.generator

import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import de.tyxar.clean_architecture_plugin.ui.Notifier
import org.eclipse.lsp4j.jsonrpc.messages.Either
import java.io.IOException

/**
 * Generator Factory to create structure
 */
interface Generator {
    companion object {
        /**
         * Creates a [parent] folder and its [children] in a given [folder].
         * [project] is needed for the notifications if there is an error or a warning situation.
         * @return [Either] false, if an error or warning occurred, or the [VirtualFile] of the created folders
         */
        fun createFolder(
            project: Project,
            folder: VirtualFile,
            parent: String,
            vararg children: String
        ): Either<Boolean, Map<String, VirtualFile>> {
            try {
                for (child in folder.children) {
                    if (child.name == parent) {
                        Notifier.warning(project, "Directory [$parent] already exists")
                        return Either.forLeft(false)
                    }
                }
                val mapOfFolder = mutableMapOf<String, VirtualFile>()
                mapOfFolder[parent] = folder.createChildDirectory(folder, parent)
                for (child in children) {
                    mapOfFolder[child] =
                        mapOfFolder[parent]?.createChildDirectory(mapOfFolder[parent], child) ?: throw IOException()
                }
                return Either.forRight(mapOfFolder)
            } catch (e: IOException) {
                Notifier.warning(project, "Couldn't create $parent directory")
                e.printStackTrace()
                return Either.forLeft(false)
            }
        }
    }
}