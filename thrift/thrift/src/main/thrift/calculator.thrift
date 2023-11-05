namespace java com.example.thrift

enum Operation {
  ADD = 1,
  SUBTRACT = 2,
  MULTIPLY = 3,
  DIVIDE = 4
}

struct Work {
  1: i32 num1 = 0,
  2: i32 num2,
  3: Operation op,
  4: optional string comment,
}

exception InvalidOperation {
  1: i32 whatOp,
  2: string why
}

/**
 * Calculator Service.
 */
service CalculatorService {
    i32 calculate(1:i32 logid, 2:Work work) throws (1:InvalidOperation invalidOperation)
}
