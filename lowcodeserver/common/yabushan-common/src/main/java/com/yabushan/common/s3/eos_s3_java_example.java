package com.yabushan.common.s3;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.ClientConfiguration;
import com.amazonaws.Protocol;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.S3ClientOptions;
import com.amazonaws.services.s3.model.*;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.TransferManagerConfiguration;
import com.amazonaws.services.s3.transfer.TransferProgress;
import com.amazonaws.services.s3.transfer.Upload;

import java.io.*;
import java.util.List;

public class eos_s3_java_example {

	public static String access_key = "=";
	public static String secret_key = "=";
	public static String bucketname = "=";
	public static String objectname = "=";
	public static String endpoint = "http://10.128.3.21:8080";
	static AmazonS3 conn;

	public static void main(String[] args) throws Exception {
		AWSCredentials credentials = new BasicAWSCredentials(
				access_key, secret_key);
		ClientConfiguration connconfig = new ClientConfiguration();
		connconfig.setProtocol(Protocol.HTTP);
		//connconfig.setSignerOverride("AWS ");

		//S3ClientOptions conn_options = new S3ClientOptions();
		//conn_options.setPathStyleAccess(true);

		conn = new AmazonS3Client(credentials, connconfig);
		conn.setEndpoint(endpoint);
                conn.setS3ClientOptions(S3ClientOptions.builder().setPathStyleAccess(true).disableChunkedEncoding().build());
		//conn.setS3ClientOptions(conn_options);
		//getSignerOverride()
		// s3_list_buckets();
		//s3_createBucket(bucketname);
		//s3_putobj_simple(bucketname, objectname, "/root/myapp.log");
		// s3_list_objs(bucketname,"");
		// s3_gettobj_info(bucketname,"obj");
//		s3_putobj_withmeta(bucketname,"/root/myapp.log");
		// s3_setobj_acl(bucketname,s3_objectname);
		// s3_getobj_acl(bucketname,s3_objectname);
		// s3_generate_url(bucketname,"xxxxx");
		// s3_get_obj(bucketname, objectname, "/root/myapp.log1");
		// s3_copy_obj(bucketname,s3_objectname,s3_objectname);
		// s3_list_multipart(bucketname);
		// s3_multipart_upload(bucketname, "sdk.zip", "/root/sdk/aws-java-sdk.zip");
		// s3_multipart_upload(bucketname, "monalog", "/root/sds-mon.a.log");
		// s3_multi_upload_1(bucketname, "sdk.zip", "/root/sdk/aws-java-sdk.zip");
		// s3_get_obj(bucketname, "monalog", "/root/log");
		// s3_setBucketversioning(bucketname);
		// s3_putobj_simple(bucketname,"objtest","/root/test1");
                // s3_get_obj(bucketname,"objtest","/root/xx");
		//s3_putobj_simple(bucketname,"obj","/root/test1");
		//list_versioning(bucketname,"obj");
		put_iostream(bucketname, objectname, "/root/jdksdkobj");
	}

	public static void put_iostream(String bucketname,
                        String key, String file_path) throws FileNotFoundException,IOException  {
		File file = new File(file_path);
		InputStream input=null;
		try {
		 input = new FileInputStream(file);
		long len = input.available();
		if (len < 20*1024*1024)
			s3_putobj_bystream(bucketname, key, input);
		else
			s3_multipart_upload_bystream(bucketname, key, input);
		} catch (AmazonServiceException ase) {
			//System.out.println("aaaa");
		}finally {
			if(input!=null){
				input.close();
			}
		}
	}



        private static void s3_putobj_bystream(String bucketname,
			String key, InputStream inputStream) {
		try {
		        ObjectMetadata metadata = new ObjectMetadata();
			PutObjectRequest s3_req = new PutObjectRequest(bucketname,
					key, inputStream, metadata);

			PutObjectResult s3_putobj_result = conn.putObject(s3_req);
			//System.out.println("s3_putobj_etag:" + s3_putobj_result.getETag());
		} catch (AmazonServiceException ase) {
			//System.out.println("s3_svr_error_message:" + ase.getMessage());
			//System.out.println("s3_svr_status_code:  " + ase.getStatusCode());
			//System.out.println("s3_svr_error_code:   " + ase.getErrorCode());
			//System.out.println("s3_svr_error_type:   " + ase.getErrorType());
			//System.out.println("s3_svr_request_id:   " + ase.getRequestId());
		} catch (AmazonClientException ace) {
			//System.out.println("s3_clt_error_message:" + ace.getMessage());
		}
	}

