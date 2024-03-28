const {HelloRequest} = require('./hello_pb.js');
const {HelloServiceClient} = require('./hello_grpc_web_pb.js');

const helloService = new HelloServiceClient('http://localhost:8080');

const request = new HelloRequest();
request.setName('hirakida');

helloService.helloUnary(request, {}, function (err, response) {
  const div = document.getElementById('content');
  div.textContent = response.getMessage();
});
