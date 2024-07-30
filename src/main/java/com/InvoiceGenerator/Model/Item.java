package com.InvoiceGenerator.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Item {
    private String productName;
    private int quantity;
    private double unitPrice;
    
}
