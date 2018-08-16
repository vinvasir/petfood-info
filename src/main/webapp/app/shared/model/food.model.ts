import { IFoodReview } from 'app/shared/model//food-review.model';

export interface IFood {
    id?: number;
    name?: string;
    description?: string;
    reviews?: IFoodReview[];
}

export class Food implements IFood {
    constructor(public id?: number, public name?: string, public description?: string, public reviews?: IFoodReview[]) {}
}
