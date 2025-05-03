package com.yabushan.common.bpm.service;


import com.yabushan.common.bpm.model.DocParams;

/**
 * @ClassName: DocumentService.java
 * @Description: 调用PIE文档操作
 * @author: yl
 * @version: V1.0
 * @Date: 2019年3月5日 上午10:07:17
 */
public interface DocumentService {
	void initDoc(DocParams dto);
	/**
	* @Title: draft
	* @Description: TODO(拟稿人)
	* @param 
	* @author : yl
	* @date : 2019年3月13日 下午4:37:24 
	* @return String    返回类型
	 */
	String draft();
	/**
	 * @Title: create
	 * @Description: 创建新文档
	 * @param:
	 * @author : yl
	 * @date : 2019年3月5日 上午10:23:28
	 * @return String 返回类型
	 */
	String create(DocParams docParams);
	
	/**
	* @Title: update
	* @Description: 更新文档
	* @param 
	* @author : yl
	* @date : 2019年3月11日 上午9:55:42 
	* @return String    返回类型
	 */
	String update();
	
	/**
	* @Title: open
	* @Description: 待办打开文档
	* @param: documentId/user/returnData 默认false
	* @author : yl
	* @date : 2019年3月11日 上午9:55:42 
	* @return String    返回类型
	* @throws
	 */
	 String open(DocParams docParams);
	 
	 /**
	* @Title: query
	* @Description: 查询文档 user 非必填 returnData 默认不返回文档信息
	* @param: documentId/user/returnData
	* @author : yl
	* @date : 2019年3月7日 下午4:16:46 
	* @return String    返回类型
	* @throws
	 */
	 String query();
	 /**
	  * @Description:  share传阅文档
	  * @Param:
	  * @return:
	  * @Author: LJ
	  * @Date: 2019/3/11
	  */
	 String share(DocParams docShare);
	  /**
     * @Description: 查询文档的审批流水记录records
     * @Param:
     * @return:
     * @Author: LJ
     * @Date: 2019/3/11
     */
	 String records(DocParams pieDocRecords);
	 /**
     * @Description: access 检查用户访问权限
     * @Param:
     * @return:
     * @Author: LJ
     * @Date: 2019/3/11
     */
	 String access(DocParams pieDocAccess);
	 /**
     * @Description: 设置文档ACL
     * @Param:
     * @return:
     * @Author: LJ
     * @Date: 2019/3/11
     */
	 String acl(DocParams pieDocAcl) ;
	 /**
	 * @Title: service
	 * @Description: TODO(查询流程编号信息)
	 * @param 
	 * @author : yl
	 * @date : 2019年4月9日 下午2:38:24 
	 * @throws
	 * @return String    返回类型
	  */
	 String service(String service);
	 /**
	  * 
	 * @Title: readShareBatch
	 * @Description: 批量处理将待阅处理为已读
	 * @param 
	 * @author : yl
	 * @date : 2019年7月17日 上午9:50:39 
	 * @throws
	 * @return String    返回类型
	  */
	 String readShareBatch(String sid, String userId);
	 
	 
	 /**
	  * 
	 * @Title: readShareBatch
	 * @Description:处理将待阅处理为已读
	 * @param 
	 * @author : yl
	 * @date : 2019年7月17日 上午9:50:39 
	 * @throws
	 * @return String    返回类型
	  */
	 String readShare(String documentId, String shareId, String user);
	 
	 /**
	 * @Title: openShare
	 * @Description: 打开待阅
	 * @param 
	 * @author : yl
	 * @date : 2019年7月17日 下午3:08:21 
	 * @throws
	 * @return String    返回类型
	  */
	 String openShare(String shareId, String user);
	 /**
	 *@MethodName relationCreate
	 *@Desc "/api/v2/document/relation/create"; //POST  创建流程关联关系
	 *@Author YvonneLee
	 *@Param [dispatchFlag,title,fromDocumentId,toDocumentId,activeTime,createdBy]
	 *@return java.lang.String
	 *@Date 2020/3/4 16:54
	 **/
	 String relationCreate();
	 /**
	 *@MethodName relationDelete
	 *@Desc "/api/v2/document/relation/delete"; //POST  解除关联流程关系
	 *@Author YvonneLee
	 *@Param [dispatchFlag,fromDocumentId,toDocumentId,deletedBy]
	 *@return java.lang.String
	 *@Date 2020/3/4 16:56
	 **/
	 String relationDelete();
	 /**
	 *@MethodName relationQuery
	 *@Desc "/api/v2/document/relation/query"; //POST  查询关联流程
	 *@Author YvonneLee
	 *@Param [documentId,queryBy]
	 *@return java.lang.String
	 *@Date 2020/3/4 16:57
	 **/
	 String relationQuery();
	 /**
	 *@MethodName relationUpdate
	 *@Desc "/api/v2/document/relation/update"; //POST  更新流程关联关系
	 *@Author YvonneLee
	  *@Param [dispatchFlag,title,fromDocumentId,toDocumentId,activeTime,createdBy,createdTime,updatedBy]
	 *@return java.lang.String
	 *@Date 2020/3/4 16:58
	 **/
	 String relationUpdate();
}
