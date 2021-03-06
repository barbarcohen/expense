package cz.rudypokorny.expense.service.impl;


import cz.rudypokorny.expense.entity.Result;
import cz.rudypokorny.expense.entity.Rules;
import cz.rudypokorny.expense.model.Account;
import cz.rudypokorny.expense.model.AccountCreator;
import cz.rudypokorny.expense.model.Expense;
import cz.rudypokorny.expense.model.User;
import cz.rudypokorny.expense.service.IExpenseService;
import cz.rudypokorny.util.SecurityContextTestUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.ConstraintViolationException;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
@javax.transaction.Transactional
public class ExpenseServiceIntegrationTest {


    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private IExpenseService expenseService;

    private Account expectedAccount;
    private User currentUser;
    private Account anotherAccount;

    @Before
    public void setup() {
        expectedAccount = Account.named("testaccount");
        currentUser = SecurityContextTestUtil.addToSecurityContext(User.create("testuser", "password")).account(expectedAccount);

        testEntityManager.persist(currentUser);
        expectedAccount.activeFor(currentUser);
        testEntityManager.persistAndFlush(expectedAccount);

        testEntityManager.flush();
        testEntityManager.clear();
    }


    @Test
    public void spend() throws Exception {
        String expectedMessage = "message";
        Expense expectedExpense = Expense.newExpense(33.0).noted(expectedMessage);

        Result<Expense> result = expenseService.spend(expectedExpense);

        assertFalse(result.isCompromised());

        testEntityManager.flush();
        testEntityManager.clear();

        Account freshAccount = testEntityManager.find(Account.class, expectedAccount.getId());
        assertEquals(1, freshAccount.getExpenses().size());

        Expense actualExpense = freshAccount.getExpenses().get(0);

        assertEquals(expectedExpense.getId(), actualExpense.getId());
        assertEquals(expectedExpense.getAmount(), actualExpense.getAmount());
        assertEquals(expectedExpense.getWhen(), actualExpense.getWhen());
        assertEquals(expectedMessage, actualExpense.getNote());

        assertEquals(expectedAccount.getId(), actualExpense.getAccount().getId());

        assertNotNull(expectedExpense.getUpdatedDate());
        assertNotNull(expectedExpense.getCreatedDate());

    }

    @Test
    public void spendForNotPersistedAccount() {
        Expense expense = Expense.newExpense(33.0).by(Account.named("abc"));

        Result<Expense> result = expenseService.spend(expense);
        assertTrue(result.isCompromised());
    }

    @Test
    public void spendForDifferentAccount() {
        //crating different user and account
        User differentUser = SecurityContextTestUtil.addToSecurityContext(User.create("different", "different"));
        testEntityManager.persist(differentUser);
        anotherAccount = Account.named("abc");
        testEntityManager.persistAndFlush(anotherAccount);
        //switching back to current
        SecurityContextTestUtil.addToSecurityContext(currentUser);

        Expense expense = Expense.newExpense(33.0).by(anotherAccount);

        Result<Expense> result = expenseService.spend(expense);
        assertTrue(result.isCompromised());
    }

    @Test
    public void spendForUserWithoutAccount() {
        //crating different user and account
        User differentUser = SecurityContextTestUtil.addToSecurityContext(User.create("different", "different"));
        testEntityManager.persist(differentUser);

        Expense expense = Expense.newExpense(33.0);

        Result<Expense> result = expenseService.spend(expense);
        assertTrue(result.isCompromised());
    }

    @Test
    public void testNewAccountNull() {
        Result<Account> result = expenseService.newAccount(null);
        assertTrue(result.isCompromised());

        assertTrue(result.rules().getExceptions().isEmpty());
        assertEquals(1, result.rules().getErrors().size());
        assertEquals(Rules.Messages.ENTITY_IS_NULL, result.rules().getErrors().get(0));
    }

    @Test
    public void testNewAccountEmpty() {
        Result<Account> result = expenseService.newAccount(AccountCreator.createEmpty());
        assertTrue(result.isCompromised());

        assertTrue(result.rules().getErrors().isEmpty());
        assertEquals(1, result.rules().getExceptions().size());
        assertEquals(ConstraintViolationException.class, result.rules().getExceptions().get(0).getClass());
    }

    @Test
    public void testNewAccount() {
        Account expectedAccount = Account.named("account sdkfj dsf");
        Result<Account> result = expenseService.newAccount(expectedAccount);

        assertTrue(result.isOk());

        assertTrue(result.rules().getErrors().isEmpty());
        assertTrue(result.rules().getExceptions().isEmpty());

        Account account = testEntityManager.find(Account.class, result.get().getId());
        assertEquals(expectedAccount.getName(), account.getName());
        assertNotNull(account.getCreatedDate());
        assertNotNull(account.getUpdatedDate());
    }

    @Test
    public void testNewAccountDuplicitName() {
        Account expectedAccount = Account.named("account sdkfj dsf");
        Result<Account> result = expenseService.newAccount(expectedAccount);
        testEntityManager.clear();

        assertTrue(result.isOk());

        assertTrue(result.rules().getErrors().isEmpty());
        assertTrue(result.rules().getExceptions().isEmpty());

        Account account = testEntityManager.find(Account.class, result.get().getId());
        assertEquals(expectedAccount.getName(), account.getName());


        Result<Account> secondAttempt = expenseService.newAccount(Account.named(expectedAccount.getName()));
        assertTrue(secondAttempt.isCompromised());
        assertTrue(secondAttempt.rules().getErrors().isEmpty());
        assertEquals(1, secondAttempt.rules().getExceptions().size());
        assertEquals(DataIntegrityViolationException.class, secondAttempt.rules().getExceptions().get(0).getClass());
    }

}