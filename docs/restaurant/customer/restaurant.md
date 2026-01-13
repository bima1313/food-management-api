# Restaurant API Spec For Customer

## Get Restaurants

Endpoint : GET /api/restaurants

Request Header :

- Authorization : Bearer Token (Mandatory)

Response Body (Success) :

```json
{
  "data": {
    "restaurants": [
      {
        "name": "Mixue",
        "address": "Buaran Raya Street number 14, Jakarta, Indonesia",
        "settings": {
          "isDineInEnabled": true
        },       
        "rating": 4.9
      },
      {
        "name": "Richeese Factory",
        "address": "Pemuda Street number 15, Jakarta, Indonesia",
        "settings": {
          "isDineInEnabled": true
        },       
        "rating": 4.8
      }
    ]
  }
}
```

Response Body (Failed, 401) :

```json
{
  "errors": "Unauthorized"
}
```

## Get Detail Restaurant

Endpoint : GET /api/restaurants/{RestaurantID}

Request Header :

- Authorization : Bearer Token (Mandatory)

Response Body (Success) :

```json
{
  "data": {
    "name": "Richeese Factory",
    "address": "Pemuda Street number 15, Jakarta, Indonesia",
    "settings": {
      "isDineInEnabled": true
    },
    "menus":[
    {
      "name": "Fried Chicken",
      "price": 10, // in dollar
      "category": "food",
      "image_url": "http://image2.com"
    }],
    "rating": 4.8
  }
}
```

Response Body (Failed, 401) :

```json
{
  "errors": "Unauthorized"
}
```

## Get Menus

Endpoint : GET /api/restaurants/{RestaurantID}/Menus

Request Header :

- Authorization : Bearer Token (Mandatory)

Response Body (Success) :

```json
{
  "data": {
    "menus":[
      {
        "name": "Mango Sundae",
        "price": 3, // in dollar
        "category": "Ice Cream",
        "image_url": "http://image4.com"
      },
      {
        "name": "Fresh Lemonade",
        "price": 2, // in dollar
        "category": "Drink",
        "image_url": "http://image142.com"
      }]
  }
}
```

Response Body (Failed, 401) :

```json
{
  "errors": "Unauthorized"
}
```

## Get Menu

Endpoint : GET /api/restaurants/{RestaurantID}/Menus/{MenuID}

Request Header :

- Authorization : Bearer Token (Mandatory)

Response Body (Success) :

```json
{
  "data": {
    "name": "Fried Chicken",
    "price": 10, // in dollar
    "category": "food",
    "image_url": "http://image2.com"
  }
}
```

Response Body (Failed, 401) :

```json
{
  "errors": "Unauthorized"
}
```