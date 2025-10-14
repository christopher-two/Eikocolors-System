package org.christophertwo.eikocolors.feature.clients.presentation

import org.christophertwo.eikocolors.feature.clients.domain.model.Client
import org.christophertwo.eikocolors.feature.clients.domain.model.ClientsFilter

data class ClientsState(
    val clients: List<Client> = emptyList(),
    val activeClients: List<Client> = emptyList(),
    val inactiveClients: List<Client> = emptyList(),
    val potentialClients: List<Client> = emptyList(),
    val isLoading: Boolean = false,
    val selectedClient: Client? = null,
    val showClientDialog: Boolean = false,
    val showAddClientDialog: Boolean = false,
    val error: String? = null,
    val selectedFilter: ClientsFilter = ClientsFilter.ALL,
    val searchQuery: String = ""
)