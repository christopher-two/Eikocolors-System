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
import org.christophertwo.eikocolors.feature.clients.domain.model.Client
import org.christophertwo.eikocolors.feature.clients.domain.model.ClientStatus
import org.christophertwo.eikocolors.feature.works.domain.model.Work
import org.christophertwo.eikocolors.feature.works.domain.model.WorkPriority
import org.christophertwo.eikocolors.feature.works.domain.model.WorkStatus
import java.util.*
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
@Composable
fun AddWorkDialog(
    availableClients: List<Client>,
    onDismiss: () -> Unit,
    onSave: (Work) -> Unit,
    onSaveClient: (Client) -> Unit
) {
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var selectedClient by remember { mutableStateOf<Client?>(null) }
    var status by remember { mutableStateOf(WorkStatus.PENDING) }
    var priority by remember { mutableStateOf(WorkPriority.MEDIUM) }
    var estimatedHours by remember { mutableStateOf("") }
    var notes by remember { mutableStateOf("") }
    var showClientSelector by remember { mutableStateOf(false) }
    var showNewClientDialog by remember { mutableStateOf(false) }
    var clientSearchQuery by remember { mutableStateOf("") }

    // Date fields
    var dueDayOfMonth by remember { mutableStateOf("") }
    var dueMonth by remember { mutableStateOf("") }
    var dueYear by remember { mutableStateOf("") }

    val filteredClients = remember(clientSearchQuery, availableClients) {
        if (clientSearchQuery.isBlank()) {
            availableClients
        } else {
            availableClients.filter {
                it.name.contains(clientSearchQuery, ignoreCase = true) ||
                        it.email.contains(clientSearchQuery, ignoreCase = true) ||
                        it.company?.contains(clientSearchQuery, ignoreCase = true) == true
            }
        }
    }

    val isFormValid = title.isNotBlank() &&
            description.isNotBlank() &&
            selectedClient != null &&
            dueDayOfMonth.isNotBlank() &&
            dueMonth.isNotBlank() &&
            dueYear.isNotBlank()

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            modifier = Modifier
                .width(650.dp)
                .heightIn(max = 750.dp),
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
                            imageVector = FontAwesomeIcons.Solid.Times,
                            contentDescription = "Cerrar",
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

                    // Client Selector
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
                        )
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "Cliente *",
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold
                                )

                                TextButton(
                                    onClick = { showNewClientDialog = true }
                                ) {
                                    Icon(
                                        imageVector = FontAwesomeIcons.Solid.UserPlus,
                                        contentDescription = null,
                                        modifier = Modifier.size(24.dp)
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text("Nuevo Cliente")
                                }
                            }

                            if (selectedClient != null) {
                                Card(
                                    modifier = Modifier.fillMaxWidth(),
                                    colors = CardDefaults.cardColors(
                                        containerColor = MaterialTheme.colorScheme.primaryContainer
                                    )
                                ) {
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(12.dp),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Column(modifier = Modifier.weight(1f)) {
                                            Text(
                                                text = selectedClient!!.name,
                                                style = MaterialTheme.typography.titleMedium,
                                                fontWeight = FontWeight.Bold,
                                                color = MaterialTheme.colorScheme.onPrimaryContainer
                                            )
                                            if (selectedClient!!.company != null) {
                                                Text(
                                                    text = selectedClient!!.company!!,
                                                    style = MaterialTheme.typography.bodyMedium,
                                                    color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.7f)
                                                )
                                            }
                                        }
                                        IconButton(onClick = { selectedClient = null }) {
                                            Icon(
                                                imageVector = FontAwesomeIcons.Solid.Times,
                                                contentDescription = "Cambiar",
                                                modifier = Modifier.size(24.dp),
                                                tint = MaterialTheme.colorScheme.onPrimaryContainer
                                            )
                                        }
                                    }
                                }
                            } else {
                                Button(
                                    onClick = { showClientSelector = true },
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Icon(
                                        imageVector = FontAwesomeIcons.Solid.Users,
                                        contentDescription = null,
                                        modifier = Modifier.size(24.dp)
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text("Seleccionar Cliente")
                                }
                            }
                        }
                    }

                    // Status
                    var expandedStatus by remember { mutableStateOf(false) }
                    Box {
                        OutlinedTextField(
                            value = when (status) {
                                WorkStatus.PENDING -> "Pendiente"
                                WorkStatus.IN_PROGRESS -> "En Progreso"
                                WorkStatus.COMPLETED -> "Completado"
                                WorkStatus.CANCELLED -> "Cancelado"
                            },
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
                                    text = {
                                        Text(
                                            when (statusOption) {
                                                WorkStatus.PENDING -> "Pendiente"
                                                WorkStatus.IN_PROGRESS -> "En Progreso"
                                                WorkStatus.COMPLETED -> "Completado"
                                                WorkStatus.CANCELLED -> "Cancelado"
                                            }
                                        )
                                    },
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
                            value = when (priority) {
                                WorkPriority.LOW -> "Baja"
                                WorkPriority.MEDIUM -> "Media"
                                WorkPriority.HIGH -> "Alta"
                                WorkPriority.URGENT -> "Urgente"
                            },
                            onValueChange = {},
                            label = { Text("Prioridad") },
                            readOnly = true,
                            modifier = Modifier.fillMaxWidth(),
                            leadingIcon = {
                                Icon(
                                    imageVector = FontAwesomeIcons.Solid.ExclamationCircle,
                                    contentDescription = null,
                                    modifier = Modifier.size(24.dp)
                                )
                            },
                            trailingIcon = {
                                IconButton(onClick = { expandedPriority = true }) {
                                    Icon(
                                        imageVector = FontAwesomeIcons.Solid.ChevronDown,
                                        contentDescription = null,
                                        modifier = Modifier.size(24.dp)
                                    )
                                }
                            }
                        )
                        DropdownMenu(
                            expanded = expandedPriority,
                            onDismissRequest = { expandedPriority = false }
                        ) {
                            WorkPriority.entries.forEach { priorityOption ->
                                DropdownMenuItem(
                                    text = {
                                        Text(
                                            when (priorityOption) {
                                                WorkPriority.LOW -> "Baja"
                                                WorkPriority.MEDIUM -> "Media"
                                                WorkPriority.HIGH -> "Alta"
                                                WorkPriority.URGENT -> "Urgente"
                                            }
                                        )
                                    },
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
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                        fontWeight = FontWeight.Bold
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        OutlinedTextField(
                            value = dueDayOfMonth,
                            onValueChange = {
                                if (it.length <= 2 && it.all { char -> char.isDigit() }) dueDayOfMonth = it
                            },
                            label = { Text("Día") },
                            modifier = Modifier.weight(1f),
                            singleLine = true,
                            placeholder = { Text("DD") }
                        )
                        OutlinedTextField(
                            value = dueMonth,
                            onValueChange = { if (it.length <= 2 && it.all { char -> char.isDigit() }) dueMonth = it },
                            label = { Text("Mes") },
                            modifier = Modifier.weight(1f),
                            singleLine = true,
                            placeholder = { Text("MM") }
                        )
                        OutlinedTextField(
                            value = dueYear,
                            onValueChange = { if (it.length <= 4 && it.all { char -> char.isDigit() }) dueYear = it },
                            label = { Text("Año") },
                            modifier = Modifier.weight(1f),
                            singleLine = true,
                            placeholder = { Text("AAAA") }
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
                            if (selectedClient != null && isFormValid) {
                                try {
                                    val now = kotlin.time.Clock.System.now()
                                        .toLocalDateTime(TimeZone.currentSystemDefault())

                                    val dueDate = kotlinx.datetime.LocalDateTime(
                                        year = dueYear.toInt(),
                                        monthNumber = dueMonth.toInt(),
                                        dayOfMonth = dueDayOfMonth.toInt(),
                                        hour = 23,
                                        minute = 59
                                    )

                                    val newWork = Work(
                                        id = UUID.randomUUID().toString(),
                                        title = title,
                                        description = description,
                                        clientId = selectedClient!!.id,
                                        clientName = selectedClient!!.name,
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
                                    // Handle error
                                }
                            }
                        },
                        modifier = Modifier.weight(1f),
                        enabled = isFormValid
                    ) {
                        Icon(
                            imageVector = FontAwesomeIcons.Solid.Save,
                            contentDescription = null,
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Crear Trabajo")
                    }
                }
            }
        }
    }

    // Client Selector Dialog
    if (showClientSelector) {
        Dialog(onDismissRequest = { showClientSelector = false }) {
            Card(
                modifier = Modifier
                    .width(500.dp)
                    .heightIn(max = 600.dp),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp)
                ) {
                    Text(
                        text = "Seleccionar Cliente",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    OutlinedTextField(
                        value = clientSearchQuery,
                        onValueChange = { clientSearchQuery = it },
                        label = { Text("Buscar cliente") },
                        modifier = Modifier.fillMaxWidth(),
                        leadingIcon = {
                            Icon(
                                imageVector = FontAwesomeIcons.Solid.Search,
                                contentDescription = null,
                                modifier = Modifier.size(24.dp)
                            )
                        },
                        singleLine = true
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .verticalScroll(rememberScrollState()),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        filteredClients.forEach { client ->
                            Card(
                                onClick = {
                                    selectedClient = client
                                    showClientSelector = false
                                    clientSearchQuery = ""
                                },
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(16.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        imageVector = FontAwesomeIcons.Solid.User,
                                        contentDescription = null,
                                        modifier = Modifier.size(24.dp),
                                        tint = MaterialTheme.colorScheme.primary
                                    )
                                    Spacer(modifier = Modifier.width(12.dp))
                                    Column(modifier = Modifier.weight(1f)) {
                                        Text(
                                            text = client.name,
                                            style = MaterialTheme.typography.titleMedium,
                                            fontWeight = FontWeight.Bold
                                        )
                                        if (client.company != null) {
                                            Text(
                                                text = client.company,
                                                style = MaterialTheme.typography.bodySmall,
                                                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                                            )
                                        }
                                        Text(
                                            text = client.email,
                                            style = MaterialTheme.typography.bodySmall,
                                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                                        )
                                    }
                                    Icon(
                                        imageVector = FontAwesomeIcons.Solid.ChevronRight,
                                        contentDescription = null,
                                        modifier = Modifier.size(24.dp)
                                    )
                                }
                            }
                        }

                        if (filteredClients.isEmpty()) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(32.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "No se encontraron clientes",
                                    style = MaterialTheme.typography.bodyLarge,
                                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    // New Client Dialog
    if (showNewClientDialog) {
        var newClientName by remember { mutableStateOf("") }
        var newClientEmail by remember { mutableStateOf("") }
        var newClientPhone by remember { mutableStateOf("") }
        var newClientCompany by remember { mutableStateOf("") }

        Dialog(onDismissRequest = { showNewClientDialog = false }) {
            Card(
                modifier = Modifier.width(500.dp),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text(
                        text = "Nuevo Cliente Rápido",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold
                    )

                    OutlinedTextField(
                        value = newClientName,
                        onValueChange = { newClientName = it },
                        label = { Text("Nombre *") },
                        modifier = Modifier.fillMaxWidth(),
                        leadingIcon = {
                            Icon(
                                imageVector = FontAwesomeIcons.Solid.User,
                                contentDescription = null,
                                modifier = Modifier.size(24.dp)
                            )
                        },
                        singleLine = true
                    )

                    OutlinedTextField(
                        value = newClientEmail,
                        onValueChange = { newClientEmail = it },
                        label = { Text("Email *") },
                        modifier = Modifier.fillMaxWidth(),
                        leadingIcon = {
                            Icon(
                                imageVector = FontAwesomeIcons.Solid.Envelope,
                                contentDescription = null,
                                modifier = Modifier.size(24.dp)
                            )
                        },
                        singleLine = true
                    )

                    OutlinedTextField(
                        value = newClientPhone,
                        onValueChange = { newClientPhone = it },
                        label = { Text("Teléfono *") },
                        modifier = Modifier.fillMaxWidth(),
                        leadingIcon = {
                            Icon(
                                imageVector = FontAwesomeIcons.Solid.Phone,
                                contentDescription = null,
                                modifier = Modifier.size(24.dp)
                            )
                        },
                        singleLine = true
                    )

                    OutlinedTextField(
                        value = newClientCompany,
                        onValueChange = { newClientCompany = it },
                        label = { Text("Empresa") },
                        modifier = Modifier.fillMaxWidth(),
                        leadingIcon = {
                            Icon(
                                imageVector = FontAwesomeIcons.Solid.Building,
                                contentDescription = null,
                                modifier = Modifier.size(24.dp)
                            )
                        },
                        singleLine = true
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    OutlinedButton(
                        onClick = { showNewClientDialog = false },
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Cancelar")
                    }

                    Button(
                        onClick = {
                            if (newClientName.isNotBlank() && newClientEmail.isNotBlank() && newClientPhone.isNotBlank()) {
                                val now = kotlin.time.Clock.System.now()
                                    .toLocalDateTime(TimeZone.currentSystemDefault())

                                val newClient = Client(
                                    id = UUID.randomUUID().toString(),
                                    name = newClientName,
                                    email = newClientEmail,
                                    phone = newClientPhone,
                                    company = newClientCompany.ifBlank { null },
                                    status = ClientStatus.ACTIVE,
                                    createdAt = now,
                                    updatedAt = now,
                                    totalWorks = 0,
                                    activeWorks = 0,
                                    completedWorks = 0
                                )

                                onSaveClient(newClient)
                                selectedClient = newClient
                                showNewClientDialog = false
                            }
                        },
                        modifier = Modifier.weight(1f),
                        enabled = newClientName.isNotBlank() && newClientEmail.isNotBlank() && newClientPhone.isNotBlank()
                    ) {
                        Icon(
                            imageVector = FontAwesomeIcons.Solid.UserPlus,
                            contentDescription = null,
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Crear")
                    }
                }
            }
        }
    }
}