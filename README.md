# Probing E-service Mock

Mock service to simulate the status of Public Administration e-services.

This repository contains both **REST** and **SOAP** endpoints for testing service status.

---

## REST Endpoints

- `GET /rest/interop/probing/{mode}/status`  
  - `mode`: `ok`, `error`, `random`  
  - Header: `Authorization: Bearer <JWT>` (required)

---

## SOAP Endpoint

- `POST /soap/interop/probing/status`  
  - SOAP payload defined in `schema-definition.xsd`  
  - Always returns status 200 OK

---

> See the OpenAPI spec (`probing.yaml`) for full details on responses, schemas, and examples.
