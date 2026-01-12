# Restaurant API Spec For Merchant

## Register Restaurant

Request Header :

- Authorization : Bearer Token (Mandatory)

Endpoint : POST /api/merchant/restaurants/register

Request Body :

```json
{
  "data": {
    "name": "Richeese Factory",
    "address": "Pemuda Street number 15, Jakarta, Indonesia",
    "settings": {
      "isDineInEnabled": true
    }
  }
}
```

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

## Get Restaurants

Endpoint : GET /api/merchant/restaurants

Request Header :

- Authorization : Bearer Token (Mandatory)

Response Body (Success) :

```json
{
  "data": {
    "restaurants": [
      {
        "id": "heello2ooGw0012ldm1xu9",
        "name": "Mixue",
        "address": "Buaran Raya Street number 14, Jakarta, Indonesia",
        "settings": {
          "isDineInEnabled": true
        },
        "menus": [],
        "rating": null
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

Endpoint : GET /api/merchant/restaurants/{RestaurantID}

Request Header :

- Authorization : Bearer Token (Mandatory)

Response Body (Success) :

```json
{
  "data": {
    "id": "w0o121d12lch66s6F6cda",
    "name": "Richeese Factory",
    "address": "Pemuda Street number 15, Jakarta, Indonesia",
    "settings": {
      "isDineInEnabled": true
    },
    "menus": [],
    "rating": null
  }
}
```

Response Body (Failed, 401) :

```json
{
  "errors": "Unauthorized"
}
```

## Update Restaurant

Endpoint : PATCH /api/merchant/restaurants/{RestaurantID}

Request Header :

- Authorization : Bearer Token (Mandatory)

Request Body :

```json
{
  "data": {
    "name": "Richeese Factory", // put if only want to change name
    "address": "Merdeka Street number 151A, Jakarta, Indonesia", // put if only want to change address
    "settings": {
      // put if only want to change "isDineInEnabled" to false or adding new settings. For example "isTakeAwayInEnabled"
      "isDineInEnabled": true
    }
  }
}
```

Response Body (Success) :

```json
{
  "data": {
    "id": "w0o121d12lch66s6F6cda",
    "name": "Richeese Factory",
    "address": "Merdeka Street number 151A, Jakarta, Indonesia",
    "settings": {
      "isDineInEnabled": true
    },
    "menus": [],
    "rating": null
  }
}
```

Response Body (Failed, 401) :

```json
{
  "errors": "Unauthorized"
}
```

## Delete Restaurant

Endpoint : DELETE /api/merchant/restaurants/{RestaurantID}

Request Header :

- Authorization : Bearer Token (Mandatory)

Response Body (Success) :

```json
{
  "data": "OK"
}
```
