package com.InvoiceGenerator.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.stereotype.Service;

import com.InvoiceGenerator.Model.Item;
import com.InvoiceGenerator.Model.Order;

@Service
public class InvoiceGeneratorServiceImpl implements InvoiceGeneratorService {

	@Override
	public byte[] generateInvoice(Order order) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PDDocument document = new PDDocument();
		PDPage page = new PDPage();
		document.addPage(page);

		PDPageContentStream contentStream = new PDPageContentStream(document, page);

		// add the header
		contentStream.beginText();
		contentStream.setFont(PDType1Font.HELVETICA_BOLD, 16);
		contentStream.newLineAtOffset(50, 750);
		contentStream.showText("Invoice");
		contentStream.endText();

		contentStream.beginText();
		contentStream.setFont(PDType1Font.HELVETICA, 12);
		contentStream.newLineAtOffset(50, 720);
		contentStream.showText("Customer Name: " + order.getCustomerName());
		contentStream.endText();

		// Add table header
		contentStream.beginText();
		contentStream.newLineAtOffset(50, 680);
		contentStream.showText("Item");
		contentStream.newLineAtOffset(200, 0);
		contentStream.showText("Quantity");
		contentStream.newLineAtOffset(100, 0);
		contentStream.showText("Unit Price");
		contentStream.endText();

		// Add the items
		contentStream.setFont(PDType1Font.HELVETICA, 12);
		int yOffset = 660;
		for (Item item : order.getItems()) {
			contentStream.beginText();
			contentStream.newLineAtOffset(50, yOffset);
			contentStream.showText(item.getProductName());
			contentStream.newLineAtOffset(200, yOffset);
			contentStream.showText(String.valueOf(item.getQuantity()));
			contentStream.newLineAtOffset(100, yOffset);
			contentStream.showText("RS" + item.getUnitPrice());
			contentStream.endText();
			yOffset -= 20;

		}
		contentStream.beginText();
		contentStream.newLineAtOffset(50, yOffset - 20);
		double totalAmount = calculateTotal(order.getItems());
		contentStream.showText("Total Amount: RS" + totalAmount);
		contentStream.endText();

		// closed the content stream class.
		contentStream.close();

		// Save the results and ensure that the document is properly closed.
		document.save("Invoice.pdf");
		document.close();

		return baos.toByteArray();
	}

	private double calculateTotal(List<Item> items) {

		double totalAmount = 0;
		for (Item item : items) {
			totalAmount += item.getQuantity() * item.getUnitPrice();
		}
		return totalAmount;
	}
}
