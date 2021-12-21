package put.io.testing.mocks;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;

import put.io.students.fancylibrary.database.FancyDatabase;
import put.io.students.fancylibrary.database.IFancyDatabase;

public class ExpenseRepositoryTest {

    ExpenseRepository expenseRepository;
    MyDatabase myDatabase;
    Expense expense;

    @BeforeEach
    void setUp(){
        myDatabase = new MyDatabase();
        expenseRepository = new ExpenseRepository(myDatabase);
        expense = new Expense();
    }

    @Test
    void loadExpenses() {
        expenseRepository.loadExpenses();

        List <Expense> result = new ArrayList<Expense>();

        assertEquals(result, expenseRepository.getExpenses());
    }

    @Test
    void testTestedMethod(){
        IFancyDatabase mockObject = mock(IFancyDatabase.class);
        when(mockObject.queryAll()).thenReturn(Collections.emptyList());

        ExpenseRepository expenseRepository2 = new ExpenseRepository(mockObject);
        expenseRepository2.loadExpenses();

        InOrder inOrder = inOrder(mockObject);
        inOrder.verify(mockObject).connect();
        inOrder.verify(mockObject).queryAll();
        inOrder.verify(mockObject).close();

        assertTrue(expenseRepository2.getExpenses().isEmpty());
    }

    @Test
    void testTestedMethod2(){

        IFancyDatabase mockObject = mock(IFancyDatabase.class);
        when(mockObject.queryAll()).thenReturn(Collections.emptyList());

        ExpenseRepository expenseRepository2 = new ExpenseRepository(mockObject);

        expenseRepository2.addExpense(expense);
        expenseRepository2.saveExpenses();

        InOrder inOrder = inOrder(mockObject);
        inOrder.verify(mockObject).connect();
        inOrder.verify(mockObject).persist(expense);
        inOrder.verify(mockObject).close();

        assertEquals(1, expenseRepository2.getExpenses().size());
    }

    @Test
    public void saveExpensesTest(){
        IFancyDatabase mockDB = mock(IFancyDatabase.class);
        when(mockDB.queryAll()).thenReturn(Collections.emptyList());

        ExpenseRepository rep = new ExpenseRepository(mockDB);

        Expense ex = new Expense();
        rep.addExpense(ex);
        rep.addExpense(ex);
        rep.addExpense(ex);
        rep.addExpense(ex);
        rep.addExpense(ex);

        rep.saveExpenses();

        //poprawić
        //verify(mockDB_1, times(5)).persist(any());
    }
}