	public static void s3_multipart_upload_bystream(String bucketname,
			String objectname, InputStream inputStream) {

		InitiateMultipartUploadRequest s3_multi_req = new InitiateMultipartUploadRequest(bucketname, objectname);
		InitiateMultipartUploadResult s3_multi_res = conn.initiateMultipartUpload(s3_multi_req);

		String s3_multi_uploadid = s3_multi_res.getUploadId();
		final int s3_part_size = 1024 * 1024 * 4;

		List<PartETag> s3_part_etags = new java.util.ArrayList<PartETag>();
		try {
			byte[] bs =new byte[s3_part_size];

			long part_size = 0;
			for (int part_no = 0; (part_size = inputStream.read(bs)) != -1; part_no++) {
				UploadPartRequest s3_upload_req = new UploadPartRequest();
				s3_upload_req.setBucketName(bucketname);
				s3_upload_req.setKey(objectname);
				s3_upload_req.setUploadId(s3_multi_uploadid);
				s3_upload_req.setInputStream( new ByteArrayInputStream(bs));
				s3_upload_req.setPartSize(part_size);
				s3_upload_req.setPartNumber(part_no + 1);
				UploadPartResult s3_upload_res = conn.uploadPart(s3_upload_req);

				s3_part_etags.add(s3_upload_res.getPartETag());
//				System.out.println(" -- part_id" + part_no + " Etag: "
//						+ s3_upload_res.getPartETag().getETag());

			}

			CompleteMultipartUploadRequest s3_complete_req = new CompleteMultipartUploadRequest(
					bucketname, objectname, s3_multi_res.getUploadId(),
					s3_part_etags);

			CompleteMultipartUploadResult s3_complete_res = conn.completeMultipartUpload(s3_complete_req);
			//System.out.println(s3_complete_res.getETag());
		} catch (Exception ie) {
			System.err.println(ie.getMessage());
			ie.printStackTrace();
			conn.abortMultipartUpload(new AbortMultipartUploadRequest(
					bucketname, objectname, s3_multi_uploadid));

		}
	}



    public static void s3_setBucketversioning(String bucketName) {


        	BucketVersioningConfiguration configuration =
        			new BucketVersioningConfiguration().withStatus("Enabled");

			SetBucketVersioningConfigurationRequest setBucketVersioningConfigurationRequest =
					new SetBucketVersioningConfigurationRequest(bucketName,configuration);

			conn.setBucketVersioningConfiguration(setBucketVersioningConfigurationRequest);


			BucketVersioningConfiguration conf = conn.getBucketVersioningConfiguration(bucketName);
			// System.out.println("bucket versioning configuration status:    " + conf.getStatus());

    }


	public static void list_versioning(String bucketname, String key) {
            //System.out.println("Listing objects");

            ListVersionsRequest request = new ListVersionsRequest();
            request.withBucketName(bucketname);

			request.withPrefix(key);


            VersionListing versionListing;
            do {
                versionListing = conn.listVersions(request);
                for (S3VersionSummary objectSummary :
                	versionListing.getVersionSummaries()) {
//                    System.out.println(" - " + objectSummary.getKey() + "  " +
//                            "(size = " + objectSummary.getSize() + ")" +
//                    		"(versionID= " + objectSummary.getVersionId() + ")");

                }
                request.setKeyMarker(versionListing.getNextKeyMarker());
                request.setVersionIdMarker(versionListing.getNextVersionIdMarker());
            } while (versionListing.isTruncated());
    }


	private static void s3_createBucket(String bucketname) {
        if(conn.doesBucketExist(bucketname) == true) {
            //System.out.println(bucketname + " already exist!");
            return;
        }
       // System.out.println("creating " + bucketname + " ...");
        conn.createBucket(bucketname);
        //System.out.println(bucketname + " has been created!");
    }

