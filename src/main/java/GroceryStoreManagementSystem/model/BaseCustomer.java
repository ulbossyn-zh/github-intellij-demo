package GroceryStoreManagementSystem.model;

import GroceryStoreManagementSystem.exception.InvalidInputException;

public class BaseCustomer extends Customer {

    public BaseCustomer(int customerId, String name, String membershipLevel, double totalPurchases)
            throws InvalidInputException {
        super(customerId, name, membershipLevel, totalPurchases);
    }

    @Override
    public String getCustomerType() {
        return "BaseCustomer";
    }
}