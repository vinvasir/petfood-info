import { browser } from 'protractor';
import { NavBarPage } from './../../page-objects/jhi-page-objects';
import { FoodReviewComponentsPage, FoodReviewUpdatePage } from './food-review.page-object';

describe('FoodReview e2e test', () => {
    let navBarPage: NavBarPage;
    let foodReviewUpdatePage: FoodReviewUpdatePage;
    let foodReviewComponentsPage: FoodReviewComponentsPage;

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load FoodReviews', () => {
        navBarPage.goToEntity('food-review');
        foodReviewComponentsPage = new FoodReviewComponentsPage();
        expect(foodReviewComponentsPage.getTitle()).toMatch(/Food Reviews/);
    });

    it('should load create FoodReview page', () => {
        foodReviewComponentsPage.clickOnCreateButton();
        foodReviewUpdatePage = new FoodReviewUpdatePage();
        expect(foodReviewUpdatePage.getPageTitle()).toMatch(/Create or edit a Food Review/);
        foodReviewUpdatePage.cancel();
    });

    it('should create and save FoodReviews', () => {
        foodReviewComponentsPage.clickOnCreateButton();
        foodReviewUpdatePage.setTitleInput('title');
        expect(foodReviewUpdatePage.getTitleInput()).toMatch('title');
        foodReviewUpdatePage.setBodyInput('body');
        expect(foodReviewUpdatePage.getBodyInput()).toMatch('body');
        foodReviewUpdatePage.ratingSelectLastOption();
        foodReviewUpdatePage.authorSelectLastOption();
        foodReviewUpdatePage.foodSelectLastOption();
        foodReviewUpdatePage.petSelectLastOption();
        foodReviewUpdatePage.save();
        expect(foodReviewUpdatePage.getSaveButton().isPresent()).toBeFalsy();
    });

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});
