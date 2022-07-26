namespace java com.example.hello

struct HelloRequest {
  1: string name,
}

struct HelloResponse {
  1: string message;
  2: list<string> messages,
  3: bool hasOptionalMessage;
  4: optional string optionalMessage;
  5: i64 epochMilli,
}

service HelloService {
   string hello1(1:string name)

   HelloResponse hello2(1:HelloRequest request)
}
