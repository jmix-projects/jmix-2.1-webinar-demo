package com.company.onboarding.view.department;

import com.company.onboarding.entity.Department;

import com.company.onboarding.view.main.MainView;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.router.Route;
import io.jmix.core.DataManager;
import io.jmix.flowui.kit.component.button.JmixButton;
import io.jmix.flowui.view.*;
import net.datafaker.Faker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.Set;

@Route(value = "departments", layout = MainView.class)
@ViewController("Department.list")
@ViewDescriptor("department-list-view.xml")
@LookupComponent("departmentsDataGrid")
@DialogMode(width = "50em", height = "37.5em")
public class DepartmentListView extends StandardListView<Department> {

    private static final Logger log = LoggerFactory.getLogger(DepartmentListView.class);

    @Autowired
    private DataManager dataManager;

    @Subscribe(id = "generateFakeData", subject = "clickListener")
    public void onGenerateFakeDataClick(final ClickEvent<JmixButton> event) {

        Set<String> departments = new HashSet<>();
        Faker faker = new Faker();
        for (int i = 0; i < 300; i++) {
            departments.add(faker.commerce().department());
        }

        for (String departmentName : departments) {
            Department department = dataManager.create(Department.class);
            department.setName(departmentName);
            dataManager.save(department);
            log.info("Saved " + departmentName);
        }
    }
}