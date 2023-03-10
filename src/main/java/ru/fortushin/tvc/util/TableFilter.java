package ru.fortushin.tvc.util;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.scene.control.TextField;
import org.springframework.stereotype.Component;
import ru.fortushin.tvc.model.Employee;

@Component
public class TableFilter {
    public FilteredList<Employee> getFilteredList(ObservableList<Employee> observableList, TextField searchField){

        FilteredList<Employee> filteredList = new FilteredList<>(observableList, p -> true);
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredList.setPredicate(employeeSearchModel -> {
                if(newValue.isEmpty() || newValue.isBlank()){
                    return true;
                }

                String searchValue = newValue.toLowerCase();

                if(employeeSearchModel.getName().toLowerCase().contains(searchValue)){
                    return true;
                }else if(employeeSearchModel.getBuilding().toLowerCase().contains(searchValue)){
                    return true;
                }else if(employeeSearchModel.getEmail().toLowerCase().contains(searchValue)){
                    return true;
                }else if(employeeSearchModel.getJobTitle().toLowerCase().contains(searchValue)){
                    return true;
                }else if(employeeSearchModel.getRoom().toLowerCase().contains(searchValue)){
                    return true;
                }else if(employeeSearchModel.getPlace().toLowerCase().contains(searchValue)){
                    return true;
                }else if(employeeSearchModel.getDepartment().toLowerCase().contains(searchValue)){
                    return true;
                }else {
                    return false;
                }
            });
        });
        return filteredList;
    }

}
