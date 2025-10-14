package org.christophertwo.eikocolors.feature.works.presentation

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Solid
import compose.icons.fontawesomeicons.solid.*
import org.christophertwo.eikocolors.core.ui.EikoColorsSystemTheme
import org.christophertwo.eikocolors.feature.works.domain.model.WorkStatus
import org.christophertwo.eikocolors.feature.works.domain.model.WorksFilter
import org.christophertwo.eikocolors.feature.works.presentation.components.AddWorkDialog
import org.christophertwo.eikocolors.feature.works.presentation.components.WorkDialog
import org.christophertwo.eikocolors.feature.works.presentation.components.WorksSection

@Composable
fun WorksRoot(
    viewModel: WorksViewModel
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    WorksScreen(
        state = state,
        onAction = viewModel::onAction
    )
}

@Composable
private fun WorksScreen(
    state: WorksState,
    onAction: (WorksAction) -> Unit,
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
                        text = "Trabajos",
                        style = MaterialTheme.typography.displaySmall,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    Text(
                        text = "Gestiona todos tus trabajos y proyectos",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
                    )
                }

                Button(
                    onClick = { onAction(WorksAction.OnAddWorkClick) },
                    contentPadding = PaddingValues(horizontal = 20.dp, vertical = 12.dp)
                ) {
                    Icon(
                        imageVector = FontAwesomeIcons.Solid.Plus,
                        contentDescription = null,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Nuevo Trabajo", fontWeight = FontWeight.Bold)
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Filter Chips
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                FilterChip(
                    label = "Todos",
                    count = state.works.size,
                    isSelected = state.selectedFilter == WorksFilter.ALL,
                    onClick = { onAction(WorksAction.OnFilterChange(WorksFilter.ALL)) },
                    icon = FontAwesomeIcons.Solid.ThList
                )
                FilterChip(
                    label = "Pendientes",
                    count = state.pendingWorks.size,
                    isSelected = state.selectedFilter == WorksFilter.PENDING,
                    onClick = { onAction(WorksAction.OnFilterChange(WorksFilter.PENDING)) },
                    icon = FontAwesomeIcons.Solid.Clock
                )
                FilterChip(
                    label = "En Progreso",
                    count = state.inProgressWorks.size,
                    isSelected = state.selectedFilter == WorksFilter.IN_PROGRESS,
                    onClick = { onAction(WorksAction.OnFilterChange(WorksFilter.IN_PROGRESS)) },
                    icon = FontAwesomeIcons.Solid.Spinner
                )
                FilterChip(
                    label = "Atrasados",
                    count = state.overdueWorks.size,
                    isSelected = state.selectedFilter == WorksFilter.OVERDUE,
                    onClick = { onAction(WorksAction.OnFilterChange(WorksFilter.OVERDUE)) },
                    icon = FontAwesomeIcons.Solid.ExclamationTriangle
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
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.spacedBy(24.dp)
                ) {
                    when (state.selectedFilter) {
                        WorksFilter.ALL -> {
                            if (state.overdueWorks.isNotEmpty()) {
                                WorksSection(
                                    title = "Atrasados",
                                    icon = FontAwesomeIcons.Solid.ExclamationTriangle,
                                    works = state.overdueWorks,
                                    onWorkClick = { onAction(WorksAction.OnWorkClick(it)) },
                                    accentColor = MaterialTheme.colorScheme.error
                                )
                            }

                            if (state.inProgressWorks.isNotEmpty()) {
                                WorksSection(
                                    title = "En Progreso",
                                    icon = FontAwesomeIcons.Solid.Spinner,
                                    works = state.inProgressWorks,
                                    onWorkClick = { onAction(WorksAction.OnWorkClick(it)) },
                                    accentColor = MaterialTheme.colorScheme.primary
                                )
                            }

                            if (state.pendingWorks.isNotEmpty()) {
                                WorksSection(
                                    title = "Pendientes",
                                    icon = FontAwesomeIcons.Solid.Clock,
                                    works = state.pendingWorks,
                                    onWorkClick = { onAction(WorksAction.OnWorkClick(it)) },
                                    accentColor = MaterialTheme.colorScheme.secondary
                                )
                            }

                            if (state.works.isEmpty()) {
                                EmptyState(
                                    message = "No hay trabajos registrados",
                                    onAddClick = { onAction(WorksAction.OnAddWorkClick) }
                                )
                            }
                        }

                        WorksFilter.PENDING -> {
                            WorksSection(
                                title = "Pendientes",
                                icon = FontAwesomeIcons.Solid.Clock,
                                works = state.pendingWorks,
                                onWorkClick = { onAction(WorksAction.OnWorkClick(it)) },
                                accentColor = MaterialTheme.colorScheme.secondary
                            )
                        }

                        WorksFilter.IN_PROGRESS -> {
                            WorksSection(
                                title = "En Progreso",
                                icon = FontAwesomeIcons.Solid.Spinner,
                                works = state.inProgressWorks,
                                onWorkClick = { onAction(WorksAction.OnWorkClick(it)) },
                                accentColor = MaterialTheme.colorScheme.primary
                            )
                        }

                        WorksFilter.OVERDUE -> {
                            WorksSection(
                                title = "Atrasados",
                                icon = FontAwesomeIcons.Solid.ExclamationTriangle,
                                works = state.overdueWorks,
                                onWorkClick = { onAction(WorksAction.OnWorkClick(it)) },
                                accentColor = MaterialTheme.colorScheme.error
                            )
                        }
                    }
                }
            }
        }

        // Dialogs
        if (state.showWorkDialog && state.selectedWork != null) {
            WorkDialog(
                work = state.selectedWork,
                onDismiss = { onAction(WorksAction.OnDismissDialog) },
                onUpdate = { onAction(WorksAction.OnUpdateWork(it)) },
                onDelete = { onAction(WorksAction.OnDeleteWork(it)) }
            )
        }

        if (state.showAddWorkDialog) {
            AddWorkDialog(
                availableClients = state.availableClients,
                onDismiss = { onAction(WorksAction.OnDismissDialog) },
                onSave = { onAction(WorksAction.OnSaveWork(it)) },
                onSaveClient = { onAction(WorksAction.OnSaveClient(it)) }
            )
        }

        // Error Snackbar
        state.error?.let { error ->
            Snackbar(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(16.dp)
            ) {
                Text(error)
            }
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
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isSelected)
                MaterialTheme.colorScheme.primary
            else
                MaterialTheme.colorScheme.surface,
            contentColor = if (isSelected)
                MaterialTheme.colorScheme.onPrimary
            else
                MaterialTheme.colorScheme.onSurface
        ),
        shape = RoundedCornerShape(24.dp),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 10.dp),
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = if (isSelected) 4.dp else 1.dp
        )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.size(16.dp)
            )
            Text(
                text = label,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
            )
            Box(
                modifier = Modifier
                    .background(
                        if (isSelected)
                            MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.3f)
                        else
                            MaterialTheme.colorScheme.primary.copy(alpha = 0.2f),
                        RoundedCornerShape(12.dp)
                    )
                    .padding(horizontal = 6.dp, vertical = 2.dp)
            ) {
                Text(
                    text = count.toString(),
                    style = MaterialTheme.typography.labelSmall,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
private fun EmptyState(
    message: String,
    onAddClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 64.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Icon(
                imageVector = FontAwesomeIcons.Solid.FolderOpen,
                contentDescription = null,
                modifier = Modifier.size(80.dp),
                tint = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.3f)
            )
            Text(
                text = message,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f)
            )
            Button(onClick = onAddClick) {
                Icon(
                    imageVector = FontAwesomeIcons.Solid.Plus,
                    contentDescription = null,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Agregar Primer Trabajo")
            }
        }
    }
}



@Preview
@Composable
private fun Preview() {
    EikoColorsSystemTheme(isDarkTheme = true) {
        WorksScreen(
            state = WorksState(),
            onAction = {}
        )
    }
}