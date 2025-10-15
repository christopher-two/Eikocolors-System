package org.christophertwo.eikocolors.feature.clients.presentation

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Solid
import compose.icons.fontawesomeicons.solid.*
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.christophertwo.eikocolors.core.ui.EikoColorsSystemTheme
import org.christophertwo.eikocolors.feature.clients.domain.model.Client
import org.christophertwo.eikocolors.feature.clients.domain.model.ClientStatus
import org.christophertwo.eikocolors.feature.clients.domain.model.ClientsFilter
import org.christophertwo.eikocolors.feature.clients.presentation.components.AddClientDialog
import org.christophertwo.eikocolors.feature.clients.presentation.components.ClientCard
import org.christophertwo.eikocolors.feature.clients.presentation.components.ClientDialog
import kotlin.time.ExperimentalTime

@Composable
fun ClientsRoot(
    viewModel: ClientsViewModel
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    ClientsScreen(
        state = state,
        onAction = viewModel::onAction
    )
}

@Composable
private fun ClientsScreen(
    state: ClientsState,
    onAction: (ClientsAction) -> Unit,
) {
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(24.dp)
        ) {
            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Clientes",
                        style = MaterialTheme.typography.displaySmall,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    Text(
                        text = "Gestiona todos tus clientes y contactos",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
                    )
                }

                Button(
                    onClick = { onAction(ClientsAction.OnAddClientClick) },
                    contentPadding = PaddingValues(horizontal = 20.dp, vertical = 12.dp)
                ) {
                    Icon(
                        imageVector = FontAwesomeIcons.Solid.Plus,
                        contentDescription = null,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Nuevo Cliente", fontWeight = FontWeight.Bold)
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Search Bar
            OutlinedTextField(
                value = state.searchQuery,
                onValueChange = { onAction(ClientsAction.OnSearchQueryChange(it)) },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("Buscar clientes por nombre, email, teléfono o empresa...") },
                leadingIcon = {
                    Icon(
                        imageVector = FontAwesomeIcons.Solid.Search,
                        contentDescription = null,
                        modifier = Modifier.size(24.dp)
                    )
                },
                trailingIcon = {
                    if (state.searchQuery.isNotEmpty()) {
                        IconButton(onClick = { onAction(ClientsAction.OnSearchQueryChange("")) }) {
                            Icon(
                                imageVector = FontAwesomeIcons.Solid.Times,
                                contentDescription = "Limpiar búsqueda",
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    }
                },
                singleLine = true,
                shape = RoundedCornerShape(12.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Filter Chips
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                FilterChip(
                    label = "Todos",
                    count = state.clients.size,
                    isSelected = state.selectedFilter == ClientsFilter.ALL,
                    onClick = { onAction(ClientsAction.OnFilterChange(ClientsFilter.ALL)) },
                    icon = FontAwesomeIcons.Solid.Users
                )
                FilterChip(
                    label = "Activos",
                    count = state.activeClients.size,
                    isSelected = state.selectedFilter == ClientsFilter.ACTIVE,
                    onClick = { onAction(ClientsAction.OnFilterChange(ClientsFilter.ACTIVE)) },
                    icon = FontAwesomeIcons.Solid.CheckCircle
                )
                FilterChip(
                    label = "Potenciales",
                    count = state.potentialClients.size,
                    isSelected = state.selectedFilter == ClientsFilter.POTENTIAL,
                    onClick = { onAction(ClientsAction.OnFilterChange(ClientsFilter.POTENTIAL)) },
                    icon = FontAwesomeIcons.Solid.Star
                )
                FilterChip(
                    label = "Inactivos",
                    count = state.inactiveClients.size,
                    isSelected = state.selectedFilter == ClientsFilter.INACTIVE,
                    onClick = { onAction(ClientsAction.OnFilterChange(ClientsFilter.INACTIVE)) },
                    icon = FontAwesomeIcons.Solid.TimesCircle
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Content
            if (state.isLoading) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
                }
            } else {
                val clientsToShow = when (state.selectedFilter) {
                    ClientsFilter.ALL -> state.clients
                    ClientsFilter.ACTIVE -> state.activeClients
                    ClientsFilter.INACTIVE -> state.inactiveClients
                    ClientsFilter.POTENTIAL -> state.potentialClients
                }

                if (clientsToShow.isEmpty()) {
                    EmptyState(
                        message = if (state.searchQuery.isNotEmpty()) {
                            "No se encontraron clientes"
                        } else {
                            "No hay clientes registrados"
                        },
                        onAddClick = { onAction(ClientsAction.OnAddClientClick) }
                    )
                } else {
                    LazyVerticalGrid(
                        columns = GridCells.Adaptive(minSize = 380.dp),
                        contentPadding = PaddingValues(bottom = 16.dp),
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        items(clientsToShow, key = { it.id }) { client ->
                            ClientCard(
                                client = client,
                                onClick = { onAction(ClientsAction.OnClientClick(client)) }
                            )
                        }
                    }
                }
            }
        }

        // Dialogs
        if (state.showClientDialog && state.selectedClient != null) {
            ClientDialog(
                client = state.selectedClient,
                onDismiss = { onAction(ClientsAction.OnDismissClientDialog) },
                onEdit = {
                    onAction(ClientsAction.OnDismissClientDialog)
                    onAction(ClientsAction.OnAddClientClick)
                },
                onDelete = {
                    onAction(ClientsAction.OnDeleteClient(state.selectedClient.id))
                }
            )
        }

        if (state.showAddClientDialog) {
            AddClientDialog(
                onDismiss = { onAction(ClientsAction.OnDismissAddClientDialog) },
                onSave = { client ->
                    if (state.selectedClient != null) {
                        onAction(ClientsAction.OnUpdateClient(client))
                    } else {
                        onAction(ClientsAction.OnSaveClient(client))
                    }
                },
                clientToEdit = state.selectedClient
            )
        }
    }
}

@Composable
private fun FilterChip(
    label: String,
    count: Int,
    isSelected: Boolean,
    onClick: () -> Unit,
    icon: ImageVector
) {
    FilterChip(
        selected = isSelected,
        onClick = onClick,
        label = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    modifier = Modifier.size(24.dp)
                )
                Text(
                    text = "$label ($count)",
                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                )
            }
        },
        colors = FilterChipDefaults.filterChipColors(
            selectedContainerColor = MaterialTheme.colorScheme.primary,
            selectedLabelColor = MaterialTheme.colorScheme.onPrimary
        )
    )
}

