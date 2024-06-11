package com.example.backend.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "Warehouse")
public class Warehouse {

    @GeneratedValue
    @Id
    private int id;

    private String name;
    private String location;

    @OneToMany(mappedBy = "warehouseId", cascade = CascadeType.PERSIST)
    private List<Team> teams;

    @OneToMany(mappedBy = "warehouse", cascade = CascadeType.PERSIST)
    private List<Project> projects;

    @OneToMany(mappedBy = "warehouseId", cascade = CascadeType.ALL)
    private List<Inventory> inventories;

    public Warehouse(int id, String name, String location, List<Team> teams, List<Project> projects, List<Inventory> inventories){
        this.id = id;
        this.name = name;
        this.location = location;
        this.teams = teams;
        this.projects = projects;
        this.inventories = inventories;
    }

    public Warehouse(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public List<Team> getTeams() {
        return teams;
    }

    public void setTeams(List<Team> teams) {
        this.teams = teams;
    }

    public List<Project> getProjects() {
        return projects;
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }

    public List<Inventory> getInventory() {
        return inventories;
    }

    public void setInventory(List<Inventory> inventory) {
        this.inventories = inventory;
    }


//    public void addTeam(Team team) {
//        if (team != null && !teams.contains(team)) {
//            teams.add(team);
//            team.setWarehouseId(this);
//            for (Team t: teams) {
//                System.out.println("RENTAL");
//                System.out.println(t.toString());
//            }
//        }
//    }
//
//    public void addProject(Project project) {
//        if (project != null && !projects.contains(project)) {
//            projects.add(project);
//            project.setWarehouse(this);
//            for (Project p: projects) {
//                System.out.println("RENTAL");
//                System.out.println(p.toString());
//            }
//        }
//    }
//
//    public void addInventory(Inventory inventory) {
//        if (inventory != null && !inventories.contains(inventory)) {
//            inventories.add(inventory);
//            inventory.setWarehouseId(this);
//            for (Inventory i: inventories) {
//                System.out.println("RENTAL");
//                System.out.println(i.toString());
//            }
//        }
//    }
//
//    public void removeTeam(Team team) {
//        if (team != null && teams.contains(team)) {
//            teams.remove(team);
//            team.setWarehouseId(null); // Clear the reference in the removed team
//        }
//    }

//
//    public void removeProject(Project project) {
//        if (project != null && projects.contains(project)) {
//            projects.remove(project);
//            project.setWarehouse(null); // Clear the reference in the removed project
//        }
//    }
//
//    public void removeInventory(Inventory inventory) {
//        if (inventory != null && inventories.contains(inventory)) {
//            inventories.remove(inventory);
//            inventory.setWarehouseId(null); // Clear the reference in the removed inventory
//        }
//    }
}
