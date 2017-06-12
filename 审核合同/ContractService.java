package Service;

import java.util.ArrayList;
import java.util.List;

import Entity.CBEntity;
import Entity.ConProcess;
import Entity.ConState;
import Entity.Contract;
import Utils.AppException;
import Utils.Constant;
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
		
		//�ҳ�t_contract_process����Ҫ������δ��ɵĺ�ͬ
		List<Integer> processInfo = conProcessDao.getConIds(conProcess);
		
		//�ҳ�t_contract_state�з�������Ҫ��ĺ�ͬ
		for (int conId : processInfo) {
			if (conStateDao.isExist(conId, Constant.STATE_FINALIZED)) {
				aCConIds.add(conId);
			}
		}
		
		//��ȡt_contract����Ҫ��˺�ͬ����Ϣ
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
		
		//������Ҫ�����ĺ�ͬlist
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
}
