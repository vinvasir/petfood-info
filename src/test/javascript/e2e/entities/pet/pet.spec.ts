import { browser } from 'protractor';
import { NavBarPage } from './../../page-objects/jhi-page-objects';
import { PetComponentsPage, PetUpdatePage } from './pet.page-object';

describe('Pet e2e test', () => {
    let navBarPage: NavBarPage;
    let petUpdatePage: PetUpdatePage;
    let petComponentsPage: PetComponentsPage;

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load Pets', () => {
        navBarPage.goToEntity('pet');
        petComponentsPage = new PetComponentsPage();
        expect(petComponentsPage.getTitle()).toMatch(/Pets/);
    });

    it('should load create Pet page', () => {
        petComponentsPage.clickOnCreateButton();
        petUpdatePage = new PetUpdatePage();
        expect(petUpdatePage.getPageTitle()).toMatch(/Create or edit a Pet/);
        petUpdatePage.cancel();
    });

    it('should create and save Pets', () => {
        petComponentsPage.clickOnCreateButton();
        petUpdatePage.setNameInput('name');
        expect(petUpdatePage.getNameInput()).toMatch('name');
        petUpdatePage.setDescriptionInput('description');
        expect(petUpdatePage.getDescriptionInput()).toMatch('description');
        petUpdatePage.setSlugInput('slug');
        expect(petUpdatePage.getSlugInput()).toMatch('slug');
        petUpdatePage.save();
        expect(petUpdatePage.getSaveButton().isPresent()).toBeFalsy();
    });

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});
