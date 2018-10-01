 ~/Downloads/jq-win64.exe 
 echo $method
 method=GET curl -s -i -H "Content-Type: application/json" -X $method http://localhost:$port/api/notes
 method=GET; curl -s -H "Content-Type: application/json" -X $method http://localhost:$port/api/notes
 method=GET; curl -s -H "Content-Type: application/json" -X $method http://localhost:$port/api/notes | ~/Downloads/jq-win64.exe 
 method=GET; curl -s -H "Content-Type: application/json" -X $method http://localhost:$port/api/notes | ~/Downloads/jq-win64.exe "."
 method=GET; curl -s -H "Content-Type: application/json" -X $method http://localhost:$port/api/notes | jq
 method=GET; curl -s -i -H "Content-Type: application/json" -X $method http://localhost:$port/api/notes
 method=GET; curl -s -i -H "Content-Type: application/json" -X $method http://localhost:$port/api/notes/
 method=GET; curl -s -i -H "Content-Type: application/json" -X $method http://localhost:$port/api/notes/88b529a3-275b-4c77-b27d-c426f0be0a64
 method=GET; curl -s -i -H "Content-Type: application/json" -X $method http://localhost:$port/api/notes/bd77bb60-526b-46e2-8d42-5fc630ef195e
 method=GET; curl -s -i -H "Content-Type: application/json" -X $method http://localhost:$port/api/notes/fd2834c3-4ca8-4177-b3d6-b14cea0d7d39
 method=GET; curl -s -i -H "Content-Type: application/json" -X $method http://localhost:$port/api/notes?query=milk
 method=POST curl -s -i -H "Content-Type: application/json" -X $method http://localhost:$port/api/notes -d '{"body" : "Pick up milk!"}'
 method=POST; curl -s -i -H "Content-Type: application/json" -X $method http://localhost:$port/api/notes -d '{"body" : "Pick up milk!"}'
port=80
