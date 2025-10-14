package org.christophertwo.eikocolors.feature.works.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import org.christophertwo.eikocolors.feature.works.domain.*
import org.christophertwo.eikocolors.feature.works.domain.model.WorkStatus

class WorksViewModel(
    private val getAllWorksUseCase: GetAllWorksUseCase,
    private val getOverdueWorksUseCase: GetOverdueWorksUseCase,
    private val saveWorkUseCase: SaveWorkUseCase,
    private val updateWorkUseCase: UpdateWorkUseCase,
    private val deleteWorkUseCase: DeleteWorkUseCase
) : ViewModel() {

    private var hasLoadedInitialData = false

    private val _state = MutableStateFlow(WorksState())
    val state = combine(
        _state,
        getAllWorksUseCase(),
        getOverdueWorksUseCase()
    ) { currentState, allWorks, overdueWorks ->
        currentState.copy(
            works = allWorks,
            pendingWorks = allWorks.filter { it.status == WorkStatus.PENDING },
            inProgressWorks = allWorks.filter { it.status == WorkStatus.IN_PROGRESS },
            overdueWorks = overdueWorks
        )
    }
        .onStart {
            if (!hasLoadedInitialData) {
                _state.update { it.copy(isLoading = true) }
                hasLoadedInitialData = true
                _state.update { it.copy(isLoading = false) }
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = WorksState()
        )

    fun onAction(action: WorksAction) {
        when (action) {
            is WorksAction.OnWorkClick -> {
                _state.update {
                    it.copy(
                        selectedWork = action.work,
                        showWorkDialog = true
                    )
                }
            }

            WorksAction.OnAddWorkClick -> {
                _state.update {
                    it.copy(showAddWorkDialog = true)
                }
            }

            WorksAction.OnDismissDialog -> {
                _state.update {
                    it.copy(
                        showWorkDialog = false,
                        showAddWorkDialog = false,
                        selectedWork = null
                    )
                }
            }

            is WorksAction.OnSaveWork -> {
                viewModelScope.launch {
                    try {
                        saveWorkUseCase(action.work)
                        _state.update {
                            it.copy(
                                showAddWorkDialog = false,
                                error = null
                            )
                        }
                    } catch (e: Exception) {
                        _state.update {
                            it.copy(error = e.message)
                        }
                    }
                }
            }

            is WorksAction.OnUpdateWork -> {
                viewModelScope.launch {
                    try {
                        updateWorkUseCase(action.work)
                        _state.update {
                            it.copy(
                                showWorkDialog = false,
                                selectedWork = null,
                                error = null
                            )
                        }
                    } catch (e: Exception) {
                        _state.update {
                            it.copy(error = e.message)
                        }
                    }
                }
            }

            is WorksAction.OnDeleteWork -> {
                viewModelScope.launch {
                    try {
                        deleteWorkUseCase(action.workId)
                        _state.update {
                            it.copy(
                                showWorkDialog = false,
                                selectedWork = null,
                                error = null
                            )
                        }
                    } catch (e: Exception) {
                        _state.update {
                            it.copy(error = e.message)
                        }
                    }
                }
            }

            is WorksAction.OnFilterChange -> {
                _state.update {
                    it.copy(selectedFilter = action.filter)
                }
            }
        }
    }
}