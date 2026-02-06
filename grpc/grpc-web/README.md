
```
% brew install protobuf
% brew install protoc-gen-grpc-web

% cd frontend
% protoc -I=../../protobuf/src/main/proto hello.proto --js_out=import_style=commonjs:./ --grpc-web_out=import_style=commonjs,mode=grpcwebtext:./

% npm install
% npx webpack ./client.js --mode development
% cp dist/main.js ../src/main/resources/static
```
