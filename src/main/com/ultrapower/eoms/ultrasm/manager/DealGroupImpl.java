package com.ultrapower.eoms.ultrasm.manager;

import com.ultrapower.eoms.common.core.dao.IDao;
import com.ultrapower.eoms.common.core.util.TimeUtils;
import com.ultrapower.eoms.ultrasm.model.DealGroup;
import com.ultrapower.eoms.ultrasm.model.UserInfo;
import com.ultrapower.eoms.ultrasm.service.DealGroupService;
import com.ultrapower.eoms.ultrasm.service.DepManagerService;
import com.ultrapower.eoms.ultrasm.service.UserManagerService;
import com.ultrapower.workflow.engine.core.model.ThreadObj;
import com.ultrapower.workflow.role.model.RoleUser;
import com.ultrapower.workflow.role.service.IRoleService;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public class DealGroupImpl
  implements DealGroupService
{
  private static Logger log = LoggerFactory.getLogger(DealGroupImpl.class);
  private IDao<DealGroup> dealGroupDao;
  private DepManagerService depManagerService;
  private UserManagerService userManagerService;
  private IRoleService roleService;

  public void deleteById(String id)
  {
    this.dealGroupDao.removeById(id);
  }

  public List<DealGroup> getAll() {
    return this.dealGroupDao.getAll();
  }

  public DealGroup getById(String id) {
    return (DealGroup)this.dealGroupDao.get(id);
  }

  public List<DealGroup> getDealGroupByUser(String userLoginName)
  {
    Set rtn = new HashSet();
    List dealGroups = getAll();
    if (CollectionUtils.isNotEmpty(dealGroups)) {
      String groupIds = this.userManagerService.getGroupIdsByLoginName(userLoginName);
      List roleUsers = this.roleService.getRoleUserByHql("from RoleUser where loginName='" + userLoginName + "'");
      if (StringUtils.isNotBlank(groupIds)) {
        String[] groups = groupIds.split(",");
        if (!ArrayUtils.isEmpty(groups)) {
          for (int i = 0; i < groups.length; i++) {
            String gid = groups[i];
            if (StringUtils.isNotBlank(gid)) {
              for (int j = 0; j < dealGroups.size(); j++) {
                DealGroup dealGroup = (DealGroup)dealGroups.get(j);
                int isEnable = dealGroup.getIsEnable();
                if (1 == isEnable) {
                  String groupId = dealGroup.getGroupId();
                  if (gid.equals(groupId)) {
                    rtn.add(dealGroup);
                  }
                }
              }
            }
          }
        }
      }
      if (CollectionUtils.isNotEmpty(roleUsers)) {
        for (int i = 0; i < roleUsers.size(); i++) {
          RoleUser roleUser = (RoleUser)roleUsers.get(i);
          String childRoleId = roleUser.getChildRoleId();
          for (int j = 0; j < dealGroups.size(); j++) {
            DealGroup dealGroup = (DealGroup)dealGroups.get(j);
            int isEnable = dealGroup.getIsEnable();
            if (1 == isEnable) {
              String groupId = dealGroup.getGroupId();
              if (childRoleId.equals(groupId)) {
                rtn.add(dealGroup);
              }
            }
          }
        }
      }
    }
    return new ArrayList(rtn);
  }

  public List<UserInfo> getDealGroupUsers(DealGroup dealGroup)
  {
    Set users = new HashSet();
    if (dealGroup != null) {
      String groupId = dealGroup.getGroupId();
      String groupType = dealGroup.getGroupType();
      if ("GROUP".equals(groupType)) {
        List userList = this.depManagerService.getUserByDepID(groupId, false);
        users.addAll(userList);
      } else if ("ROLE".equals(groupType)) {
        List roleUsers = this.roleService.getRoleUserByCroleID(groupId);
        if (CollectionUtils.isNotEmpty(roleUsers)) {
          for (int j = 0; j < roleUsers.size(); j++) {
            RoleUser roleUser = (RoleUser)roleUsers.get(j);
            if (roleUser != null) {
              UserInfo u = ThreadObj.getUsersInfo(roleUser.getLoginName());
              users.add(u);
            }
          }
        }
      }
    }
    return new ArrayList(users);
  }

  public List<UserInfo> getDealGroupUsers(String userLoginName, String entryState)
  {
    Set users = new HashSet();
    List groups = getDealGroupByUser(userLoginName);
    if (CollectionUtils.isNotEmpty(groups)) {
      for (int i = 0; i < groups.size(); i++) {
        DealGroup dealGroup = (DealGroup)groups.get(i);
        String groupId = dealGroup.getGroupId();
        String groupType = dealGroup.getGroupType();
        String es = dealGroup.getEntryState();
        if ((StringUtils.isNotBlank(es)) && (es.indexOf(entryState) == -1)) {
          continue;
        }
        log.info(userLoginName + " 同组操作id=" + groupId + ",type=" + groupType);
        users.addAll(getDealGroupUsers(dealGroup));
      }
    }
    return new ArrayList(users);
  }

  public void saveOrUpdate(DealGroup dealGroup) {
    if (StringUtils.isBlank(dealGroup.getId())) {
      dealGroup.setId(null);
      dealGroup.setCreateTime(TimeUtils.getCurrentTime());
    }
    this.dealGroupDao.saveOrUpdate(dealGroup);
  }

  public IDao<DealGroup> getDealGroupDao() {
    return this.dealGroupDao;
  }

  public void setDealGroupDao(IDao<DealGroup> dealGroupDao) {
    this.dealGroupDao = dealGroupDao;
  }

  public DepManagerService getDepManagerService() {
    return this.depManagerService;
  }

  public void setDepManagerService(DepManagerService depManagerService) {
    this.depManagerService = depManagerService;
  }

  public UserManagerService getUserManagerService() {
    return this.userManagerService;
  }

  public void setUserManagerService(UserManagerService userManagerService) {
    this.userManagerService = userManagerService;
  }

  public IRoleService getRoleService() {
    return this.roleService;
  }

  public void setRoleService(IRoleService roleService) {
    this.roleService = roleService;
  }
}
