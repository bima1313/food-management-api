# Customer API Spec

## Register Customer

Endpoint : POST /api/customer

Request Body :

```json
{
  "email": "test@gmail.com",
  "username": "test421",
  "password": "secret",
  "name": "Bima Hamdhika Irfy"
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

## Login Customer

Endpoint : POST /api/auth/customer/login

Request Body :

```json
{
  "email": "test@gmail.com",
  "password": "secret"
}
```

Response Body (Success) :

```json
{
  "data": {
    "token": "TOKEN",    
  }
}
```

Response Body (Failed, 401) :

```json
{
  "errors": "Email or password wrong"
}
```

## Get Customer

Endpoint : GET /api/customer/${customerID}

Request Header :

- Authorization : Bearer Token (Mandatory)

Response Body (Success) :

```json
{
  "data": {
    "email": "test@gmail.com",
    "username": "test421",
    "name": "Bima Hamdhika Irfy"
  }
}
```

Response Body (Failed, 401) :

```json
{
  "errors": "Unauthorized"
}
```

## Update Customer

Endpoint : PATCH /api/customer/${customerID}

Request Header :

- Authorization : Bearer Token (Mandatory)

Request Body :

```json
{
  "email": "test@gmail.com", // put if only want to update email
  "password": "secret", // put if only want to update password
  "name": "Bima Hamdhika Irfy" // put if only want to update name
}
```

Response Body (Success) :

```json
{
  "data": {
    "email": "test2@yahoo.com",
    "username": "test421",
    "name": "Bima Hamdhika Irfy"
  }
}
```

Response Body (Failed, 401) :

```json
{
  "errors": "Unauthorized"
}
```

## Logout Customer

Endpoint : DELETE /api/auth/customer/logout

Request Header :

- Authorization : Bearer Token (Mandatory)

Response Body (Success) :

```json
{
  "data": "OK"
}
```
