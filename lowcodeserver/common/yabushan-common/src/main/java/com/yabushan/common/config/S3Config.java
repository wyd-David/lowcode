package com.yabushan.common.config;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.Protocol;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.S3ClientOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * S3客户端配置类
 *
 * @author：yabushan @date：2020/12/1
 */
@Configuration
public class S3Config {

	@Value("${s3.accesskey}")
	private String accessKey;

	@Value("${s3.secretKey}")
	private String secretKey;

	@Value("${s3.endpoint}")
	private String endpoint;

	/**
	 * 初始化生成AmazonS3 客户端配置
	 *
	 * @return AmazonS3
	 */
	@SuppressWarnings("deprecation")
	@Bean
	public AmazonS3 amazonS3() {

		// AwsClientBuilder.EndpointConfiguration endpointConfig =
		// new AwsClientBuilder.EndpointConfiguration(url, "ap-northeast-1");

		// AWSCredentialsProvider awsCredentialsProvider = new
		// AWSStaticCredentialsProvider(awsCredentials);
		// ClientConfiguration clientConfig = new ClientConfiguration();
		// clientConfig.setProtocol(Protocol.HTTPS);
		// clientConfig.setSignerOverride("S3SignerType");//凭证验证方式
		//
		// AmazonS3 S3client = AmazonS3ClientBuilder.standard()
		// .withEndpointConfiguration(endpointConfig)
		// .withClientConfiguration(clientConfig)
		// .withCredentials(awsCredentialsProvider)
		// .disableChunkedEncoding()
		// .withPathStyleAccessEnabled(true)
		// .withForceGlobalBucketAccessEnabled(true)
		// .build();
		// log.info("create s3Client success");

		ClientConfiguration clientConfig = new ClientConfiguration();
		clientConfig.setProtocol(Protocol.HTTP);
		// clientConfig.withSignerOverride("S3SignerType");

		AWSCredentials awsCredentials = new BasicAWSCredentials(accessKey, secretKey);
		AmazonS3 s3Client = new AmazonS3Client(awsCredentials, clientConfig);
		s3Client.setEndpoint(endpoint);
		s3Client.setS3ClientOptions(
				S3ClientOptions.builder().setPathStyleAccess(true).disableChunkedEncoding().build());

		return s3Client;
	}
}
