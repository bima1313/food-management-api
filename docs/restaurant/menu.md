# Menu API Spec

## Add Menu

Request Header :

- Authorization : Bearer Token (Mandatory)

Endpoint : POST /api/merchant/restaurants/{RestaurantID}/menus

Request Body (Multipart Form-Data):

| Key | Type | Requirement | Description |
| :--- | :--- | :--- | :--- |
| `name` | String | Mandatory | Menu name (example: Fried Chicken) |
| `price` | Number | Mandatory | Price of menu |
| `category` | String | Mandatory | Menu category |
| `image` | File | Mandatory | Image file (max 10MB) |

Response Body (Success) :

```json
{
  "data": "OK"
}
```

Response Body (Failed) :

```json
{
  "errors": "Field must not blank"
}
```

Response Body (401) :

```json
{
  "errors": "Unauthorized"
}
```

## Get Menus

Endpoint : GET /api/merchant/restaurants/{RestaurantID}/Menus

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

Endpoint : GET /api/merchant/restaurants/{RestaurantID}/Menus/{MenuID}

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

## Update Menu

Endpoint : PATCH /api/merchant/restaurants/{RestaurantID}/Menus/{MenuID}

Request Header :

- Authorization : Bearer Token (Mandatory)

Request Body (Multipart Form-Data):

| Key | Type | Requirement | Description |
| :--- | :--- | :--- | :--- |
| `name` | String | Optional | Menu name (example: Fried Chicken) |
| `price` | Number | Optional | Price of menu |
| `category` | String | Optional | Menu category |
| `image` | File | Optional | Image file (max 10MB) |

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

## Delete Menu

Endpoint : DELETE /api/merchant/restaurants/{RestaurantID}/Menus/{MenuID}

Request Header :

- Authorization : Bearer Token (Mandatory)

Response Body (Success) :

```json
{
  "data": "OK"
}
```
