package com.example.app.repository;

import com.example.app.model.Employee;
import com.example.app.model.HolidayRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;

@Repository
public interface IHolidayRequestRepository extends JpaRepository<HolidayRequest, Integer> {

   List<HolidayRequest> findHolidayRequestByEmployee(Employee employee);
   HolidayRequest findHolidayRequestByRequestId(long id);
   Set<HolidayRequest> findHolidayRequestsByEmployee(Employee employee);
}
