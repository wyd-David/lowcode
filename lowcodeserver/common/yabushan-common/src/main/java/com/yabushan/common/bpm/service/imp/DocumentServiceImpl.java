package com.yabushan.common.bpm.service.imp;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yabushan.common.bpm.model.AmsConstants;
import com.yabushan.common.bpm.model.DocParams;
import com.yabushan.common.bpm.service.DocumentService;
import com.yabushan.common.bpm.utils.HttpToolUtil;
import com.yabushan.common.bpm.utils.JavaBeanUtil;
import com.yabushan.common.utils.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;

@Service
public class DocumentServiceImpl implements DocumentService {
	private final Logger log = LogManager.getLogger(getClass());
	private String getHttpPortal() {
		return AmsConstants.PBMURL;
	}
	private DocParams dto =null;

	public void initDoc(DocParams dto){
		this.dto = dto;
		if(this.dto == null){
			this.dto = new DocParams();
		}
	}

	public String draft() {
		String result = "";
		Map<String, Object> params = new HashMap<>();
		params.put("service", this.dto.getService());// 文档编号
		params.put("title", this.dto.getTitle());//
		params.put("legacy", this.dto.getLegacy());// 版本号向后兼容
		try {
			result = HttpToolUtil.post(params, getHttpPortal()
					+ AmsConstants.PBMURLDOC_Draft);
		} catch (Exception e) {
			log.error("流程管理拟稿人文档异常" + e.getLocalizedMessage());
			e.printStackTrace();
		}
		return result;
	}
	public String create(DocParams docParams) {
		String result = "";
		Map<String, Object> params = new HashMap<>();
		params.put("service", docParams.getService());// 类型类型
		params.put("title",docParams.getTitle());// 操作类型
		params.put("user", docParams.getUser());// 操作用户
		String attribute = docParams.getAttribute();
		params.put("attribute", attribute == null?"":attribute);// 参数数据
		String data = docParams.getData();
		//处理紧急
		String oa_urgency = docParams.getOa_urgency();
		if(StringUtils.isNotBlank(oa_urgency)){
			oa_urgency = "1".equalsIgnoreCase(oa_urgency)?"加急":"无";
			if(StringUtils.isNotBlank(data)){
				try{
					JSONObject p = JSON.parseObject(data);
					p.put("oa_urgency",oa_urgency);
					data = p.toString();
				}catch (Exception e){
					JSONObject p = new JSONObject();
					p.put("oa_urgency",oa_urgency);
					data = p.toString();
				}
			}else{
				JSONObject p = new JSONObject();
				p.put("oa_urgency",oa_urgency);
				data = p.toString();
			}
		}
		params.put("data",data == null?"":data);// 业务数据
		try {
			result = HttpToolUtil.post(params, getHttpPortal()
					+ AmsConstants.PBMURLDOC_Create);
		} catch (Exception e) {
			log.error("流程管理创建新文档异常" + e.getLocalizedMessage());
			e.printStackTrace();
		}
		return result;
	}

	public String update(){
		String result = "";
		Map<String, Object> params = new HashMap<>();
		params.put("documentId", this.dto.getDocumentId());// 类型类型
		params.put("title", this.dto.getTitle());
		params.put("user", this.dto.getUser());//创建用户
		params.put("data", this.dto.getData());//业务数据
		params.put("attribute", this.dto.getAttribute());//参数数据
		params.put("version", this.dto.getVersion());//数据版本号
		params.put("merge", this.dto.isMerge());//是否合并模式
		try {
			result = HttpToolUtil.post(params, getHttpPortal()
					+ AmsConstants.PBMURLDOC_Update);
		} catch (Exception e) {
			log.error("流程管理更新文档异常" + e.getLocalizedMessage());
			e.printStackTrace();
		}
		return result;
	}
	public String open(DocParams docParams){
		initDoc(docParams);
		String result = "";
		Map<String, Object> params = new HashMap<>();
		params.put("documentId", this.dto.getDocumentId());// 类型类型
		String user = this.dto.getUser();
		if(StringUtils.isNotBlank(user)){
			params.put("user", user);//用户账号
		}

		params.put("returnData", this.dto.isReturnData());//	默认false是否返回文档数据
		try {
			result = HttpToolUtil.post(params, getHttpPortal()
					+ AmsConstants.PBMURLDOC_Open);
		} catch (Exception e) {
			log.error("流程管理打开文档异常" + e.getLocalizedMessage());
			e.printStackTrace();
		}
		return result;
	}
	public String query(){
		String result = "";
		Map<String, Object> params = new HashMap<>();
		params.put("documentId", this.dto.getDocumentId());// 文档ID 必填
		params.put("user", this.dto.getUser());// 用户非必填
		params.put("returnData", this.dto.isReturnData());// 是否返回文档数据 默认false
		try {
			result = HttpToolUtil.post(params, getHttpPortal()
					+ AmsConstants.PBMURLDOC_Query);
		} catch (Exception e) {
			log.error("流程管理创建新文档异常" + e.getLocalizedMessage());
			e.printStackTrace();
		}
		return result;
	}

	public String share(DocParams docShare) {
		String result = "";
		try {
			result = HttpToolUtil.post(JavaBeanUtil.convertBeanToMap(docShare), getHttpPortal()
					+ AmsConstants.PBMURLDOC_Share);
		} catch (Exception e) {
			log.error("share传阅文档异常" + e.getLocalizedMessage());
			e.printStackTrace();
		}
		return result;
	}

