const {HelloRequest} = require('./hello_pb.js');
const {HelloServiceClient} = require('./hello_grpc_web_pb.js');

const helloService = new HelloServiceClient('http://localhost:8080');

const request = new HelloRequest();
request.setName('hirakida');

helloService.helloUnary(request, {}, function (err, response) {
  const div = document.getElementById('unary');
  div.textContent = response.getMessage();
});

const stream = helloService.helloServerStreaming(request, {});
stream.on('data', function(response) {
  const div = document.createElement('div');
  div.textContent = response.getMessage();
  document.getElementById('streaming').appendChild(div);
});
stream.on('status', function(status) {
  console.log(status);
});
stream.on('end', function(end) {
  stream.cancel();
});
