package org.christophertwo.eikocolors.feature.works.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.Month
import org.christophertwo.eikocolors.feature.works.desingsystem.WorkRepository
import org.christophertwo.eikocolors.feature.works.domain.model.Work
import org.christophertwo.eikocolors.feature.works.domain.model.WorkPriority
import org.christophertwo.eikocolors.feature.works.domain.model.WorkStatus

class WorkRepositoryImpl : WorkRepository {
    private val mockWorks = listOf(
        // Trabajos Atrasados
        Work(
            id = "1",
            title = "Diseño Logo Corporativo",
            description = "Crear logo moderno y minimalista para empresa de tecnología. Incluye variaciones en color y blanco y negro.",
            clientId = "client-1",
            status = WorkStatus.IN_PROGRESS,
            priority = WorkPriority.URGENT,
            dueDate = LocalDateTime(2025, Month.SEPTEMBER, 15, 23, 59),
            createdAt = LocalDateTime(2025, Month.AUGUST, 20, 10, 30),
            updatedAt = LocalDateTime(2025, Month.OCTOBER, 10, 14, 20),
            estimatedHours = 15.0,
            actualHours = 12.5,
            notes = "Cliente solicita revisar paleta de colores. Prefiere tonos azules.",
            clientName = "Jhon Doe"
        ),
        Work(
            id = "2",
            title = "Rediseño Sitio Web",
            description = "Modernizar el sitio web corporativo con nueva identidad visual y mejorar UX/UI.",
            clientId = "client-2",
            clientName = "Comercial del Norte",
            status = WorkStatus.PENDING,
            priority = WorkPriority.HIGH,
            dueDate = LocalDateTime(2025, Month.OCTOBER, 1, 23, 59),
            createdAt = LocalDateTime(2025, Month.SEPTEMBER, 10, 9, 0),
            updatedAt = LocalDateTime(2025, Month.SEPTEMBER, 10, 9, 0),
            estimatedHours = 40.0,
            actualHours = null,
            notes = null
        ),

        // Trabajos En Progreso
        Work(
            id = "3",
            title = "Branding Restaurante",
            description = "Desarrollo completo de identidad corporativa: logo, menú, tarjetas, uniformes y señalética.",
            clientId = "client-3",
            clientName = "Sabores del Mar",
            status = WorkStatus.IN_PROGRESS,
            priority = WorkPriority.MEDIUM,
            dueDate = LocalDateTime(2025, Month.OCTOBER, 25, 23, 59),
            createdAt = LocalDateTime(2025, Month.OCTOBER, 1, 11, 0),
            updatedAt = LocalDateTime(2025, Month.OCTOBER, 12, 16, 45),
            estimatedHours = 50.0,
            actualHours = 22.0,
            notes = "Reunión programada para el viernes para revisar avances. Cliente muy satisfecho con propuestas iniciales."
        ),
        Work(
            id = "4",
            title = "Campaña Redes Sociales",
            description = "Diseñar 20 posts para Instagram y Facebook, incluyendo historias y banners promocionales.",
            clientId = "client-4",
            clientName = "Fitness Pro Gym",
            status = WorkStatus.IN_PROGRESS,
            priority = WorkPriority.MEDIUM,
            dueDate = LocalDateTime(2025, Month.OCTOBER, 20, 23, 59),
            createdAt = LocalDateTime(2025, Month.OCTOBER, 5, 8, 30),
            updatedAt = LocalDateTime(2025, Month.OCTOBER, 13, 10, 15),
            estimatedHours = 25.0,
            actualHours = 15.0,
            notes = "Cliente aprobó 12 diseños. Pendientes 8 más para esta semana."
        ),
        Work(
            id = "5",
            title = "Packaging Producto Nuevo",
            description = "Diseño de empaque para línea de productos orgánicos. Debe ser eco-friendly y atractivo.",
            clientId = "client-5",
            clientName = "EcoNatura",
            status = WorkStatus.IN_PROGRESS,
            priority = WorkPriority.HIGH,
            dueDate = LocalDateTime(2025, Month.OCTOBER, 18, 23, 59),
            createdAt = LocalDateTime(2025, Month.SEPTEMBER, 28, 14, 0),
            updatedAt = LocalDateTime(2025, Month.OCTOBER, 11, 9, 30),
            estimatedHours = 30.0,
            actualHours = 18.5,
            notes = "Esperando aprobación de muestras impresas del cliente."
        ),

        // Trabajos Pendientes
        Work(
            id = "6",
            title = "Identidad Visual Startup",
            description = "Crear identidad visual completa para startup de delivery. Logo, colores, tipografía y guía de uso.",
            clientId = "client-6",
            clientName = "QuickDel",
            status = WorkStatus.PENDING,
            priority = WorkPriority.LOW,
            dueDate = LocalDateTime(2025, Month.NOVEMBER, 5, 23, 59),
            createdAt = LocalDateTime(2025, Month.OCTOBER, 12, 10, 0),
            updatedAt = LocalDateTime(2025, Month.OCTOBER, 12, 10, 0),
            estimatedHours = 35.0,
            actualHours = null,
            notes = "Cliente aún está definiendo el brief. Reunión pendiente."
        ),
        Work(
            id = "7",
            title = "Catálogo de Productos",
            description = "Diseñar catálogo digital de 30 páginas con fotografías de productos y descripciones.",
            clientId = "client-7",
            clientName = "Muebles Modernos",
            status = WorkStatus.PENDING,
            priority = WorkPriority.MEDIUM,
            dueDate = LocalDateTime(2025, Month.OCTOBER, 30, 23, 59),
            createdAt = LocalDateTime(2025, Month.OCTOBER, 8, 15, 30),
            updatedAt = LocalDateTime(2025, Month.OCTOBER, 8, 15, 30),
            estimatedHours = 20.0,
            actualHours = null,
            notes = null
        ),
        Work(
            id = "8",
            title = "Banner Publicitario",
            description = "Diseño de banner para campaña de verano. Tamaños: web, móvil y vallas publicitarias.",
            clientId = "client-8",
            clientName = "Viajes del Sol",
            status = WorkStatus.PENDING,
            priority = WorkPriority.LOW,
            dueDate = LocalDateTime(2025, Month.NOVEMBER, 10, 23, 59),
            createdAt = LocalDateTime(2025, Month.OCTOBER, 13, 9, 0),
            updatedAt = LocalDateTime(2025, Month.OCTOBER, 13, 9, 0),
            estimatedHours = 10.0,
            actualHours = null,
            notes = "Esperando envío de fotografías por parte del cliente."
        ),
        Work(
            id = "9",
            title = "Ilustraciones para App",
            description = "Crear 15 ilustraciones personalizadas para aplicación móvil educativa infantil.",
            clientId = "client-9",
            clientName = "KidsLearn",
            status = WorkStatus.PENDING,
            priority = WorkPriority.MEDIUM,
            dueDate = LocalDateTime(2025, Month.OCTOBER, 28, 23, 59),
            createdAt = LocalDateTime(2025, Month.OCTOBER, 10, 11, 20),
            updatedAt = LocalDateTime(2025, Month.OCTOBER, 10, 11, 20),
            estimatedHours = 45.0,
            actualHours = null,
            notes = "Estilo: alegre, colorido y amigable para niños de 5-8 años."
        ),
        Work(
            id = "10",
            title = "Presentación Corporativa",
            description = "Diseñar plantilla PowerPoint profesional para presentaciones de ventas. 20 diapositivas maestras.",
            clientId = "client-10",
            clientName = "Consultoría Global",
            status = WorkStatus.PENDING,
            priority = WorkPriority.HIGH,
            dueDate = LocalDateTime(2025, Month.OCTOBER, 22, 23, 59),
            createdAt = LocalDateTime(2025, Month.OCTOBER, 11, 16, 0),
            updatedAt = LocalDateTime(2025, Month.OCTOBER, 11, 16, 0),
            estimatedHours = 12.0,
            actualHours = null,
            notes = "Urgente: Cliente necesita para presentación importante."
        ),

        // Más trabajos en progreso
        Work(
            id = "11",
            title = "Rediseño de App Móvil",
            description = "Actualizar diseño UI/UX de aplicación bancaria móvil. Modernizar interfaz y mejorar accesibilidad.",
            clientId = "client-11",
            clientName = "Banco Digital",
            status = WorkStatus.IN_PROGRESS,
            priority = WorkPriority.URGENT,
            dueDate = LocalDateTime(2025, Month.OCTOBER, 19, 23, 59),
            createdAt = LocalDateTime(2025, Month.SEPTEMBER, 25, 8, 0),
            updatedAt = LocalDateTime(2025, Month.OCTOBER, 13, 11, 45),
            estimatedHours = 60.0,
            actualHours = 42.0,
            notes = "En fase de pruebas con usuarios. Feedback muy positivo hasta ahora."
        ),
        Work(
            id = "12",
            title = "Video Animado Explicativo",
            description = "Crear video de 2 minutos explicando servicios de la empresa con animaciones 2D.",
            clientId = "client-12",
            clientName = "Cloud Services Inc",
            status = WorkStatus.IN_PROGRESS,
            priority = WorkPriority.MEDIUM,
            dueDate = LocalDateTime(2025, Month.OCTOBER, 27, 23, 59),
            createdAt = LocalDateTime(2025, Month.OCTOBER, 3, 14, 30),
            updatedAt = LocalDateTime(2025, Month.OCTOBER, 12, 17, 20),
            estimatedHours = 40.0,
            actualHours = 25.0,
            notes = "Storyboard aprobado. En proceso de animación."
        )
    )

    override fun getAllWorks(): Flow<List<Work>> = flowOf(mockWorks)

    override fun getWorkById(id: String): Flow<Work?> = flowOf(mockWorks.find { it.id == id })

    override fun getWorksByStatus(status: WorkStatus): Flow<List<Work>> =
        flowOf(mockWorks.filter { it.status == status })

    override suspend fun insertWork(work: Work) {
        // Mock implementation - en producción guardaría en base de datos
        println("Mock: Insertando trabajo ${work.title}")
    }

    override suspend fun updateWork(work: Work) {
        // Mock implementation - en producción actualizaría en base de datos
        println("Mock: Actualizando trabajo ${work.title}")
    }

    override suspend fun deleteWork(id: String) {
        // Mock implementation - en producción eliminaría de base de datos
        println("Mock: Eliminando trabajo con id $id")
    }

    override fun searchWorks(query: String): Flow<List<Work>> {
        val lowerCaseQuery = query.lowercase()
        return flowOf(
            mockWorks.filter {
                it.title.lowercase().contains(lowerCaseQuery) ||
                        (it.description?.lowercase()?.contains(lowerCaseQuery) == true) ||
                        it.clientName.lowercase().contains(lowerCaseQuery)
            }
        )
    }
}