	public static void s3_list_buckets() {
		try {
			List<Bucket> buckets = conn.listBuckets();
			if (!buckets.isEmpty()) {
				for (Bucket bucket : buckets) {
//					System.out.println("bucket_name: " + bucket.getName()
//							+ " bucket_createdate: "
//							+ bucket.getCreationDate());
				}
			}
		} catch (AmazonServiceException ase) {
//			System.out.println("s3_svr_error_message:" + ase.getMessage());
//			System.out.println("s3_svr_status_code:  " + ase.getStatusCode());
//			System.out.println("s3_svr_error_code:   " + ase.getErrorCode());
//			System.out.println("s3_svr_error_type:   " + ase.getErrorType());
//			System.out.println("s3_svr_request_id:   " + ase.getRequestId());
		} catch (AmazonClientException ace) {
			//System.out.println("s3_clt_error_message:" + ace.getMessage());
		}
	}

	public static void s3_list_objs(String bucketname, String s3_prefix) {
		try {
			ListObjectsRequest s3_req = new ListObjectsRequest()
					.withBucketName(bucketname);
			if (!s3_prefix.isEmpty()) {
				s3_req.setPrefix(s3_prefix);
			}

			ObjectListing s3_list;
			do {
				s3_list = conn.listObjects(s3_req);
				for (S3ObjectSummary s3_objsum : s3_list.getObjectSummaries()) {
					System.out.println(" -- " + s3_objsum.getKey() + "  "
							+ "(size = " + s3_objsum.getSize() + " ,ETag = "
							+ s3_objsum.getETag() + ")");
				}
				s3_req.setMarker(s3_list.getNextMarker());
			} while (s3_list.isTruncated());

		} catch (AmazonServiceException ase) {
//			System.out.println("s3_svr_error_message:" + ase.getMessage());
//			System.out.println("s3_svr_status_code:  " + ase.getStatusCode());
//			System.out.println("s3_svr_error_code:   " + ase.getErrorCode());
//			System.out.println("s3_svr_error_type:   " + ase.getErrorType());
//			System.out.println("s3_svr_request_id:   " + ase.getRequestId());
		} catch (AmazonClientException ace) {
			//System.out.println("s3_clt_error_message:" + ace.getMessage());
		}
	}

	public static void s3_putobj_simple(String bucketname, String objectname, String file_path) {
		try {
			File file = new File(file_path);
			PutObjectResult s3_putobj_result = conn
					.putObject(new PutObjectRequest(bucketname, objectname, file));
//			System.out
//					.println("s3_putobj_etag:" + s3_putobj_result.getETag());
		} catch (AmazonServiceException ase) {
//			System.out.println("s3_svr_error_message:" + ase.getMessage());
//			System.out.println("s3_svr_status_code:  " + ase.getStatusCode());
//			System.out.println("s3_svr_error_code:   " + ase.getErrorCode());
//			System.out.println("s3_svr_error_type:   " + ase.getErrorType());
//			System.out.println("s3_svr_request_id:   " + ase.getRequestId());
		} catch (AmazonClientException ace) {
//			System.out.println("s3_clt_error_message:" + ace.getMessage());
		}
	}

	public static void s3_putobj_withmeta(String bucketname,
			String file_path) {
		try {
			ObjectMetadata s3_meta = new ObjectMetadata();
			s3_meta.addUserMetadata("metaname", "meta1");
			s3_meta.setContentType("image/jpeg");
			s3_meta.setHeader("x-amz-acl", "public-read");
			//s3_meta.setHeader("x-amz-meta-your-key-here", "your-private-value");

			File file = new File(file_path);

			PutObjectRequest s3_req = new PutObjectRequest(bucketname,
					file.getName(), file);
			s3_req.setMetadata(s3_meta);
			s3_req.setCannedAcl(CannedAccessControlList.PublicRead);



			PutObjectResult s3_putobj_result = conn.putObject(s3_req);
			//System.out.println("s3_putobj_etag:" + s3_putobj_result.getETag());
		} catch (AmazonServiceException ase) {
//			System.out.println("s3_svr_error_message:" + ase.getMessage());
//			System.out.println("s3_svr_status_code:  " + ase.getStatusCode());
//			System.out.println("s3_svr_error_code:   " + ase.getErrorCode());
//			System.out.println("s3_svr_error_type:   " + ase.getErrorType());
//			System.out.println("s3_svr_request_id:   " + ase.getRequestId());
		} catch (AmazonClientException ace) {
			//System.out.println("s3_clt_error_message:" + ace.getMessage());
		}
	}

