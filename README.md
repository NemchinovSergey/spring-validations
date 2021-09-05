# Spring Boot Application

## 1. An example of request validation

Validations:
1. `seller`/`customer` must be a string of 9 chars
2. `product code` must be a string of 13 chars

#### Extra:
Added an additional `seller`/`customer` code checking - if the company active or not. \
There is a hardcoded code `987654321` that means the company is disabled.

#### OK request
`POST http://localhost:8080/rest/v1/products`
```json
{
    "seller": "123534251",
    "customer": "648563524",
    "products": [
        {
            "name": "milk",
            "code": "2364758363546"
        },
        {
            "name": "water",
            "code": "3656352437590"
        }
    ]
}
```

#### Totally invalid request:
```json
{
    "seller": "12353425",
    "customer": "987654321",
    "products": [
        {
            "name": null,
            "code": "2364758363546"
        },
        {
            "name": "water",
            "code": "111111"
        }
    ]
}
```
Expected response:
```json
{
    "id": "a3652756-cd6b-46fb-ae64-612958232f7f",
    "errorType": "VALIDATION_ERROR",
    "fieldErrors": {
        "seller": "Некорректный код продавца",
        "customer": "Компания не активна",
        "products[0].name": "Название товара не может быть пустым",
        "products[1].code": "Код товара должен содержать 13 символов"
    }
}
```

## 2. Thread-safe computing with caching

A thread-safe class with a method `Future<V> compute(K k, Function<K, V> f)`. 
The method returns `Future<V>` using a key `K` and function `Function<K, V> f`.
If the value for given key is computed already the method should return the value from the cache.

#### Just useful links:
* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/2.5.4/maven-plugin/reference/html/)
* [Create an OCI image](https://docs.spring.io/spring-boot/docs/2.5.4/maven-plugin/reference/html/#build-image)
* [Validation](https://docs.spring.io/spring-boot/docs/2.5.4/reference/htmlsingle/#boot-features-validation)
* [Spring Web](https://docs.spring.io/spring-boot/docs/2.5.4/reference/htmlsingle/#boot-features-developing-web-applications)
* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/bookmarks/)

