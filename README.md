Some stats:
-

Time I spend on project:

[![time tracker](https://wakatime.com/badge/github/StanislawNagorski/eshop.svg)](https://wakatime.com/badge/github/StanislawNagorski/eshop)

[Active sprint with Jira](https://scrumtrening.atlassian.net/secure/RapidBoard.jspa?rapidView=4&projectKey=ES&atlOrigin=eyJpIjoiNDRjYjJjNTk3ZjVhNGJhNjg1OGY5ZDNjMjg0OTFhN2YiLCJwIjoiaiJ9)


About project:
-
Objective for this project is for me to advance Spring framework and get familiar with ecommerce application.
Store API will be connected with TypeScript Angular front.

Technology stack:
-
- Java 11, Spring,
- hibernate and MySQL,
- build with Maven,
- Rest Api,
- TypeScrip, Angular,

Resources
-
- Products
   - http://NOT_HOSTED_YET/products

```{
    "name": "Kaftan szkoły wilka",
    "description": "Kiedy na planszy stajesz przeciwko potworom",
    "price": 350.00,
    "quantity": 3,
    "category": {
        "id": 1,
        "name": "odzież_szermiercza"
    },
    "promoPrice": 349.99,
    "productImages": [],
    "creationTime": "2020-12-30T18:30:06.23791",
    "updateTime": "2020-12-30T19:24:46.843609",
    "active": true,
    "promo": true
} 
```

- Categories names:

  * http://NOT_HOSTED_YET/products/categories

How to and all available routes
-
### Get all products
 http://NOT_HOSTED_YET/products
### Get single product
by product id:
http://NOT_HOSTED_YET/products/1
### Get categories of products
http://NOT_HOSTED_YET/products/categories
### Get all product 
  - by category
http://NOT_HOSTED_YET/products/filter?category=exampleCategory
  - by product name
http://NOT_HOSTED_YET/products/search?name=ExampleName
  - with promo price
http://NOT_HOSTED_YET/products/promo
### Get all products ordered by price
- Ascending price
  * http://NOT_HOSTED_YET/products/price?order=asc
- Ascending price by category
  * http://NOT_HOSTED_YET/products/price?order=asc&category=exampleCategory

- Descending price
  * http://NOT_HOSTED_YET/products/price?order=desc
- Descending price by category
  * http://NOT_HOSTED_YET/products/price?order=desc&category=exampleCategory

### Post new category
POST to: http://NOT_HOSTED_YET/products/categories?category=newCategory will return:
```
{
       "id": 5,
       "name": "newcategory"
}
```

### Update category by id
PUT to: http://NOT_HOSTED_YET/products/categories/5, note: requires body.
```
{
    "name": "updated test category name"
}
```

### Delete category by id
DELETE to: http://NOT_HOSTED_YET/products/categories/5, will return a deleted category.

  
### Post new product
 POST to: http://NOT_HOSTED_YET/products, will return a created object.
 ``` 
{
     "name": "Srebrny miecz szkoły kota", //CANNOT BE NULL
     "description": "Mistrzowski, ale sam walki nie wygra", //CANNOT BE NULL
     "price": 1350.00, //CANNOT BE NULL
     "quantity": 1,
     "category": { //you may give id or category
         "id": 1,
         "name": "miecz_długi"
     },
     "promoPrice": 349.99,
     "productImages": [//NOT IMPLANTED YET],
     "active": true,
     "promo": true
 } 
 ```
Note: if want to post a new product with category, you need be using existing one.

### Update product by id
PUT to: http://NOT_HOSTED_YET/products/1
will update only fields You selected (and update time);
```
{
     "promoPrice": 249.99,
     "promo": true
 } 
 ```

### Delete product by id
DELETE to: http://NOT_HOSTED_YET/products/1 will deactivate a product, and return it.








