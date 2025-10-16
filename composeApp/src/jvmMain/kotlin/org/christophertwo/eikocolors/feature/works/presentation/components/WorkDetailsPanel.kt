package org.christophertwo.eikocolors.feature.works.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Solid
import compose.icons.fontawesomeicons.solid.*
import org.christophertwo.eikocolors.feature.works.domain.model.Work
import org.christophertwo.eikocolors.feature.works.domain.model.WorkPriority
import org.christophertwo.eikocolors.feature.works.domain.model.WorkStatus

@Composable
fun WorkDetailsPanel(
    work: Work,
    onDismiss: () -> Unit,
    onUpdate: (Work) -> Unit,
    onDelete: (String) -> Unit
) {
    var isEditing by remember { mutableStateOf(false) }
    var editedWork by remember(work) { mutableStateOf(work) }

    Surface(
        modifier = Modifier
            .fillMaxHeight()
            .width(480.dp)
            .shadow(8.dp),
        color = MaterialTheme.colorScheme.surface,
        tonalElevation = 2.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
        ) {
            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = if (isEditing) "Editar Trabajo" else "Detalles del Trabajo",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }

                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    IconButton(onClick = {
                        if (isEditing) {
                            editedWork = work
                        }
                        isEditing = !isEditing
                    }) {
                        Icon(
                            imageVector = if (isEditing) FontAwesomeIcons.Solid.Times else FontAwesomeIcons.Solid.Edit,
                            contentDescription = if (isEditing) "Cancelar edición" else "Editar",
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(20.dp)
                        )
                    }

                    IconButton(onClick = onDismiss) {
                        Icon(
                            imageVector = FontAwesomeIcons.Solid.TimesCircle,
                            contentDescription = "Cerrar",
                            tint = MaterialTheme.colorScheme.onSurface,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }

            HorizontalDivider(
                modifier = Modifier.padding(vertical = 20.dp),
                color = MaterialTheme.colorScheme.outlineVariant
            )

            // Content
            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                if (isEditing) {
                    WorkEditForm(
                        work = editedWork,
                        onWorkChange = { editedWork = it }
                    )
                } else {
                    WorkDetailsView(work = work)
                }
            }

            HorizontalDivider(
                modifier = Modifier.padding(vertical = 20.dp),
                color = MaterialTheme.colorScheme.outlineVariant
            )

            // Actions
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                if (isEditing) {
                    OutlinedButton(
                        onClick = {
                            editedWork = work
                            isEditing = false
                        },
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Cancelar")
                    }

                    Button(
                        onClick = {
                            onUpdate(editedWork)
                            isEditing = false
                        },
                        modifier = Modifier.weight(1f)
                    ) {
                        Icon(
                            imageVector = FontAwesomeIcons.Solid.Save,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Guardar")
                    }
                } else {
                    OutlinedButton(
                        onClick = {
                            onDelete(work.id)
                            onDismiss()
                        },
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = MaterialTheme.colorScheme.error
                        )
                    ) {
                        Icon(
                            imageVector = FontAwesomeIcons.Solid.Trash,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Eliminar")
                    }

                    Button(
                        onClick = onDismiss,
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Cerrar")
                    }
                }
            }
        }
    }
}