	/*public static void s3_get_obj(String bucketname, String objectname, String file_path) {
		BufferedInputStream in_stream = null;
		BufferedOutputStream out_stream = null;
		try {
			S3Object s3_object = conn.getObject(new GetObjectRequest(
					bucketname, objectname));
			System.out.println("s3_content-type :"
					+ s3_object.getObjectMetadata().getContentType());
			System.out.println("s3_etag : "
					+ s3_object.getObjectMetadata().getETag());
			System.out.println("s3_content_length:"
					+ s3_object.getObjectMetadata().getContentLength());

			in_stream = new BufferedInputStream(s3_object.getObjectContent());
			File s3_local_file = new File(file_path + objectname);
			boolean exists = s3_local_file.getParentFile().exists();
			if (!exists) {
				s3_local_file.getParentFile().delete();
			}


			try {
				out_stream = new BufferedOutputStream(new FileOutputStream(
						s3_local_file, false));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}

			byte[] buffer = new byte[1024];
			int s3_offset = 0;
			try {
				while ((s3_offset = in_stream.read(buffer)) != -1) {
					out_stream.write(buffer, 0, s3_offset);
					out_stream.flush();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

		} catch (AmazonServiceException ase) {
			System.out.println("s3_svr_error_message:" + ase.getMessage());
			System.out.println("s3_svr_status_code:  " + ase.getStatusCode());
			System.out.println("s3_svr_error_code:   " + ase.getErrorCode());
			System.out.println("s3_svr_error_type:   " + ase.getErrorType());
			System.out.println("s3_svr_request_id:   " + ase.getRequestId());
		} catch (AmazonClientException ace) {
			System.out.println("s3_clt_error_message:" + ace.getMessage());
		} finally {
			if (null != in_stream)
				try {
					in_stream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			if (null != out_stream)
				try {
					out_stream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
	}*/

	public static void s3_gettobj_meta(String bucketname,
			String objectname) {

		try {
			ObjectMetadata s3_meta = conn
					.getObjectMetadata(new GetObjectMetadataRequest(
							bucketname, objectname));
//			System.out.println("s3_content_length:"
//					+ s3_meta.getContentLength());
//			System.out.println("s3_content_type:   "
//					+ s3_meta.getContentType());
//			System.out.println("s3_etag :          " + s3_meta.getETag());

		} catch (AmazonServiceException ase) {
//			System.out.println("s3_svr_error_message:" + ase.getMessage());
//			System.out.println("s3_svr_status_code:  " + ase.getStatusCode());
//			System.out.println("s3_svr_error_code:   " + ase.getErrorCode());
//			System.out.println("s3_svr_error_type:   " + ase.getErrorType());
//			System.out.println("s3_svr_request_id:   " + ase.getRequestId());
		} catch (AmazonClientException ace) {
			//System.out.println("s3_clt_error_message:" + ace.getMessage());
		}
	}

	public static void s3_delete_obj(String bucketname, String objectname) {
		try {
			conn.deleteObject(bucketname, objectname);
		} catch (AmazonServiceException ase) {
//			System.out.println("s3_svr_error_message:" + ase.getMessage());
//			System.out.println("s3_svr_status_code:  " + ase.getStatusCode());
//			System.out.println("s3_svr_error_code:   " + ase.getErrorCode());
//			System.out.println("s3_svr_error_type:   " + ase.getErrorType());
//			System.out.println("s3_svr_request_id:   " + ase.getRequestId());
		} catch (AmazonClientException ace) {
			//System.out.println("s3_clt_error_message:" + ace.getMessage());
		}
	}

	public static void s3_setobj_acl(String bucketname, String objectname) {
		try {
			conn.setObjectAcl(bucketname, objectname,
					CannedAccessControlList.PublicRead);
		} catch (AmazonServiceException ase) {
//			System.out.println("s3_svr_error_message:" + ase.getMessage());
//			System.out.println("s3_svr_status_code:  " + ase.getStatusCode());
//			System.out.println("s3_svr_error_code:   " + ase.getErrorCode());
//			System.out.println("s3_svr_error_type:   " + ase.getErrorType());
//			System.out.println("s3_svr_request_id:   " + ase.getRequestId());
		} catch (AmazonClientException ace) {
			//System.out.println("s3_clt_error_message:" + ace.getMessage());
		}
	}

