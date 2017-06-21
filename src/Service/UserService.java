package Service;

import java.util.ArrayList;
import java.util.List;

import Entity.PermissionBusiModel;
import Entity.Right;
import Entity.Role;
import Entity.User;
import Utils.AppException;
import dao.RightDao;
import dao.RoleDao;
import dao.UserDao;

public class UserService {
	RightDao rightDao = new RightDao();
	RoleDao roleDao = new RoleDao();
	private UserDao dao = new UserDao();
	public boolean register(String name, String password) {

		boolean registflag = false;
		boolean existflag = false;

		existflag = dao.Check(name);
		registflag = dao.insertUser(name, password);

		return registflag && (!existflag);
	}

	public boolean login(String name, String password) {
		boolean exist = false;
		boolean match = false;
		boolean loginflag = false;

		System.out.println("this is my test");
		exist = dao.Check(name);
		if (!exist) {
			System.out.println("数据库中不存在" + name);
		}
		match = dao.Login(name, password);

		if (exist && match) {
			loginflag = true;
			System.out.println("登录成功");

		}
		return loginflag;
	}

	public List<User> getUserListByRole(String role) {
		List<User> userList = new ArrayList<User>();
		// Declare userIds
		List<Integer> userIds = null;

		/*
		 * 1.Get designated user's userIds
		 */
		userIds = dao.getUserIDsByRole(role);

		/*
		 * 2.Get user information list according to userIds
		 */
		for (int userId : userIds) {
			// Get user's information
			User user = dao.getByID(userId);
			if (user != null) {
				userList.add(user);
			}
		}

		// Return userList
		return userList;
	}

	public int getUserID(String name) {
		return dao.getUserID(name);
	}

	public String getRolenifo(int userID) {
		return dao.getRole(userID);
	}
	
	public List<PermissionBusiModel> getYhqxList() throws AppException {
		// Initialize permissionList
		List<PermissionBusiModel> permissionList = new ArrayList<PermissionBusiModel>();
		// Declare userIds
		List<Integer> userIds = null; 
		
		try {
			/*
			 * 1.Get user id set
			 */
			userIds = dao.getIds();
			
			/*
			 * 2.Get user permission information: user information and corresponding role information
			 */
			for (int userId : userIds) {
			
				// Initialize business entity class object
				PermissionBusiModel permission = new PermissionBusiModel();
				
				User user = dao.getByID(userId); // Get user information according to user id
				int roleId = -1;
				roleId = rightDao.getRoleIdByUserId(userId); // Get role id according to user id
				
				if (roleId > 0) {
					Role role = roleDao.getById(roleId); // Get role information according to role id
					// Save role information to permission
					permission.setRoleId(roleId);
					permission.setRoleName(role.getName());
				}
				
				// Save user information to permission
				permission.setUserId(userId);
				permission.setUserName(user.getName());
				
				permissionList.add(permission);
			}
			
		} catch (AppException e) {
			e.printStackTrace();
			throw new AppException("com.ruanko.service.UserService.getYhqxList");
		}	
		// Permission business entity set
		return permissionList;
	}
	
	public boolean assignPermission(Right right) throws AppException {
		boolean flag = false;// Define flag
		try {
			//  Get user's role 
			int roleId = -1; // Initialize roleId
			// Get user's role id
			roleId = rightDao.getRoleIdByUserId(right.getUserId());
			// Declare role object
			Role role = null;
			if (roleId > 0) {
				// Get role information
				role = roleDao.getById(roleId);
			}
		
			/*
			 * Judgement role of user owned before,if user has a role before,so change the role,otherwise add a new role
			 */
			if (role != null) {
				// Get user's permission
				int rightId = rightDao.getIdByUserId(right.getUserId());
				// Set permission id to right object
				right.setId(rightId);
				right.setDescription("update");
				// Update permission information
				flag = rightDao.updateById(right);
			} else {
				flag = rightDao.add(right);
			}
			
		} catch (AppException e) {
			e.printStackTrace();
			throw new AppException(
					"com.ruanko.service.UserService.assignPermission");
		}
		return flag;
	}
	
	public List<Role> getRoleList() throws AppException {	
		// Initialize role set
		List<Role> roleList = new ArrayList<Role>();
		
		try {
			// Get all role object set
			roleList = roleDao.getAll();
			
		} catch (AppException e) {
			e.printStackTrace();
			throw new AppException("com.ruanko.service.UserService.getRoleList");
		}
		return roleList;
	}
	
	public boolean changePersoninfo(int userid, String password1, String password2) {
		boolean flag = false;
		if ( password1.equals(password2)) {
			flag = dao.updateNewpassword(userid, password1);
		} else {
			System.out.println("两次密码不一致");
		}
		return flag;
	}
	
	public User getUserByid(Integer userId) {
		User user = new User();
		user = dao.getUserByid(userId);
		return user;
	}


}
