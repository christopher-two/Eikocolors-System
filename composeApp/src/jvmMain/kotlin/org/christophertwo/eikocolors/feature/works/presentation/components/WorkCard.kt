package org.christophertwo.eikocolors.feature.works.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Solid
import compose.icons.fontawesomeicons.solid.*
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.christophertwo.eikocolors.feature.works.domain.model.Work
import org.christophertwo.eikocolors.feature.works.domain.model.WorkPriority
import org.christophertwo.eikocolors.feature.works.domain.model.WorkStatus
import kotlin.time.ExperimentalTime

@Composable
fun WorkCard(
    work: Work,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            // Header: Title and Priority
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = work.title,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.weight(1f)
                )

                PriorityBadge(priority = work.priority)
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Description
            Text(
                text = work.description,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Client Info
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(bottom = 8.dp)
            ) {
                Icon(
                    imageVector = FontAwesomeIcons.Solid.User,
                    contentDescription = null,
                    modifier = Modifier.size(14.dp),
                    tint = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = work.clientName,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)
                )
            }

            // Status and Due Date
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                StatusChip(status = work.status)

                DueDateInfo(dueDate = work.dueDate, status = work.status)
            }
        }
    }
}

@Composable
private fun PriorityBadge(priority: WorkPriority) {
    val (color, icon) = when (priority) {
        WorkPriority.LOW -> MaterialTheme.colorScheme.primary.copy(alpha = 0.3f) to FontAwesomeIcons.Solid.ArrowDown
        WorkPriority.MEDIUM -> MaterialTheme.colorScheme.secondary.copy(alpha = 0.5f) to FontAwesomeIcons.Solid.Minus
        WorkPriority.HIGH -> MaterialTheme.colorScheme.errorContainer.copy(alpha = 0.6f) to FontAwesomeIcons.Solid.ArrowUp
        WorkPriority.URGENT -> MaterialTheme.colorScheme.errorContainer to FontAwesomeIcons.Solid.ExclamationTriangle
    }

    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .background(color)
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.size(12.dp),
                tint = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = priority.name,
                style = MaterialTheme.typography.labelSmall,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
private fun StatusChip(status: WorkStatus) {
    val (backgroundColor, textColor, icon) = StatusTriple(status)

    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(6.dp))
            .background(backgroundColor)
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.size(12.dp),
                tint = textColor
            )
            Text(
                text = status.name.replace("_", " "),
                style = MaterialTheme.typography.labelSmall,
                color = textColor,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@OptIn(ExperimentalTime::class)
@Composable
private fun DueDateInfo(dueDate: LocalDateTime, status: WorkStatus) {
    val now = kotlin.time.Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
    val isOverdue = dueDate < now && status != WorkStatus.COMPLETED && status != WorkStatus.CANCELLED

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Icon(
            imageVector = FontAwesomeIcons.Solid.Calendar,
            contentDescription = null,
            modifier = Modifier.size(12.dp),
            tint = if (isOverdue) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
        )
        Text(
            text = "${dueDate.date.dayOfMonth}/${dueDate.date.monthNumber}/${dueDate.date.year}",
            style = MaterialTheme.typography.labelSmall,
            color = if (isOverdue) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
            fontWeight = if (isOverdue) FontWeight.Bold else FontWeight.Normal
        )
    }
}
