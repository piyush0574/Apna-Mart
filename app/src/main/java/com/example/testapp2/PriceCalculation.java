package com.example.testapp2;

public class PriceCalculation {
    public static String discountAmount(String cuttedPrice,String sellingPrice)
    {
        Double CP=Double.parseDouble(cuttedPrice);
        Double SP=Double.parseDouble(sellingPrice);
        int discount=(int)(CP-SP);
        return String.valueOf(discount);
    }
    public static String discountPercentage(String cuttedPrice,String sellingPrice)
    {
        Double CP=Double.parseDouble(cuttedPrice);
        Double SP=Double.parseDouble(sellingPrice);
        int discount=(int)(CP-SP);
        int discountPercenta=(int)(Math.ceil(discount/CP*100));
        return String.valueOf(discountPercenta);
    }

}
