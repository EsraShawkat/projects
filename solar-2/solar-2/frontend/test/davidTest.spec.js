import {mount} from '@vue/test-utils';
import WarehouseComponent from '../src/components/warehouses/WarehouseComponent.vue';
import confirmDialog from "@/models/confirmDialog";

// Mocking the warehouse service for testing
const warehousesServiceMock = {
    findAll: jest.fn().mockResolvedValue([{id: 1, name: 'Test Warehouse', location: 'Utrecht'}]),
    add: jest.fn().mockResolvedValue({}),
    deleteById: jest.fn().mockResolvedValue({}),
    removeTeam: jest.fn().mockResolvedValue({}),
    save: jest.fn().mockResolvedValue(true),
};

let wrapper;

beforeEach(async () => {
    // Clearing the mock functions before each test
    Object.values(warehousesServiceMock).forEach(mockFunction => mockFunction.mockClear());

    // Mounting the WarehouseComponent with the mocked warehouses service
    wrapper = mount(WarehouseComponent, {
        global: {
            provide: {
                warehousesService: warehousesServiceMock
            }
        }
    });
    await wrapper.vm.$nextTick();
});

describe('Warehouse', () => {

    // This test checks if the component renders correctly.
    it('Renders correctly', () => {
        // Check if the component exists
        expect(wrapper.exists()).toBe(true);
    });

    // This test verifies that the component fetches and displays warehouses on creation.
    it('Fetches and displays warehouses on created', async () => {
        // Call for the findAll function
        expect(warehousesServiceMock.findAll).toHaveBeenCalled();

        // Check if the findAll has returned all the warehouses in the list
        expect(wrapper.vm.warehouses).toEqual([{id: 1, name: 'Test Warehouse', location: 'Utrecht'}]);
        expect(wrapper.vm.filteredWarehouses).toEqual([{id: 1, name: 'Test Warehouse', location: 'Utrecht'}]);
    });

    // This test checks if the component adds a warehouse correctly.
    it('Adds a warehouse correctly', async () => {
        // Add a warehouse to the list
        await wrapper.vm.add_warehouse({id: 2, name: 'New Warehouse', location: 'Arnhem'});
        await wrapper.vm.$nextTick();

        // Check if the add function has been called and if the warehouse has been added
        expect(warehousesServiceMock.add).toHaveBeenCalledWith({id: 2, name: 'New Warehouse', location: 'Arnhem'});
        expect(wrapper.vm.filteredWarehouses).toEqual([
            {id: 1, name: 'Test Warehouse', location: 'Utrecht'},
            {id: 2, name: 'New Warehouse', location: 'Arnhem'}
        ]);
    });

    // This test checks if the component deletes a warehouse correctly.
    it('Deletes a warehouse correctly', async () => {
        // Delete a warehouse and confirm the verification
        confirmDialog.showConfirmationDialog = jest.fn().mockImplementation((name, callback) => {
            callback();
            return Promise.resolve(true);
        });
        await wrapper.vm.delete_warehouse({id: 1, name: 'Test Warehouse'});

        // Check if the warehouse has been deleted
        expect(warehousesServiceMock.deleteById).toHaveBeenCalledWith(1);
    });

    // This test checks if the component saves warehouse location correctly.
    it('Saves warehouse location correctly', async () => {
        // Save the new location
        await wrapper.vm.savedLocation({id: 1, location: 'New Location'});
        await wrapper.vm.$nextTick();

        // Check if the save function has been called in the mock
        expect(warehousesServiceMock.save).toHaveBeenCalledWith({id: 1, location: 'New Location'});
    });

    // This test checks if the component handles warehouse search correctly.
    it('Handles warehouse search correctly', async () => {
        // Set up initial data
        wrapper.setData({
            warehouses: [
                { id: 1, name: 'Warehouse A', location: 'City A' },
                { id: 2, name: 'Warehouse B', location: 'City B' },
                { id: 3, name: 'Warehouse C', location: 'City C' }
            ],
            filteredWarehouses: []
        });

        // Trigger a search for warehouses with the term 'B'
        wrapper.vm.handleSearch('B');

        // Check if the component correctly updates filteredWarehouses based on the search term
        expect(wrapper.vm.filteredWarehouses).toEqual([
            { id: 2, name: 'Warehouse B', location: 'City B' }
        ]);
    });


});



