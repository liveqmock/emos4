package com.ultrapower.eoms.ultrabpp.runtime.manage;

import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ultrapower.eoms.ultrabpp.utils.ProcessUtil;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.transaction.annotation.Transactional;

import com.ultrapower.eoms.ultrabpp.runtime.service.WorkflowRoleService;
import com.ultrapower.eoms.ultrasm.model.DepInfo;
import com.ultrapower.workflow.bizworkflow.facade.IBizFacade;
import com.ultrapower.workflow.client.WorkFlowServiceClient;
import com.ultrapower.workflow.engine.core.model.DataField;
import com.ultrapower.workflow.role.model.ChildRole;
import com.ultrapower.workflow.role.model.Dimension;
import com.ultrapower.workflow.role.service.IRoleService;

@Transactional
public class WorkflowRoleManagerImpl implements WorkflowRoleService {

	protected IBizFacade bizFacade = WorkFlowServiceClient.clientInstance().getBizFacade();
	private IRoleService roleService;
	
	public String[] getDimensions(String baseSchema) {
		String[] rtn = null;
		List<Dimension> dims = roleService.getDimensionBySchema(baseSchema);
		if (CollectionUtils.isNotEmpty(dims)) {
			rtn = new String[dims.size()];
			for (int i = 0; i < dims.size(); i++) {
				Dimension dim = dims.get(i);
				String filedName = dim.getDimCode();
				String fieldid = dim.getFieldid();
				if (StringUtils.isNotBlank(fieldid)) {
					filedName = fieldid;
				}
				rtn[i] = filedName;
			}
		}
		return rtn;
	}

	public String[] matchRole(String baseSchema, String defName, String phaseNo,
			Map<String, String> args) {
		String[] chi = new String[2];
		StringBuffer chiName = new StringBuffer();
		StringBuffer chiCode = new StringBuffer();
		Map<String, DataField> inputData = new HashMap<String, DataField>();
		ProcessUtil.putAll(inputData, args);
		List<ChildRole> chiRoles = null; 
		try {
			chiRoles = bizFacade.matchRole(baseSchema, defName, phaseNo, inputData);
			if (CollectionUtils.isNotEmpty(chiRoles)) {
				for (int i = 0; i < chiRoles.size(); i++) {
					ChildRole chiRole = chiRoles.get(i);
					String childRoleId = chiRole.getChildRoleId();
					List<DepInfo> depList = chiRole.getDepList();
					String code = null;
					//如果角色细分配置的是要虚拟组，则返回虚拟组
					if (CollectionUtils.isNotEmpty(depList)) {
						DepInfo depInfo = depList.get(0);
						code = "G#:"+depInfo.getPid()+"#:NEXT#:2#:#:#:#:#:"+phaseNo+"#:#:#;";
						chiName.append(depInfo.getDepname()+";");
					} else {
						code = "R#:"+childRoleId+"#:NEXT#:2#:#:#:#:#:"+phaseNo+"#:#:#;";
						chiName.append(chiRole.getChildRoleName()+";");
					}
					chiCode.append(code);
				}
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		chi[0] = chiName.toString();
		chi[1] = chiCode.toString();
		return chi;
	}
	
	public List<Dimension> getDimensions(String baseSchema, String roleCode) {
		return roleService.getDimensionIds(baseSchema, roleCode);
	}
	
	public List<ChildRole> matchRole(String baseSchema, String roleCode, Map<String, String> args) {
		Map<String, DataField> inputData = new HashMap<String, DataField>();
		ProcessUtil.putAll(inputData, args);
		return roleService.matchChildRole(baseSchema, baseSchema, roleCode, inputData);
	}

	public IRoleService getRoleService() {
		return roleService;
	}

	public void setRoleService(IRoleService roleService) {
		this.roleService = roleService;
	}

}
