package org.christophertwo.eikocolors.feature.clients.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.christophertwo.eikocolors.feature.clients.data.ClientRepositoryImpl
import org.christophertwo.eikocolors.feature.clients.domain.DeleteClientUseCase
import org.christophertwo.eikocolors.feature.clients.domain.GetAllClientsUseCase
import org.christophertwo.eikocolors.feature.clients.domain.SaveClientUseCase
import org.christophertwo.eikocolors.feature.clients.domain.SearchClientsUseCase
import org.christophertwo.eikocolors.feature.clients.domain.UpdateClientUseCase
import org.christophertwo.eikocolors.feature.clients.domain.model.ClientStatus

class ClientsViewModel : ViewModel() {

    private var hasLoadedInitialData = false

    // TODO: Inyectar dependencias con DI
    private val repository = ClientRepositoryImpl()
    private val getAllClientsUseCase = GetAllClientsUseCase(repository)
    private val saveClientUseCase = SaveClientUseCase(repository)
    private val updateClientUseCase = UpdateClientUseCase(repository)
    private val deleteClientUseCase = DeleteClientUseCase(repository)
    private val searchClientsUseCase = SearchClientsUseCase(repository)

    private val _state = MutableStateFlow(ClientsState())
    val state = _state
        .onStart {
            if (!hasLoadedInitialData) {
                loadClients()
                hasLoadedInitialData = true
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = ClientsState()
        )

    fun onAction(action: ClientsAction) {
        when (action) {
            is ClientsAction.OnAddClientClick -> {
                _state.update { it.copy(showAddClientDialog = true) }
            }
            is ClientsAction.OnClientClick -> {
                _state.update {
                    it.copy(
                        selectedClient = action.client,
                        showClientDialog = true
                    )
                }
            }
            is ClientsAction.OnDismissClientDialog -> {
                _state.update {
                    it.copy(
                        selectedClient = null,
                        showClientDialog = false
                    )
                }
            }
            is ClientsAction.OnDismissAddClientDialog -> {
                _state.update { it.copy(showAddClientDialog = false) }
            }
            is ClientsAction.OnSaveClient -> {
                viewModelScope.launch {
                    try {
                        _state.update { it.copy(isLoading = true) }
                        saveClientUseCase(action.client)
                        _state.update { it.copy(showAddClientDialog = false, isLoading = false) }
                        loadClients()
                    } catch (e: Exception) {
                        _state.update {
                            it.copy(
                                error = e.message ?: "Error al guardar cliente",
                                isLoading = false
                            )
                        }
                    }
                }
            }
            is ClientsAction.OnUpdateClient -> {
                viewModelScope.launch {
                    try {
                        _state.update { it.copy(isLoading = true) }
                        updateClientUseCase(action.client)
                        _state.update {
                            it.copy(
                                showClientDialog = false,
                                selectedClient = null,
                                isLoading = false
                            )
                        }
                        loadClients()
                    } catch (e: Exception) {
                        _state.update {
                            it.copy(
                                error = e.message ?: "Error al actualizar cliente",
                                isLoading = false
                            )
                        }
                    }
                }
            }
            is ClientsAction.OnDeleteClient -> {
                viewModelScope.launch {
                    try {
                        _state.update { it.copy(isLoading = true) }
                        deleteClientUseCase(action.clientId)
                        _state.update {
                            it.copy(
                                showClientDialog = false,
                                selectedClient = null,
                                isLoading = false
                            )
                        }
                        loadClients()
                    } catch (e: Exception) {
                        _state.update {
                            it.copy(
                                error = e.message ?: "Error al eliminar cliente",
                                isLoading = false
                            )
                        }
                    }
                }
            }
            is ClientsAction.OnFilterChange -> {
                _state.update { it.copy(selectedFilter = action.filter) }
            }
            is ClientsAction.OnSearchQueryChange -> {
                _state.update { it.copy(searchQuery = action.query) }
                if (action.query.isNotEmpty()) {
                    searchClients(action.query)
                } else {
                    loadClients()
                }
            }
        }
    }

    private fun loadClients() {
        viewModelScope.launch {
            getAllClientsUseCase().collect { clients ->
                _state.update {
                    it.copy(
                        clients = clients,
                        activeClients = clients.filter { it.status == ClientStatus.ACTIVE },
                        inactiveClients = clients.filter { it.status == ClientStatus.INACTIVE },
                        potentialClients = clients.filter { it.status == ClientStatus.POTENTIAL }
                    )
                }
            }
        }
    }

    private fun searchClients(query: String) {
        viewModelScope.launch {
            searchClientsUseCase(query).collect { clients ->
                _state.update {
                    it.copy(
                        clients = clients,
                        activeClients = clients.filter { it.status == ClientStatus.ACTIVE },
                        inactiveClients = clients.filter { it.status == ClientStatus.INACTIVE },
                        potentialClients = clients.filter { it.status == ClientStatus.POTENTIAL }
                    )
                }
            }
        }
    }
}