@Composable
private fun EmptyState(
    message: String,
    onAddClick: () -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Icon(
                imageVector = FontAwesomeIcons.Solid.Users,
                contentDescription = null,
                modifier = Modifier.size(64.dp),
                tint = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.3f)
            )
            Text(
                text = message,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
            )
            Button(onClick = onAddClick) {
                Icon(
                    imageVector = FontAwesomeIcons.Solid.Plus,
                    contentDescription = null,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Agregar Cliente")
            }
        }
    }
}

@OptIn(ExperimentalTime::class)
@Preview
@Composable
private fun Preview() {
    val now = kotlin.time.Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
    val mockClients = listOf(
        Client(
            id = "1",
            name = "María García",
            email = "maria.garcia@email.com",
            phone = "+34 612 345 678",
            company = "García Diseño S.L.",
            address = "Calle Mayor 123, Madrid",
            status = ClientStatus.ACTIVE,
            totalWorks = 15,
            activeWorks = 3,
            completedWorks = 12,
            notes = "Cliente VIP, preferencia por colores cálidos",
            createdAt = now,
            updatedAt = now,
            lastContactDate = now
        ),
        Client(
            id = "2",
            name = "Carlos Rodríguez",
            email = "carlos.r@empresa.com",
            phone = "+34 698 765 432",
            company = "Tech Solutions",
            status = ClientStatus.ACTIVE,
            totalWorks = 8,
            activeWorks = 2,
            completedWorks = 6,
            createdAt = now,
            updatedAt = now
        ),
        Client(
            id = "3",
            name = "Ana Martínez",
            email = "ana.martinez@correo.com",
            phone = "+34 645 123 789",
            status = ClientStatus.POTENTIAL,
            totalWorks = 0,
            activeWorks = 0,
            completedWorks = 0,
            notes = "Interesada en proyecto de renovación completa",
            createdAt = now,
            updatedAt = now
        ),
        Client(
            id = "4",
            name = "Luis Fernández",
            email = "luis.f@hotmail.com",
            phone = "+34 677 888 999",
            company = "Construcciones Fernández",
            status = ClientStatus.INACTIVE,
            totalWorks = 5,
            activeWorks = 0,
            completedWorks = 5,
            createdAt = now,
            updatedAt = now
        )
    )

    EikoColorsSystemTheme {
        ClientsScreen(
            state = ClientsState(
                clients = mockClients,
                activeClients = mockClients.filter { it.status == ClientStatus.ACTIVE },
                inactiveClients = mockClients.filter { it.status == ClientStatus.INACTIVE },
                potentialClients = mockClients.filter { it.status == ClientStatus.POTENTIAL }
            ),
            onAction = {}
        )
    }
}