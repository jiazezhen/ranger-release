/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.xasecure.biz;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.ws.rs.WebApplicationException;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.xasecure.common.ContextUtil;
import com.xasecure.common.MessageEnums;
import com.xasecure.common.RESTErrorUtil;
import com.xasecure.common.SearchCriteria;
import com.xasecure.common.StringUtil;
import com.xasecure.common.UserSessionBase;
import com.xasecure.db.XADaoManager;
import com.xasecure.db.XXPortalUserDao;
import com.xasecure.db.XXPortalUserRoleDao;
import com.xasecure.entity.XXPortalUser;
import com.xasecure.entity.XXPortalUserRole;
import com.xasecure.security.context.XAContextHolder;
import com.xasecure.security.context.XASecurityContext;
import com.xasecure.view.VXPasswordChange;
import com.xasecure.view.VXPortalUser;
import com.xasecure.view.VXPortalUserList;
import com.xasecure.view.VXResponse;
import com.xasecure.view.VXString;

@RunWith(MockitoJUnitRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestUserMgr {

	private static Long userId = 1L;

	@InjectMocks
	UserMgr userMgr = new UserMgr();

	@Mock
	VXPortalUser VXPortalUser;

	@Mock
	XADaoManager daoManager;

	@Mock
	RESTErrorUtil restErrorUtil;

	@Mock
	ContextUtil contextUtil;

	@Mock
	StringUtil stringUtil;

	@Mock
	XABizUtil msBizUtil;

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	public void setup() {
		XASecurityContext context = new XASecurityContext();
		context.setUserSession(new UserSessionBase());
		XAContextHolder.setSecurityContext(context);
		UserSessionBase currentUserSession = ContextUtil
				.getCurrentUserSession();
		currentUserSession.setUserAdmin(true);
	}

	private VXPortalUser userProfile() {
		VXPortalUser userProfile = new VXPortalUser();
		userProfile.setEmailAddress("test@test.com");
		userProfile.setFirstName("user12");
		userProfile.setLastName("test12");
		userProfile.setLoginId("134");
		userProfile.setPassword("usertest12323");
		userProfile.setUserSource(123);
		userProfile.setPublicScreenName("user");
		userProfile.setId(userId);
		return userProfile;
	}

	@Test
	public void test11CreateUser() {
		setup();
		XXPortalUserDao userDao = Mockito.mock(XXPortalUserDao.class);
		XXPortalUserRoleDao roleDao = Mockito.mock(XXPortalUserRoleDao.class);

		VXPortalUser userProfile = userProfile();

		Collection<String> userRoleList = new ArrayList<String>();
		userRoleList.add("ROLE_USER");

		XXPortalUser user = new XXPortalUser();
		user.setEmailAddress(userProfile.getEmailAddress());
		user.setFirstName(userProfile.getFirstName());
		user.setLastName(userProfile.getLastName());
		user.setLoginId(userProfile.getLoginId());
		user.setPassword(userProfile.getPassword());
		user.setUserSource(userProfile.getUserSource());
		user.setPublicScreenName(userProfile.getPublicScreenName());
		user.setId(userProfile.getId());

		XXPortalUserRole XXPortalUserRole = new XXPortalUserRole();
		XXPortalUserRole.setId(user.getId());
		XXPortalUserRole.setUserRole("ROLE_USER");
		List<XXPortalUserRole> list = new ArrayList<XXPortalUserRole>();
		list.add(XXPortalUserRole);

		Mockito.when(daoManager.getXXPortalUser()).thenReturn(userDao);
		Mockito.when(userDao.create((XXPortalUser) Mockito.anyObject()))
				.thenReturn(user);
		Mockito.when(daoManager.getXXPortalUserRole()).thenReturn(roleDao);
		Mockito.when(roleDao.findByUserId(userId)).thenReturn(list);

		XXPortalUser dbxxPortalUser = userMgr.createUser(userProfile, 1,
				userRoleList);
		Assert.assertNotNull(dbxxPortalUser);
		userId = dbxxPortalUser.getId();
		Assert.assertEquals(userId, dbxxPortalUser.getId());
		Assert.assertEquals(userProfile.getFirstName(), dbxxPortalUser.getFirstName());		
		Assert.assertEquals(userProfile.getFirstName(), dbxxPortalUser.getFirstName());
		Assert.assertEquals(userProfile.getLastName(), dbxxPortalUser.getLastName());
		Assert.assertEquals(userProfile.getLoginId(), dbxxPortalUser.getLoginId());
		Assert.assertEquals(userProfile.getEmailAddress(), dbxxPortalUser.getEmailAddress());
		Assert.assertEquals(userProfile.getPassword(), dbxxPortalUser.getPassword());
		
		Mockito.verify(daoManager).getXXPortalUser();
		Mockito.verify(daoManager).getXXPortalUserRole();
	}
	

	@Test
	public void test12CreateUser() {
		setup();
		XXPortalUserDao userDao = Mockito.mock(XXPortalUserDao.class);
		XXPortalUserRoleDao roleDao = Mockito.mock(XXPortalUserRoleDao.class);

		VXPortalUser userProfile = userProfile();

		XXPortalUser user = new XXPortalUser();
		user.setEmailAddress(userProfile.getEmailAddress());
		user.setFirstName(userProfile.getFirstName());
		user.setLastName(userProfile.getLastName());
		user.setLoginId(userProfile.getLoginId());
		user.setPassword(userProfile.getPassword());
		user.setUserSource(userProfile.getUserSource());
		user.setPublicScreenName(userProfile.getPublicScreenName());
		user.setId(userProfile.getId());

		XXPortalUserRole XXPortalUserRole = new XXPortalUserRole();
		XXPortalUserRole.setId(user.getId());
		XXPortalUserRole.setUserRole("ROLE_USER");
		List<XXPortalUserRole> list = new ArrayList<XXPortalUserRole>();
		list.add(XXPortalUserRole);

		Mockito.when(daoManager.getXXPortalUser()).thenReturn(userDao);
		Mockito.when(userDao.create((XXPortalUser) Mockito.anyObject()))
				.thenReturn(user);
		Mockito.when(daoManager.getXXPortalUserRole()).thenReturn(roleDao);
		Mockito.when(roleDao.findByUserId(userId)).thenReturn(list);

		XXPortalUser dbxxPortalUser = userMgr.createUser(userProfile, 1);
		userId = dbxxPortalUser.getId();
		
		Assert.assertNotNull(dbxxPortalUser);
		Assert.assertEquals(userId, dbxxPortalUser.getId());
		Assert.assertEquals(userProfile.getFirstName(), dbxxPortalUser.getFirstName());		
		Assert.assertEquals(userProfile.getFirstName(), dbxxPortalUser.getFirstName());
		Assert.assertEquals(userProfile.getLastName(), dbxxPortalUser.getLastName());
		Assert.assertEquals(userProfile.getLoginId(), dbxxPortalUser.getLoginId());
		Assert.assertEquals(userProfile.getEmailAddress(), dbxxPortalUser.getEmailAddress());
		Assert.assertEquals(userProfile.getPassword(), dbxxPortalUser.getPassword());

		Mockito.verify(daoManager).getXXPortalUser();
		Mockito.verify(daoManager).getXXPortalUserRole();
	}

	@Test
	public void test15ChangePassword() {
		setup();
		XXPortalUserDao userDao = Mockito.mock(XXPortalUserDao.class);
		VXPortalUser userProfile = userProfile();

		VXPasswordChange pwdChange = new VXPasswordChange();
		pwdChange.setId(userProfile.getId());
		pwdChange.setLoginId(userProfile.getLoginId());
		pwdChange.setOldPassword(userProfile.getPassword());
		pwdChange.setEmailAddress(userProfile.getEmailAddress());
		pwdChange.setUpdPassword(userProfile.getPassword());

		XXPortalUser user = new XXPortalUser();

		Mockito.when(daoManager.getXXPortalUser()).thenReturn(userDao);
		Mockito.when(userDao.findByLoginId(Mockito.anyString())).thenReturn(
				user);
		Mockito.when(
				stringUtil.equals(Mockito.anyString(), Mockito.anyString()))
				.thenReturn(true);

		Mockito.when(daoManager.getXXPortalUser()).thenReturn(userDao);
		Mockito.when(userDao.getById(Mockito.anyLong())).thenReturn(user);
		Mockito.when(
				stringUtil.validatePassword(Mockito.anyString(),
						new String[] { Mockito.anyString() })).thenReturn(true);
		
		VXResponse dbVXResponse = userMgr.changePassword(pwdChange);
		Assert.assertNotNull(dbVXResponse);		
		Assert.assertEquals(userProfile.getStatus(), dbVXResponse.getStatusCode());		
		
		Mockito.verify(stringUtil).equals(Mockito.anyString(),Mockito.anyString());
		Mockito.verify(stringUtil).validatePassword(Mockito.anyString(),
				new String[] { Mockito.anyString() });
	}

	@Test
	public void test16GetEmailAddress() {
		setup();
		VXPortalUser userProfile = userProfile();

		XXPortalUser user = new XXPortalUser();
		user.setEmailAddress(userProfile.getEmailAddress());
		user.setFirstName(userProfile.getFirstName());
		user.setLastName(userProfile.getLastName());
		user.setLoginId(userProfile.getLoginId());
		user.setPassword(userProfile.getPassword());
		user.setUserSource(userProfile.getUserSource());
		user.setPublicScreenName(userProfile.getPublicScreenName());
		user.setId(userProfile.getId());

		VXPasswordChange changeEmail = new VXPasswordChange();
		changeEmail.setEmailAddress(user.getEmailAddress());
		changeEmail.setId(user.getId());
		changeEmail.setLoginId(user.getLoginId());

		Mockito.when(
				restErrorUtil.createRESTException(
						"serverMsg.userMgrEmailChange",
						MessageEnums.OPER_NO_PERMISSION, null, null, ""
								+ changeEmail)).thenThrow(
				new WebApplicationException());
		thrown.expect(WebApplicationException.class);
		
		VXPortalUser dbVXPortalUser = userMgr.changeEmailAddress(user,changeEmail);
		Assert.assertNotNull(dbVXPortalUser);
		Assert.assertEquals(userId, dbVXPortalUser.getId());		
		Assert.assertEquals(userProfile.getLastName(), dbVXPortalUser.getLastName());
		Assert.assertEquals(changeEmail.getLoginId(), dbVXPortalUser.getLoginId());
		Assert.assertEquals(changeEmail.getEmailAddress(), dbVXPortalUser.getEmailAddress());
		
		Mockito.verify(restErrorUtil).createRESTException("serverMsg.userMgrEmailChange",
				MessageEnums.OPER_NO_PERMISSION, null, null, ""
						+ changeEmail);
	}

	@Test
	public void test17ValidateEmailAddress() {
		setup();
		VXPortalUser userProfile = userProfile();

		XXPortalUser user = new XXPortalUser();
		user.setFirstName(userProfile.getFirstName());
		user.setLastName(userProfile.getLastName());
		user.setLoginId(userProfile.getLoginId());
		user.setPassword(userProfile.getPassword());
		user.setUserSource(userProfile.getUserSource());
		user.setPublicScreenName(userProfile.getPublicScreenName());
		user.setId(userProfile.getId());

		VXPasswordChange changeEmail = new VXPasswordChange();
		changeEmail.setId(user.getId());
		changeEmail.setLoginId(user.getLoginId());

		Mockito.when(
				restErrorUtil.createRESTException(
						"serverMsg.userMgrInvalidEmail",
						MessageEnums.INVALID_INPUT_DATA, changeEmail.getId(),
						"emailAddress", changeEmail.toString())).thenThrow(
				new WebApplicationException());
		thrown.expect(WebApplicationException.class);
		
		VXPortalUser dbVXPortalUser = userMgr.changeEmailAddress(user,
				changeEmail);
		Assert.assertNotNull(dbVXPortalUser);
		Assert.assertEquals(userId, dbVXPortalUser.getId());		
		Assert.assertEquals(userProfile.getLastName(), dbVXPortalUser.getLastName());
		Assert.assertEquals(changeEmail.getLoginId(), dbVXPortalUser.getLoginId());
		Assert.assertEquals(changeEmail.getEmailAddress(), dbVXPortalUser.getEmailAddress());
		
		Mockito.verify(restErrorUtil).createRESTException("serverMsg.userMgrInvalidEmail",
				MessageEnums.INVALID_INPUT_DATA, changeEmail.getId(),
				"emailAddress", changeEmail.toString());
	}

	@Test
	public void test21CreateUser() {
		setup();
		XXPortalUserDao userDao = Mockito.mock(XXPortalUserDao.class);
		XXPortalUserRoleDao roleDao = Mockito.mock(XXPortalUserRoleDao.class);

		XXPortalUser user = new XXPortalUser();
		VXPortalUser userProfile = userProfile();

		XXPortalUserRole XXPortalUserRole = new XXPortalUserRole();
		XXPortalUserRole.setId(userId);
		XXPortalUserRole.setUserRole("ROLE_USER");
		List<XXPortalUserRole> list = new ArrayList<XXPortalUserRole>();
		list.add(XXPortalUserRole);

		Mockito.when(daoManager.getXXPortalUser()).thenReturn(userDao);
		Mockito.when(userDao.create((XXPortalUser) Mockito.anyObject()))
				.thenReturn(user);
		Mockito.when(daoManager.getXXPortalUserRole()).thenReturn(roleDao);
		Mockito.when(roleDao.findByUserId(Mockito.anyLong())).thenReturn(list);
		
		VXPortalUser dbVXPortalUser = userMgr.createUser(userProfile);
		Assert.assertNotNull(dbVXPortalUser);
		Assert.assertEquals(user.getId(), dbVXPortalUser.getId());
		Assert.assertEquals(user.getFirstName(), dbVXPortalUser.getFirstName());		
		Assert.assertEquals(user.getFirstName(), dbVXPortalUser.getFirstName());
		Assert.assertEquals(user.getLastName(), dbVXPortalUser.getLastName());
		Assert.assertEquals(user.getLoginId(), dbVXPortalUser.getLoginId());
		Assert.assertEquals(user.getEmailAddress(), dbVXPortalUser.getEmailAddress());
		Assert.assertEquals(user.getPassword(), dbVXPortalUser.getPassword());
		
		Mockito.verify(daoManager).getXXPortalUser();
	}

	@Test
	public void test22CreateDefaultAccountUser() {
		setup();
		XXPortalUserDao userDao = Mockito.mock(XXPortalUserDao.class);
		XXPortalUserRoleDao roleDao = Mockito.mock(XXPortalUserRoleDao.class);
		VXPortalUser userProfile = userProfile();
		XXPortalUser user = new XXPortalUser();

		XXPortalUserRole XXPortalUserRole = new XXPortalUserRole();
		XXPortalUserRole.setId(userId);
		XXPortalUserRole.setUserRole("ROLE_USER");

		List<XXPortalUserRole> list = new ArrayList<XXPortalUserRole>();
		list.add(XXPortalUserRole);

		Mockito.when(daoManager.getXXPortalUser()).thenReturn(userDao);
		Mockito.when(userDao.findByLoginId(Mockito.anyString())).thenReturn(
				user);
		Mockito.when(daoManager.getXXPortalUserRole()).thenReturn(roleDao);
		Mockito.when(roleDao.findByParentId(Mockito.anyLong()))
				.thenReturn(list);

		VXPortalUser dbVXPortalUser = userMgr
				.createDefaultAccountUser(userProfile);
		Assert.assertNotNull(dbVXPortalUser);
		Assert.assertEquals(user.getId(), dbVXPortalUser.getId());
		Assert.assertEquals(user.getFirstName(), dbVXPortalUser.getFirstName());		
		Assert.assertEquals(user.getFirstName(), dbVXPortalUser.getFirstName());
		Assert.assertEquals(user.getLastName(), dbVXPortalUser.getLastName());
		Assert.assertEquals(user.getLoginId(), dbVXPortalUser.getLoginId());
		Assert.assertEquals(user.getEmailAddress(), dbVXPortalUser.getEmailAddress());
		Assert.assertEquals(user.getPassword(), dbVXPortalUser.getPassword());
		
		Mockito.verify(daoManager).getXXPortalUser();
		Mockito.verify(daoManager).getXXPortalUserRole();
	}

	@Test
	public void test23IsUserInRole() {
		XXPortalUserRoleDao roleDao = Mockito.mock(XXPortalUserRoleDao.class);

		XXPortalUserRole XXPortalUserRole = new XXPortalUserRole();
		XXPortalUserRole.setId(userId);
		XXPortalUserRole.setUserRole("ROLE_USER");

		List<XXPortalUserRole> list = new ArrayList<XXPortalUserRole>();
		list.add(XXPortalUserRole);

		Mockito.when(daoManager.getXXPortalUserRole()).thenReturn(roleDao);
		Mockito.when(roleDao.findByRoleUserId(userId, "ROLE_USER")).thenReturn(
				XXPortalUserRole);

		boolean isValue = userMgr.isUserInRole(userId, "ROLE_USER");
		Assert.assertTrue(isValue);
		
		Mockito.verify(daoManager).getXXPortalUserRole();
	}

	@Test
	public void test24UpdateUserWithPass() {
		setup();
		XXPortalUserDao userDao = Mockito.mock(XXPortalUserDao.class);

		VXPortalUser userProfile = userProfile();
		XXPortalUser user = new XXPortalUser();

		Mockito.when(daoManager.getXXPortalUser()).thenReturn(userDao);
		Mockito.when(userDao.getById(userProfile.getId())).thenReturn(user);

		Mockito.when(
				restErrorUtil.createRESTException(
						"Please provide valid email address.",
						MessageEnums.INVALID_INPUT_DATA)).thenThrow(
				new WebApplicationException());
		thrown.expect(WebApplicationException.class);
		
		XXPortalUser dbXXPortalUser = userMgr.updateUserWithPass(userProfile);
		Assert.assertNotNull(dbXXPortalUser);
		Assert.assertEquals(userId, dbXXPortalUser.getId());
		Assert.assertEquals(userProfile.getFirstName(), dbXXPortalUser.getFirstName());		
		Assert.assertEquals(userProfile.getFirstName(), dbXXPortalUser.getFirstName());
		Assert.assertEquals(userProfile.getLastName(), dbXXPortalUser.getLastName());
		Assert.assertEquals(userProfile.getLoginId(), dbXXPortalUser.getLoginId());
		Assert.assertEquals(userProfile.getEmailAddress(), dbXXPortalUser.getEmailAddress());
		Assert.assertEquals(userProfile.getPassword(), dbXXPortalUser.getPassword());
		
		Mockito.verify(restErrorUtil).createRESTException("Please provide valid email address.",
				MessageEnums.INVALID_INPUT_DATA);
	}

	@Test
	public void test25searchUsers() {
		Query query = Mockito.mock(Query.class);
		EntityManager entityManager = Mockito.mock(EntityManager.class);
		SearchCriteria searchCriteria = new SearchCriteria();
		searchCriteria.setDistinct(true);
		searchCriteria.setGetChildren(true);
		searchCriteria.setGetCount(true);
		searchCriteria.setMaxRows(12);
		searchCriteria.setOwnerId(userId);
		searchCriteria.setStartIndex(1);
		searchCriteria.setSortBy("asc");

		Mockito.when(daoManager.getEntityManager()).thenReturn(entityManager);
		Mockito.when(entityManager.createQuery(Mockito.anyString()))
				.thenReturn(query);
		
		VXPortalUserList dbVXPortalUserList = userMgr
				.searchUsers(searchCriteria);

		Assert.assertNotNull(dbVXPortalUserList);
		
		Mockito.verify(daoManager).getEntityManager();
	}

	@Test
	public void test26FindByEmailAddress() {
		XXPortalUserDao userDao = Mockito.mock(XXPortalUserDao.class);

		XXPortalUser user = new XXPortalUser();

		String emailId = "jeet786sonkar@gmail.com";
		Mockito.when(daoManager.getXXPortalUser()).thenReturn(userDao);
		Mockito.when(userDao.findByEmailAddress(emailId)).thenReturn(user);

		XXPortalUser dbXXPortalUser = userMgr.findByEmailAddress(emailId);
		Assert.assertNotNull(dbXXPortalUser);
		Assert.assertNotEquals(emailId, dbXXPortalUser.getEmailAddress());
		
		Mockito.verify(daoManager).getXXPortalUser();
	}

	@Test
	public void test27GetRolesForUser() {
		XXPortalUserRoleDao roleDao = Mockito.mock(XXPortalUserRoleDao.class);
		VXPortalUser userProfile = userProfile();

		XXPortalUser user = new XXPortalUser();
		user.setEmailAddress(userProfile.getEmailAddress());
		user.setFirstName(userProfile.getFirstName());
		user.setLastName(userProfile.getLastName());
		user.setLoginId(userProfile.getLoginId());
		user.setPassword(userProfile.getPassword());
		user.setUserSource(userProfile.getUserSource());
		user.setPublicScreenName(userProfile.getPublicScreenName());
		user.setId(userProfile.getId());

		XXPortalUserRole XXPortalUserRole = new XXPortalUserRole();
		XXPortalUserRole.setId(user.getId());
		XXPortalUserRole.setUserRole("ROLE_USER");
		List<XXPortalUserRole> list = new ArrayList<XXPortalUserRole>();
		list.add(XXPortalUserRole);

		Mockito.when(daoManager.getXXPortalUserRole()).thenReturn(roleDao);
		Mockito.when(roleDao.findByUserId(userId)).thenReturn(list);
		
		Collection<String> stringReturn = userMgr.getRolesForUser(user);
		Assert.assertNotNull(stringReturn);
		
		Mockito.verify(daoManager).getXXPortalUserRole();
	}

	@Test
	public void test28DeleteUserRole() {
		setup();
		XXPortalUserRoleDao roleDao = Mockito.mock(XXPortalUserRoleDao.class);

		XXPortalUserRole XXPortalUserRole = new XXPortalUserRole();
		String userRole = "ROLE_USER";
		XXPortalUser user = new XXPortalUser();
		XXPortalUserRole.setId(user.getId());
		XXPortalUserRole.setUserRole("ROLE_USER");
		List<XXPortalUserRole> list = new ArrayList<XXPortalUserRole>();
		list.add(XXPortalUserRole);

		Mockito.when(daoManager.getXXPortalUserRole()).thenReturn(roleDao);
		Mockito.when(roleDao.findByUserId(userId)).thenReturn(list);
		
		boolean deleteValue = userMgr.deleteUserRole(userId, userRole);
		Assert.assertTrue(deleteValue);
	}

	@Test
	public void test29DeactivateUser() {
		setup();
		XXPortalUserDao userDao = Mockito.mock(XXPortalUserDao.class);
		XXPortalUserRoleDao roleDao = Mockito.mock(XXPortalUserRoleDao.class);
		VXPortalUser userProfile = userProfile();

		XXPortalUser user = new XXPortalUser();
		user.setEmailAddress(userProfile.getEmailAddress());
		user.setFirstName(userProfile.getFirstName());
		user.setLastName(userProfile.getLastName());
		user.setLoginId(userProfile.getLoginId());
		user.setPassword(userProfile.getPassword());
		user.setUserSource(userProfile.getUserSource());
		user.setPublicScreenName(userProfile.getPublicScreenName());
		user.setId(userProfile.getId());

		XXPortalUserRole XXPortalUserRole = new XXPortalUserRole();
		XXPortalUserRole.setId(userId);
		XXPortalUserRole.setUserRole("ROLE_USER");

		List<XXPortalUserRole> list = new ArrayList<XXPortalUserRole>();
		list.add(XXPortalUserRole);
		Mockito.when(daoManager.getXXPortalUser()).thenReturn(userDao);
		Mockito.when(userDao.update(user)).thenReturn(user);

		Mockito.when(daoManager.getXXPortalUserRole()).thenReturn(roleDao);
		Mockito.when(roleDao.findByParentId(Mockito.anyLong()))
				.thenReturn(list);

		VXPortalUser dbVXPortalUser = userMgr.deactivateUser(user);
		Assert.assertNotNull(dbVXPortalUser);
		Assert.assertEquals(user.getId(), dbVXPortalUser.getId());
		Assert.assertEquals(user.getFirstName(), dbVXPortalUser.getFirstName());		
		Assert.assertEquals(user.getFirstName(), dbVXPortalUser.getFirstName());
		Assert.assertEquals(user.getLastName(), dbVXPortalUser.getLastName());
		Assert.assertEquals(user.getLoginId(), dbVXPortalUser.getLoginId());
		
		Mockito.verify(daoManager).getXXPortalUser();
	}
}