import { element, by, promise, ElementFinder } from 'protractor';

export class FoodReviewComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    title = element.all(by.css('jhi-food-review div h2#page-heading span')).first();

    clickOnCreateButton(): promise.Promise<void> {
        return this.createButton.click();
    }

    getTitle(): any {
        return this.title.getText();
    }
}

export class FoodReviewUpdatePage {
    pageTitle = element(by.id('jhi-food-review-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    titleInput = element(by.id('field_title'));
    bodyInput = element(by.id('field_body'));
    ratingSelect = element(by.id('field_rating'));
    authorSelect = element(by.id('field_author'));
    foodSelect = element(by.id('field_food'));
    petSelect = element(by.id('field_pet'));

    getPageTitle() {
        return this.pageTitle.getText();
    }

    setTitleInput(title): promise.Promise<void> {
        return this.titleInput.sendKeys(title);
    }

    getTitleInput() {
        return this.titleInput.getAttribute('value');
    }

    setBodyInput(body): promise.Promise<void> {
        return this.bodyInput.sendKeys(body);
    }

    getBodyInput() {
        return this.bodyInput.getAttribute('value');
    }

    setRatingSelect(rating): promise.Promise<void> {
        return this.ratingSelect.sendKeys(rating);
    }

    getRatingSelect() {
        return this.ratingSelect.element(by.css('option:checked')).getText();
    }

    ratingSelectLastOption(): promise.Promise<void> {
        return this.ratingSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }
    authorSelectLastOption(): promise.Promise<void> {
        return this.authorSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    authorSelectOption(option): promise.Promise<void> {
        return this.authorSelect.sendKeys(option);
    }

    getAuthorSelect(): ElementFinder {
        return this.authorSelect;
    }

    getAuthorSelectedOption() {
        return this.authorSelect.element(by.css('option:checked')).getText();
    }

    foodSelectLastOption(): promise.Promise<void> {
        return this.foodSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    foodSelectOption(option): promise.Promise<void> {
        return this.foodSelect.sendKeys(option);
    }

    getFoodSelect(): ElementFinder {
        return this.foodSelect;
    }

    getFoodSelectedOption() {
        return this.foodSelect.element(by.css('option:checked')).getText();
    }

    petSelectLastOption(): promise.Promise<void> {
        return this.petSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    petSelectOption(option): promise.Promise<void> {
        return this.petSelect.sendKeys(option);
    }

    getPetSelect(): ElementFinder {
        return this.petSelect;
    }

    getPetSelectedOption() {
        return this.petSelect.element(by.css('option:checked')).getText();
    }

    save(): promise.Promise<void> {
        return this.saveButton.click();
    }

    cancel(): promise.Promise<void> {
        return this.cancelButton.click();
    }

    getSaveButton(): ElementFinder {
        return this.saveButton;
    }
}
