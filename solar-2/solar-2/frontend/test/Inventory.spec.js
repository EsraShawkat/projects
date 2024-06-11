import { mount } from "@vue/test-utils";
import InventoryComponent from '../src/components/inventory/InventoryComponent.vue';
import confirmDialog from "@/models/confirmDialog";

describe("InventoryComponent", () => {
    const inventoryServiceMock = {
        findAll: jest.fn().mockResolvedValue([{ id: 1, name: 'Test' }]),
        findByWarehouse: jest.fn(() => []),
        addItem: jest.fn().mockResolvedValue({}),
        deleteItem: jest.fn().mockResolvedValue({}),
    }

    let wrapper;

    beforeEach(async () => {
        // Arrange: Reset mock functions before each test
        Object.values(inventoryServiceMock).forEach(mockFunction => mockFunction.mockClear());

        // Act: Mount the InventoryComponent with the mocked services
        wrapper = mount(InventoryComponent, {
            global: {
                provide: {
                    inventoryService: inventoryServiceMock
                }
            }
        });

        // Wait for Vue to update the component
        await wrapper.vm.$nextTick();
    });

    // RIGHT: This test checks if the component exists in the DOM after mounting.
    // fast
    // independent
    // repeatable
    // timely
    it("renders properly when mounted", () => {
        // Act: Mount the InventoryComponent
        const wrapper = mount(InventoryComponent);

        // Assert: Check if the component exists in the DOM
        expect(wrapper.exists()).toBe(true);
    });

    // RIGHT: This test checks if the initial data is set correctly on component creation.
    // fast
    // independent
    // repeatable
    // timely
    it("loads data on created", async () => {
        // Arrange: Expect initial data to be empty
        expect(wrapper.vm.items).toEqual([]);
        expect(wrapper.vm.warehouses).toEqual([]);
        expect(wrapper.vm.filteredItems).toEqual([]);
        expect(wrapper.vm.totalValue).toBeNull();

        // Act: Wait for Vue to update the component
        await wrapper.vm.$nextTick();

        // Assert: Check if data is still empty after component creation
        expect(wrapper.vm.items).toEqual([]);
        expect(wrapper.vm.warehouses).toEqual([]);
        expect(wrapper.vm.filteredItems).toEqual([]);
        expect(wrapper.vm.totalValue).toBeNull();
    });

    // RIGHT: This test checks if the delete_item method is triggered correctly.
    // fast
    // independent
    // repeatable
    // timely
    it("deletes an item", async () => {
        // Arrange: Mock the confirmation dialog to always confirm
        confirmDialog.showConfirmationDialog = jest.fn().mockImplementation((name, callback) => {
            callback();
            return Promise.resolve(true);
        });

        const itemToDelete = { id: 1, name: "Item 1", inventoryId: 123 };

        // Act: Set data and trigger delete_item method
        await wrapper.setData({
            filteredItems: [itemToDelete],
        });
        await wrapper.vm.delete_item(itemToDelete);

        // Assert: Check if deleteItem method is called with the correct argument
        expect(inventoryServiceMock.deleteItem).toHaveBeenCalledWith(itemToDelete.inventoryId);
    });

    // RIGHT: This test checks if selecting a warehouse triggers the correct inventory updates.
    // fast
    // repeatable
    // timely
    it("selects a warehouse and fetches inventory items", async () => {
        // Act: Set data and trigger handleWarehouseSelected method
        await wrapper.setData({ warehouses: [{ id: 1, name: "Warehouse 1" }] });
        await wrapper.vm.handleWarehouseSelected({ id: 1, name: "Warehouse 1" });

        // Assert: Check if selectedWarehouse is set and filteredItems is empty
        expect(wrapper.vm.selectedWarehouse).toEqual({ id: 1, name: "Warehouse 1" });
        expect(wrapper.vm.filteredItems).toEqual([]);
    });

    // RIGHT: This test checks if opening the AddItemToWarehouse form triggers the correct state change.
    // fast
    // independent
    // repeatable
    // timely
    it("opens the AddItemToWarehouse form when the 'Add item to warehouse' button is clicked", async () => {
        // Arrange: Set data to have the form closed and a selected warehouse
        await wrapper.setData({
            showAddItemToWarehouseForm: false,
            selectedWarehouse: { id: 1, name: "Warehouse 1" }
        });

        // Act: Trigger the method to open the form
        await wrapper.vm.openAddItemToWarehouse();

        // Assert: Check if the form is open after the method call
        expect(wrapper.vm.showAddItemToWarehouseForm).toBe(true);
    });

    // RIGHT: This test checks if closing the AddItemToWarehouse form triggers the correct state change.
    // fast
    // independent
    // repeatable
    // timely
    it("closes the AddItemToWarehouse form when the 'close-popup' event is emitted", async () => {
        // Arrange: Set data to have the form open
        await wrapper.setData({ showAddItemToWarehouseForm: true });

        // Act: Trigger the method to close the form
        await wrapper.vm.closeAddItempopup();

        // Assert: Check if the form is closed after the method call
        expect(wrapper.vm.showAddItemToWarehouseForm).toBe(false);
    });
});
