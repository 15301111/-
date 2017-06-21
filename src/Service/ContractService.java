package Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import Entity.CBEntity;
import Entity.ConProcess;
import Entity.ConState;
import Entity.Contract;
import Utils.AppException;
import Utils.Constant;
import dao.ConStateDao;
import dao.ContractDao;
import dao.ProcessDao;

public class ContractService {
	private ContractDao contractDao = new ContractDao();
	private ProcessDao processDao = new ProcessDao();
	ConProcess conProcess = new ConProcess();
	ContractDao contractdao = new ContractDao();
	private ConStateDao conStateDao = new ConStateDao();

	public List<CBEntity> getACList(int userId) throws AppException {

		List<CBEntity> aCConList = new ArrayList<CBEntity>();
		List<Integer> aCConIds = new ArrayList<Integer>();

		conProcess.setUserId(userId);
		conProcess.setType(Constant.PROCESS_APPROVE);
		conProcess.setState(Constant.UNDONE);

		//找出t_contract_process中需要审批且未完成的合同
		List<Integer> processInfo = processDao.getConIds(conProcess);

		// 找出t_contract_state中符合审批要求的合同
		for (int conId : processInfo) {
			if (conStateDao.isExist(conId, Constant.STATE_FINALIZED)) {
				aCConIds.add(conId);
			}
		}

		// 获取t_contract中需要审核合同的信息
		for (int conId : aCConIds) {
			Contract contract = contractDao.getContract(conId);
			ConState conState = conStateDao.getConState(conId, Constant.STATE_DRAFTED);
			CBEntity cBEntity = new CBEntity();
			if (contract != null) {
				cBEntity.setConId(contract.getId());
				cBEntity.setConName(contract.getName());
			}
			if (conState != null) {
				cBEntity.setDrafTime(conState.getTime());
			}
			aCConList.add(cBEntity);
		}

		// 返回需要审批的合同list
		return aCConList;
	}

	public List<CBEntity> getApprovedList(int userId) throws AppException {

		List<CBEntity> aCConList = new ArrayList<CBEntity>();
		List<Integer> aCConIds = new ArrayList<Integer>();

		conProcess.setUserId(userId);
		conProcess.setType(Constant.PROCESS_APPROVE);
		conProcess.setState(Constant.DONE);

		// 找出t_contract_process中需要审批且未完成的合同
		List<Integer> processInfo = processDao.getConIds(conProcess);

		// 找出t_contract_state中符合审批要求的合同
		for (int conId : processInfo) {
			if (conStateDao.isExist(conId, Constant.STATE_FINALIZED)) {
				aCConIds.add(conId);
			}
		}

		// 获取t_contract中需要审核合同的信息
		for (int conId : aCConIds) {
			Contract contract = contractDao.getContract(conId);
			// ConState conState = conStateDao.getConState(conId,
			// Constant.STATE_DRAFTED);
			CBEntity cBEntity = new CBEntity();
			if (contract != null) {
				cBEntity.setConId(contract.getId());
				cBEntity.setConName(contract.getName());
			}
			aCConList.add(cBEntity);
		}

		// 返回需要审批的合同list
		return aCConList;
	}

	public Contract getContract(int id) throws AppException {
		Contract contract = null;

		contract = contractDao.getContract(id);
		return contract;
	}

	public boolean approving(ConProcess conProcess) throws AppException {
		boolean flag = false;

		conProcess.setType(Constant.PROCESS_APPROVE);

		try {
			if (processDao.update(conProcess)) {
				conProcess.setState(Constant.UNDONE);
				int tbApprovedCount = processDao.getTotalCount(conProcess);

				conProcess.setState(Constant.VETOED);
				int refusedCount = processDao.getTotalCount(conProcess);

				if (tbApprovedCount == 0 && refusedCount == 0) {
					ConState conState = new ConState();
					conState.setConId(conProcess.getConId());
					flag = conStateDao.add(conState);
				}
			}
		} catch (AppException e) {
			e.printStackTrace();
			throw new AppException("ContractService.approve");
		}
		return flag;
	}

	public boolean breach(ConProcess conProcess) throws AppException {
		boolean flag = false;

		conProcess.setType(Constant.PROCESS_breach);

		processDao.updateType(conProcess);
		return true;
	}

