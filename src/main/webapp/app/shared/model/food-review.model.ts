import { IUser } from 'app/core/user/user.model';
import { IFood } from 'app/shared/model//food.model';
import { IPet } from 'app/shared/model//pet.model';

export const enum FoodRating {
    ONE = 'ONE',
    TWO = 'TWO',
    THREE = 'THREE',
    FOUR = 'FOUR',
    FIVE = 'FIVE'
}

export interface IFoodReview {
    id?: number;
    title?: string;
    body?: string;
    rating?: FoodRating;
    author?: IUser;
    food?: IFood;
    pet?: IPet;
}

export class FoodReview implements IFoodReview {
    constructor(
        public id?: number,
        public title?: string,
        public body?: string,
        public rating?: FoodRating,
        public author?: IUser,
        public food?: IFood,
        public pet?: IPet
    ) {}
}