@Composable
private fun WorkDetailsView(work: Work) {
    Column(verticalArrangement = Arrangement.spacedBy(20.dp)) {
        // Title
        DetailSection(
            icon = FontAwesomeIcons.Solid.Briefcase,
            label = "Título",
            value = work.title
        )

        // Description
        DetailSection(
            icon = FontAwesomeIcons.Solid.AlignLeft,
            label = "Descripción",
            value = work.description
        )

        // Client
        DetailSection(
            icon = FontAwesomeIcons.Solid.User,
            label = "Cliente",
            value = work.clientName
        )

        // Status and Priority
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Icon(
                        imageVector = FontAwesomeIcons.Solid.InfoCircle,
                        contentDescription = null,
                        modifier = Modifier.size(14.dp),
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = "Estado",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                        fontWeight = FontWeight.Bold
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                StatusChip(status = work.status)
            }

            Column(modifier = Modifier.weight(1f)) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Icon(
                        imageVector = FontAwesomeIcons.Solid.Flag,
                        contentDescription = null,
                        modifier = Modifier.size(14.dp),
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = "Prioridad",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                        fontWeight = FontWeight.Bold
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                PriorityBadge(priority = work.priority)
            }
        }

        // Due Date
        DetailSection(
            icon = FontAwesomeIcons.Solid.Calendar,
            label = "Fecha de entrega",
            value = "${work.dueDate.date.dayOfMonth}/${work.dueDate.date.month}/${work.dueDate.date.year}"
        )

        // Hours
        if (work.estimatedHours != null || work.actualHours != null) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                work.estimatedHours?.let { hours ->
                    DetailSection(
                        icon = FontAwesomeIcons.Solid.Clock,
                        label = "Horas estimadas",
                        value = "$hours hrs",
                        modifier = Modifier.weight(1f)
                    )
                }

                work.actualHours?.let { hours ->
                    DetailSection(
                        icon = FontAwesomeIcons.Solid.Hourglass,
                        label = "Horas reales",
                        value = "$hours hrs",
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }

        // Notes
        work.notes?.let { notes ->
            if (notes.isNotBlank()) {
                DetailSection(
                    icon = FontAwesomeIcons.Solid.StickyNote,
                    label = "Notas",
                    value = notes
                )
            }
        }
    }
}