	public static void s3_getobj_acl(String bucketname, String objectname) {
		try {
			AccessControlList s3_obj_acl = conn.getObjectAcl(
					bucketname, objectname);
			for (Grant grant : s3_obj_acl.getGrants()) {
//				System.out.println(grant.getGrantee().getIdentifier());
			}
		} catch (AmazonServiceException ase) {
			System.out.println("s3_svr_error_message:" + ase.getMessage());
			System.out.println("s3_svr_status_code:  " + ase.getStatusCode());
			System.out.println("s3_svr_error_code:   " + ase.getErrorCode());
			System.out.println("s3_svr_error_type:   " + ase.getErrorType());
			System.out.println("s3_svr_request_id:   " + ase.getRequestId());
		} catch (AmazonClientException ace) {
			System.out.println("s3_clt_error_message:" + ace.getMessage());
		}
	}

	/*public static void s3_multi_upload_1(String bucketname,
					String objname, String file_path) {
			int threshold = 4 * 1024 * 1024;
			TransferManager tm = new TransferManager(conn);
			TransferManagerConfiguration conf = tm.getConfiguration();
			conf.setMultipartUploadThreshold(threshold);
			tm.setConfiguration(conf);

			File file = new File(file_path);
			PutObjectRequest s3_req = new PutObjectRequest(bucketname, objname, file);

			Upload upload = tm.upload(s3_req);
			try {
					TransferProgress progress = upload.getProgress();
					while (false == upload.isDone()) {
							int percent = (int) (progress.getPercentTransferred());
							System.out.print("\r" + "[ " + percent + "% ] "
											+ progress.getBytesTransferred() + " / "
											+ progress.getTotalBytesToTransfer());
							Thread.sleep(500);
					}
					System.out.println("\ndone");
					if (upload.isDone())
					{
							tm.shutdownNow();
							return;
					}

			} catch (AmazonServiceException ase) {
					System.out.println("s3_svr_error_message:" + ase.getMessage());
					System.out.println("s3_svr_status_code:  " + ase.getStatusCode());
					System.out.println("s3_svr_error_code:   " + ase.getErrorCode());
					System.out.println("s3_svr_error_type:   " + ase.getErrorType());
					System.out.println("s3_svr_request_id:   " + ase.getRequestId());
			} catch (InterruptedException ie) {
					System.out.println("s3_ie_error_message:" + ie.getMessage());
					ie.printStackTrace();
			} catch (AmazonClientException ace) {
					System.out.println("s3_clt_error_message:" + ace.getMessage());
					ace.printStackTrace();
			}
	}*/

