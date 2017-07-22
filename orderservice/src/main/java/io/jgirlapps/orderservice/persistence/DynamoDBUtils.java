package io.jgirlapps.orderservice.persistence;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.UUID;

import com.amazonaws.AmazonClientException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig.TableNameOverride;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.model.AttributeDefinition;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.KeySchemaElement;
import com.amazonaws.services.dynamodbv2.model.KeyType;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.amazonaws.services.dynamodbv2.util.TableUtils;
import com.amazonaws.services.dynamodbv2.util.TableUtils.TableNeverTransitionedToStateException;

import io.jgirlapps.orderservice.models.AddressType;
import io.jgirlapps.orderservice.models.IncentiveEnumType;
import io.jgirlapps.orderservice.models.Order;
import io.jgirlapps.orderservice.models.OrderStatusEnumType;
import io.jgirlapps.orderservice.models.ResourceEnumType;
import io.jgirlapps.orderservice.models.SpecialtyEnumType;

public class DynamoDBUtils {
	private static DynamoDBUtils instance = null;
	private AmazonDynamoDBClient amazonDynamoDbClient;
	private DynamoDB documentClient;
	private DynamoDBMapper dynamoDBMapperOrder;
	//private Logger log = Logger.getLogger(this.getClass());
	Properties configProps = new Properties();
	String orderTableName;
	
	
	protected DynamoDBUtils() {
		long start, stop, duration;
		
		//get AWS Credentials
		AWSCredentials credentials = null;
        try {
        	credentials = new DefaultAWSCredentialsProviderChain().getCredentials();
        	//log.info("credentials loaded");
        	System.out.println("credentials loaded");
        } catch (Exception e) {
            throw new AmazonClientException(
                    "Cannot load the credentials from the credential profiles file. " +
                    "Please make sure that your credentials file is at the correct " +
                    "location (C:\\Users\\sadams\\.aws\\credentials), and is in valid format.",
                    e);
        }
        
        
        //Read in configuration properties
		InputStream in = null;
		
		in = getClass().getClassLoader().getResourceAsStream("config.properties");
		
		try {
			configProps.load(in);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String endpoint = configProps.getProperty("aws.dynamodb.endpoint");
		//log.info("database endpoint=" + endpoint);		
		
		setOrderTableName(configProps.getProperty("aws.dynamodb.table.order"));
	
		AmazonDynamoDBClientBuilder clientBuilder = AmazonDynamoDBClientBuilder
				.standard()
				.withCredentials(new AWSStaticCredentialsProvider(credentials))
				.withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(endpoint,"us-east-1"));
		
		amazonDynamoDbClient = (AmazonDynamoDBClient) clientBuilder.build();		
              
        //get mapper for order table
        String orderTableName = getOrderTableName();
  		TableNameOverride tableNameOverride = new TableNameOverride(orderTableName);
  		DynamoDBMapperConfig.Builder mappingConfigBuilder = new DynamoDBMapperConfig.Builder();
  		mappingConfigBuilder.withTableNameOverride(tableNameOverride);
  		DynamoDBMapperConfig mapperConfig = mappingConfigBuilder.build();
  		
  		dynamoDBMapperOrder = new DynamoDBMapper(amazonDynamoDbClient,mapperConfig);
  		setDynamoDBMapperOrder(dynamoDBMapperOrder);
  		        
        createOrderTable();        
	}
	
	private void createOrderTable() {
		String table = getOrderTableName();
		
		//log.info("attempting to create device id table " + tableNameDeviceId);
		System.out.println("attempting to create table");
		
		CreateTableRequest req = getDynamoDBMapperOrder().generateCreateTableRequest(Order.class);
		req.setProvisionedThroughput(new ProvisionedThroughput(1L, 1L));
		
		
		if(TableUtils.createTableIfNotExists(amazonDynamoDbClient, req)) {
		
			try {
				TableUtils.waitUntilActive(amazonDynamoDbClient, table);
				System.out.println("table created");
			} catch (TableNeverTransitionedToStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			//log.info("device id table createad");
		} else {
			//log.info("device id table already exists");
		}
	}
	
	public static DynamoDBUtils getInstance() {
		if(instance==null) {
			instance = new DynamoDBUtils();
		}
		
		return instance;
	}
	
	public DynamoDB getDBClient() {
		return documentClient;
		//return new DynamoDB(amazonDynamoDbClient);
	}

	public String getOrderTableName() {
		return orderTableName;
	}

	public void setOrderTableName(String orderTableName) {
		this.orderTableName = orderTableName;
	}

	public DynamoDBMapper getDynamoDBMapperOrder() {
		return dynamoDBMapperOrder;
	}

	public void setDynamoDBMapperOrder(DynamoDBMapper dynamoDBMapperOrder) {
		this.dynamoDBMapperOrder = dynamoDBMapperOrder;
	}
	
	
	
	/*
	public String getTableNameForEntity(Class clazz) {
		//Class clazz = entity.getClass();
		DynamoDBTable annotation = (DynamoDBTable) clazz.getAnnotation(DynamoDBTable.class);
		
		if(annotation == null) return null;
				
		return annotation.tableName();
		
		
	}
	*/
	
	public static void main(String[] args) {
		DynamoDBUtils utils = DynamoDBUtils.getInstance();
		DynamoDBMapper mapper = utils.getDynamoDBMapperOrder();
		mapper.save(getOrder());
		System.out.println("order created");
	}
	
	private static Order getOrder() {
		Order order = new Order();
		
		AddressType address = new AddressType();
		address.setCity("Hurst");
		address.setState("TX");
		address.setZip("76053");
		order.setAddress(address);
		
		order.setAgencyId(UUID.randomUUID().toString());
		order.setAgencyRatingId(UUID.randomUUID().toString());
		order.setDateRequired(new Date());
		order.setId(UUID.randomUUID().toString());
		
		List<IncentiveEnumType> incentives = new ArrayList();
		IncentiveEnumType incentive = IncentiveEnumType.BONUS100;
		incentives.add(incentive);
		
		order.setIncentives(incentives);
		
		order.setOrderStatus(OrderStatusEnumType.ASSIGNED);
		order.setReporterId(UUID.randomUUID().toString());
		order.setReporterRatingId(UUID.randomUUID().toString());
		order.setResourceType(ResourceEnumType.COURTREPORTER);
		order.setSpecialRequirements("this is really important");
		order.setSpecialtyType(SpecialtyEnumType.STANDARD);
		
		return order;
	}

}
