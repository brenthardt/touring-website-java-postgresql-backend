SEE FRONTEND HERE: https://github.com/brenthardt/touring-website-java-postgresql-frontend.git <br/>


## Table of Contents
- [Features](#features)
- [API Documentation](#api-documentation)
- [Installation](#installation)
- [Configuration](#configuration)
- [Usage](#usage)
- [Contributing](#contributing)
- [License](#license)

## Features
- User authentication (login/signup)
- CRUD operations for users
- Comment system
- Quick registration for potential buyers
- Paginated user listing

## API Documentation

### User Endpoints

| Method | Endpoint              | Description                          |
|--------|-----------------------|--------------------------------------|
| GET    | `/users`              | Get paginated list of users          |
| POST   | `/users`              | Create new user                      |
| DELETE | `/users/{id}`         | Delete user by ID                    |
| PUT    | `/users/{id}`         | Update user by ID                    |
| POST   | `/users/signup`       | Register new user                    |
| POST   | `/users/login`        | Authenticate user                    |
| POST   | `/users/quick`        | Quick user creation                  |
| GET    | `/users/wannabuyers`  | Get potential buyers                 |
| POST   | `/users/comment`      | Create comment                       |
| GET    | `/users/comments`     | Get all comments                     |
