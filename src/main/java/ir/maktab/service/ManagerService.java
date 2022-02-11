package ir.maktab.service;

import ir.maktab.data.entity.user.Manager;


public interface ManagerService {


    Manager createManager(String userName, String pass);

    void Save(Manager manager);

    Manager checkManager(Manager manager);

    void customerConfirmation();

    void payment(int number, double amount, int score);
}
