package com.example.employeeservice.service;

import com.example.employeeservice.Exception.ResourceNotFoundException;
import com.example.employeeservice.dto.APIResponseDto;
import com.example.employeeservice.dto.DepartmentDto;
import com.example.employeeservice.dto.EmployeeDto;
import com.example.employeeservice.entity.Employee;
import com.example.employeeservice.mapper.EmployeeMapper;
import com.example.employeeservice.repository.EmployeeRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@AllArgsConstructor
public class EmployeeServiceImpl implements EmployeeService{

    private EmployeeRepository employeeRepository;

   // private RestTemplate restTemplate;

  //  private WebClient webClient;

    private APIClient apiClient;

    @Override
    public EmployeeDto saveEmployee(EmployeeDto employeeDto) {

        Employee employee = EmployeeMapper.mapToEmployee(employeeDto);

        Employee saveDEmployee = employeeRepository.save(employee);

        EmployeeDto savedEmployeeDto = EmployeeMapper.mapToEmployeeDto(saveDEmployee);

        return savedEmployeeDto;
    }

    @Override
    public APIResponseDto getEmployeeById(Long employeeId) {
        Employee employee =  employeeRepository.findById(employeeId)
                .orElseThrow(()->new ResourceNotFoundException
                        (String.format("Employee not found for EmployeeID %s",employeeId)));
/*
       ResponseEntity<DepartmentDto> departmentResponse =
               restTemplate.getForEntity(
                       "http://localhost:8080/api/departments/"+employee.getDepartmentCode(), DepartmentDto.class);
        DepartmentDto departmentDto = departmentResponse.getBody();
        */

        /*DepartmentDto departmentDto = webClient.get().uri("http://localhost:8080/api/departments/"+employee.getDepartmentCode())
                .retrieve().bodyToMono(DepartmentDto.class).block();*/

       DepartmentDto departmentDto =  apiClient.getDepartment(employee.getDepartmentCode());
        APIResponseDto apiResponseDto = new APIResponseDto();
        apiResponseDto.setEmployeeDto(EmployeeMapper.mapToEmployeeDto(employee));
        apiResponseDto.setDepartmentDto(departmentDto);
        return apiResponseDto;
    }
}
