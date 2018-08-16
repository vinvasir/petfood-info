import { browser } from 'protractor';
import { NavBarPage } from './../../page-objects/jhi-page-objects';
import { FoodComponentsPage, FoodUpdatePage } from './food.page-object';

describe('Food e2e test', () => {
    let navBarPage: NavBarPage;
    let foodUpdatePage: FoodUpdatePage;
    let foodComponentsPage: FoodComponentsPage;

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load Foods', () => {
        navBarPage.goToEntity('food');
        foodComponentsPage = new FoodComponentsPage();
        expect(foodComponentsPage.getTitle()).toMatch(/Foods/);
    });

    it('should load create Food page', () => {
        foodComponentsPage.clickOnCreateButton();
        foodUpdatePage = new FoodUpdatePage();
        expect(foodUpdatePage.getPageTitle()).toMatch(/Create or edit a Food/);
        foodUpdatePage.cancel();
    });

    it('should create and save Foods', () => {
        foodComponentsPage.clickOnCreateButton();
        foodUpdatePage.setNameInput('name');
        expect(foodUpdatePage.getNameInput()).toMatch('name');
        foodUpdatePage.setDescriptionInput('description');
        expect(foodUpdatePage.getDescriptionInput()).toMatch('description');
        foodUpdatePage.save();
        expect(foodUpdatePage.getSaveButton().isPresent()).toBeFalsy();
    });

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});
