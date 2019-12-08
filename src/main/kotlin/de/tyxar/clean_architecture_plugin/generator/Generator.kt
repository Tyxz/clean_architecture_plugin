/*
 * Copyright: Copyright (c) 2019 Arne Rantzen <arne@rantzen.net>
 * License: GPL-3
 * Last Edited: 07.12.19, 23:26
 */

package de.tyxar.clean_architecture_plugin.generator

import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import de.tyxar.clean_architecture_plugin.ui.Notifier
import java.io.IOException

/**
 * Generator Factory to create structure
 */
interface Generator {
    companion object {
        /**
         * Creates a [parent] folder and its [children] in a given [folder].
         * [project] is needed for the notifications if there is an error or a warning situation.
         * @return false, if something went wrong.
         */
        fun createFolder(project: Project, folder: VirtualFile, parent: String, vararg children: String): Boolean {
            try {
                for (child in folder.children) {
                    if (child.name == parent) {
                        Notifier.warning(project, "Directory [$parent] already exists")
                        return false
                    }
                }
                val parentFolder = folder.createChildDirectory(folder, parent)
                for (child in children) {
                    parentFolder.createChildDirectory(parentFolder, child)
                }
                return true
            } catch (e: IOException) {
                Notifier.warning(project, "Couldn't create $parent directory")
                e.printStackTrace()
                return false
            }
        }

        /**
         * Creates a [root] folder in a given [folder].
         * [project] is needed for the notifications if there is an error or a warning situation.
         * @return the [VirtualFile] of the newly created folder.
         */
        fun createRoot(project: Project, folder: VirtualFile, root: String): VirtualFile? {
            try {
                for (child in folder.children) {
                    if (child.name == root) {
                        Notifier.warning(project, "Directory [$root] already exists")
                        return null
                    }
                }
                return folder.createChildDirectory(folder, root)
            } catch (e: IOException) {
                Notifier.warning(project, "Couldn't create $root directory")
                e.printStackTrace()
                return null
            }
        }
    }
}