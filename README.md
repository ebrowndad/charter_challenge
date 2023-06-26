# Pricing Homework Assignment Solution

A half an hour seems a little fast for this problem. I probably could have rushed it, but 
I ended up spending more than an hour to get a solution I like, and spent some time getting
the date math to make sense. This is an interesting problem.

I chose CSV as the input because thinking as a customer this seems like the easiest
format to use. Each line represents a transaction as [customer_name],[date],[price]. The
output is a json report.

Please note I wrote Unit Tests in the test directory. They test the math and the aggregation.

I put a couple of test files in the scripts directory. The file 'test2.csv' is the most 
interesting. I run this with the command:

> curl -X POST -H "Content-Type: text/csv" --data-binary @test2.csv http://localhost:8080/price

The --data-binary option is needed to prevent curl from swallowing up newlines.

This toy app was fun to write. I would love the chance to show you the *actual application* I
am working on. It is an email ingestion/routing application using Kotlin, Spring Boot, AWS and
DynamoDB.