	public List<CBEntity> getSCList(int userId) throws AppException {
		List<CBEntity> SCconList = new ArrayList<CBEntity>();
		List<Integer> SCconIds = new ArrayList<Integer>();

		ConProcess conProcess = new ConProcess();
		conProcess.setUserId(userId);
		conProcess.setType(Constant.PROCESS_SIGN);
		conProcess.setState(Constant.UNDONE);

		try {
			List<Integer> processInfo = processDao.getConIds(conProcess);

			for (int conId : processInfo) {
				if (conStateDao.isExist(conId, Constant.STATE_APPROVED)) {
					SCconIds.add(conId);
				}
			}

			for (int conId : SCconIds) {
				Contract contract = contractDao.getContract(conId);
				ConState conState = conStateDao.getConState(conId, Constant.STATE_DRAFTED);
				CBEntity conBusiModel = new CBEntity();
				if (contract != null) {
					conBusiModel.setConId(contract.getId());
					conBusiModel.setConName(contract.getName());
				}
				if (conState != null) {
					conBusiModel.setDrafTime(conState.getTime());
				}
				SCconList.add(conBusiModel);
			}
		} catch (AppException e) {
			e.printStackTrace();
			throw new AppException("ContractService.getSCList");
		}
		return SCconList;
	}

	public List<CBEntity> getSignedList(int userId) throws AppException {
		List<CBEntity> SCconList = new ArrayList<CBEntity>();
		List<Integer> SCconIds = new ArrayList<Integer>();

		ConProcess conProcess = new ConProcess();
		conProcess.setUserId(userId);
		conProcess.setType(Constant.PROCESS_SIGN);
		conProcess.setState(Constant.DONE);

		List<Integer> processInfo = processDao.getConIds(conProcess);

		for (int conId : processInfo) {
			if (conStateDao.isExist(conId, Constant.STATE_APPROVED)) {
				SCconIds.add(conId);
			}
		}
		
		for (int conId : SCconIds) {
			Contract contract = contractDao.getContract(conId);
			ConState conState = conStateDao.getConState(conId,Constant.STATE_DRAFTED);
			CBEntity conBusiModel = new CBEntity();
			if (contract != null) {
				conBusiModel.setConId(contract.getId());
				conBusiModel.setConName(contract.getName());
				conBusiModel.setbreach(contract.getBreach());
			}
			SCconList.add(conBusiModel);
		}
		return SCconList;
	}

	public boolean sign(ConProcess conProcess) throws AppException {
		boolean flag = false;

		conProcess.setType(Constant.PROCESS_SIGN);
		conProcess.setState(Constant.DONE);

		try {
			if (processDao.update(conProcess)) {
				ConState conState = new ConState();
				conState.setConId(conProcess.getConId());
				conState.setType(Constant.STATE_SIGNED);
				flag = conStateDao.add(conState);
			}
		} catch (AppException e) {
			e.printStackTrace();
			throw new AppException("ContractService.sign");
		}
		return flag;
	}

	public boolean Draft(Contract contract) {

		boolean flag = false;
		ConState constate = new ConState();
		if (contractDao.insertContract(contract)) {
			constate.setId(contract.getId());
			constate.setType(Constant.STATE_DRAFTED);
			try {
				flag = conStateDao.add(constate);
			} catch (AppException e) {
				e.printStackTrace();
			}
		}
		return flag;

	}

	public boolean distribute(int conId, int userID, int type) {
		boolean flag = false;

		ConProcess conProcess = new ConProcess();
		// Assign value to contract process object
		conProcess.setConId(conId);
		conProcess.setType(type);
		// Set status to "UNDONE"
		conProcess.setState(Constant.UNDONE);
		conProcess.setUserId(userID);
		// Save contract information,return operation result to flag
		flag = contractDao.insertProcess(conProcess);
		return flag;
	}

	/*public List<CBEntity> getDraphtList() {


		List<CBEntity> contractlist = new ArrayList<CBEntity>();

		List<Integer> conIDs = contractDao.getIdByType(Constant.STATE_DRAFTED);

		for (int conID : conIDs) {

			if (!contractDao.CheckConById(conID)) {
				Contract contract = contractDao.getContract(conID);

				ConState constate = conStateDao.getConState(conID, type);

				CBEntity conbusi = new CBEntity();
				if (contract != null) {
					conbusi.setConId(contract.getId());
					conbusi.setConName(contract.getName());
				}

				if (constate != null) {
					conbusi.setDrafTime(constate.getTime());
				}

				contractlist.add(conbusi);
			}
		}

		return contractlist;

	}*/

