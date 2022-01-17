namespace java com.example.hello

struct HelloRequest {
  1: string name = "hirakida",
}

struct HelloResponse {
  1: list<string> messages,
  2: optional i64 datetime,
}

service Hello {
   string hello1(1:string name)
   HelloResponse hello2(1:HelloRequest request)
}
