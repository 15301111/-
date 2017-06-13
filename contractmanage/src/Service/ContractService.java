package Service;

import java.util.ArrayList;
import java.util.List;

import Entity.CBEntity;
import Entity.CBEntity;
import Entity.ConProcess;
import Entity.ConState;
import Entity.ConState;
import Entity.Contract;
import Utils.AppException;
import Utils.Constant;
import Utils.DBUtil;
import dao.ConProcessDao;
import dao.ConStateDao;
import dao.ContractDao;

public class ContractService {
	
	ConProcess conProcess = new ConProcess();
	ConProcessDao conProcessDao = new ConProcessDao();
	ConStateDao conStateDao = new ConStateDao();
	ContractDao contractDao = new ContractDao();
	
	public List<CBEntity> getACList(int userId) throws AppException {
		
		List<CBEntity> aCConList = new ArrayList<CBEntity>();
		List<Integer> aCConIds = new ArrayList<Integer>();
		
		conProcess.setUserId(userId);
		conProcess.setType(Constant.PROCESS_APPROVE);
		conProcess.setState(Constant.UNDONE);
		
		//找出t_contract_process中需要审批且未完成的合同
		List<Integer> processInfo = conProcessDao.getConIds(conProcess);
		
		//找出t_contract_state中符合审批要求的合同
		for (int conId : processInfo) {
			if (conStateDao.isExist(conId, Constant.STATE_FINALIZED)) {
				aCConIds.add(conId);
			}
		}
		
		//获取t_contract中需要审核合同的信息
		for (int conId : aCConIds) {
			Contract contract = contractDao.getById(conId);
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
		
		//返回需要审批的合同list
		return aCConList;
	}
	
	public List<CBEntity> getApprovedList(int userId) throws AppException {
		
		List<CBEntity> aCConList = new ArrayList<CBEntity>();
		List<Integer> aCConIds = new ArrayList<Integer>();
		
		conProcess.setUserId(userId);
		conProcess.setType(Constant.PROCESS_APPROVE);
		conProcess.setState(Constant.DONE);
		
		//找出t_contract_process中需要审批且未完成的合同
		List<Integer> processInfo = conProcessDao.getConIds(conProcess);
		
		//找出t_contract_state中符合审批要求的合同
		for (int conId : processInfo) {
			if (conStateDao.isExist(conId, Constant.STATE_FINALIZED)) {
				aCConIds.add(conId);
			}
		}
		
		//获取t_contract中需要审核合同的信息
		for (int conId : aCConIds) {
			Contract contract = contractDao.getById(conId);
			//ConState conState = conStateDao.getConState(conId, Constant.STATE_DRAFTED);
			CBEntity cBEntity = new CBEntity();
			if (contract != null) {
				cBEntity.setConId(contract.getId());
				cBEntity.setConName(contract.getName());
			}
			aCConList.add(cBEntity);
		}
		
		//返回需要审批的合同list
		return aCConList;
	}
	
	public Contract getContract(int id) throws AppException {
		Contract contract = null;
		
		try {
			contract = contractDao.getById(id);
		} catch (AppException e) {
			e.printStackTrace();
			throw new AppException(
					"ContractService.getContract");
		}
		return contract;
	}
	
	public boolean approving(ConProcess conProcess) throws AppException {
		boolean flag = false;
		
		conProcess.setType(Constant.PROCESS_APPROVE);

		try {
			if (conProcessDao.update(conProcess)) {
				conProcess.setState(Constant.UNDONE);
				int tbApprovedCount = conProcessDao.getTotalCount(conProcess);
				
				conProcess.setState(Constant.VETOED);
				int refusedCount = conProcessDao.getTotalCount(conProcess);

				if (tbApprovedCount == 0 && refusedCount == 0) {
					ConState conState = new ConState();
					conState.setConId(conProcess.getConId());
					flag = conStateDao.add(conState);
				}
			}
		} catch (AppException e) {
			e.printStackTrace();
			throw new AppException(
					"ContractService.approve");
		}
		return flag;
	}
	
	public List<CBEntity> getSCList(int userId) throws AppException {
		List<CBEntity> SCconList = new ArrayList<CBEntity>();
		List<Integer> SCconIds = new ArrayList<Integer>();
		
		ConProcess conProcess = new ConProcess();
		conProcess.setUserId(userId);
		conProcess.setType(Constant.PROCESS_SIGN);
		conProcess.setState(Constant.UNDONE);
		
		try {
			List<Integer> processInfo = conProcessDao.getConIds(conProcess);
			
			for (int conId : processInfo) {
				if (conStateDao.isExist(conId, Constant.STATE_APPROVED)) {
					SCconIds.add(conId);
				}
			}
			
			for (int conId : SCconIds) {
				Contract contract = contractDao.getById(conId);
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
			throw new AppException(
					"ContractService.getSCList");
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
		
		try {
			List<Integer> processInfo = conProcessDao.getConIds(conProcess);
			
			for (int conId : processInfo) {
				if (conStateDao.isExist(conId, Constant.STATE_APPROVED)) {
					SCconIds.add(conId);
				}
			}
			
			for (int conId : SCconIds) {
				Contract contract = contractDao.getById(conId);
				//ConState conState = conStateDao.getConState(conId, Constant.STATE_DRAFTED);
				CBEntity conBusiModel = new CBEntity();
				if (contract != null) {
					conBusiModel.setConId(contract.getId());
					conBusiModel.setConName(contract.getName());
				}
				SCconList.add(conBusiModel);
			}
		} catch (AppException e) {
			e.printStackTrace();
			throw new AppException(
					"ContractService.getSignedList");
		}
		return SCconList;
	}
	
	public boolean sign(ConProcess conProcess) throws AppException {
		boolean flag = false;
		
		conProcess.setType(Constant.PROCESS_SIGN);
		conProcess.setState(Constant.DONE);
		
		try {
			if (conProcessDao.update(conProcess)) {
				ConState conState = new ConState();
				conState.setConId(conProcess.getConId());
				conState.setType(Constant.STATE_SIGNED);
				flag = conStateDao.add(conState);
			}
		} catch (AppException e) {
			e.printStackTrace();
			throw new AppException(
					"ContractService.sign");
		}
		return flag;
	}
public boolean Draft(Contract contract){
		
		boolean flag = false;
		DBUtil util =new DBUtil();
		ConState constate = new ConState();
		if(util.AddContract(contract)){
			constate.setId(contract.getId());
			constate.setType(Constant.STATE_DRAFTED);
			
			flag = util.AddConstate(constate);
			
		}
		
		return flag;
		
	}

public boolean distribute(int conId, int userID, int type) {
	boolean flag = false;
	DBUtil util = new DBUtil();

	ConProcess conProcess = new ConProcess();
	// Assign value to contract process object
	conProcess.setConId(conId);
	conProcess.setType(type);
	// Set status to "UNDONE"
	conProcess.setState(Constant.UNDONE);
	conProcess.setUserId(userID);
	// Save contract information,return operation result to flag
	flag = util.addProcess(conProcess);
return flag;
}

public List<CBEntity> getDraphtList(){
	
	DBUtil util = new DBUtil();
	
	List<CBEntity> contractlist = new ArrayList<CBEntity>();
	
	List<Integer> conIDs = util.getIdByType(Constant.STATE_DRAFTED);
	
	for(int conID : conIDs){
		
		if(!util.CheckconById(conID)){
			Contract contract = util.getContract(conID);
			
			ConState constate = util.getConstate(conID);
			
			CBEntity conbusi = new CBEntity();
			if(contract != null){
				conbusi.setConId(contract.getId());
				conbusi.setConName(contract.getName());
			}
			
			if(constate != null){
				conbusi.setDrafTime(constate.getTime());
			}
			
			contractlist.add(conbusi);
		}
	}
	
	return contractlist;
	
}
}
