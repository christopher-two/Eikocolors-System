package org.christophertwo.eikocolors.feature.clients.presentation.components

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
import java.util.*
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
@Composable
fun AddClientDialog(
    onDismiss: () -> Unit,
    onSave: (Client) -> Unit,
    clientToEdit: Client? = null
) {
    var name by remember { mutableStateOf(clientToEdit?.name ?: "") }
    var email by remember { mutableStateOf(clientToEdit?.email ?: "") }
    var phone by remember { mutableStateOf(clientToEdit?.phone ?: "") }
    var address by remember { mutableStateOf(clientToEdit?.address ?: "") }
    var company by remember { mutableStateOf(clientToEdit?.company ?: "") }
    var notes by remember { mutableStateOf(clientToEdit?.notes ?: "") }
    var selectedStatus by remember { mutableStateOf(clientToEdit?.status ?: ClientStatus.ACTIVE) }
    var showStatusMenu by remember { mutableStateOf(false) }

    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier
                .width(600.dp)
                .heightIn(max = 700.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            )
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
                        text = if (clientToEdit != null) "Editar Cliente" else "Nuevo Cliente",
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

                Spacer(modifier = Modifier.height(24.dp))

                // Form Content
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // Name Field
                    OutlinedTextField(
                        value = name,
                        onValueChange = { name = it },
                        label = { Text("Nombre *") },
                        leadingIcon = {
                            Icon(
                                imageVector = FontAwesomeIcons.Solid.User,
                                contentDescription = null,
                                modifier = Modifier.size(24.dp)
                            )
                        },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )

                    // Email Field
                    OutlinedTextField(
                        value = email,
                        onValueChange = { email = it },
                        label = { Text("Email *") },
                        leadingIcon = {
                            Icon(
                                imageVector = FontAwesomeIcons.Solid.Envelope,
                                contentDescription = null,
                                modifier = Modifier.size(24.dp)
                            )
                        },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )

                    // Phone Field
                    OutlinedTextField(
                        value = phone,
                        onValueChange = { phone = it },
                        label = { Text("Teléfono *") },
                        leadingIcon = {
                            Icon(
                                imageVector = FontAwesomeIcons.Solid.Phone,
                                contentDescription = null,
                                modifier = Modifier.size(24.dp)
                            )
                        },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )

                    // Company Field
                    OutlinedTextField(
                        value = company,
                        onValueChange = { company = it },
                        label = { Text("Empresa") },
                        leadingIcon = {
                            Icon(
                                imageVector = FontAwesomeIcons.Solid.Building,
                                contentDescription = null,
                                modifier = Modifier.size(24.dp)
                            )
                        },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )

                    // Address Field
                    OutlinedTextField(
                        value = address,
                        onValueChange = { address = it },
                        label = { Text("Dirección") },
                        leadingIcon = {
                            Icon(
                                imageVector = FontAwesomeIcons.Solid.MapMarkerAlt,
                                contentDescription = null,
                                modifier = Modifier.size(24.dp)
                            )
                        },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )

                    // Status Dropdown
                    Box {
                        OutlinedTextField(
                            value = getStatusText(selectedStatus),
                            onValueChange = { },
                            label = { Text("Estado *") },
                            leadingIcon = {
                                Icon(
                                    imageVector = FontAwesomeIcons.Solid.InfoCircle,
                                    contentDescription = null,
                                    modifier = Modifier.size(24.dp)
                                )
                            },
                            trailingIcon = {
                                IconButton(onClick = { showStatusMenu = !showStatusMenu }) {
                                    Icon(
                                        imageVector = FontAwesomeIcons.Solid.ChevronDown,
                                        contentDescription = null,
                                        modifier = Modifier.size(24.dp)
                                    )
                                }
                            },
                            modifier = Modifier.fillMaxWidth(),
                            readOnly = true
                        )

                        DropdownMenu(
                            expanded = showStatusMenu,
                            onDismissRequest = { showStatusMenu = false }
                        ) {
                            ClientStatus.entries.forEach { status ->
                                DropdownMenuItem(
                                    text = { Text(getStatusText(status)) },
                                    onClick = {
                                        selectedStatus = status
                                        showStatusMenu = false
                                    },
                                    leadingIcon = {
                                        Icon(
                                            imageVector = getStatusIcon(status),
                                            contentDescription = null,
                                            modifier = Modifier.size(24.dp),
                                            tint = getStatusColor(status)
                                        )
                                    }
                                )
                            }
                        }
                    }

                    // Notes Field
                    OutlinedTextField(
                        value = notes,
                        onValueChange = { notes = it },
                        label = { Text("Notas") },
                        leadingIcon = {
                            Icon(
                                imageVector = FontAwesomeIcons.Solid.StickyNote,
                                contentDescription = null,
                                modifier = Modifier.size(24.dp)
                            )
                        },
                        modifier = Modifier.fillMaxWidth(),
                        minLines = 3,
                        maxLines = 5
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Action Buttons
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
                            if (name.isNotBlank() && email.isNotBlank() && phone.isNotBlank()) {
                                val now = kotlin.time.Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
                                val client = Client(
                                    id = clientToEdit?.id ?: UUID.randomUUID().toString(),
                                    name = name,
                                    email = email,
                                    phone = phone,
                                    address = address.ifBlank { null },
                                    company = company.ifBlank { null },
                                    status = selectedStatus,
                                    notes = notes.ifBlank { null },
                                    createdAt = clientToEdit?.createdAt ?: now,
                                    updatedAt = now,
                                    totalWorks = clientToEdit?.totalWorks ?: 0,
                                    activeWorks = clientToEdit?.activeWorks ?: 0,
                                    completedWorks = clientToEdit?.completedWorks ?: 0,
                                    lastContactDate = clientToEdit?.lastContactDate
                                )
                                onSave(client)
                            }
                        },
                        modifier = Modifier.weight(1f),
                        enabled = name.isNotBlank() && email.isNotBlank() && phone.isNotBlank()
                    ) {
                        Icon(
                            imageVector = FontAwesomeIcons.Solid.Save,
                            contentDescription = null,
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(if (clientToEdit != null) "Actualizar" else "Guardar")
                    }
                }
            }
        }
    }
}

@Composable
private fun getStatusText(status: ClientStatus): String {
    return when (status) {
        ClientStatus.ACTIVE -> "Activo"
        ClientStatus.INACTIVE -> "Inactivo"
        ClientStatus.POTENTIAL -> "Potencial"
    }
}

@Composable
private fun getStatusIcon(status: ClientStatus): androidx.compose.ui.graphics.vector.ImageVector {
    return when (status) {
        ClientStatus.ACTIVE -> FontAwesomeIcons.Solid.CheckCircle
        ClientStatus.INACTIVE -> FontAwesomeIcons.Solid.TimesCircle
        ClientStatus.POTENTIAL -> FontAwesomeIcons.Solid.ExclamationCircle
    }
}

@Composable
private fun getStatusColor(status: ClientStatus): androidx.compose.ui.graphics.Color {
    return when (status) {
        ClientStatus.ACTIVE -> MaterialTheme.colorScheme.primary
        ClientStatus.INACTIVE -> MaterialTheme.colorScheme.error
        ClientStatus.POTENTIAL -> MaterialTheme.colorScheme.tertiary
    }
}
