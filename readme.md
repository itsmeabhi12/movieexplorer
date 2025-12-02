# Project Overview

Monorepo with:

- `client/` (Next.js frontend)
- `server/` (Spring Boot backend)
- PostgreSQL (via Docker Compose)

## Quick Start

1. Start PostgreSQL

```bash
Install Docker, then run the following command in terminal
cd server
docker compose up -d
```

2. Run backend

```bash
Use a Spring Boot extension to run backend in VSCode or Intellij
```

3. Run frontend

```bash
cd client
npm i
npm run dev
```

## Environment Variables

Create `.env` files (DO NOT commit secrets to git).

### Backend (`server/.env` or use application properties)

```
POSTGRES_USER=
POSTGRES_PASSWORD=
TMDB_API_KEY=
JWT_SECRET=
JWT_EXPIRES_IN=
```

Configure datasource in `application.properties`:

```
spring.datasource.url=jdbc:postgresql://localhost:5432/postgres
spring.datasource.username=${POSTGRES_USER}
spring.datasource.password=${POSTGRES_PASSWORD}
spring.jpa.hibernate.ddl-auto=update
```

Run Database(from Docker) first as it will load .env

### Frontend (`client/.env`)

```
NEXT_PUBLIC_API_URL=http://localhost:8080
NEXT_PUBLIC_TMDB_IMAGE_BASE_URL=https://image.tmdb.org/t/p/w342
```

## Folder Structure

```
/
├─ client/        # Next.js app
├─ server/        # Spring Boot app
├─ docker-compose.yml
└─ README.md
```
