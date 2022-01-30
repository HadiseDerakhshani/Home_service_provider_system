package ir.maktab.service;

import ir.maktab.data.entity.user.Manager;


public interface ManagerService {


    public Manager createManager(String userName, String pass);

    public void Save(Manager manager);

    public Manager checkManager(Manager manager);

    public void customerConfirmation();

    public void payment(int number, double amount, int score);
}
