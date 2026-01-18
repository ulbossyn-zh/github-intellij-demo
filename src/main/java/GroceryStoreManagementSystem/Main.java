package GroceryStoreManagementSystem;

import GroceryStoreManagementSystem.menu.Menu;
import GroceryStoreManagementSystem.menu.MenuManager;

public class Main {
    public static void main(String[] args) {
        Menu menu = new MenuManager();
        menu.run();
    }
}