import { IFoodReview } from 'app/shared/model//food-review.model';

export interface IPet {
    id?: number;
    name?: string;
    description?: string;
    slug?: string;
    foodReviews?: IFoodReview[];
}

export class Pet implements IPet {
    constructor(
        public id?: number,
        public name?: string,
        public description?: string,
        public slug?: string,
        public foodReviews?: IFoodReview[]
    ) {}
}