	public String records(DocParams pieDocRecords) {
		String result = "";
		try {
			result = HttpToolUtil.post(JavaBeanUtil.convertBeanToMap(pieDocRecords), getHttpPortal()
					+ AmsConstants.PBMURLDOC_Records);
		} catch (Exception e) {
			log.error("查询文档的审批流水记录records异常" + e.getLocalizedMessage());
			e.printStackTrace();
		}
		return result;
	}
	public String access(DocParams pieDocAccess) {
		String result = "";
		try {
			result = HttpToolUtil.post(JavaBeanUtil.convertBeanToMap(pieDocAccess), getHttpPortal()
					+ AmsConstants.PBMURLDOC_Access);
		} catch (Exception e) {
			log.error("检查用户访问权限异常" + e.getLocalizedMessage());
			e.printStackTrace();
		}
		return result;
	}

	public String acl(DocParams pieDocAcl) {
		String result = "";
		try {
			result = HttpToolUtil.post(JavaBeanUtil.convertBeanToMap(pieDocAcl), getHttpPortal()
					+ AmsConstants.PBMURLDOC_Acl);
		} catch (Exception e) {
			log.error("设置文档ACL异常" + e.getLocalizedMessage());
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public String service(String service) {
		Map<String, Object> params = new HashMap<>();
		params.put("service", service);// 类型类型
		String result = "";
		try {
			result = HttpToolUtil.post(params, getHttpPortal()+AmsConstants.PBMURLDOC_Service);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public static void main(String[] args) {
		// String result = new DocumentService().draft();
		// String result = new DocumentService().create();
	}

	@Override
	public String readShareBatch(String sid, String userId) {
		Map<String, Object> params = new HashMap<>();
		params.put("sid", sid);// 待阅任务ID,逗号分隔
		params.put("userId", userId);
		String result = "";
		try {
			result = HttpToolUtil.post(params, getHttpPortal()+AmsConstants.PBMURLDOC_Service);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public String readShare(String documentId, String shareId, String user) {
		Map<String, Object> params = new HashMap<>();
		params.put("documentId", documentId);//
		params.put("shareId", shareId);
		params.put("user", user);
		String result = "";
		try {
			result = HttpToolUtil.post(params, getHttpPortal()+AmsConstants.PBMURLDOC_Service);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public String openShare(String shareId, String user) {
		Map<String, Object> params = new HashMap<>();
		params.put("shareId", shareId);
		params.put("user", user);
		String result = "";
		try {
			result = HttpToolUtil.post(params, getHttpPortal()+AmsConstants.PBMURLDOC_Open_Share);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public String relationCreate() {
		String result = "";
		Map<String, Object> params = new HashMap<>();
		params.put("dispatchFlag", this.dto.getDispatchFlag());// 发文标志 默认0
		params.put("title", this.dto.getTitle());// 标题（普通流程不用传）
		params.put("fromDocumentId", this.dto.getFromDocumentId());// 主流程
		params.put("toDocumentId", this.dto.getToDocumentId());// 关联流程
		params.put("activeTime", this.dto.getActiveTime());// 生效时间默认 9999-12-31 23:59:59
		params.put("createdBy", this.dto.getCreatedBy());// 创建人
		try {
			result = HttpToolUtil.post(params, getHttpPortal()
					+ AmsConstants.PBMURLDOC_Rel_Create);
		} catch (Exception e) {
			log.error("流程管理添加流程关联关系异常" + e.getLocalizedMessage());
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public String relationDelete() {
		String result = "";
		Map<String, Object> params = new HashMap<>();
		params.put("dispatchFlag", this.dto.getDispatchFlag());// 发文标志 默认0
		params.put("fromDocumentId", this.dto.getFromDocumentId());// 主流程
		params.put("toDocumentId", this.dto.getToDocumentId());// 关联流程
		params.put("deletedBy", this.dto.getDeletedBy());//删除人
		try {
			result = HttpToolUtil.post(params, getHttpPortal()
					+ AmsConstants.PBMURLDOC_Rel_Delete);
		} catch (Exception e) {
			log.error("流程管理删除流程关联关系异常" + e.getLocalizedMessage());
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public String relationQuery() {
		String result = "";
		Map<String, Object> params = new HashMap<>();
		params.put("documentId", this.dto.getDocumentId());// 主流程ID
		params.put("queryBy", this.dto.getQueryBy());//查询人
		try {
			result = HttpToolUtil.post(params, getHttpPortal()
					+ AmsConstants.PBMURLDOC_Rel_Query);
		} catch (Exception e) {
			log.error("流程管理查询流程关联关系异常" + e.getLocalizedMessage());
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public String relationUpdate() {
		String result = "";
		Map<String, Object> params = new HashMap<>();
		params.put("dispatchFlag", this.dto.getDocumentId());// 发文标志 默认0
		params.put("title", this.dto.getTitle());// 标题（普通流程不用传）
		params.put("fromDocumentId", this.dto.getFromDocumentId());// 主流程
		params.put("toDocumentId", this.dto.getToDocumentId());// 关联流程
		params.put("activeTime", this.dto.getActiveTime());// 生效时间
		params.put("createdBy", this.dto.getCreatedBy());// 创建人
		params.put("createdTime", this.dto.getCreatedTime());// 创建时间
		params.put("updatedBy", this.dto.getUpdatedBy());// 修改人
		try {
			result = HttpToolUtil.post(params, getHttpPortal()
					+ AmsConstants.PBMURLDOC_Rel_Update);
		} catch (Exception e) {
			log.error("流程管理更新流程关联关系异常" + e.getLocalizedMessage());
			e.printStackTrace();
		}
		return result;
	}
}
