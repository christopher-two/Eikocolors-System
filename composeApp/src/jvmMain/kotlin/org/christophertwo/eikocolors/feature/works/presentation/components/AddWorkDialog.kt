package org.christophertwo.eikocolors.feature.works.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Solid
import compose.icons.fontawesomeicons.solid.*
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.christophertwo.eikocolors.feature.works.domain.model.Work
import org.christophertwo.eikocolors.feature.works.domain.model.WorkPriority
import org.christophertwo.eikocolors.feature.works.domain.model.WorkStatus
import java.util.*
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
@Composable
fun AddWorkDialog(
    onDismiss: () -> Unit,
    onSave: (Work) -> Unit
) {
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var clientId by remember { mutableStateOf("") }
    var clientName by remember { mutableStateOf("") }
    var status by remember { mutableStateOf(WorkStatus.PENDING) }
    var priority by remember { mutableStateOf(WorkPriority.MEDIUM) }
    var estimatedHours by remember { mutableStateOf("") }
    var notes by remember { mutableStateOf("") }

    // Date fields
    var dueDayOfMonth by remember { mutableStateOf("") }
    var dueMonth by remember { mutableStateOf("") }
    var dueYear by remember { mutableStateOf("") }

    val isFormValid = title.isNotBlank() &&
            description.isNotBlank() &&
            clientName.isNotBlank() &&
            dueDayOfMonth.isNotBlank() &&
            dueMonth.isNotBlank() &&
            dueYear.isNotBlank()

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            modifier = Modifier
                .width(600.dp)
                .heightIn(max = 700.dp),
            shape = RoundedCornerShape(16.dp),
            color = MaterialTheme.colorScheme.surface
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp)
            ) {
                // Header
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Nuevo Trabajo",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold
                    )

                    IconButton(onClick = onDismiss) {
                        Icon(
                            imageVector = FontAwesomeIcons.Solid.TimesCircle,
                            contentDescription = "Cerrar",
                            tint = MaterialTheme.colorScheme.onSurface,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }

                HorizontalDivider(modifier = Modifier.padding(vertical = 16.dp))

                // Form
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // Title
                    OutlinedTextField(
                        value = title,
                        onValueChange = { title = it },
                        label = { Text("Título *") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        leadingIcon = {
                            Icon(
                                imageVector = FontAwesomeIcons.Solid.Briefcase,
                                contentDescription = null,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    )

                    // Description
                    OutlinedTextField(
                        value = description,
                        onValueChange = { description = it },
                        label = { Text("Descripción *") },
                        modifier = Modifier.fillMaxWidth(),
                        minLines = 3,
                        maxLines = 5
                    )

                    // Client Name
                    OutlinedTextField(
                        value = clientName,
                        onValueChange = { clientName = it },
                        label = { Text("Nombre del Cliente *") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        leadingIcon = {
                            Icon(FontAwesomeIcons.Solid.User, contentDescription = null, modifier = Modifier.size(24.dp))
                        }
                    )

                    // Status
                    var expandedStatus by remember { mutableStateOf(false) }
                    Box {
                        OutlinedTextField(
                            value = status.name.replace("_", " "),
                            onValueChange = {},
                            label = { Text("Estado") },
                            readOnly = true,
                            modifier = Modifier.fillMaxWidth(),
                            leadingIcon = {
                                Icon(
                                    imageVector = FontAwesomeIcons.Solid.Tasks,
                                    contentDescription = null,
                                    modifier = Modifier.size(24.dp)
                                )
                            },
                            trailingIcon = {
                                IconButton(onClick = { expandedStatus = true }) {
                                    Icon(
                                        imageVector = FontAwesomeIcons.Solid.ChevronDown,
                                        contentDescription = null,
                                        modifier = Modifier.size(24.dp)
                                    )
                                }
                            }
                        )
                        DropdownMenu(
                            expanded = expandedStatus,
                            onDismissRequest = { expandedStatus = false }
                        ) {
                            WorkStatus.entries.forEach { statusOption ->
                                DropdownMenuItem(
                                    text = { Text(statusOption.name.replace("_", " ")) },
                                    onClick = {
                                        status = statusOption
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
                            value = priority.name,
                            onValueChange = {},
                            label = { Text("Prioridad") },
                            readOnly = true,
                            modifier = Modifier.fillMaxWidth(),
                            leadingIcon = {
                                Icon(FontAwesomeIcons.Solid.ExclamationCircle, contentDescription = null, modifier = Modifier.size(24.dp))
                            },
                            trailingIcon = {
                                IconButton(onClick = { expandedPriority = true }) {
                                    Icon(FontAwesomeIcons.Solid.ChevronDown, null, modifier = Modifier.size(24.dp))
                                }
                            }
                        )
                        DropdownMenu(
                            expanded = expandedPriority,
                            onDismissRequest = { expandedPriority = false }
                        ) {
                            WorkPriority.entries.forEach { priorityOption ->
                                DropdownMenuItem(
                                    text = { Text(priorityOption.name) },
                                    onClick = {
                                        priority = priorityOption
                                        expandedPriority = false
                                    }
                                )
                            }
                        }
                    }

                    // Due Date
                    Text(
                        text = "Fecha de entrega *",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                        fontWeight = FontWeight.Bold
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        OutlinedTextField(
                            value = dueDayOfMonth,
                            onValueChange = { if (it.length <= 2) dueDayOfMonth = it },
                            label = { Text("Día") },
                            modifier = Modifier.weight(1f),
                            singleLine = true
                        )
                        OutlinedTextField(
                            value = dueMonth,
                            onValueChange = { if (it.length <= 2) dueMonth = it },
                            label = { Text("Mes") },
                            modifier = Modifier.weight(1f),
                            singleLine = true
                        )
                        OutlinedTextField(
                            value = dueYear,
                            onValueChange = { if (it.length <= 4) dueYear = it },
                            label = { Text("Año") },
                            modifier = Modifier.weight(1f),
                            singleLine = true
                        )
                    }

                    // Estimated Hours
                    OutlinedTextField(
                        value = estimatedHours,
                        onValueChange = { estimatedHours = it },
                        label = { Text("Horas estimadas") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        leadingIcon = {
                            Icon(
                                imageVector = FontAwesomeIcons.Solid.Clock,
                                contentDescription = null,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    )

                    // Notes
                    OutlinedTextField(
                        value = notes,
                        onValueChange = { notes = it },
                        label = { Text("Notas") },
                        modifier = Modifier.fillMaxWidth(),
                        minLines = 3,
                        maxLines = 5
                    )
                }

                HorizontalDivider(modifier = Modifier.padding(vertical = 16.dp))

                // Actions
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    OutlinedButton(
                        onClick = onDismiss,
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Cancelar")
                    }

                    Button(
                        onClick = {
                            try {
                                val now = kotlin.time.Clock.System.now()
                                    .toLocalDateTime(TimeZone.currentSystemDefault())

                                val dueDate = kotlinx.datetime.LocalDateTime(
                                    year = dueYear.toInt(),
                                    month = kotlinx.datetime.Month(dueMonth.toInt()),
                                    dayOfMonth = dueDayOfMonth.toInt(),
                                    hour = 23,
                                    minute = 59
                                )

                                val newWork = Work(
                                    id = UUID.randomUUID().toString(),
                                    title = title,
                                    description = description,
                                    clientId = clientId.ifBlank { UUID.randomUUID().toString() },
                                    clientName = clientName,
                                    status = status,
                                    priority = priority,
                                    dueDate = dueDate,
                                    createdAt = now,
                                    updatedAt = now,
                                    estimatedHours = estimatedHours.toDoubleOrNull(),
                                    actualHours = null,
                                    notes = notes.ifBlank { null }
                                )

                                onSave(newWork)
                            } catch (_: Exception) {
                                // Handle error - could add a snackbar or error message
                            }
                        },
                        modifier = Modifier.weight(1f),
                        enabled = isFormValid
                    ) {
                        Icon(
                            imageVector = FontAwesomeIcons.Solid.Plus,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Crear Trabajo")
                    }
                }
            }
        }
    }
}
