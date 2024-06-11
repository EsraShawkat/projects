package com.example.backend;

import com.example.backend.models.Inventory;
import com.example.backend.repositories.imp.InventoryRepositoryJPA;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
public class InventoryRepositoryTest {
    @Autowired
    private InventoryRepositoryJPA inventoryRepositoryJPA;


    // independent
    // repeatable
    // timely
    @Test
    public void saveInventoryTest(){
        // Arrange
        Inventory testItem = inventoryRepositoryJPA.findById(409);

        // Action
        Inventory savedItem = inventoryRepositoryJPA.save(testItem);

        // Assert
        assertNotNull(savedItem);
    }

    // independent
    // timely
    @Test
    public void deleteInventoryItemTest(){
        // Arrange
        Inventory testItem = inventoryRepositoryJPA.findById(444);
        Inventory savedItem = inventoryRepositoryJPA.save(testItem);

        // Action
        inventoryRepositoryJPA.deleteItem(savedItem.getId());

        // Assert
        Inventory deletedItem = inventoryRepositoryJPA.findById(savedItem.getId());
        assertNull(deletedItem);
    }
}g
