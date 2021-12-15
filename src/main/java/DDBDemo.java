import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import env.EnvironmentVariables;
import models.Customer;

import java.util.List;

public class DDBDemo {
    public static void main(String[] args) {
        AWSCredentialsProvider creds = new AWSStaticCredentialsProvider(
                new BasicAWSCredentials(EnvironmentVariables.ACCESS_KEY,
                        EnvironmentVariables.SECRET_KEY));

        AmazonDynamoDB ddbClient = AmazonDynamoDBClientBuilder.standard()
                .withCredentials(creds)
                .withRegion("eu-west-2")
                .build();

        DynamoDBMapper mapper = new DynamoDBMapper(ddbClient);
               //load(mapper);
              // save(mapper);
               query(mapper);
               //delete(mapper);


    }

    private static void delete(DynamoDBMapper mapper) {
        //1- single delete

        Transaction t = new Transaction();
        t.setTransactionId("t7");
        t.setDate("2021-12-15");

        Transaction result = mapper.load(t);
        mapper.delete(result);
    }

    private static void query(DynamoDBMapper mapper) {
        //1- Normal query

        Transaction t = new Transaction();
        t.setTransactionId("t5");

        DynamoDBQueryExpression<Transaction> queryExpression= new DynamoDBQueryExpression<Transaction>()
                .withHashKeyValues(t)
                .withLimit(10);

        List<Transaction>result = mapper.query(Transaction.class, queryExpression);
        System.out.println("The amount of this transaction is £" +result.get(0).getAmount());
        System.out.println("Customer name is: " + result.get(0).getCustomer().getCustomerName());
        //System.out.println(result.get(1));

        //2- Query with Global Secondary Index (GSI)

//        Transaction t = new Transaction();
//        t.setDate("2021-12-15");
//
//        DynamoDBQueryExpression <Transaction> queryExpression =
//                new DynamoDBQueryExpression<Transaction>()
//                        .withHashKeyValues(t)
//                        .withIndexName("date-index")
//                        .withConsistentRead(false)
//                        .withLimit(10);
//        List <Transaction> result = mapper.query(Transaction.class, queryExpression);
//        System.out.println(result.get(0));

    }

    private static void save(DynamoDBMapper mapper) {
        //1- Basic Save
        Transaction t = new Transaction();
        t.setTransactionId("t7");
        t.setDate("2021-12-15");
       // t.setType("PURCHASE");
        t.setAmount(180);
        t.setCustomer(Customer.builder().customerId("c7").customerName("Sarah Smith").build());

        mapper.save(t);

    }

    //Reading from table
    private static void load(DynamoDBMapper mapper) {
        //1. Basic Load
        Transaction t = new Transaction();
        t.setTransactionId("t5");
        t.setDate("2021-12-02");

        Transaction result = mapper.load(t);
        System.out.println(result);

    }


}
