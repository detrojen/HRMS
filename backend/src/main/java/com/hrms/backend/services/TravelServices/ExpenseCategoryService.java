package com.hrms.backend.services.TravelServices;

import com.hrms.backend.dtos.responseDtos.travel.ExpenseCategoryDto;
import com.hrms.backend.entities.TravelEntities.ExpenseCategory;
import com.hrms.backend.repositories.TravelRepositories.ExpenseCategoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExpenseCategoryService {
    private final ExpenseCategoryRepository expenseCategoryRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public ExpenseCategoryService(
            ExpenseCategoryRepository expenseCategoryRepository
            ,ModelMapper modelMapper
    ){
        this.expenseCategoryRepository = expenseCategoryRepository;
        this.modelMapper = modelMapper;
    }

    public List<ExpenseCategoryDto> getExpenseCategories(){
        List<ExpenseCategory> categories = expenseCategoryRepository.findAll();
        return  categories.stream().map(category -> modelMapper.map(category, ExpenseCategoryDto.class)).toList();
    }
}
