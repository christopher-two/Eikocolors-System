package org.christophertwo.eikocolors.feature.clients.presentation

import org.christophertwo.eikocolors.feature.clients.domain.model.Client
import org.christophertwo.eikocolors.feature.clients.domain.model.ClientsFilter

sealed interface ClientsAction {
    data object OnAddClientClick : ClientsAction
    data class OnClientClick(val client: Client) : ClientsAction
    data object OnDismissClientDialog : ClientsAction
    data object OnDismissAddClientDialog : ClientsAction
    data class OnSaveClient(val client: Client) : ClientsAction
    data class OnUpdateClient(val client: Client) : ClientsAction
    data class OnDeleteClient(val clientId: String) : ClientsAction
    data class OnFilterChange(val filter: ClientsFilter) : ClientsAction
    data class OnSearchQueryChange(val query: String) : ClientsAction
}