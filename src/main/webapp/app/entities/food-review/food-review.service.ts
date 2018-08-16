import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IFoodReview } from 'app/shared/model/food-review.model';

type EntityResponseType = HttpResponse<IFoodReview>;
type EntityArrayResponseType = HttpResponse<IFoodReview[]>;

@Injectable({ providedIn: 'root' })
export class FoodReviewService {
    private resourceUrl = SERVER_API_URL + 'api/food-reviews';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/food-reviews';

    constructor(private http: HttpClient) {}

    create(foodReview: IFoodReview): Observable<EntityResponseType> {
        return this.http.post<IFoodReview>(this.resourceUrl, foodReview, { observe: 'response' });
    }

    update(foodReview: IFoodReview): Observable<EntityResponseType> {
        return this.http.put<IFoodReview>(this.resourceUrl, foodReview, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IFoodReview>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IFoodReview[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IFoodReview[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
    }
}