	/*public static void s3_multipart_upload(String bucketname,
			String objectname, String file_path) {

		InitiateMultipartUploadRequest s3_multi_req = new InitiateMultipartUploadRequest(
				bucketname, objectname);
		InitiateMultipartUploadResult s3_multi_res = conn
				.initiateMultipartUpload(s3_multi_req);

		String s3_multi_uploadid = s3_multi_res.getUploadId();
		//int len = s3_multi_uploadid.length();
		//s3_multi_uploadid = s3_multi_uploadid.substring(0, 1) + '-' + s3_multi_uploadid.substring(2);
	        //s3_multi_res.setUploadId(s3_multi_uploadid);
		final int s3_part_size = 1024 * 1024 * 5;
		File local_file = new File(file_path);

		int s3_part_count = (int) Math.ceil((double) (local_file.length())
				/ (double) s3_part_size);

		System.out.println(" -- part_num " + s3_part_count + " ----id:" + s3_multi_uploadid);

		List<PartETag> s3_part_etags = new java.util.ArrayList<PartETag>();
		FileInputStream s3_input=null;
		try {
			for (int part_no = 0; part_no < s3_part_count; part_no++) {
				try{
					 s3_input = new FileInputStream(local_file);
					long s3_offset_bytes = (long)s3_part_size * part_no;
					long skip = s3_input.skip(s3_offset_bytes);
					long part_size = s3_part_size < (local_file.length() - s3_offset_bytes) ? s3_part_size
							: (local_file.length() - s3_offset_bytes);

					UploadPartRequest s3_upload_req = new UploadPartRequest();
					s3_upload_req.setBucketName(bucketname);
					s3_upload_req.setKey(objectname);
					s3_upload_req.setUploadId(s3_multi_uploadid);
					s3_upload_req.setInputStream(s3_input);
					s3_upload_req.setPartSize(part_size);
					s3_upload_req.setPartNumber(part_no + 1);
					UploadPartResult s3_upload_res = conn
							.uploadPart(s3_upload_req);

					s3_part_etags.add(s3_upload_res.getPartETag());
					//s3_input.close();
					System.out.println(" -- part_id" + part_no + " Etag: "
							+ s3_upload_res.getPartETag().getETag());
				}catch (Exception e){
					System.out.println(e);
				}finally {
					s3_input.close();
				}





			}

			PartListing s3_parts = conn.listParts(new ListPartsRequest(bucketname, objectname, s3_multi_uploadid));
			for (PartSummary part : s3_parts.getParts()) {
				System.out.println("PartNumber: " + part.getPartNumber()
						+ " ETag: " + part.getETag());
			}

			CompleteMultipartUploadRequest s3_complete_req = new CompleteMultipartUploadRequest(
					bucketname, objectname, s3_multi_res.getUploadId(),
					s3_part_etags);

			CompleteMultipartUploadResult s3_complete_res = conn
					.completeMultipartUpload(s3_complete_req);
			System.out.println(s3_complete_res.getETag());
		} catch (Exception ie) {
			System.err.println(ie.getMessage());
			ie.printStackTrace();
			conn.abortMultipartUpload(new AbortMultipartUploadRequest(
					bucketname, objectname, s3_multi_uploadid));

		}
	}*/

	/*public static void s3_list_multipart(String bucketname) {
		try {
			ListMultipartUploadsRequest s3_list_req = new ListMultipartUploadsRequest(
					bucketname);
			MultipartUploadListing s3_list_res = conn
					.listMultipartUploads(s3_list_req);

			for (MultipartUpload multipartUpload : s3_list_res
					.getMultipartUploads()) {
				System.out.println("Key: " + multipartUpload.getKey()
						+ " UploadId: " + multipartUpload.getUploadId());
				AbortMultipartUploadRequest s3_abort_req = new AbortMultipartUploadRequest(
						bucketname, multipartUpload.getKey(),
						multipartUpload.getUploadId());
				conn.abortMultipartUpload(s3_abort_req);
			}
		} catch (AmazonServiceException ase) {
			System.out.println("s3_svr_error_message:" + ase.getMessage());
			System.out.println("s3_svr_status_code:  " + ase.getStatusCode());
			System.out.println("s3_svr_error_code:   " + ase.getErrorCode());
			System.out.println("s3_svr_error_type:   " + ase.getErrorType());
			System.out.println("s3_svr_request_id:   " + ase.getRequestId());
		} catch (AmazonClientException ace) {
			System.out.println("s3_clt_error_message:" + ace.getMessage());
		}
	}*/

	/*public static void s3_copy_obj(String bucketname, String objectname,
			String s3_new_objname) {
		try {
			CopyObjectRequest s3_req = new CopyObjectRequest(bucketname,
					objectname, bucketname, s3_new_objname);
			ObjectMetadata s3_meta = new ObjectMetadata();
			s3_meta.addUserMetadata("usermetakey", "usermetavale");
			s3_req.setNewObjectMetadata(s3_meta);
			s3_req.setCannedAccessControlList(CannedAccessControlList.PublicRead);

			CopyObjectResult s3_res = conn.copyObject(s3_req);
			System.out.println(s3_res.getETag());
		} catch (AmazonServiceException ase) {
			System.out.println("s3_svr_error_message:" + ase.getMessage());
			System.out.println("s3_svr_status_code:  " + ase.getStatusCode());
			System.out.println("s3_svr_error_code:   " + ase.getErrorCode());
			System.out.println("s3_svr_error_type:   " + ase.getErrorType());
			System.out.println("s3_svr_request_id:   " + ase.getRequestId());
		} catch (AmazonClientException ace) {
			System.out.println("s3_clt_error_message:" + ace.getMessage());
		}
	}*/
}
