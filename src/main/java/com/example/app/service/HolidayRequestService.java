package com.example.app.service;

import com.example.app.model.Employee;
import com.example.app.model.HolidayRequest;
import com.example.app.repository.IHolidayRequestRepository;
import com.example.app.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class HolidayRequestService {

    @Autowired
    private IHolidayRequestRepository requestRepository;


    public boolean addOrUpdateRequest(HolidayRequest request){
        try{
            this.requestRepository.save(request);
            return true;
        }catch (Exception ex){
            return false;
        }
    }

    public List<HolidayRequest> getRequestsByEmployee(Employee employee){
        return requestRepository.findHolidayRequestByEmployee(employee);
    }

    public HolidayRequest getRequestById(long id){
        return requestRepository.findHolidayRequestByRequestId(id);
    }

    public void declineRequest(HolidayRequest request){
        request.setState(Utils.declinedState);
        requestRepository.save(request);
    }

    public void updateRequests(Employee employee, int nrOfDays){
        Set<HolidayRequest> requests = requestRepository.findHolidayRequestsByEmployee(employee);
        requests.forEach(req -> req.setRemainingDays(req.getRemainingDays() - nrOfDays));
        requests.forEach(req -> req.setState(req.getState().getStateId()==1? req.getRemainingDays()<0? Utils.declinedState : req.getState() : req.getState()));
        requestRepository.saveAll(requests);
    }

    public void deleteRequest(HolidayRequest request){
        requestRepository.delete(request);
    }
}
