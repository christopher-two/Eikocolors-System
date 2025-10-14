package org.christophertwo.eikocolors.feature.works.presentation

import org.christophertwo.eikocolors.feature.clients.domain.model.Client
import org.christophertwo.eikocolors.feature.works.domain.model.Work
import org.christophertwo.eikocolors.feature.works.domain.model.WorksFilter

sealed interface WorksAction {
    data class OnWorkClick(val work: Work) : WorksAction
    data object OnAddWorkClick : WorksAction
    data object OnDismissDialog : WorksAction
    data class OnSaveWork(val work: Work) : WorksAction
    data class OnDeleteWork(val workId: String) : WorksAction
    data class OnUpdateWork(val work: Work) : WorksAction
    data class OnFilterChange(val filter: WorksFilter) : WorksAction
    data class OnSaveClient(val client: Client) : WorksAction
}