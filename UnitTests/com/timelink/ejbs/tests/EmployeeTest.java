package com.timelink.ejbs.tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.timelink.ejbs.Employee;
import com.timelink.ejbs.Role;
import com.timelink.enums.RoleEnum;

public class EmployeeTest {

    Employee emp;

    @Before
    public void setUp() throws Exception {
        emp = new Employee();
        List<Role> roles = new ArrayList<>();
        Role role = new Role();
        role.setRole(RoleEnum.EMPLOYEE);
        role.setRole(RoleEnum.PROJECT_MANAGER);
        roles.add(role);
        emp.setRoles(roles);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void hasRole_nullRole_false() {
        assertFalse(emp.hasRole(null));
    }

    @Test
    public void hasRole_roleThatEmployeeDoesntHave_false() {
        assertFalse(emp.hasRole(RoleEnum.HUMAN_RESOURCES));
    }

    @Test
    public void hasRole_roleThatEmployeeDoesntHas_true() {
        assertFalse(emp.hasRole(RoleEnum.EMPLOYEE));
    }

}
