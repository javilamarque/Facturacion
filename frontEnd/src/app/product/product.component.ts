import { Component, OnInit } from '@angular/core';
import { Product } from './product';
import { ProductService } from './product.service';

@Component({
    selector: 'app-product',
    templateUrl: './product.component.html',
    styleUrls: ['./product.component.scss']
})
export class ProductComponent implements OnInit {
    public name: string;
    public description: string;
    public price: number;

    constructor(private productService: ProductService) { 
    this.name = '';
    this.description = '';
    this.price = null;
    }

    ngOnInit() {
    }

    createProduct() {
    let productObject: Product;

    productObject = {
    name: this.name,
    description: this.description,
    price: this.price,
    }

    this.productService.postProduct(productObject).subscribe(response => {
    alert(JSON.stringify(response));
    });

    this.name = '';
    this.description = '';
    this.price = null;
    }
}
