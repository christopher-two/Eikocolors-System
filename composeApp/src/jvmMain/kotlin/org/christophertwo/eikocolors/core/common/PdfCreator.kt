package org.christophertwo.eikocolors.core.common

import org.openpdf.text.*
import org.openpdf.text.pdf.PdfPCell
import org.openpdf.text.pdf.PdfPTable
import org.openpdf.text.pdf.PdfWriter
import java.awt.Color
import java.io.FileOutputStream
import java.time.LocalDate
import java.time.format.DateTimeFormatter

// Modelo de datos para el cliente
data class CustomerInfo(
    val name: String,
    val address: String = "",
    val phone: String = "",
    val whatsapp: String = "",
    val email: String = ""
)

// Modelo de datos para un producto/artículo
data class ProductItem(
    val quantity: String,
    val description: String,
    val unitPrice: Double
)

// Modelo de datos para la nota de venta
data class SalesNote(
    val noteNumber: String,
    val date: LocalDate = LocalDate.now(),
    val customer: CustomerInfo,
    val items: List<ProductItem>
)

class PdfCreator : PdfCreatorInterface {

    private val fontBold = Font(Font.HELVETICA, 10f, Font.BOLD, Color.BLACK)
    private val fontSmall = Font(Font.HELVETICA, 8f, Font.NORMAL, Color.BLACK)
    private val fontSmallBold = Font(Font.HELVETICA, 8f, Font.BOLD, Color.BLACK)
    private val fontTitle = Font(Font.HELVETICA, 14f, Font.BOLD, Color.BLACK)

    // Colores corporativos (morado/rosa de Eiko Colors)
    private val purpleColor = Color(153, 102, 153)

    override fun createPdf(name: String) {
        val customer = CustomerInfo(
            name = "Gloria Yuritzia Chavez Cerecero",
            address = "Niños Héroes No. 06 Col. Centro C.P. 60000",
            phone = "4521012562",
            whatsapp = "@eiko_colors",
            email = "Uruapan, Michoacán"
        )

        val items = listOf(
            ProductItem(
                quantity = "2",
                description = "Calcas para t-cartera",
                unitPrice = 150.0
            )
        )

        val salesNote = SalesNote(
            noteNumber = "01868",
            date = LocalDate.now(),
            customer = customer,
            items = items
        )

        createSalesNotePdf(name, salesNote)
    }

    fun createSalesNotePdf(
        outputPath: String,
        salesNote: SalesNote
    ) {
        val document = Document(PageSize.A4.rotate(), 36f, 36f, 36f, 36f)

        try {
            val writer = PdfWriter.getInstance(document, FileOutputStream("$outputPath.pdf"))
            document.open()

            // 1. Encabezado: Logo + Info empresa (izquierda) y Nota de venta + Fecha (derecha)
            document.add(createHeader(salesNote))

            document.add(Chunk.NEWLINE)

            // 2. Cliente y teléfono en tabla
            document.add(createClientSection(salesNote.customer))

            document.add(Chunk.NEWLINE)

            // 3. Tabla de productos SIN total integrado
            document.add(createProductsTable(salesNote.items))

            // 4. Total alineado con Importe
            document.add(createTotalSection(salesNote.items))

            document.add(Chunk.NEWLINE)

            // 5. Imagen de agradecimiento abajo a la izquierda
            document.add(createFooterImage())

            document.close()
            writer.close()
        } catch (e: Exception) {
            e.printStackTrace()
            if (document.isOpen) {
                document.close()
            }
        }
    }

