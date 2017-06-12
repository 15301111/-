package Service;

import java.util.ArrayList;
import java.util.List;

import Entity.ConBusiModel;
import Entity.ConProcess;
import Entity.Constant;
import Entity.Constate;
import Entity.Contract;
import Utils.DBUtil;

public class ContractService {

	public boolean Draft(Contract contract){
		
		boolean flag = false;
		DBUtil util =new DBUtil();
		Constate constate = new Constate();
		if(util.AddContract(contract)){
			constate.setID(contract.getId());
			constate.setType(Constant.STATE_DRAFTED);
			
			flag = util.AddConstate(constate);
			
		}
		
		return flag;
		
	}
	
	public List<ConBusiModel> getDraphtList(){
		
		DBUtil util = new DBUtil();
		
		List<ConBusiModel> contractlist = new ArrayList<ConBusiModel>();
		
		List<Integer> conIDs = util.getIdByType(Constant.STATE_DRAFTED);
		
		for(int conID : conIDs){
			
			if(!util.CheckconById(conID)){
				Contract contract = util.getContract(conID);
				
				Constate constate = util.getConstate(conID);
				
				ConBusiModel conbusi = new ConBusiModel();
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


}
