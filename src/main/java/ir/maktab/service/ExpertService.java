package ir.maktab.service;

import ir.maktab.data.dto.ExpertDto;
import ir.maktab.data.dto.SubServiceDto;
import ir.maktab.data.dto.SuggestionDto;
import ir.maktab.data.model.enums.UserStatus;
import ir.maktab.data.model.serviceSystem.SubService;
import ir.maktab.data.model.user.Expert;

import java.util.List;


public interface ExpertService {


    public Expert save(Expert expert);

    public Expert createExpert(String name, String family, String email, String pass, String phone,
                               double credit, int score, String image);

    public Expert addPicture(Expert expert, String imageFileAddress);


    public void changePassword(ExpertDto expertDto, String newPass);

    public void changePhoneNumber(ExpertDto expertDto, String newPhoneNumber);

    public Expert findByEmail(String email);


    public void addSuggest(int number, SuggestionDto suggestionDto, ExpertDto expertDto);

    public List<SubService> addSubServiceExpert(ExpertDto expertDto, List<SubServiceDto> subServiceDtoList, int index);


    public void updateServiceList(List<SubService> list, ExpertDto expert);

    public void updateScore(int score, Expert expert);

    public void updateStatus(UserStatus status, Expert expert);

    public void updateCredit(double amount, Expert expert);

    public List<ExpertDto> findAll(int pageNumber, int pageSize);

    public long totalRecord();

    public void deleteExpert(String email);

}