    private fun createHeader(salesNote: SalesNote): PdfPTable {
        val headerTable = PdfPTable(2)
        headerTable.widthPercentage = 100f
        headerTable.setWidths(floatArrayOf(50f, 50f))

        // Columna izquierda: Logo e info de la empresa
        val leftCell = PdfPCell()
        leftCell.border = Rectangle.NO_BORDER

        // Tabla interna 2x2 para logo + info
        val infoTable = PdfPTable(2)
        infoTable.widthPercentage = 100f
        infoTable.setWidths(floatArrayOf(30f, 70f))

        // Logo (sin borde)
        val logoCell = PdfPCell()
        logoCell.border = Rectangle.NO_BORDER
        logoCell.horizontalAlignment = Element.ALIGN_CENTER
        logoCell.verticalAlignment = Element.ALIGN_TOP
        logoCell.paddingTop = 4f
        logoCell.paddingBottom = 4f

        try {
            val logo = Image.getInstance(javaClass.classLoader.getResource("eikocolors.png"))
            logo.scaleToFit(100f, 100f)
            logoCell.addElement(logo)
        } catch (e: Exception) {
            val titlePara = Paragraph("Eiko\nColors", Font(Font.HELVETICA, 12f, Font.BOLD, purpleColor))
            titlePara.alignment = Element.ALIGN_CENTER
            logoCell.addElement(titlePara)
        }
        infoTable.addCell(logoCell)

        // Info empresa (tabla 4x2 - ampliada)
        val companyInfoCell = PdfPCell()
        companyInfoCell.border = Rectangle.NO_BORDER
        companyInfoCell.paddingLeft = 5f

        val companyDataTable = PdfPTable(2)
        companyDataTable.widthPercentage = 100f
        companyDataTable.setWidths(floatArrayOf(50f, 50f))

        // Fila 1: Nombre completo y Teléfono
        addInfoBoxCell(companyDataTable, "Gloria Yuritzia Chavez Cerecero")
        addInfoBoxCell(companyDataTable, "4521012562")

        // Fila 2: Dirección y Facebook
        addInfoBoxCell(companyDataTable, "Niños Héroes No. 06")
        addInfoBoxCell(companyDataTable, "Facebook: Eiko Colors")

        // Fila 3: Colonia y CP + Instagram
        addInfoBoxCell(companyDataTable, "Col. Centro C.P. 60000")
        addInfoBoxCell(companyDataTable, "Instagram: @eiko_colors")

        // Fila 4: Ciudad y Estado (span completo)
        val locationCell = PdfPCell(Phrase("Uruapan, Michoacán", Font(Font.HELVETICA, 9f, Font.NORMAL, Color.BLACK)))
        locationCell.border = Rectangle.BOX
        locationCell.borderColor = purpleColor
        locationCell.colspan = 2
        locationCell.paddingTop = 4f
        locationCell.paddingBottom = 4f
        locationCell.paddingLeft = 4f
        locationCell.paddingRight = 4f
        locationCell.horizontalAlignment = Element.ALIGN_CENTER
        companyDataTable.addCell(locationCell)

        companyInfoCell.addElement(companyDataTable)
        infoTable.addCell(companyInfoCell)

        leftCell.addElement(infoTable)
        headerTable.addCell(leftCell)

        // Columna derecha: Nota de venta + Fecha
        val rightCell = PdfPCell()
        rightCell.border = Rectangle.NO_BORDER
        rightCell.horizontalAlignment = Element.ALIGN_RIGHT
        rightCell.verticalAlignment = Element.ALIGN_TOP

        // Nota de venta
        val noteTable = PdfPTable(1)
        noteTable.widthPercentage = 50f
        noteTable.horizontalAlignment = Element.ALIGN_RIGHT

        val noteTitleCell = PdfPCell(Phrase("Nota de venta", Font(Font.HELVETICA, 8f, Font.BOLD, Color.WHITE)))
        noteTitleCell.backgroundColor = purpleColor
        noteTitleCell.horizontalAlignment = Element.ALIGN_CENTER
        noteTitleCell.paddingTop = 3f
        noteTitleCell.paddingBottom = 3f
        noteTable.addCell(noteTitleCell)

        val noteNumberCell = PdfPCell(Phrase(salesNote.noteNumber, Font(Font.HELVETICA, 12f, Font.BOLD, Color.BLACK)))
        noteNumberCell.horizontalAlignment = Element.ALIGN_CENTER
        noteNumberCell.paddingTop = 5f
        noteNumberCell.paddingBottom = 5f
        noteTable.addCell(noteNumberCell)

        rightCell.addElement(noteTable)

        // Espacio
        rightCell.addElement(Chunk.NEWLINE)

        // Fecha
        val dateTable = PdfPTable(1)
        dateTable.widthPercentage = 50f
        dateTable.horizontalAlignment = Element.ALIGN_RIGHT

        val dateTitleCell = PdfPCell(Phrase("Fecha", Font(Font.HELVETICA, 8f, Font.BOLD, Color.WHITE)))
        dateTitleCell.backgroundColor = purpleColor
        dateTitleCell.horizontalAlignment = Element.ALIGN_CENTER
        dateTitleCell.paddingTop = 3f
        dateTitleCell.paddingBottom = 3f
        dateTable.addCell(dateTitleCell)

        val dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yy")
        val dateValueCell = PdfPCell(Phrase(salesNote.date.format(dateFormatter), fontSmallBold))
        dateValueCell.horizontalAlignment = Element.ALIGN_CENTER
        dateValueCell.paddingTop = 5f
        dateValueCell.paddingBottom = 5f
        dateTable.addCell(dateValueCell)

        rightCell.addElement(dateTable)

        headerTable.addCell(rightCell)

        return headerTable
    }

