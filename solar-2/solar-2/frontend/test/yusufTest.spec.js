import {mount} from '@vue/test-utils';
import DashboardComponent from "@/components/dashboard/DashboardComponent.vue";

// Mocking the services for testing
const warehousesServiceMock = {
    findAll: jest.fn().mockResolvedValue([{id: 1, name: 'Test Warehouse', location: 'Utrecht'}]),
    add: jest.fn().mockResolvedValue({}),
    deleteById: jest.fn().mockResolvedValue({}),
    removeTeam: jest.fn().mockResolvedValue({}),
    save: jest.fn().mockResolvedValue(true),
};

const forecastServiceMock = {
    findAll: jest.fn().mockResolvedValue([
        {
            id: 1,
            orderQuantity: 2,
            stock: 20,
            quantityAfter: 18,
            date: "2023-12-28T23:00:00.000+00:00",
            orders: {
                id: 46,
                project: {
                    id: 52,
                    name: "Park Amsterdam",
                    location: "amsterdam",
                    description: "Dirty water",
                    status: "UNSTARTED",
                    warehouse: 3152,
                    startDate: "2023-12-28T23:00:00.000+00:00",
                    dueAt: "2024-01-20T00:00:00.000+00:00",
                    teamId: 32
                },
                inventory: {
                    id: 417,
                    quantity: 20,
                    max_quantity: 200,
                    status: "LOW",
                    low_stock_limit: 50,
                    warehouseId: 3152,
                    productId: 5,
                    pending: false
                },
                quantity: 2,
                status: "REQUESTED"
            }
        }
    ]),
};

const projectServiceMock = {
    findAll: jest.fn().mockResolvedValue([{}]),
}


let wrapper;

beforeEach(async () => {
    // Clearing the mock functions before each test
    Object.values(warehousesServiceMock).forEach(mockFunction => mockFunction.mockClear());


    // Mounting the DashboardComponent with the mocked services
    wrapper = mount(DashboardComponent, {
        global: {
            provide: {
                warehousesService: warehousesServiceMock,
                forecastServiceMock: forecastServiceMock,
                projectServiceMock: projectServiceMock
            }
        }
    });
    await wrapper.vm.$nextTick();
});

describe('Dashboard', () => {

    // Test 1: Checks if the component renders correctly.
    it('renders correctly', () => {
        expect(wrapper.exists()).toBe(true);
    });

    // Test 2: Checks if the warehouses are loaded correctly on component creation.
    it('loads warehouses on component creation', async () => {
        await wrapper.vm.$nextTick();
        expect(warehousesServiceMock.findAll).toHaveBeenCalled();
        expect(wrapper.vm.warehouses).toHaveLength(1); // Assuming one warehouse is mocked
    });

// Test 3: Checks if the forecasting data is loaded correctly on component creation.

// - Focused: The test is focused on checking whether forecasting data is loaded correctly during component creation.
// - Automated: The test is automated and can be executed as part of an automated test suite.
// - Specific: The test is specific in its scope, targeting a particular behavior related to forecasting data loading.
// - Timely: The test is timely as it is executed during the component creation, ensuring the correct behavior at that point in the lifecycle.

    it('loads forecasting data on component creation', async () => {
        // Arrange: Clearing the mock functions before the test
        Object.values(forecastServiceMock).forEach(mockFunction => mockFunction.mockClear());

        // Act: Triggering component creation and waiting for the next tick
        await wrapper.vm.$nextTick();

        // Assert: Verifying that the service method was called and the data is populated
        expect(forecastServiceMock.findAll).toHaveBeenCalled();
        expect(wrapper.vm.forecasting).toHaveLength(1); // Assuming one forecast item is mocked
    });

    // Test 4: Checks if selecting a dashboard updates the selectedDashboard property.
    it('updates selectedDashboard on dashboard selection', async () => {
        const dashboardTitle = 'Test Warehouse';
        await wrapper.vm.$nextTick();
        wrapper.vm.handleDashboardSelected(dashboardTitle);
        expect(wrapper.vm.selectedDashboard).toEqual({id: 1, name: 'Test Warehouse', location: 'Utrecht'});
    });

// Test 5: Checks if clicking an individual dashboard item triggers the handleDashboardSelected method.
    it('handles individual dashboard item selection', async () => {
        // Arrange: Clearing the mock functions before the test
        const handleDashboardSelectedSpy = jest.spyOn(wrapper.vm, 'handleDashboardSelected');

        // Act: Triggering a click on an individual dashboard item (replace 'Test Warehouse' with an actual dashboard item)
        await wrapper.find('.button-title').trigger('click');
        await wrapper.vm.$nextTick();

        // Assert: Verifying that the handleDashboardSelected method was called with the correct title
        expect(handleDashboardSelectedSpy).toHaveBeenCalledWith('Test Warehouse');
        expect(wrapper.vm.showAllWarehouses).toBe(false); // Assuming clicking an individual item hides AllWarehousesContainer
    });

// Test 6: Checks if the toggleProject method sets the selectedProject property.
    it('toggles the selectedProject when a project is clicked', async () => {
        // Arrange: Clearing the mock functions before the test
        const toggleProjectSpy = jest.spyOn(wrapper.vm, 'toggleProject');

        // Act: Triggering a click on a project (replace 'Test Project' with an actual project name)
        await wrapper.find('.project-button').trigger('click');
        await wrapper.vm.$nextTick();

        // Assert: Verifying that the toggleProject method was called and the selectedProject is set correctly
        expect(toggleProjectSpy).toHaveBeenCalled();
        expect(wrapper.vm.selectedProject).toEqual(/* Expected project object */);
    });
});