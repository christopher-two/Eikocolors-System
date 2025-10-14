package org.christophertwo.eikocolors.feature.clients.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.christophertwo.eikocolors.feature.clients.desingsystem.ClientRepository
import org.christophertwo.eikocolors.feature.clients.domain.model.Client
import org.christophertwo.eikocolors.feature.clients.domain.model.ClientStatus
import kotlin.time.ExperimentalTime

class ClientRepositoryImpl : ClientRepository {

    // TODO: Implementar con base de datos real
    @OptIn(ExperimentalTime::class)
    private val clients = mutableListOf<Client>().apply {
        val now = kotlin.time.Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())

        addAll(listOf(
            Client(
                id = "1",
                name = "María García López",
                email = "maria.garcia@email.com",
                phone = "+34 612 345 678",
                company = "García Diseño S.L.",
                address = "Calle Mayor 123, 28013 Madrid",
                status = ClientStatus.ACTIVE,
                totalWorks = 15,
                activeWorks = 3,
                completedWorks = 12,
                notes = "Cliente VIP. Preferencia por colores cálidos y estilos modernos. Muy puntual con los pagos.",
                createdAt = now,
                updatedAt = now,
                lastContactDate = now
            ),
            Client(
                id = "2",
                name = "Carlos Rodríguez Martínez",
                email = "carlos.rodriguez@techsolutions.com",
                phone = "+34 698 765 432",
                company = "Tech Solutions S.A.",
                address = "Av. Diagonal 456, Barcelona",
                status = ClientStatus.ACTIVE,
                totalWorks = 8,
                activeWorks = 2,
                completedWorks = 6,
                notes = "Director de una empresa tecnológica. Interesado en diseños minimalistas.",
                createdAt = now,
                updatedAt = now,
                lastContactDate = now
            ),
            Client(
                id = "3",
                name = "Ana Martínez Sánchez",
                email = "ana.martinez@correo.com",
                phone = "+34 645 123 789",
                company = null,
                address = "Plaza España 12, Valencia",
                status = ClientStatus.POTENTIAL,
                totalWorks = 0,
                activeWorks = 0,
                completedWorks = 0,
                notes = "Contacto inicial realizado. Interesada en proyecto de renovación completa de vivienda. Presupuesto medio-alto.",
                createdAt = now,
                updatedAt = now,
                lastContactDate = now
            ),
            Client(
                id = "4",
                name = "Luis Fernández Torres",
                email = "luis.fernandez@construcciones.es",
                phone = "+34 677 888 999",
                company = "Construcciones Fernández",
                address = "Calle Alcalá 789, Madrid",
                status = ClientStatus.INACTIVE,
                totalWorks = 5,
                activeWorks = 0,
                completedWorks = 5,
                notes = "Cliente antiguo. Última colaboración hace 6 meses. Mantener contacto para futuros proyectos.",
                createdAt = now,
                updatedAt = now,
                lastContactDate = null
            ),
            Client(
                id = "5",
                name = "Isabel Gómez Ruiz",
                email = "isabel.gomez@interiordesign.com",
                phone = "+34 633 456 789",
                company = "Interior Design Studio",
                address = "Paseo de Gracia 234, Barcelona",
                status = ClientStatus.ACTIVE,
                totalWorks = 22,
                activeWorks = 5,
                completedWorks = 17,
                notes = "Interiorista profesional. Solicita trabajos frecuentemente para sus clientes. Excelente relación comercial.",
                createdAt = now,
                updatedAt = now,
                lastContactDate = now
            ),
            Client(
                id = "6",
                name = "Pedro Navarro Castro",
                email = "pedro.navarro@gmail.com",
                phone = "+34 689 123 456",
                company = null,
                address = null,
                status = ClientStatus.POTENTIAL,
                totalWorks = 0,
                activeWorks = 0,
                completedWorks = 0,
                notes = "Recomendado por María García. Interesado en pintado exterior de chalet.",
                createdAt = now,
                updatedAt = now,
                lastContactDate = null
            ),
            Client(
                id = "7",
                name = "Carmen Díaz Moreno",
                email = "carmen.diaz@hotel.com",
                phone = "+34 654 987 321",
                company = "Hotel Mediterráneo",
                address = "Playa del Carmen s/n, Málaga",
                status = ClientStatus.ACTIVE,
                totalWorks = 12,
                activeWorks = 1,
                completedWorks = 11,
                notes = "Cadena hotelera. Proyectos de renovación anual. Cliente de alto valor.",
                createdAt = now,
                updatedAt = now,
                lastContactDate = now
            ),
            Client(
                id = "8",
                name = "Javier López Hernández",
                email = "javier.lopez@outlook.com",
                phone = "+34 611 222 333",
                company = null,
                address = "Calle Real 45, Sevilla",
                status = ClientStatus.INACTIVE,
                totalWorks = 3,
                activeWorks = 0,
                completedWorks = 3,
                notes = "Última colaboración hace 1 año. Satisfecho con los trabajos realizados.",
                createdAt = now,
                updatedAt = now,
                lastContactDate = null
            )
        ))
    }

    override fun getAllClients(): Flow<List<Client>> {
        return flowOf(clients)
    }

    override fun getClientById(id: String): Flow<Client?> {
        return flowOf(clients.find { it.id == id })
    }

    override fun getClientsByStatus(status: ClientStatus): Flow<List<Client>> {
        return flowOf(clients.filter { it.status == status })
    }

    override suspend fun insertClient(client: Client) {
        clients.add(client)
    }

    override suspend fun updateClient(client: Client) {
        val index = clients.indexOfFirst { it.id == client.id }
        if (index != -1) {
            clients[index] = client
        }
    }

    override suspend fun deleteClient(id: String) {
        clients.removeAll { it.id == id }
    }

    override fun searchClients(query: String): Flow<List<Client>> {
        val filtered = clients.filter { client ->
            client.name.contains(query, ignoreCase = true) ||
            client.email.contains(query, ignoreCase = true) ||
            client.phone.contains(query, ignoreCase = true) ||
            client.company?.contains(query, ignoreCase = true) == true
        }
        return flowOf(filtered)
    }
}
