package com.udacity.jdnd.course3.critter.repositories;

import com.udacity.jdnd.course3.critter.entities.Employee;
import com.udacity.jdnd.course3.critter.entities.Pet;
import com.udacity.jdnd.course3.critter.entities.Customer;
import com.udacity.jdnd.course3.critter.entities.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    @Query(value = "select * from schedule s inner join schedule_emp se on s.id = se.schedule_id where se.emp_id = :empId", nativeQuery = true)
    List<Schedule> getScheduleByEmployeeId(@Param("empId") Long empId);

    @Query(value = "select * from schedule s inner join schedule_pet sp on s.id = sp.schedule_id where sp.pet_id = :petId", nativeQuery = true)
    List<Schedule> getScheduleByPetId(@Param("petId") Long petId);

    @Query(value = "select * from schedule s inner join schedule_pet sp on s.id = sp.schedule_id " +
            "inner join pet p on p.id = sp.pet_id " +
            "inner join customer c on c.id = p.customer_id " +
            "where c.id = :cusId", nativeQuery = true)
    List<Schedule> getScheduleByCustomerId(@Param("cusId") Long cusId);
}
