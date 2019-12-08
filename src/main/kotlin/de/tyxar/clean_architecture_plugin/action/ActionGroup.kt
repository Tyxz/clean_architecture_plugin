/*
 * Copyright: Copyright (c) 2019 Arne Rantzen <arne@rantzen.net>
 * License: GPL-3
 * Last Edited: 07.12.19, 22:13
 */

package de.tyxar.clean_architecture_plugin.action

import com.intellij.icons.AllIcons
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.actionSystem.DefaultActionGroup


/**
 * Defines the ActionGroup to be only visible when parent is a [PSI_ELEMENT]
 */
class ActionGroup : DefaultActionGroup() {
    override fun update(event: AnActionEvent) {
        // Enable/disable depending on whether user is editing
        val psi = event.getData(CommonDataKeys.PSI_ELEMENT)
        //event.presentation.isEnabled = project != null
        // Always make visible.
        event.presentation.isVisible = psi != null
        // Take this opportunity to set an icon for the menu entry.
        event.presentation.icon = AllIcons.Actions.NewFolder
    }
}