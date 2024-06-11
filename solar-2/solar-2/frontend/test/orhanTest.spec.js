import {mount} from '@vue/test-utils';
import LoginComponent from '@/components/login/LoginComponent.vue';
import DashboardComponent from "@/components/dashboard/DashboardComponent.vue";
import {createRouter, createWebHistory} from 'vue-router';

const router = createRouter({
    history: createWebHistory(),
    routes: [
        {path: '/', component: DashboardComponent},
        {path: '/dashboard', component: DashboardComponent},
    ],
});

const mockRouter = {
    ...router,
    push: jest.fn(),
    currentRoute: {path: '/'},

};

const setupWrapper = (authenticateMock = jest.fn().mockResolvedValue({
    success: true,
    token: 'fakeToken',
    userId: 1,
    email: 'test@example.com',
    role: 'admin',
})) => {
    return mount(LoginComponent, {
        global: {
            provide: {
                usersService: {
                    authenticate: authenticateMock,
                },
            },
            plugins: [mockRouter],
        },
    });
};

describe('LoginComponent', () => {
    let wrapper;

    beforeEach(() => {
        wrapper = setupWrapper();
    });

    afterEach(() => {
        wrapper.unmount();
    });

    it('renders correctly', () => {
        expect(wrapper.exists()).toBe(true);
    });

    it('performs login successfully and navigates to /dashboard', async () => {
        // Arrange
        await wrapper.setData({
            email: 'test@example.com',
            password: 'password',
        });

        // Act
        await wrapper.vm.login();
        await wrapper.vm.$nextTick();

        console.log('Current route:', wrapper.vm.$route); // Log de huidige route

        // Assert
        expect(mockRouter.push).toHaveBeenCalledWith('/dashboard');
    });

    it('handles login failure with invalid credentials', async () => {
        // Mock de authenticate-functie van usersService om een mislukte inlogpoging te simuleren
        const authenticateMock = jest.fn().mockResolvedValue({ success: false, message: 'Invalid credentials' });

        // Initialisatie van de wrapper met aangepaste authenticateMock
        wrapper = setupWrapper(authenticateMock);

        // Simuleer het invullen van ongeldige inloggegevens en controleer of het foutbericht correct wordt weergegeven
        await wrapper.setData({
            email: 'invalid@example.com',
            password: 'invalidpassword',
        });
        await wrapper.find('form').trigger('submit.prevent');
        await wrapper.vm.$nextTick();

        // Assertions om te controleren of het foutbericht correct is weergegeven
        expect(authenticateMock).toHaveBeenCalledWith('invalid@example.com', 'invalidpassword');
        expect(wrapper.vm.error).toBe(true);
        expect(wrapper.find('.error-message').text()).toContain('Invalid credentials');
    });

    it('handles login failure with empty fields', async () => {
        // Arrange: Leave email and password empty

        // Act
        await wrapper.vm.login();
        await wrapper.vm.$nextTick();

        // Assert
        expect(wrapper.vm.error).toBe(true);
        expect(wrapper.find('.error-message').text()).toContain("Email or password can't be empty.");
    });

    it('toggles password visibility on button click', async () => {
        // Arrange: Password is initially hidden

        // Act
        await wrapper.find('.show-password-button').trigger('click');
        await wrapper.vm.$nextTick();

        // Assert
        expect(wrapper.vm.passwordFieldType).toBe('text');
    });

    it('performs login successfully with correct credentials', async () => {
        // Mock de authenticate-functie van usersService om een succesvolle inlogpoging te simuleren
        const authenticateMock = jest.fn().mockResolvedValue({
            success: true,
            token: 'fakeToken',
            userId: 1,
            email: 'test@example.com',
            role: 'admin',
        });

        // Initialisatie van de wrapper met aangepaste authenticateMock
        wrapper = setupWrapper(authenticateMock);

        // Simuleer het invullen van geldige inloggegevens en controleer of er geen fouten optreden
        await wrapper.setData({
            email: 'valid@example.com',
            password: 'validpassword',
        });
        await wrapper.find('form').trigger('submit.prevent');
        await wrapper.vm.$nextTick();

        // Assertions om te controleren of er geen fouten zijn opgetreden
        expect(authenticateMock).toHaveBeenCalledWith('valid@example.com', 'validpassword');
        expect(wrapper.vm.error).toBe(false);
        expect(wrapper.find('.error-message').exists()).toBe(false);
    });



});
