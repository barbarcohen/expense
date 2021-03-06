package cz.rudypokorny.expense.validator;

import cz.rudypokorny.expense.entity.Rules;
import cz.rudypokorny.expense.model.Account;
import org.springframework.stereotype.Component;

@Component("accountValidator")
public class AccountValidator implements IValidator<Account> {

    @Override
    public Rules validateNew(Account entity) {
        //TODO only BS rules
        Rules rules = new Rules();
        if(entity == null){
            return rules.broken(Rules.Messages.ENTITY_IS_NULL);
        }

        //TODO implement
        return rules;
    }

    @Override
    public Rules validateUpdate(Account entity) {
        Rules rules = new Rules();
        return rules;
    }

    @Override
    public Rules validateDelete(Account entity) {
        Rules rules = new Rules();
        return rules;
    }
}
