import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders} from '@angular/common/http';
import { Product } from './product';

@Injectable({
    providedIn: 'root'
})
export class ProductService {
    constructor(private http: HttpClient) {}

    postProduct(product: Product) {
        const httpOptions = {
            headers: new HttpHeaders({
                'Content-Type': 'application/json',
            }),
        };

        return this.http.post (
            '/api/product',
            JSON.stringify(product),
            httpOptions
        );
    }
}