    private fun addInfoBoxCell(table: PdfPTable, text: String) {
        val cell = PdfPCell(Phrase(text, Font(Font.HELVETICA, 9f, Font.NORMAL, Color.BLACK)))
        cell.border = Rectangle.BOX
        cell.borderColor = purpleColor
        cell.paddingTop = 4f
        cell.paddingBottom = 4f
        cell.paddingLeft = 4f
        cell.paddingRight = 4f
        cell.horizontalAlignment = Element.ALIGN_CENTER
        table.addCell(cell)
    }

    private fun createClientSection(customer: CustomerInfo): PdfPTable {
        val clientTable = PdfPTable(2)
        clientTable.widthPercentage = 100f
        clientTable.setWidths(floatArrayOf(70f, 30f))

        // Fila 1: Encabezados con fondo morado
        val nameHeaderCell = PdfPCell(Phrase("Nombre", Font(Font.HELVETICA, 9f, Font.BOLD, Color.WHITE)))
        nameHeaderCell.backgroundColor = purpleColor
        nameHeaderCell.horizontalAlignment = Element.ALIGN_LEFT
        nameHeaderCell.paddingTop = 5f
        nameHeaderCell.paddingBottom = 5f
        nameHeaderCell.paddingLeft = 8f
        clientTable.addCell(nameHeaderCell)

        val phoneHeaderCell = PdfPCell(Phrase("Teléfono", Font(Font.HELVETICA, 9f, Font.BOLD, Color.WHITE)))
        phoneHeaderCell.backgroundColor = purpleColor
        phoneHeaderCell.horizontalAlignment = Element.ALIGN_LEFT
        phoneHeaderCell.paddingTop = 5f
        phoneHeaderCell.paddingBottom = 5f
        phoneHeaderCell.paddingLeft = 8f
        clientTable.addCell(phoneHeaderCell)

        // Fila 2: Datos del cliente
        val nameCell = PdfPCell(Phrase(customer.name, fontSmall))
        nameCell.paddingTop = 5f
        nameCell.paddingBottom = 5f
        nameCell.paddingLeft = 8f
        clientTable.addCell(nameCell)

        val phoneCell = PdfPCell(Phrase(customer.phone, fontSmall))
        phoneCell.paddingTop = 5f
        phoneCell.paddingBottom = 5f
        phoneCell.paddingLeft = 8f
        clientTable.addCell(phoneCell)

        return clientTable
    }

