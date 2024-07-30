package com.InvoiceGenerator.Service;

import java.io.IOException;

import com.InvoiceGenerator.Model.Order;

public interface InvoiceGeneratorService {

	public byte[] generateInvoice(Order order) throws IOException;
}
