package org.christophertwo.eikocolors.feature.works.presentation.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Solid
import compose.icons.fontawesomeicons.solid.CheckCircle
import compose.icons.fontawesomeicons.solid.Clock
import compose.icons.fontawesomeicons.solid.Spinner
import compose.icons.fontawesomeicons.solid.TimesCircle
import org.christophertwo.eikocolors.feature.works.domain.model.WorkStatus

@Composable
fun StatusTriple(
    status: WorkStatus
): Triple<Color, Color, ImageVector> {
    val (backgroundColor, textColor, icon) = when (status) {
        WorkStatus.PENDING -> Triple(
            MaterialTheme.colorScheme.secondary.copy(alpha = 0.2f),
            MaterialTheme.colorScheme.secondary,
            FontAwesomeIcons.Solid.Clock
        )

        WorkStatus.IN_PROGRESS -> Triple(
            MaterialTheme.colorScheme.primary.copy(alpha = 0.2f),
            MaterialTheme.colorScheme.primary,
            FontAwesomeIcons.Solid.Spinner
        )

        WorkStatus.COMPLETED -> Triple(
            MaterialTheme.colorScheme.tertiary.copy(alpha = 0.3f),
            MaterialTheme.colorScheme.tertiary,
            FontAwesomeIcons.Solid.CheckCircle
        )

        WorkStatus.CANCELLED -> Triple(
            MaterialTheme.colorScheme.errorContainer.copy(alpha = 0.2f),
            MaterialTheme.colorScheme.onErrorContainer,
            FontAwesomeIcons.Solid.TimesCircle
        )
    }

    return Triple(backgroundColor, textColor, icon)
}