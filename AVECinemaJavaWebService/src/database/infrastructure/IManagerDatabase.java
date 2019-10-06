package database.infrastructure;


import java.util.ArrayList;

import model.Manager;
import model.Voucher;

public interface IManagerDatabase {
	
	public Manager logInManager(String emailManager, String password);
	public Manager getManager (String email);
	public boolean updateManagerInfo(Manager manager);
	public boolean createVoucher(Voucher voucher);
	public ArrayList<Voucher> getAllVouchers();
	

}