    private fun createProductsTable(items: List<ProductItem>): PdfPTable {
        // Solo 4 columnas: Cantidad, Descripción, Precio unitario, Importe
        val table = PdfPTable(4)
        table.widthPercentage = 100f
        table.setWidths(floatArrayOf(15f, 45f, 20f, 20f))

        // Encabezados con fondo morado y texto blanco
        val headers = listOf("Cantidad", "Descripción", "Precio unitario", "Importe")
        headers.forEach { headerText ->
            val cell = PdfPCell(Phrase(headerText, Font(Font.HELVETICA, 9f, Font.BOLD, Color.WHITE)))
            cell.backgroundColor = purpleColor
            cell.horizontalAlignment = Element.ALIGN_CENTER
            cell.verticalAlignment = Element.ALIGN_MIDDLE
            cell.paddingTop = 8f
            cell.paddingBottom = 8f
            table.addCell(cell)
        }

        // Productos
        items.forEach { item ->
            // Cantidad
            addProductCell(table, item.quantity, Element.ALIGN_CENTER)

            // Descripción
            addProductCell(table, item.description, Element.ALIGN_LEFT)

            // Precio unitario
            addProductCell(table, String.format("$ %.2f", item.unitPrice), Element.ALIGN_RIGHT)

            // Importe
            val quantity = item.quantity.toDoubleOrNull() ?: 1.0
            val importe = quantity * item.unitPrice
            addProductCell(table, String.format("$ %.2f", importe), Element.ALIGN_RIGHT)
        }

        // Filas vacías (3 filas extras)
        for (i in 1..3) {
            for (j in 1..4) {
                val emptyCell = PdfPCell(Phrase(" ", fontSmall))
                emptyCell.minimumHeight = 30f
                emptyCell.paddingTop = 5f
                emptyCell.paddingBottom = 5f
                table.addCell(emptyCell)
            }
        }

        return table
    }

    private fun addProductCell(table: PdfPTable, text: String, alignment: Int) {
        val cell = PdfPCell(Phrase(text, fontSmall))
        cell.horizontalAlignment = alignment
        cell.verticalAlignment = Element.ALIGN_MIDDLE
        cell.paddingTop = 6f
        cell.paddingBottom = 6f
        cell.paddingLeft = 4f
        cell.paddingRight = 4f
        table.addCell(cell)
    }

    private fun createTotalSection(items: List<ProductItem>): PdfPTable {
        val totalTable = PdfPTable(4)
        totalTable.widthPercentage = 100f
        totalTable.setWidths(floatArrayOf(15f, 45f, 20f, 20f))

        // Celdas vacías para alineación
        for (i in 1..2) {
            val emptyCell = PdfPCell(Phrase("", fontSmall))
            emptyCell.border = Rectangle.NO_BORDER
            totalTable.addCell(emptyCell)
        }

        // Total label con fondo morado
        val totalLabelCell = PdfPCell(Phrase("Total", Font(Font.HELVETICA, 10f, Font.BOLD, Color.WHITE)))
        totalLabelCell.backgroundColor = purpleColor
        totalLabelCell.horizontalAlignment = Element.ALIGN_RIGHT
        totalLabelCell.paddingTop = 8f
        totalLabelCell.paddingBottom = 8f
        totalLabelCell.paddingRight = 10f
        totalTable.addCell(totalLabelCell)

        // Total amount con fondo morado
        val total = items.sumOf {
            val qty = it.quantity.toDoubleOrNull() ?: 1.0
            qty * it.unitPrice
        }
        val totalAmountCell = PdfPCell(Phrase(String.format("$ %.2f", total), Font(Font.HELVETICA, 10f, Font.BOLD, Color.WHITE)))
        totalAmountCell.backgroundColor = purpleColor
        totalAmountCell.horizontalAlignment = Element.ALIGN_RIGHT
        totalAmountCell.paddingTop = 8f
        totalAmountCell.paddingBottom = 8f
        totalAmountCell.paddingRight = 10f
        totalTable.addCell(totalAmountCell)

        return totalTable
    }

    private fun createFooterImage(): PdfPTable {
        val footerTable = PdfPTable(1)
        footerTable.widthPercentage = 100f

        val imageCell = PdfPCell()
        imageCell.border = Rectangle.NO_BORDER
        imageCell.horizontalAlignment = Element.ALIGN_LEFT
        imageCell.paddingTop = 10f

        try {
            val thanksImage = Image.getInstance(javaClass.classLoader.getResource("gracias.png"))
            thanksImage.scaleToFit(200f, 250f)
            imageCell.addElement(thanksImage)
        } catch (e: Exception) {
            // Si no se encuentra la imagen, no agregar nada
        }

        footerTable.addCell(imageCell)
        return footerTable
    }
}

interface PdfCreatorInterface {
    fun createPdf(name: String)
}