@Composable
private fun DetailSection(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.size(14.dp),
                tint = MaterialTheme.colorScheme.primary
            )
            Text(
                text = label,
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                fontWeight = FontWeight.Bold
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = value,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

@Composable
private fun WorkEditForm(
    work: Work,
    onWorkChange: (Work) -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(20.dp)) {
        // Title
        OutlinedTextField(
            value = work.title,
            onValueChange = { onWorkChange(work.copy(title = it)) },
            label = { Text("Título") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            leadingIcon = {
                Icon(
                    imageVector = FontAwesomeIcons.Solid.Briefcase,
                    contentDescription = null,
                    modifier = Modifier.size(18.dp)
                )
            }
        )

        // Description
        OutlinedTextField(
            value = work.description,
            onValueChange = { onWorkChange(work.copy(description = it)) },
            label = { Text("Descripción") },
            modifier = Modifier.fillMaxWidth(),
            minLines = 3,
            maxLines = 5,
            leadingIcon = {
                Icon(
                    imageVector = FontAwesomeIcons.Solid.AlignLeft,
                    contentDescription = null,
                    modifier = Modifier.size(18.dp)
                )
            }
        )

        // Status
        var expandedStatus by remember { mutableStateOf(false) }
        Box {
            OutlinedTextField(
                value = work.status.name.replace("_", " "),
                onValueChange = {},
                label = { Text("Estado") },
                readOnly = true,
                modifier = Modifier.fillMaxWidth(),
                leadingIcon = {
                    Icon(
                        imageVector = FontAwesomeIcons.Solid.InfoCircle,
                        contentDescription = null,
                        modifier = Modifier.size(18.dp)
                    )
                },
                trailingIcon = {
                    IconButton(onClick = { expandedStatus = true }) {
                        Icon(FontAwesomeIcons.Solid.ChevronDown, null)
                    }
                }
            )
            DropdownMenu(
                expanded = expandedStatus,
                onDismissRequest = { expandedStatus = false }
            ) {
                WorkStatus.entries.forEach { status ->
                    DropdownMenuItem(
                        text = { Text(status.name.replace("_", " ")) },
                        onClick = {
                            onWorkChange(work.copy(status = status))
                            expandedStatus = false
                        }
                    )
                }
            }
        }

        // Priority
        var expandedPriority by remember { mutableStateOf(false) }
        Box {
            OutlinedTextField(
                value = work.priority.name,
                onValueChange = {},
                label = { Text("Prioridad") },
                readOnly = true,
                modifier = Modifier.fillMaxWidth(),
                leadingIcon = {
                    Icon(
                        imageVector = FontAwesomeIcons.Solid.Flag,
                        contentDescription = null,
                        modifier = Modifier.size(18.dp)
                    )
                },
                trailingIcon = {
                    IconButton(onClick = { expandedPriority = true }) {
                        Icon(
                            imageVector = FontAwesomeIcons.Solid.ChevronDown,
                            contentDescription = null,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }
            )
            DropdownMenu(
                expanded = expandedPriority,
                onDismissRequest = { expandedPriority = false }
            ) {
                WorkPriority.entries.forEach { priority ->
                    DropdownMenuItem(
                        text = { Text(priority.name) },
                        onClick = {
                            onWorkChange(work.copy(priority = priority))
                            expandedPriority = false
                        }
                    )
                }
            }
        }

        // Hours
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OutlinedTextField(
                value = work.estimatedHours?.toString() ?: "",
                onValueChange = {
                    onWorkChange(work.copy(estimatedHours = it.toDoubleOrNull()))
                },
                label = { Text("Horas estimadas") },
                modifier = Modifier.weight(1f),
                singleLine = true,
                leadingIcon = {
                    Icon(
                        imageVector = FontAwesomeIcons.Solid.Clock,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp)
                    )
                }
            )

            OutlinedTextField(
                value = work.actualHours?.toString() ?: "",
                onValueChange = {
                    onWorkChange(work.copy(actualHours = it.toDoubleOrNull()))
                },
                label = { Text("Horas reales") },
                modifier = Modifier.weight(1f),
                singleLine = true,
                leadingIcon = {
                    Icon(
                        imageVector = FontAwesomeIcons.Solid.Hourglass,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp)
                    )
                }
            )
        }

        // Notes
        OutlinedTextField(
            value = work.notes ?: "",
            onValueChange = { onWorkChange(work.copy(notes = it)) },
            label = { Text("Notas") },
            modifier = Modifier.fillMaxWidth(),
            minLines = 3,
            maxLines = 5,
            leadingIcon = {
                Icon(
                    imageVector = FontAwesomeIcons.Solid.StickyNote,
                    contentDescription = null,
                    modifier = Modifier.size(18.dp)
                )
            }
        )
    }
}

@Composable
private fun StatusChip(status: WorkStatus) {
    val (backgroundColor, textColor, icon) = StatusTriple(status)
    Row(
        modifier = Modifier
            .background(backgroundColor, RoundedCornerShape(8.dp))
            .padding(horizontal = 12.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier.size(14.dp),
            tint = textColor
        )
        Text(
            text = status.name.replace("_", " "),
            style = MaterialTheme.typography.labelMedium,
            color = textColor,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Composable
private fun PriorityBadge(priority: WorkPriority) {
    val (color, icon) = when (priority) {
        WorkPriority.LOW -> MaterialTheme.colorScheme.primaryContainer to FontAwesomeIcons.Solid.ArrowDown
        WorkPriority.MEDIUM -> MaterialTheme.colorScheme.secondaryContainer to FontAwesomeIcons.Solid.Minus
        WorkPriority.HIGH -> MaterialTheme.colorScheme.errorContainer to FontAwesomeIcons.Solid.ArrowUp
        WorkPriority.URGENT -> MaterialTheme.colorScheme.errorContainer to FontAwesomeIcons.Solid.ExclamationTriangle
    }

    Row(
        modifier = Modifier
            .background(color, RoundedCornerShape(8.dp))
            .padding(horizontal = 12.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier.size(14.dp),
            tint = MaterialTheme.colorScheme.onSurface
        )
        Text(
            text = priority.name,
            style = MaterialTheme.typography.labelMedium,
            fontWeight = FontWeight.Bold
        )
    }
}
