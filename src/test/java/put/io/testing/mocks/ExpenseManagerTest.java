package put.io.testing.mocks;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.net.ConnectException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import put.io.students.fancylibrary.service.FancyService;

public class ExpenseManagerTest {
    @Test
    public void calculateTotalTest(){
        ExpenseRepository mockRep = mock(ExpenseRepository.class);
        FancyService mockFS = mock(FancyService.class);
        List<Expense> expenses = new ArrayList<Expense>();

        Expense expense = new Expense();

        expense.setAmount(1);

        expenses.add(expense);
        expenses.add(expense);
        expenses.add(expense);

        when(mockRep.getExpenses()).thenReturn(expenses);

        ExpenseManager em = new ExpenseManager(mockRep, mockFS);

        assertEquals(3, em.calculateTotal());
    }

    @Test
    public void calculateTotalForCategoryTest(){
        ExpenseRepository mockRep = mock(ExpenseRepository.class);
        FancyService mockFS = mock (FancyService.class);

        List<Expense> expensesCar = new ArrayList<Expense>();
        List<Expense> expensesHome = new ArrayList<Expense>();

        Expense car_1 = new Expense();
        car_1.setCategory("Car");
        car_1.setAmount(10);

        Expense car_2 = new Expense();
        car_2.setCategory("Car");
        car_2.setAmount(2);

        Expense car_3 = new Expense();
        car_3.setCategory("Car");
        car_3.setAmount(6);

        expensesCar.add(car_1);
        expensesCar.add(car_2);
        expensesCar.add(car_3);

        Expense home_1 = new Expense();
        home_1.setCategory("Home");
        home_1.setAmount(100);

        Expense home_2 = new Expense();
        home_2.setCategory("Home");
        home_2.setAmount(500);

        Expense home_3 = new Expense();
        home_3.setCategory("Home");
        home_3.setAmount(800);

        expensesHome.add(home_1);
        expensesHome.add(home_2);
        expensesHome.add(home_3);

        when(mockRep.getExpensesByCategory(anyString())).thenReturn(Collections.emptyList());
        when(mockRep.getExpensesByCategory("Car")).thenReturn(expensesCar);
        when(mockRep.getExpensesByCategory("Home")).thenReturn(expensesHome);

        ExpenseManager manager = new ExpenseManager(mockRep, mockFS);
        assertEquals(1400, manager.calculateTotalForCategory("Home"));
        assertEquals(18, manager.calculateTotalForCategory("Car"));
        assertEquals(0, manager.calculateTotalForCategory("Food"));
        assertEquals(0, manager.calculateTotalForCategory("Sport"));
    }

    @Test
    public void calculateTotalInDollarsTest() throws ConnectException{
        ExpenseRepository mockRep = mock(ExpenseRepository.class);
        FancyService mockFS = mock(FancyService.class);;

        List<Expense> expenses = new ArrayList<Expense>();

        Expense expense_1 = new Expense();
        Expense expense_2 = new Expense();
        Expense expense_3 = new Expense();

        expense_1.setAmount(1);
        expense_2.setAmount(2);
        expense_3.setAmount(3);

        expenses.add(expense_1);
        expenses.add(expense_2);
        expenses.add(expense_3);

        when(mockRep.getExpenses()).thenReturn(expenses);
        when(mockFS.convert(anyDouble(), eq("PLN"), eq("USD"))).thenAnswer(new Answer<Double>() {
            @Override
            public Double answer(InvocationOnMock invocation) throws Throwable {
                Object[] args = invocation.getArguments();
                return new Double(args[0].toString())/4;
            }
        });

        ExpenseManager manager = new ExpenseManager(mockRep, mockFS);
        assertEquals(1, manager.calculateTotalInDollars());
    }
}
