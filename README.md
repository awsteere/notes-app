## Notes App

The Notes App server implements the API specified in the Level Beyond coding assignment.

## Installation

This project assumes you have Java 1.8+ and Maven 3.0+ installed.

Clone this repository.

Cd into the repository.

## Build server

Run `mvn compile`.

## Run the server 

Run `mvn jetty:run -Djetty.http.port=80`.

### Validate functionality using curl
### Validate GET with no notes
`curl -s -i -H "Content-Type: application/json" -X GET http://localhost:80/api/notes`

### Validate GET with an invalid note id
`curl -s -i -H "Content-Type: application/json" -X GET http://localhost:80/api/notes/88b529a3-275b-4c77-b27d-c426f0be0a64`

### Validate POST creates two notes with identical notes and one unique note
`curl -s -i -H "Content-Type: application/json" -X POST http://localhost:80/api/notes -d '{"body" : "Pick up milk!"}'`
`curl -s -i -H "Content-Type: application/json" -X POST http://localhost:80/api/notes -d '{"body" : "Pick up milk!"}'`
`curl -s -i -H "Content-Type: application/json" -X POST http://localhost:80/api/notes -d '{"body" : "Pick up mess!"}'`

### Validate GETs the notes
`curl -s -i -H "Content-Type: application/json" -X GET http://localhost:80/api/notes`

Save the note id for the next test.

### Validate GET notes with id works
`curl -s -i -H "Content-Type: application/json" -X GET http://localhost:80/api/notes/<saved notes id>`

### Validate invalid UUID is detected
`curl -s -i -H "Content-Type: application/json" -X GET http://localhost:80/api/notes/foobar`

### Validate query with whitespace returns valid results
`curl -s -i -H "Content-Type: application/json" -X GET http://localhost:80/api/notes/query='up%20milk`

## Unit and Integration Tests :-(

I regret that unit and integration tests were not written due to a lack of time. Upon completing the server code,
I switched to learning Angular 6. Lacking time, I backed out the integration tests and relied on the curl tests
above for testing.

## Known issues

There is a warning in the console log when an invalid note id is processed: `[WARNING] Error page loop /error404.html`.

