# User API Spec

## Register User

Endpoint : POST /api/users/register

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

## Login User

Endpoint : POST /api/auth/users/login

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
    "tokenExpiredAt": 1242146021 // long
  }
}
```

Response Body (Failed, 401) :

```json
{
  "errors": "Email or password wrong"
}
```

## Get User

Endpoint : GET /api/users/profile

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

## Update User

Endpoint : PATCH /api/users/profile

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

## Logout User

Endpoint : DELETE /api/auth/logout

Request Header :

- Authorization : Bearer Token (Mandatory)

Response Body (Success) :

```json
{
  "data": "OK"
}
```

## Delete User

Endpoint : DELETE /api/auth

Request Header :

- Authorization : Bearer Token (Mandatory)

Response Body (Success) :

```json
{
  "data": "OK"
}
```
