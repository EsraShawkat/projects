package com.example.backend.controllers;

import com.example.backend.exceptions.NotFoundException;
import com.example.backend.exceptions.PreConditionFailedException;
import com.example.backend.exceptions.ResourceNotFoundException;
import com.example.backend.models.Inventory;
import com.example.backend.models.Team;
import com.example.backend.models.Warehouse;
import com.example.backend.repositories.InventoryRepository;
import com.example.backend.repositories.OrdersRepository;
import com.example.backend.repositories.WarehouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/warehouses")
public class WarehousesController {

    private final WarehouseRepository warehouseRepository;

    private final InventoryRepository inventoryRepository;

    private final OrdersRepository ordersRepository;


    @Autowired
    public WarehousesController(WarehouseRepository warehouseRepository, InventoryRepository inventoryRepository, OrdersRepository ordersRepository) {
        this.warehouseRepository = warehouseRepository;
        this.inventoryRepository = inventoryRepository;
        this.ordersRepository = ordersRepository;
    }

    @GetMapping("")
    public List<Warehouse> getWarehouses(){
        return warehouseRepository.findAll();
    }

    @GetMapping("/{id}")
    public Warehouse getWarehouse(@PathVariable int id){
        Warehouse warehouse = warehouseRepository.findById(id);
        if (warehouse == null){
            throw new ResourceNotFoundException("Warehouse " + id + " not found");
        }
        return warehouse;
    }

    @PostMapping(path = "/add", consumes = "application/json")
    public Warehouse addWarehouse(@RequestBody Warehouse warehouse) {
        Warehouse savedWarehouse = warehouseRepository.save(warehouse);
        System.out.println("WAREHOUSE !!!"+ warehouse.toString());

        // Build the location URI
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedWarehouse.getId())
                .toUri();

        return warehouse;
    }

    @PutMapping(path = "/{id}", produces = "application/json")
        public Warehouse updateWarehouse(@RequestBody Warehouse warehouse, @PathVariable int id) {
        System.out.println(warehouse);
        System.out.println(id);
        Warehouse existingWarehouse = warehouseRepository.findById(id);
        System.out.println(existingWarehouse);

        if (existingWarehouse == null) {
            throw new NotFoundException(); // Assuming NotFoundException handles cabin not found scenario
        }

        if (warehouse.getId() != id) {
            throw new PreConditionFailedException("Warehouse ID in path does not match ID in request body");
        }

        return warehouseRepository.update(warehouse);
    }

//    @PostMapping("/{id}/team")
//    public void assignTeam(@PathVariable long id, @RequestBody Team team) {
//        Warehouse foundWarehouse = warehouseRepositoryMock.findById(id);
//        if (foundWarehouse == null){
//            throw new ResourceNotFoundException("Warehouse " + id + " not found");
//        }
//
//        warehouseRepositoryMock.assignTeam(id, team);
//    }

    @DeleteMapping(path = "{id}", produces = "application/json")
    public void deleteWarehouse(@PathVariable int id){
        Warehouse deletedWarehouse = warehouseRepository.findById(id);
        if (deletedWarehouse == null){
            throw  new ResourceNotFoundException("Warehouse " + id + " not found");
        }
        List<Inventory> inventories = deletedWarehouse.getInventory();
        if(deletedWarehouse.getInventory() != null) {
            for (Inventory inventory : inventories){
                ordersRepository.findAll().stream().filter(orders -> orders.getInventory().getId() == inventory.getId()).forEach(orders -> ordersRepository.deleteOrder(orders.getId()));
                inventoryRepository.deleteItem(inventory.getId());
            }
        }
        warehouseRepository.deleteById(id);
    }

    @DeleteMapping ("/{warehouseId}/removeTeam/{teamId}")
    public void removeTeam(@PathVariable int warehouseId, @PathVariable int teamId){
        Warehouse foundWarehouse = warehouseRepository.findById(warehouseId);
        int foundWarehouseId = foundWarehouse.getId();
        Team foundTeam = warehouseRepository.findTeam(foundWarehouseId, teamId);
        warehouseRepository.removeTeam(foundWarehouseId, foundTeam);
    }

//    @Override
//    public void run(String... args) throws Exception {
//        Warehouse warehouse1 = new Warehouse();
//        warehouse1.setName("Warehouse356");
//        warehouse1.setLocation("Nassaukade, Amsterdam");
//
////        Team team1 = new Team(1, "Test", );
////        Team team2 = new Team(2 , "Test2", );
//
////        warehouse1.addTeam(team1);
////        warehouse1.addTeam(team2);
//
////        Project project1 = new Project();
////        Project project2 = new Project();
////        warehouse1.addProject(project1);
////        warehouse1.addProject(project2);
//
////        Inventory inventory1 = new Inventory();
////        Inventory inventory2 = new Inventory();
////        warehouse1.addInventory(inventory1);
////        warehouse1.addInventory(inventory2);
//        warehouseRepository.save(warehouse1);
//    }
}
