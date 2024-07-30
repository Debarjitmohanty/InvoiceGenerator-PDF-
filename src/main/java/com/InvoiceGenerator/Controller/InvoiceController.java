package com.InvoiceGenerator.Controller;

import java.io.IOException;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.InvoiceGenerator.Model.Item;
import com.InvoiceGenerator.Model.Order;
import com.InvoiceGenerator.Service.InvoiceGeneratorService;

@RestController
public class InvoiceController {

	@Autowired
	private InvoiceGeneratorService generatorService;

	@GetMapping("getInvoice/{orderId}")
	public ResponseEntity<byte[]> getInvoice(@PathVariable("orderId") int orderId) throws IOException {

		Item i1 = new Item("Product 1", 10, 1010);
		Item i2 = new Item("Product 2", 10, 1020);
		Item i3 = new Item("Product 3", 10, 1030);
		Item i4 = new Item("Product 4", 10, 1040);
		Item i5 = new Item("Product 5", 10, 1050);

		Order order = new Order(orderId, "Debarjit Mohanty", Arrays.asList(i1, i2, i3, i4, i5));

		byte[] bytes = generatorService.generateInvoice(order);

		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.CONTENT_TYPE, "application/pdf");

		return ResponseEntity.ok().headers(headers).body(bytes);
	}
}
