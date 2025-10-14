package org.christophertwo.eikocolors.feature.clients.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Solid
import compose.icons.fontawesomeicons.solid.*
import org.christophertwo.eikocolors.feature.clients.domain.model.Client
import org.christophertwo.eikocolors.feature.clients.domain.model.ClientStatus

@Composable
fun ClientDialog(
    client: Client,
    onDismiss: () -> Unit,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {
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
                        text = "Detalles del Cliente",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold
                    )

                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        IconButton(onClick = onEdit) {
                            Icon(
                                imageVector = FontAwesomeIcons.Solid.Edit,
                                contentDescription = "Editar",
                                modifier = Modifier.size(24.dp),
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                        IconButton(onClick = onDelete) {
                            Icon(
                                imageVector = FontAwesomeIcons.Solid.Trash,
                                contentDescription = "Eliminar",
                                modifier = Modifier.size(24.dp),
                                tint = MaterialTheme.colorScheme.error
                            )
                        }
                        IconButton(onClick = onDismiss) {
                            Icon(
                                imageVector = FontAwesomeIcons.Solid.Times,
                                contentDescription = "Cerrar",
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Content
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // Client Name and Status
                    InfoSection(
                        title = "Información General",
                        content = {
                            InfoRow(
                                icon = FontAwesomeIcons.Solid.User,
                                label = "Nombre",
                                value = client.name
                            )

                            StatusRow(status = client.status)

                            if (client.company != null) {
                                InfoRow(
                                    icon = FontAwesomeIcons.Solid.Building,
                                    label = "Empresa",
                                    value = client.company
                                )
                            }
                        }
                    )

                    // Contact Information
                    InfoSection(
                        title = "Información de Contacto",
                        content = {
                            InfoRow(
                                icon = FontAwesomeIcons.Solid.Envelope,
                                label = "Email",
                                value = client.email
                            )
                            InfoRow(
                                icon = FontAwesomeIcons.Solid.Phone,
                                label = "Teléfono",
                                value = client.phone
                            )
                            if (client.address != null) {
                                InfoRow(
                                    icon = FontAwesomeIcons.Solid.MapMarkerAlt,
                                    label = "Dirección",
                                    value = client.address
                                )
                            }
                        }
                    )

                    // Work Statistics
                    InfoSection(
                        title = "Estadísticas de Trabajos",
                        content = {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceEvenly
                            ) {
                                StatCard(
                                    icon = FontAwesomeIcons.Solid.Tasks,
                                    label = "Total",
                                    value = client.totalWorks.toString(),
                                    color = MaterialTheme.colorScheme.primary
                                )
                                StatCard(
                                    icon = FontAwesomeIcons.Solid.Spinner,
                                    label = "Activos",
                                    value = client.activeWorks.toString(),
                                    color = MaterialTheme.colorScheme.secondary
                                )
                                StatCard(
                                    icon = FontAwesomeIcons.Solid.CheckCircle,
                                    label = "Completados",
                                    value = client.completedWorks.toString(),
                                    color = MaterialTheme.colorScheme.tertiary
                                )
                            }
                        }
                    )

                    // Notes
                    if (client.notes != null && client.notes.isNotEmpty()) {
                        InfoSection(
                            title = "Notas",
                            content = {
                                Text(
                                    text = client.notes,
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                                )
                            }
                        )
                    }

                    // Dates
                    InfoSection(
                        title = "Información Adicional",
                        content = {
                            InfoRow(
                                icon = FontAwesomeIcons.Solid.Calendar,
                                label = "Creado",
                                value = formatDate(client.createdAt.toString())
                            )
                            InfoRow(
                                icon = FontAwesomeIcons.Solid.CalendarCheck,
                                label = "Actualizado",
                                value = formatDate(client.updatedAt.toString())
                            )
                            if (client.lastContactDate != null) {
                                InfoRow(
                                    icon = FontAwesomeIcons.Solid.CalendarAlt,
                                    label = "Último Contacto",
                                    value = formatDate(client.lastContactDate.toString())
                                )
                            }
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun InfoSection(
    title: String,
    content: @Composable ColumnScope.() -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f)
            ),
            shape = RoundedCornerShape(8.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                content()
            }
        }
    }
}

@Composable
private fun InfoRow(
    icon: ImageVector,
    label: String,
    value: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier.size(24.dp),
            tint = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.width(12.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = label,
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
            )
            Text(
                text = value,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Composable
private fun StatusRow(status: ClientStatus) {
    val (color, text, icon) = when (status) {
        ClientStatus.ACTIVE -> Triple(
            MaterialTheme.colorScheme.primary,
            "Activo",
            FontAwesomeIcons.Solid.CheckCircle
        )
        ClientStatus.INACTIVE -> Triple(
            MaterialTheme.colorScheme.error,
            "Inactivo",
            FontAwesomeIcons.Solid.TimesCircle
        )
        ClientStatus.POTENTIAL -> Triple(
            MaterialTheme.colorScheme.tertiary,
            "Potencial",
            FontAwesomeIcons.Solid.ExclamationCircle
        )
    }

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier.size(24.dp),
            tint = color
        )
        Spacer(modifier = Modifier.width(12.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = "Estado",
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
            )
            Surface(
                shape = RoundedCornerShape(6.dp),
                color = color.copy(alpha = 0.15f)
            ) {
                Text(
                    text = text,
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = color
                )
            }
        }
    }
}

@Composable
private fun StatCard(
    icon: ImageVector,
    label: String,
    value: String,
    color: Color
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier.size(24.dp),
            tint = color
        )
        Text(
            text = value,
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = color
        )
        Text(
            text = label,
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
        )
    }
}

private fun formatDate(dateString: String): String {
    return try {
        dateString.substringBefore('T')
    } catch (_: Exception) {
        dateString
    }
}
