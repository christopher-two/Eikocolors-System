package org.christophertwo.eikocolors.feature.works.presentation

import org.christophertwo.eikocolors.feature.clients.domain.model.Client
import org.christophertwo.eikocolors.feature.works.domain.model.Work
import org.christophertwo.eikocolors.feature.works.domain.model.WorksFilter

data class WorksState(
    val works: List<Work> = emptyList(),
    val pendingWorks: List<Work> = emptyList(),
    val inProgressWorks: List<Work> = emptyList(),
    val overdueWorks: List<Work> = emptyList(),
    val availableClients: List<Client> = emptyList(),
    val isLoading: Boolean = false,
    val selectedWork: Work? = null,
    val showWorkDialog: Boolean = false,
    val showAddWorkDialog: Boolean = false,
    val error: String? = null,
    val selectedFilter: WorksFilter = WorksFilter.ALL
)