	public List<CBEntity> getDraphtList() {
		List<CBEntity> contractlist = new ArrayList<CBEntity>();

		List<Integer> conIDs = contractdao.getIdByType(Constant.STATE_DRAFTED);

		for (int conID : conIDs) {

			if (!contractdao.CheckConById(conID)) {
				Contract contract = contractdao.getContract(conID);

				ConState constate = contractdao.getAssignConstate(conID);

				CBEntity conbusi = new CBEntity();
				if (contract != null) {
					conbusi.setConId(contract.getId());
					conbusi.setConName(contract.getName());
				}

				if (constate != null) {
					conbusi.setDrafTime(constate.getTime());
				}

				contractlist.add(conbusi);
			}
		}

		return contractlist;

	}

	
	public List<Contract> getContractList(Integer userId,int state) {
		return contractDao.getContracts(userId);
	}

	public Contract getContract(String contractname) {
		return contractDao.getContract(contractname);
	}

	public void deleteContract(String contractname) {
		contractDao.deleteContract(contractname);
	}
	
	public boolean counterSign(ConProcess conProcess) throws AppException {
		boolean flag = false;// Define flag 
		
		// Set process's operation type to "PROCESS_CSIGN"
		conProcess.setType(Constant.PROCESS_CSIGN);
		// Set corresponding state of "PROCESS_CSIGN" type  is "DONE"
		conProcess.setState(Constant.DONE);
		
		try {
			if (processDao.update(conProcess)) { // Countersigning contract, entry countersigned information
				conProcess.setType(Constant.PROCESS_APPROVE);
				processDao.updateType(conProcess);
				/*
				 * 
				 * After countersign successful, statistics total number of persons to be countersigned, 
				 * if the total number is 0, then all people have completed countersign
				 * and set contract process state to "STATE_CSIGNED"
				 */
				// Pass parameters  through conProcess to statistics the number of persons to be countersigned,set state to "UNDONE"
				conProcess.setState(Constant.UNDONE);

				// Total number of persons to be countersigned
				int totalCount = processDao.getTotalCount(conProcess);
				
				// if the number of persons to be countersigned is 0, then all people have completed countersign
				if (totalCount == 0) {
					ConState conState = new ConState();
					conState.setConId(conProcess.getConId());
					// Set contract state to "STATE_CSIGNED"
					conState.setType(Constant.STATE_CSIGNED);
					// Save contract state information
					flag = conStateDao.add(conState);
				}
			}
		} catch (AppException e) {
			e.printStackTrace();
			throw new AppException(
					"ContractService.counterSign");
		}
		return flag;
	}
	
	public List<CBEntity> getDhqhtList(int userId) throws AppException {
		// Initialize  conList
		List<CBEntity> conList = new ArrayList<CBEntity>();
		ConProcess conProcess = new ConProcess();
		// Set values to contract process object
		conProcess.setUserId(userId);
		// Set process's operation type to "PROCESS_CSIGN"
		conProcess.setType(Constant.PROCESS_CSIGN);
		// Set corresponding state of "PROCESS_CSIGN" type  is "UNDONE"
		conProcess.setState(Constant.UNDONE);
		try {
			/*
			 * 1.Get contract id set that to be countersigned
			 */
			List<Integer> conIds = processDao.getConIds(conProcess);

			/* 
			 * 2.Get contract's information that to be countersigned,and save into contract business entity object,and put the entity class into conList
			 */
			for (int conId : conIds) {
				// Get contract's information that designated 
				Contract contract = contractDao.getById(conId);
				// Get status of designated contract
				ConState conState = conStateDao.getConState(conId, Constant.STATE_DRAFTED);
				// Initialize conBusiModel
				CBEntity conBusiModel = new CBEntity();
				if (contract != null) {
					// Set contract id and name into conBusiModel object
					conBusiModel.setConId(contract.getId());
					conBusiModel.setConName(contract.getName());
				}
				if (conState != null) {
					// Set Drafting time into conBusiModel object
					conBusiModel.setDrafTime(conState.getTime()); 
				}
				conList.add(conBusiModel);
			}
		} catch (AppException e) {
			e.printStackTrace();
			throw new AppException("ContractService.getDhqhtList");
		}
		// Return the set of storage contract business entities
		return conList;
	}
	
	public List<Contract> getallcontractList() {
		List<Contract> contractlist = new ArrayList<Contract>();
		contractlist = contractdao.getallContract();
		return contractlist;
		
	}
	
	public boolean updateEndTimeByname(String name, Date newtime) {
		boolean flag = false;
		flag = contractdao.updateEndTimeByname(name, newtime);
		return flag;
	}


}
